package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

/**
 * Dados da Wallet PicPay na resposta de cancelamento.
 */
public final class RefundWalletInfo {

    @SerializedName("qrCode")
    private String qrCode;

    @SerializedName("qrCodeBase64")
    private String qrCodeBase64;

    @SerializedName("expiresAt")
    private String expiresAt;

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

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }
}
