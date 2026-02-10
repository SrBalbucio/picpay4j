package com.picpay.checkout.webhook;

import com.google.gson.annotations.SerializedName;

/**
 * Dados do evento de resultado do desafio 3DS.
 * Corresponde ao objeto {@code data} quando {@code event-type: Challenge3dsUpdateMessage}.
 */
public final class Challenge3dsData {

    @SerializedName("status")
    private String status;

    @SerializedName("paresStatus")
    private String paresStatus;

    @SerializedName("eciRaw")
    private String eciRaw;

    @SerializedName("merchantChargeId")
    private String merchantChargeId;

    @SerializedName("chargeId")
    private String chargeId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParesStatus() {
        return paresStatus;
    }

    public void setParesStatus(String paresStatus) {
        this.paresStatus = paresStatus;
    }

    public String getEciRaw() {
        return eciRaw;
    }

    public void setEciRaw(String eciRaw) {
        this.eciRaw = eciRaw;
    }

    public String getMerchantChargeId() {
        return merchantChargeId;
    }

    public void setMerchantChargeId(String merchantChargeId) {
        this.merchantChargeId = merchantChargeId;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }
}
