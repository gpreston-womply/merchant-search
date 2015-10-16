package com.womply.db;


import com.womply.models.Merchant;

import java.util.Optional;

/**
 * A DataAccessObject for reading (and eventually writing) to the merchant-search table
 */
public interface MerchantDAO {

    /**
     * Finds and returns a Merchant with the given id.
     */
    Optional<Merchant> getById(Integer id);

}
