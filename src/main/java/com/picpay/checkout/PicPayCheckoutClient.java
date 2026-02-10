package com.picpay.checkout;

import com.picpay.checkout.client.TokenProvider;
import com.picpay.checkout.exception.PicPayApiException;
import com.picpay.checkout.model.*;
import com.picpay.checkout.webhook.WebhookEvent;
import com.picpay.checkout.webhook.WebhookParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Cliente Java para o PicPay Checkout Padrão e Lightbox (backend).
 * <p>
 * Fluxo típico (Checkout Padrão):
 * <ol>
 *   <li>Configure com {@link PicPayCheckoutConfig#sellerToken(String)} (API E-commerce) ou
 *       {@link PicPayCheckoutConfig#oauth(String, String)} (API Checkout OAuth).</li>
 *   <li>Chame {@link #createPayment(CreatePaymentRequest)} a partir do seu backend.</li>
 *   <li>Redirecione o consumidor para {@link CreatePaymentResponse#getPaymentUrl()}.</li>
 *   <li>Receba a notificação de pagamento na sua callbackUrl (webhook).</li>
 * </ol>
 * <p>
 * Fluxo Lightbox: use {@link #createPaymentForLightbox(CreatePaymentRequest)} e passe
 * {@link LightboxCheckoutSession#getIframePaymentUrl()} para o frontend exibir em iframe.
 *
 * @see <a href="https://developers-business.picpay.com/checkout/docs/default-checkout-and-lightbox-checkout">Checkout Padrão e Lightbox</a>
 * @see <a href="https://developers-business.picpay.com/checkout/docs/authentication">Autenticação</a>
 */
public final class PicPayCheckoutClient {

    private static final String ECOMMERCE_PAYMENTS_PATH = "/ecommerce/public/payments";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final PicPayCheckoutConfig config;
    private final OkHttpClient httpClient;
    private final Gson gson;
    private final TokenProvider tokenProvider;

    public PicPayCheckoutClient(PicPayCheckoutConfig config) {
        this(config, new OkHttpClient.Builder().build());
    }

    public PicPayCheckoutClient(PicPayCheckoutConfig config, OkHttpClient httpClient) {
        this.config = Objects.requireNonNull(config, "config");
        this.httpClient = Objects.requireNonNull(httpClient, "httpClient");
        this.gson = new GsonBuilder().create();
        this.tokenProvider = config.isUseOAuth()
                ? new TokenProvider(config, httpClient, gson)
                : null;
    }

    /**
     * Cria um pagamento no PicPay (Checkout Padrão).
     * A requisição deve ser feita exclusivamente pelo backend do lojista.
     *
     * @param request dados do pagamento e do comprador
     * @return resposta com paymentUrl para redirecionar o consumidor e IDs da transação
     * @throws PicPayApiException se a API retornar erro
     */
    public CreatePaymentResponse createPayment(CreatePaymentRequest request) {
        return createPaymentInternal(request);
    }

    /**
     * Cria um pagamento para exibição no <b>Checkout Lightbox</b>.
     * <p>
     * O backend utiliza a mesma API do Checkout Padrão; a diferença é que o frontend
     * deve exibir a URL de pagamento em um <b>iframe</b> (overlay na página do lojista)
     * em vez de redirecionar o usuário. Use o retorno {@link LightboxCheckoutSession}
     * para enviar ao frontend a URL a ser carregada no iframe.
     *
     * @param request dados do pagamento e do comprador (mesmo do Checkout Padrão)
     * @return sessão Lightbox com {@link LightboxCheckoutSession#getIframePaymentUrl()} para o iframe
     * @throws PicPayApiException se a API retornar erro
     */
    public LightboxCheckoutSession createPaymentForLightbox(CreatePaymentRequest request) {
        CreatePaymentResponse response = createPaymentInternal(request);
        return new LightboxCheckoutSession(response);
    }

    private CreatePaymentResponse createPaymentInternal(CreatePaymentRequest request) {
        String url = config.getBaseUrl() + ECOMMERCE_PAYMENTS_PATH;
        String bodyStr = gson.toJson(request);

        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .post(RequestBody.create(bodyStr, JSON))
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json");

        if (config.isUseOAuth()) {
            reqBuilder.addHeader("Authorization", "Bearer " + tokenProvider.getAccessToken());
        } else {
            reqBuilder.addHeader("x-picpay-token", config.getSellerToken());
        }

        Request httpRequest = reqBuilder.build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            ResponseBody responseBody = response.body();
            String responseStr = responseBody != null ? responseBody.string() : "";

            if (!response.isSuccessful()) {
                ApiErrorResponse error = parseError(responseStr);
                String message = error != null && error.getMessage() != null
                        ? error.getMessage()
                        : ("HTTP " + response.code());
                throw new PicPayApiException(message, response.code(), responseStr,
                        error != null ? error.getBusinessCode() : null);
            }

            CreatePaymentResponse result = gson.fromJson(responseStr, CreatePaymentResponse.class);
            if (result == null) {
                throw new PicPayApiException("Resposta vazia da API", response.code(), responseStr);
            }
            return result;
        } catch (IOException e) {
            throw new PicPayApiException("Erro ao chamar API PicPay", e);
        }
    }

    private ApiErrorResponse parseError(String json) {
        try {
            return gson.fromJson(json, ApiErrorResponse.class);
        } catch (Exception ignored) {
            return null;
        }
    }

    // ---------- Webhook ----------

    /**
     * Produz o objeto do evento de webhook a partir do body e do header {@code event-type}.
     * Não impõe nenhum framework HTTP: use com Servlet, Spring, JAX-RS, etc.
     * <p>
     * Valide o token do webhook comparando o header {@code Authorization} com o token
     * configurado no Painel Lojista (Ajustes &gt; Meu checkout &gt; URL de notificação).
     *
     * @param body              corpo da requisição POST (JSON)
     * @param eventTypeHeader   valor do header {@code event-type} (ex.: "TransactionUpdateMessage", "Challenge3dsUpdateMessage")
     * @return evento parseado com {@link WebhookEvent#getChargeData()} ou {@link WebhookEvent#getChallenge3dsData()} preenchido
     * @see com.picpay.checkout.webhook.WebhookParser#parse(String, String)
     * @see <a href="https://developers-business.picpay.com/checkout/docs/webhook">Webhook PicPay</a>
     */
    public WebhookEvent parseWebhookEvent(String body, String eventTypeHeader) {
        return WebhookParser.parse(body, eventTypeHeader);
    }

    /**
     * Produz o objeto do evento de webhook a partir do body (InputStream) e do header {@code event-type}.
     *
     * @param body              corpo da requisição POST (JSON); não é fechado por este método
     * @param eventTypeHeader   valor do header {@code event-type}
     * @return evento parseado
     * @throws IOException em caso de erro de leitura do stream
     */
    public WebhookEvent parseWebhookEvent(InputStream body, String eventTypeHeader) throws IOException {
        return WebhookParser.parseFromStream(body, eventTypeHeader);
    }
}
