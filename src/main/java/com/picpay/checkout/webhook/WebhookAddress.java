package com.picpay.checkout.webhook;

import com.google.gson.annotations.SerializedName;

/**
 * Endereço (ex.: subMerchant no webhook Credit).
 */
public final class WebhookAddress {

    @SerializedName("city")
    private String city;

    @SerializedName("country")
    private String country;

    @SerializedName("number")
    private String number;

    @SerializedName("street")
    private String street;

    @SerializedName("state")
    private String state;

    @SerializedName("zipCode")
    private String zipCode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
