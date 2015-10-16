package com.womply.models;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.List;

/**
 * POJO representing a merchant search query from the frontend
 */
@Data
public class MerchantSearchQuery {
    String body;
    String location;


    /**
     *
     */
    public String getAsSolrQuery() {
        String solrQuery = "";
        if (body != null && !body.trim().isEmpty()) {
            solrQuery += "body:(" + toSolrParamString(body) + ")";
        }

        if (location != null && !location.trim().isEmpty()) {
            solrQuery += " location:(" + toSolrParamString(location) + ")";
        }
        return solrQuery;
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
            phrase+="*";
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

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(query.getAsSolrQuery());
        QueryResponse response = solr.query(solrQuery);
        System.out.println(response);

    }
}
