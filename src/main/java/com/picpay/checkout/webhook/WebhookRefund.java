package com.picpay.checkout.webhook;

import com.google.gson.annotations.SerializedName;

/**
 * Estorno no webhook (dentro de transactions).
 */
public final class WebhookRefund {

    @SerializedName("refundId")
    private String refundId;

    @SerializedName("amount")
    private Integer amount;

    @SerializedName("createdAt")
    private String createdAt;

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
