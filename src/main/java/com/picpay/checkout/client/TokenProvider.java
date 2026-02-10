package com.picpay.checkout.client;

import com.picpay.checkout.PicPayCheckoutConfig;
import com.picpay.checkout.auth.TokenRequest;
import com.picpay.checkout.auth.TokenResponse;
import com.picpay.checkout.exception.PicPayApiException;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Obtém e cacheia o token OAuth 2.0 (client_credentials).
 * Tokens expiram em 5 minutos; renova antes de expirar.
 */
public final class TokenProvider {

    private static final String TOKEN_PATH = "/oauth2/token";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final long REFRESH_BEFORE_SECONDS = 60;

    private final PicPayCheckoutConfig config;
    private final OkHttpClient httpClient;
    private final Gson gson;

    private volatile String cachedToken;
    private volatile long tokenExpiresAtMs;
    private final ReentrantLock lock = new ReentrantLock();

    public TokenProvider(PicPayCheckoutConfig config, OkHttpClient httpClient, Gson gson) {
        this.config = config;
        this.httpClient = httpClient;
        this.gson = gson;
    }

    /**
     * Retorna um token válido (do cache ou novo).
     */
    public String getAccessToken() {
        if (!config.isUseOAuth()) {
            throw new IllegalStateException("TokenProvider só deve ser usado com OAuth (clientId/clientSecret)");
        }
        if (isTokenValid()) {
            return cachedToken;
        }
        lock.lock();
        try {
            if (isTokenValid()) {
                return cachedToken;
            }
            fetchNewToken();
            return cachedToken;
        } finally {
            lock.unlock();
        }
    }

    private boolean isTokenValid() {
        return cachedToken != null && System.currentTimeMillis() < (tokenExpiresAtMs - REFRESH_BEFORE_SECONDS * 1000);
    }

    private void fetchNewToken() throws PicPayApiException {
        String url = config.getBaseUrl() + TOKEN_PATH;
        TokenRequest req = new TokenRequest(config.getClientId(), config.getClientSecret());
        RequestBody body = RequestBody.create(gson.toJson(req), JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            String responseStr = responseBody != null ? responseBody.string() : "";
            if (!response.isSuccessful()) {
                throw new PicPayApiException(
                        "Falha ao obter token: " + response.code(),
                        response.code(),
                        responseStr
                );
            }
            TokenResponse tokenResponse = gson.fromJson(responseStr, TokenResponse.class);
            Objects.requireNonNull(tokenResponse.getAccessToken(), "access_token não retornado");
            this.cachedToken = tokenResponse.getAccessToken();
            int expiresIn = tokenResponse.getExpiresIn() > 0 ? tokenResponse.getExpiresIn() : 300;
            this.tokenExpiresAtMs = System.currentTimeMillis() + expiresIn * 1000L;
        } catch (IOException e) {
            throw new PicPayApiException("Erro ao solicitar token OAuth", e);
        }
    }
}
