package com.picpay.checkout.webhook;

import com.google.gson.annotations.SerializedName;

/**
 * Evento de webhook PicPay (notificação de mudança de status de charge ou resultado 3DS).
 * <p>
 * Use {@link WebhookParser#parse(String, String)} ou
 * {@link PicPayCheckoutClient#parseWebhookEvent(String, String)} para produzir este objeto
 * a partir do body (String ou InputStream) e do header {@code event-type}.
 *
 * @see <a href="https://developers-business.picpay.com/checkout/docs/webhook">Webhook PicPay</a>
 */
public final class WebhookEvent {

    @SerializedName("type")
    private String type;

    @SerializedName("eventDate")
    private String eventDate;

    @SerializedName("merchantDocument")
    private String merchantDocument;

    @SerializedName("id")
    private String id;

    @SerializedName("merchantCode")
    private String merchantCode;

    private WebhookEventType eventTypeHeader;
    private ChargeEventData chargeData;
    private Challenge3dsData challenge3dsData;

    // Gson usa setter ou campo; mantemos setters para o parser preencher
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getMerchantDocument() {
        return merchantDocument;
    }

    public void setMerchantDocument(String merchantDocument) {
        this.merchantDocument = merchantDocument;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    /**
     * Tipo do evento conforme o header {@code event-type} da requisição.
     */
    public WebhookEventType getEventTypeHeader() {
        return eventTypeHeader;
    }

    public void setEventTypeHeader(WebhookEventType eventTypeHeader) {
        this.eventTypeHeader = eventTypeHeader;
    }

    /**
     * Dados da charge (Wallet, Credit, PIX). Não nulo quando o evento é de atualização de transação.
     */
    public ChargeEventData getChargeData() {
        return chargeData;
    }

    public void setChargeData(ChargeEventData chargeData) {
        this.chargeData = chargeData;
    }

    /**
     * Dados do desafio 3DS. Não nulo quando o evento é de resultado 3DS.
     */
    public Challenge3dsData getChallenge3dsData() {
        return challenge3dsData;
    }

    public void setChallenge3dsData(Challenge3dsData challenge3dsData) {
        this.challenge3dsData = challenge3dsData;
    }

    /**
     * Indica se este evento é de atualização de transação (PAYMENT).
     */
    public boolean isTransactionUpdate() {
        return chargeData != null;
    }

    /**
     * Indica se este evento é de resultado do desafio 3DS.
     */
    public boolean isChallenge3ds() {
        return challenge3dsData != null;
    }
}
