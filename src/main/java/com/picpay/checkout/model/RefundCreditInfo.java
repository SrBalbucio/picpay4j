package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

/**
 * Dados do cartão de crédito na resposta de cancelamento.
 */
public final class RefundCreditInfo {

    @SerializedName("nsu")
    private String nsu;

    @SerializedName("cardNumber")
    private String cardNumber;

    @SerializedName("authorizationCode")
    private String authorizationCode;

    @SerializedName("authorizationResponseCode")
    private String authorizationResponseCode;

    @SerializedName("brand")
    private String brand;

    @SerializedName("cardholderName")
    private String cardholderName;

    @SerializedName("cardholderDocument")
    private String cardholderDocument;

    @SerializedName("expirationMonth")
    private Integer expirationMonth;

    @SerializedName("expirationYear")
    private Integer expirationYear;

    @SerializedName("installmentNumber")
    private Integer installmentNumber;

    @SerializedName("installmentType")
    private String installmentType;

    @SerializedName("reasonCode")
    private Integer reasonCode;

    @SerializedName("reasonMessage")
    private String reasonMessage;

    public String getNsu() {
        return nsu;
    }

    public void setNsu(String nsu) {
        this.nsu = nsu;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

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

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public String getInstallmentType() {
        return installmentType;
    }

    public void setInstallmentType(String installmentType) {
        this.installmentType = installmentType;
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
}
