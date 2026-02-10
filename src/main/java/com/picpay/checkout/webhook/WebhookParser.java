package com.picpay.checkout.webhook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Parser de eventos de webhook PicPay.
 * <p>
 * Não depende de nenhum framework HTTP: recebe o body (String ou InputStream) e o valor
 * do header {@code event-type} e retorna um {@link WebhookEvent} tipado. Use em qualquer
 * ambiente (Servlet, Spring, JAX-RS, etc.).
 * <p>
 * O PicPay envia o token no header {@code Authorization}; valide-o no seu endpoint
 * comparando com o token configurado no Painel Lojista.
 *
 * @see <a href="https://developers-business.picpay.com/checkout/docs/webhook">Webhook PicPay</a>
 */
public final class WebhookParser {

    private static final Gson GSON = new GsonBuilder().create();

    private WebhookParser() {
    }

    /**
     * Produz o objeto do evento a partir do body (JSON) e do header {@code event-type}.
     *
     * @param body          corpo da requisição POST do webhook (JSON)
     * @param eventTypeHeader valor do header {@code event-type} (ex.: "TransactionUpdateMessage" ou "Challenge3dsUpdateMessage"); pode ser null para inferir pelo campo "type" do body
     * @return evento parseado; {@link WebhookEvent#getChargeData()} ou {@link WebhookEvent#getChallenge3dsData()} preenchido conforme o tipo
     * @throws JsonParseException se o body não for um JSON válido
     * @throws IllegalArgumentException se body for nulo ou vazio
     */
    public static WebhookEvent parse(String body, String eventTypeHeader) {
        if (body == null || body.isBlank()) {
            throw new IllegalArgumentException("body não pode ser nulo ou vazio");
        }
        WebhookEventType eventType = WebhookEventType.fromHeader(eventTypeHeader);
        JsonObject root = GSON.fromJson(body, JsonObject.class);
        if (root == null) {
            throw new JsonParseException("Body não é um JSON válido");
        }
        return buildEvent(root, eventType);
    }

    /**
     * Produz o objeto do evento a partir do body (InputStream) e do header {@code event-type}.
     * O stream não é fechado por este método.
     *
     * @param body          corpo da requisição POST do webhook (JSON)
     * @param eventTypeHeader valor do header {@code event-type}
     * @return evento parseado
     * @throws IOException       em caso de erro de leitura do stream
     * @throws JsonParseException se o conteúdo não for um JSON válido
     */
    public static WebhookEvent parseFromStream(InputStream body, String eventTypeHeader) throws IOException {
        if (body == null) {
            throw new IllegalArgumentException("body (InputStream) não pode ser nulo");
        }
        String bodyStr = new String(body.readAllBytes(), StandardCharsets.UTF_8);
        return parse(bodyStr, eventTypeHeader);
    }

    private static WebhookEvent buildEvent(JsonObject root, WebhookEventType eventType) {
        WebhookEvent event = new WebhookEvent();
        event.setType(getString(root, "type"));
        event.setEventDate(getString(root, "eventDate"));
        event.setMerchantDocument(getString(root, "merchantDocument"));
        event.setId(getString(root, "id"));
        event.setMerchantCode(getString(root, "merchantCode"));
        event.setEventTypeHeader(eventType != null ? eventType : inferEventType(event.getType()));

        if (root.has("data") && root.get("data").isJsonObject()) {
            String type = event.getType();
            boolean isCharge = eventType == WebhookEventType.TRANSACTION_UPDATE_MESSAGE || "PAYMENT".equals(type);
            boolean is3ds = eventType == WebhookEventType.CHALLENGE_3DS_UPDATE_MESSAGE || "THREE_DS_CHALLENGE".equals(type);
            if (isCharge && !is3ds) {
                ChargeEventData chargeData = GSON.fromJson(root.getAsJsonObject("data"), ChargeEventData.class);
                event.setChargeData(chargeData);
            } else if (is3ds) {
                Challenge3dsData challenge3dsData = GSON.fromJson(root.getAsJsonObject("data"), Challenge3dsData.class);
                event.setChallenge3dsData(challenge3dsData);
            }
        }

        return event;
    }

    private static WebhookEventType inferEventType(String bodyType) {
        if ("THREE_DS_CHALLENGE".equals(bodyType)) {
            return WebhookEventType.CHALLENGE_3DS_UPDATE_MESSAGE;
        }
        if ("PAYMENT".equals(bodyType)) {
            return WebhookEventType.TRANSACTION_UPDATE_MESSAGE;
        }
        return null;
    }

    private static String getString(JsonObject obj, String key) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsString() : null;
    }
}
