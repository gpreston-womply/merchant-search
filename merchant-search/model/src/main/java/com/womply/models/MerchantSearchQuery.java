package com.womply.models;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * POJO representing a merchant search query from the frontend
 */
@Data
public class MerchantSearchQuery {
    String body;
    String location;

    Map<String, String> filters = Maps.newHashMap();

    /**
     *
     */
    public SolrQuery getAsSolrQuery() {
        // main query
        SolrQuery solrQuery = new SolrQuery();
        String queryString = "";
        if (body != null && !body.trim().isEmpty()) {
            queryString += "+body:(" + toSolrParamString(body) + ")";
        }

        if (location != null && !location.trim().isEmpty()) {
            queryString += " +location:(" + toSolrParamString(location) + ")";
        }

        solrQuery.setQuery(queryString);

        // filters
        for (String facet : filters.keySet()) {
            String value = filters.get(facet);
            if (value != null && !value.toLowerCase().trim().equals("null") && !value.trim().isEmpty()) {
                solrQuery.addFilterQuery("+" + facet + ":" + value);
            }
        }
        solrQuery.addFacetField("is_claimed");
        solrQuery.addFacetField("category");
        solrQuery.addFacetField("product");
        solrQuery.addFacetField("yelp_is_claimed");
        solrQuery.addFacetField("processor_name");
        solrQuery.setParam("facet.limit", "5");
        return solrQuery;
    }

    public void addFacet(String field, String value) {
        filters.put(field, value);
    }

    /**
     * This isn't totally infallible, but should work unless someone is actively trying to search really terribly
     * malformed queries.
     */
    protected String toSolrParamString(String input) {
        List<String> phrases = Lists.newArrayList();
        String inputRemainder = input;

        int quoteCount = StringUtils.countMatches(inputRemainder, "\"");
        // if there's an odd number of quotes, add an extra one to the end.
        if (quoteCount != 0 && quoteCount % 2 != 0) {
            inputRemainder += "\"";
        }

        int openQuote = inputRemainder.indexOf("\"");
        // extract all quoted phrases
        while (openQuote != -1) {
            int closedQuote = inputRemainder.indexOf("\"", openQuote + 1);
            phrases.add(inputRemainder.substring(openQuote, closedQuote + 1));
            inputRemainder = inputRemainder.substring(0, openQuote) + " " + inputRemainder
                    .substring(closedQuote + 1);
            openQuote = inputRemainder.indexOf("\"");
        }

        // extract all non-quoted phrases (delimited by ' ')
        final List<String> remainingPhrases = Lists.newArrayList(inputRemainder.split(" "));
        final List<String> validPhrases = Lists.newArrayList();
        phrases.addAll(remainingPhrases);

        // strip out any whitespace that was added as a phrase
        for (String phrase : phrases) {
            if (!phrase.trim().isEmpty()) {
                validPhrases.add(phrase);
            }
        }
        phrases = validPhrases;

        // build the query string
        String phrasesString = "";
        for (String phrase : phrases) {
            phrase += "*";
            phrasesString += phrase + " AND ";
        }
        phrasesString = phrasesString.substring(0, phrasesString.length() - " AND ".length()).trim();
        return phrasesString;
    }

    /**
     * testing
     */
    public static void main(String[] args) throws IOException, SolrServerException {
        MerchantSearchQuery query = new MerchantSearchQuery();
        query.setBody("Blaguard");
        query.setLocation("Washington");

        String urlString = "http://10.0.1.39:8983/solr/merchant_locations/";
        SolrClient solr = new HttpSolrClient(urlString);

        SolrQuery solrQuery = query.getAsSolrQuery();
        QueryResponse response = solr.query(solrQuery);
        System.out.println(response);

    }
}
