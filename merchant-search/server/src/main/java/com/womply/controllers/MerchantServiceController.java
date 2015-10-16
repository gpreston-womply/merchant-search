package com.womply.controllers;

import com.womply.common.controllers.BaseController;
import com.womply.db.MerchantDAO;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.vertx.ext.web.RoutingContext;

/**
 * The controller for merchant-search endpoints.
 */
@Singleton
public class MerchantServiceController extends BaseController {

    @Inject
    private MerchantDAO serviceDAO;

    /**
     * Gets a Merchant object for a given id
     */
    public void getMerchantById(RoutingContext routingContext) {
        resolveById(routingContext, serviceDAO::getById);
    }
}
