package com.womply.server;

import com.womply.common.health.HealthCheckable;
import com.womply.common.routing.RouteRegistry;

import com.google.inject.AbstractModule;
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
    }

}
