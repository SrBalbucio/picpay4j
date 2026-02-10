package com.picpay.checkout.webhook;

import com.google.gson.annotations.SerializedName;

/**
 * Dados da transação PIX no webhook.
 */
public final class WebhookPixInfo {

    @SerializedName("qrCode")
    private String qrCode;

    @SerializedName("qrCodeBase64")
    private String qrCodeBase64;

    @SerializedName("endToEndId")
    private String endToEndId;

    @SerializedName("payer")
    private WebhookPixPayer payer;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCodeBase64() {
        return qrCodeBase64;
    }

    public void setQrCodeBase64(String qrCodeBase64) {
        this.qrCodeBase64 = qrCodeBase64;
    }

    public String getEndToEndId() {
        return endToEndId;
    }

    public void setEndToEndId(String endToEndId) {
        this.endToEndId = endToEndId;
    }

    public WebhookPixPayer getPayer() {
        return payer;
    }

    public void setPayer(WebhookPixPayer payer) {
        this.payer = payer;
    }
}
