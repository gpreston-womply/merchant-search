package com.womply.client;

import com.womply.client.BaseServiceClient;
import com.womply.models.Merchant;

import java.util.Optional;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * A java client endpoint for the merchant-search service.
 */
public class MerchantServiceClient extends BaseServiceClient {

    private final WebTarget target;

    /**
     * Creates a MerchantServiceClient with the given uri, using the supplied timeouts.
     * example uri: "http://localhost:44338/api/v1"
     */
    public MerchantServiceClient(final String uri, final int readTimeout, final int connectTimeout) {
        super(uri, readTimeout, connectTimeout);

        target = client.target(uri).path("merchant-searchs/{id}");

    }

    /**
     * Finds a Merchant with the given id.
     */
    public Optional<Merchant> getMerchantById(final int id) {
        Response response =  target.resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON_TYPE).get();
        return hydrateModel(response, Merchant.class);
    }
}
