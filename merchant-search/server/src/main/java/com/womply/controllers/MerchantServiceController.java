package com.womply.controllers;

import com.womply.common.controllers.BaseController;
import com.womply.common.filter.RequestParamParser;
import com.womply.models.MerchantSearchFacet;
import com.womply.models.MerchantSearchQuery;
import com.womply.models.MerchantSearchResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import io.vertx.ext.web.RoutingContext;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The controller for merchant-search endpoints.
 */
@Singleton
public class MerchantServiceController extends BaseController {

    /**
     * Gets a Merchant object for a given id
     */
    public void getMerchants(RoutingContext routingContext) {
        RequestParamParser requestParamParser = new RequestParamParser(routingContext);
        Optional<String> queryBody = requestParamParser.parseStringParam("body");
        Optional<String> queryLocation = requestParamParser.parseStringParam("location");
        MerchantSearchQuery query = new MerchantSearchQuery();
        for (String filter : routingContext.request().params().names()) {
            if (!filter.equals("body") && !filter.equals("location")) {

                Optional<String> filterBody = requestParamParser.parseStringParam(filter);
                if (filterBody.isPresent()) {
                    query.addFacet(filter, filterBody.get());
                }
            }
        }
        if (queryBody.isPresent() && !queryBody.get().toLowerCase().trim().equals("null")) {
            query.setBody(queryBody.get());
        }

        if (queryLocation.isPresent() && !queryLocation.get().toLowerCase().trim().equals("null")) {
            query.setLocation(queryLocation.get());
        }

        String urlString = "http://10.0.1.39:8983/solr/merchant_locations/";
        SolrClient solr = new HttpSolrClient(urlString);

        SolrQuery solrQuery = query.getAsSolrQuery();
        log.info("SOLR QUERY: '" + solrQuery);

        try {
            ObjectMapper mapper = new ObjectMapper();
            QueryResponse solrResponse = solr.query(solrQuery);
            routingContext.response().putHeader("Access-Control-Allow-Credentials", "true");
            routingContext.response().putHeader("Access-Control-Allow-Origin", "http://local.womply.com:9999");
            MerchantSearchResult results = new MerchantSearchResult();
            results.setResults(solrResponse.getResults());
            List<MerchantSearchFacet> facets = Lists.newArrayList();
            for (FacetField facetField : solrResponse.getFacetFields()) {
                Map<String, String> facetMap = Maps.newLinkedHashMap();
                MerchantSearchFacet facet = new MerchantSearchFacet();
                facet.setName(facetField.getName());
                for (FacetField.Count count : facetField.getValues()) {
                    facetMap.put(count.getName(), String.valueOf(count.getCount()));
                }
                facet.setValues(facetMap);
                facets.add(facet);
            }
            results.setFacets(facets);
            routingContext.response().end(mapper.writeValueAsString(results));
        } catch (SolrServerException e) {
            routingContext.fail(e);
        } catch (IOException e) {
            routingContext.fail(e);
        }
    }


    public void getWordCloud(RoutingContext routingContext) {
        RequestParamParser requestParamParser = new RequestParamParser(routingContext);
        Long merchantLocationId = requestParamParser.parseLongParam("merchantLocationid").get();
        String urlString = "http://10.0.1.39:8983/solr/reviews/";
        SolrClient solr = new HttpSolrClient(urlString);
        SolrQuery wordCloudQuery = new SolrQuery("*:*");
        wordCloudQuery.setRows(0);
        wordCloudQuery.addFilterQuery("+merchant_location_id:" + String.valueOf(merchantLocationId));
        wordCloudQuery.addFacetField("text");
        wordCloudQuery.setFacetLimit(30);
        try {
            log.info("SOLR QUERY: " + wordCloudQuery);
            QueryResponse solrResponse = solr.query(wordCloudQuery);
            Map<String, Long> wordCloud = Maps.newLinkedHashMap();
            FacetField facetField = solrResponse.getFacetFields().get(0);
            for (FacetField.Count count : facetField.getValues()) {
                wordCloud.put(count.getName(), count.getCount());
            }
            ObjectMapper mapper = new ObjectMapper();
            routingContext.response().end(mapper.writeValueAsString(wordCloud));
        } catch (SolrServerException e) {
            routingContext.fail(e);
        } catch (IOException e) {
            routingContext.fail(e);
        }
    }
}
