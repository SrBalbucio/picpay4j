package com.picpay.checkout.webhook;

import com.google.gson.annotations.SerializedName;

/**
 * Dados da transação Credit no webhook.
 */
public final class WebhookCreditInfo {

    @SerializedName("cardholderName")
    private String cardholderName;

    @SerializedName("cardholderDocument")
    private String cardholderDocument;

    @SerializedName("expirationMonth")
    private Integer expirationMonth;

    @SerializedName("expirationYear")
    private Integer expirationYear;

    @SerializedName("brand")
    private String brand;

    @SerializedName("nsu")
    private Long nsu;

    @SerializedName("authorizationCode")
    private String authorizationCode;

    @SerializedName("authorizationResponseCode")
    private String authorizationResponseCode;

    @SerializedName("reasonCode")
    private Integer reasonCode;

    @SerializedName("reasonMessage")
    private String reasonMessage;

    @SerializedName("acquirerName")
    private String acquirerName;

    @SerializedName("installmentType")
    private String installmentType;

    @SerializedName("subMerchant")
    private WebhookSubMerchant subMerchant;

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getCardholderDocument() {
        return cardholderDocument;
    }

    public void setCardholderDocument(String cardholderDocument) {
        this.cardholderDocument = cardholderDocument;
    }

    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public Integer getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getNsu() {
        return nsu;
    }

    public void setNsu(Long nsu) {
        this.nsu = nsu;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getAuthorizationResponseCode() {
        return authorizationResponseCode;
    }

    public void setAuthorizationResponseCode(String authorizationResponseCode) {
        this.authorizationResponseCode = authorizationResponseCode;
    }

    public Integer getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(Integer reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonMessage() {
        return reasonMessage;
    }

    public void setReasonMessage(String reasonMessage) {
        this.reasonMessage = reasonMessage;
    }

    public String getAcquirerName() {
        return acquirerName;
    }

    public void setAcquirerName(String acquirerName) {
        this.acquirerName = acquirerName;
    }

    public String getInstallmentType() {
        return installmentType;
    }

    public void setInstallmentType(String installmentType) {
        this.installmentType = installmentType;
    }

    public WebhookSubMerchant getSubMerchant() {
        return subMerchant;
    }

    public void setSubMerchant(WebhookSubMerchant subMerchant) {
        this.subMerchant = subMerchant;
    }
}
