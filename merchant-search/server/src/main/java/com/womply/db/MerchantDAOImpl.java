package com.womply.db;

import com.womply.common.health.HealthCheckable;
import com.womply.common.health.HealthReport;

import com.womply.models.Merchant;

import java.util.Optional;

/**
 * A DataAccessObject for reading (and eventually writing) to the merchant-search table
 */
public class MerchantDAOImpl implements MerchantDAO, HealthCheckable {

    /**
     * Finds and returns a Merchant with the given id.
     */
    public Optional<Merchant> getById(Integer id) {
        return Optional.empty();
    }

    public HealthReport getHealthL1() {
        return new HealthReport(1, "MerchantDAO", true, "ok", "L1 check doesn\'t confirm DB connection");
    }

}
