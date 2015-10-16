package com.womply.server;

import com.womply.common.health.HealthCheckable;
import com.womply.common.info.ISummarizable;
import com.womply.common.info.NoSummarySummarizable;
import com.womply.common.routing.RouteRegistry;

import com.womply.db.MerchantDAO;
import com.womply.db.MerchantDAOImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;

/**
 * Manages the bindings for the merchant-search Service
 */
public class MerchantServiceModule extends AbstractModule {

    @Override
    protected void configure() {

        // bind the RouteRegistries
        Multibinder<RouteRegistry> routes = Multibinder.newSetBinder(binder(), RouteRegistry.class);
        routes.addBinding().to(MerchantServiceRouter.class);

        // bind the HealthCheckable components
        Multibinder<HealthCheckable> healthCheckComponents = Multibinder.newSetBinder(binder(), HealthCheckable.class);
        healthCheckComponents.addBinding().to(MerchantDAOImpl.class);

        // bind DAO interfaces to implementations
        bind(MerchantDAO.class).to(MerchantDAOImpl.class).in(Scopes.SINGLETON);
        // bind in a default ISummarizable, replace this with your own ISummarizable
        // instance to enable the dashboard
        bind(ISummarizable.class).toInstance(new NoSummarySummarizable());
    }

}
