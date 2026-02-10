package com.picpay.checkout.webhook;

import com.google.gson.annotations.SerializedName;

/**
 * Dados do pagador PIX no webhook.
 */
public final class WebhookPixPayer {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("documentNumber")
    private String documentNumber;

    @SerializedName("type")
    private String type;

    @SerializedName("bankIspb")
    private String bankIspb;

    @SerializedName("branchNumber")
    private String branchNumber;

    @SerializedName("bankName")
    private String bankName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBankIspb() {
        return bankIspb;
    }

    public void setBankIspb(String bankIspb) {
        this.bankIspb = bankIspb;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
