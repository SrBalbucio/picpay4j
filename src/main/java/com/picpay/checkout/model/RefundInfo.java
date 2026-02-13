package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

/**
 * Informação de um estorno individual dentro de uma transação.
 */
public final class RefundInfo {

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
