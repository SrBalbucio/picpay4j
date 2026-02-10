package com.picpay.checkout.webhook;

import com.google.gson.annotations.SerializedName;

/**
 * Submerchant no webhook Credit.
 */
public final class WebhookSubMerchant {

    @SerializedName("id")
    private String id;

    @SerializedName("mcc")
    private String mcc;

    @SerializedName("document")
    private String document;

    @SerializedName("address")
    private WebhookAddress address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public WebhookAddress getAddress() {
        return address;
    }

    public void setAddress(WebhookAddress address) {
        this.address = address;
    }
}
