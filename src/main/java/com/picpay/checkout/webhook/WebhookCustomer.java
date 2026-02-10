package com.picpay.checkout.webhook;

import com.google.gson.annotations.SerializedName;

/**
 * Dados do cliente/c comprador no payload do webhook.
 */
public final class WebhookCustomer {

    @SerializedName("document")
    private String document;

    @SerializedName("documentType")
    private String documentType;

    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private String name;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
