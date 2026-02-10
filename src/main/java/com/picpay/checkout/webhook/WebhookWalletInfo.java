package com.picpay.checkout.webhook;

import com.google.gson.annotations.SerializedName;

/**
 * Dados da transação Wallet no webhook.
 */
public final class WebhookWalletInfo {

    @SerializedName("expiresAt")
    private String expiresAt;

    @SerializedName("qrCode")
    private String qrCode;

    @SerializedName("qrCodeBase64")
    private String qrCodeBase64;

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

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
}
