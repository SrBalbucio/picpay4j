package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

/**
 * Telefone do cliente para cobrança PIX.
 */
public final class ChargePhone {

    @SerializedName("countryCode")
    private final String countryCode;

    @SerializedName("areaCode")
    private final String areaCode;

    @SerializedName("number")
    private final String number;

    @SerializedName("type")
    private final String type;

    private ChargePhone(Builder b) {
        this.countryCode = b.countryCode;
        this.areaCode = b.areaCode;
        this.number = b.number;
        this.type = b.type;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String countryCode;
        private String areaCode;
        private String number;
        private String type;

        public Builder countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder areaCode(String areaCode) {
            this.areaCode = areaCode;
            return this;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder typeResidential() {
            return type("RESIDENTIAL");
        }

        public Builder typeCommercial() {
            return type("COMMERCIAL");
        }

        public Builder typeTemporary() {
            return type("TEMPORARY");
        }

        public Builder typeMobile() {
            return type("MOBILE");
        }

        public ChargePhone build() {
            if (countryCode == null || countryCode.isBlank()) {
                throw new IllegalArgumentException("countryCode é obrigatório");
            }
            if (areaCode == null || areaCode.isBlank()) {
                throw new IllegalArgumentException("areaCode é obrigatório");
            }
            if (number == null || number.isBlank()) {
                throw new IllegalArgumentException("number é obrigatório");
            }
            if (type == null || type.isBlank()) {
                throw new IllegalArgumentException("type é obrigatório (RESIDENTIAL, COMMERCIAL, TEMPORARY, MOBILE)");
            }
            return new ChargePhone(this);
        }
    }
}
