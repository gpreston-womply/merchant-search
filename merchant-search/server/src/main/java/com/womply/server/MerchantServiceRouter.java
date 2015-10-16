package com.womply.server;

import com.womply.common.routing.RouteDefinition;
import com.womply.common.routing.RouteRegistry;

import com.womply.controllers.MerchantServiceController;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

/**
 * Defines the routes for the merchant-search Service
 */
@Singleton
public class MerchantServiceRouter implements RouteRegistry {

    private static Logger LOGGER = LogManager.getLogger();

    @Inject
    private MerchantServiceController serviceController;

    @Override
    public Set<RouteDefinition> getRouteDescriptors() {
        LOGGER.info("Registering route: /v1/merchant-search/");
        RouteDefinition merchantSearchRoute =
            RouteDefinition.get("/v1/merchant-search/", serviceController::getMerchants);
        return Sets.newHashSet(merchantSearchRoute);
    }
}
