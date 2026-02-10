package com.picpay.checkout.webhook;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Dados do evento de atualização de charge (Wallet, Credit, PIX).
 * Corresponde ao objeto {@code data} quando {@code event-type: TransactionUpdateMessage}.
 */
public final class ChargeEventData {

    @SerializedName("status")
    private String status;

    @SerializedName("amount")
    private Integer amount;

    @SerializedName("originalAmount")
    private Integer originalAmount;

    @SerializedName("refundedAmount")
    private Integer refundedAmount;

    @SerializedName("customer")
    private WebhookCustomer customer;

    @SerializedName("merchantChargeId")
    private String merchantChargeId;

    @SerializedName("smartCheckoutId")
    private String smartCheckoutId;

    @SerializedName("paymentSource")
    private String paymentSource;

    @SerializedName("lateCapture")
    private Boolean lateCapture;

    @SerializedName("transactions")
    private List<WebhookTransaction> transactions;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Integer originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Integer getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(Integer refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    public WebhookCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(WebhookCustomer customer) {
        this.customer = customer;
    }

    public String getMerchantChargeId() {
        return merchantChargeId;
    }

    public void setMerchantChargeId(String merchantChargeId) {
        this.merchantChargeId = merchantChargeId;
    }

    public String getSmartCheckoutId() {
        return smartCheckoutId;
    }

    public void setSmartCheckoutId(String smartCheckoutId) {
        this.smartCheckoutId = smartCheckoutId;
    }

    public String getPaymentSource() {
        return paymentSource;
    }

    public void setPaymentSource(String paymentSource) {
        this.paymentSource = paymentSource;
    }

    public Boolean getLateCapture() {
        return lateCapture;
    }

    public void setLateCapture(Boolean lateCapture) {
        this.lateCapture = lateCapture;
    }

    public List<WebhookTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<WebhookTransaction> transactions) {
        this.transactions = transactions;
    }
}
