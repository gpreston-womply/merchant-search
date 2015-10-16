package com.womply.controllers;

import com.womply.common.controllers.BaseController;
import com.womply.common.filter.RequestParamParser;
import com.womply.models.Merchant;
import com.womply.models.MerchantSearchQuery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import io.vertx.ext.web.RoutingContext;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import java.io.IOException;
import java.util.List;
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
        if (queryBody.isPresent() && !queryBody.get().toLowerCase().trim().equals("null")) {
            query.setBody(queryBody.get());
        }

        if (queryLocation.isPresent() && !queryLocation.get().toLowerCase().trim().equals("null")) {
            query.setBody(queryLocation.get());
        }

        String urlString = "http://10.0.1.39:8983/solr/merchant_locations/";
        SolrClient solr = new HttpSolrClient(urlString);

        SolrQuery solrQuery = new SolrQuery();
            log.info("SOLR QUERY: '" + query.getAsSolrQuery()+"'");
            solrQuery.setQuery(query.getAsSolrQuery());

        try {
            ObjectMapper mapper = new ObjectMapper();
            QueryResponse solrResponse = solr.query(solrQuery);
//            List<Merchant> merchants = solrResponseToPojos(solrResponse);
            routingContext.response().putHeader("Access-Control-Allow-Credentials","true");
            routingContext.response().putHeader("Access-Control-Allow-Origin","http://local.womply.com:9999");
            routingContext.response().end(mapper.writeValueAsString(solrResponse.getResults()));
        } catch (SolrServerException e) {
            routingContext.fail(e);
        } catch (IOException e) {
            routingContext.fail(e);
        }
    }

    protected List<Merchant> solrResponseToPojos(QueryResponse response) {
        if (response == null) {
            return Lists.newArrayList();
        }
        for (SolrDocument document : response.getResults()) {
            Merchant merchant = new Merchant();
            merchant.setCity(document.getFieldValue("city").toString());
            merchant.setPartnerName(document.getFieldValue("partner_name").toString());
        }
        return null;
    }
}
