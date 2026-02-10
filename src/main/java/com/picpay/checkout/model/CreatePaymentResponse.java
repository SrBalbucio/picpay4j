package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

/**
 * Resposta da criação de pagamento (Checkout Padrão ou Lightbox).
 * Contém a URL de pagamento e os IDs da transação.
 * <p>
 * Para <b>Checkout Padrão</b>: redirecione o consumidor para {@link #getPaymentUrl()}.
 * Para <b>Checkout Lightbox</b>: use a mesma URL como {@code src} de um iframe na sua página.
 */
public final class CreatePaymentResponse {

    @SerializedName("referenceId")
    private String referenceId;

    @SerializedName("paymentUrl")
    private String paymentUrl;

    @SerializedName("expiresAt")
    private String expiresAt;

    @SerializedName("picpayTransactionId")
    private String picpayTransactionId;

    @SerializedName("qrcode")
    private String qrcode;

    @SerializedName("qrcodeBase64")
    private String qrcodeBase64;

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * URL do checkout PicPay.
     * <ul>
     *   <li>Checkout Padrão: redirecione o usuário para esta URL.</li>
     *   <li>Checkout Lightbox: use como {@code src} do iframe no frontend.</li>
     * </ul>
     */
    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getPicpayTransactionId() {
        return picpayTransactionId;
    }

    public void setPicpayTransactionId(String picpayTransactionId) {
        this.picpayTransactionId = picpayTransactionId;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrcodeBase64() {
        return qrcodeBase64;
    }

    public void setQrcodeBase64(String qrcodeBase64) {
        this.qrcodeBase64 = qrcodeBase64;
    }
}
