package com.picpay.checkout;

/**
 * Configuração do cliente PicPay Checkout.
 * <p>
 * Para a API de Checkout (OAuth 2.0), use {@code clientId} e {@code clientSecret}.
 * Para a API E-commerce legada (token único), use {@code sellerToken}.
 *
 * @see <a href="https://developers-business.picpay.com/checkout/docs/authentication">Autenticação PicPay</a>
 */
public final class PicPayCheckoutConfig {

    private final String baseUrl;
    private final String clientId;
    private final String clientSecret;
    private final String sellerToken;
    private final boolean useOAuth;

    private PicPayCheckoutConfig(Builder builder) {
        this.baseUrl = builder.baseUrl != null ? builder.baseUrl : "https://checkout-api.picpay.com";
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.sellerToken = builder.sellerToken;
        this.useOAuth = builder.useOAuth;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getSellerToken() {
        return sellerToken;
    }

    public boolean isUseOAuth() {
        return useOAuth;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Configuração usando OAuth 2.0 (API de Checkout - recomendado).
     */
    public static Builder oauth(String clientId, String clientSecret) {
        return new Builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .useOAuth(true)
                .baseUrl("https://checkout-api.picpay.com");
    }

    /**
     * Configuração usando token do lojista (API E-commerce - Checkout Padrão).
     * Cria pagamentos em https://appws.picpay.com/ecommerce/public/payments
     * e retorna a URL para redirecionar o consumidor.
     */
    public static Builder sellerToken(String sellerToken) {
        return new Builder()
                .sellerToken(sellerToken)
                .useOAuth(false)
                .baseUrl("https://appws.picpay.com");
    }

    public static final class Builder {
        private String baseUrl;
        private String clientId;
        private String clientSecret;
        private String sellerToken;
        private boolean useOAuth = true;

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder sellerToken(String sellerToken) {
            this.sellerToken = sellerToken;
            return this;
        }

        public Builder useOAuth(boolean useOAuth) {
            this.useOAuth = useOAuth;
            return this;
        }

        public PicPayCheckoutConfig build() {
            if (useOAuth) {
                if (clientId == null || clientId.isBlank() || clientSecret == null || clientSecret.isBlank()) {
                    throw new IllegalArgumentException("clientId e clientSecret são obrigatórios para OAuth");
                }
            } else {
                if (sellerToken == null || sellerToken.isBlank()) {
                    throw new IllegalArgumentException("sellerToken é obrigatório para API E-commerce");
                }
            }
            return new PicPayCheckoutConfig(this);
        }
    }
}
