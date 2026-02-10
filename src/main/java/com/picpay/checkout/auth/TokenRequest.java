package com.picpay.checkout.auth;

import com.google.gson.annotations.SerializedName;

/**
 * Requisição de token OAuth 2.0 (grant type client_credentials).
 */
public final class TokenRequest {

    @SerializedName("grant_type")
    private final String grantType = "client_credentials";

    @SerializedName("client_id")
    private final String clientId;

    @SerializedName("client_secret")
    private final String clientSecret;

    public TokenRequest(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
