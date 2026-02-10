package com.picpay.checkout.webhook;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Uma transação dentro do evento de charge (Wallet, Credit ou PIX).
 */
public final class WebhookTransaction {

    @SerializedName("paymentType")
    private String paymentType;

    @SerializedName("transactionId")
    private String transactionId;

    @SerializedName("status")
    private String status;

    @SerializedName("numberOfInstallments")
    private Integer numberOfInstallments;

    @SerializedName("softDescriptor")
    private String softDescriptor;

    @SerializedName("amount")
    private Integer amount;

    @SerializedName("originalAmount")
    private Integer originalAmount;

    @SerializedName("refundedAmount")
    private Integer refundedAmount;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("wallet")
    private WebhookWalletInfo wallet;

    @SerializedName("credit")
    private WebhookCreditInfo credit;

    @SerializedName("pix")
    private WebhookPixInfo pix;

    @SerializedName("refunds")
    private List<WebhookRefund> refunds;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public String getSoftDescriptor() {
        return softDescriptor;
    }

    public void setSoftDescriptor(String softDescriptor) {
        this.softDescriptor = softDescriptor;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public WebhookWalletInfo getWallet() {
        return wallet;
    }

    public void setWallet(WebhookWalletInfo wallet) {
        this.wallet = wallet;
    }

    public WebhookCreditInfo getCredit() {
        return credit;
    }

    public void setCredit(WebhookCreditInfo credit) {
        this.credit = credit;
    }

    public WebhookPixInfo getPix() {
        return pix;
    }

    public void setPix(WebhookPixInfo pix) {
        this.pix = pix;
    }

    public List<WebhookRefund> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<WebhookRefund> refunds) {
        this.refunds = refunds;
    }
}
