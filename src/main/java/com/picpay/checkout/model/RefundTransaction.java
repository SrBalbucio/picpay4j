package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Transação dentro da resposta de cancelamento.
 */
public final class RefundTransaction {

    @SerializedName("paymentType")
    private String paymentType;

    @SerializedName("amount")
    private Integer amount;

    @SerializedName("originalAmount")
    private Integer originalAmount;

    @SerializedName("refundedAmount")
    private Integer refundedAmount;

    @SerializedName("transactionStatus")
    private String transactionStatus;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("transactionId")
    private String transactionId;

    @SerializedName("softDescriptor")
    private String softDescriptor;

    @SerializedName("mac")
    private String mac;

    @SerializedName("credit")
    private RefundCreditInfo credit;

    @SerializedName("wallet")
    private RefundWalletInfo wallet;

    @SerializedName("pix")
    private RefundPixInfo pix;

    @SerializedName("refunds")
    private List<RefundInfo> refunds;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    /**
     * Status da transação após o cancelamento.
     * Valores possíveis: CANCELED, CHARGEBACK, DENIED, ERROR, EXPIRED, PAID, PARTIALLY_REFUNDED,
     * PENDING, PRE_AUTHORIZED, REFUNDED
     */
    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSoftDescriptor() {
        return softDescriptor;
    }

    public void setSoftDescriptor(String softDescriptor) {
        this.softDescriptor = softDescriptor;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public RefundCreditInfo getCredit() {
        return credit;
    }

    public void setCredit(RefundCreditInfo credit) {
        this.credit = credit;
    }

    public RefundWalletInfo getWallet() {
        return wallet;
    }

    public void setWallet(RefundWalletInfo wallet) {
        this.wallet = wallet;
    }

    public RefundPixInfo getPix() {
        return pix;
    }

    public void setPix(RefundPixInfo pix) {
        this.pix = pix;
    }

    public List<RefundInfo> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<RefundInfo> refunds) {
        this.refunds = refunds;
    }
}
