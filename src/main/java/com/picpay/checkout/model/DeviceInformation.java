package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

/**
 * Informações do dispositivo (obrigatório para análise antifraude).
 */
public final class DeviceInformation {

    @SerializedName("ip")
    private final String ip;

    @SerializedName("id")
    private final String id;

    @SerializedName("ipCountryCode")
    private final String ipCountryCode;

    @SerializedName("ipCity")
    private final String ipCity;

    @SerializedName("ipRegion")
    private final String ipRegion;

    @SerializedName("sessionId")
    private final String sessionId;

    private DeviceInformation(Builder b) {
        this.ip = b.ip;
        this.id = b.id;
        this.ipCountryCode = b.ipCountryCode;
        this.ipCity = b.ipCity;
        this.ipRegion = b.ipRegion;
        this.sessionId = b.sessionId;
    }

    public String getIp() {
        return ip;
    }

    public String getId() {
        return id;
    }

    public String getIpCountryCode() {
        return ipCountryCode;
    }

    public String getIpCity() {
        return ipCity;
    }

    public String getIpRegion() {
        return ipRegion;
    }

    public String getSessionId() {
        return sessionId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String ip;
        private String id;
        private String ipCountryCode;
        private String ipCity;
        private String ipRegion;
        private String sessionId;

        /**
         * IP do dispositivo usado para realizar a transação (obrigatório).
         */
        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        /**
         * ID exclusivo do dispositivo do cliente.
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Código do país do endereço do cliente final (ex.: BRA).
         */
        public Builder ipCountryCode(String ipCountryCode) {
            this.ipCountryCode = ipCountryCode;
            return this;
        }

        /**
         * Cidade estimada do endereço IP.
         */
        public Builder ipCity(String ipCity) {
            this.ipCity = ipCity;
            return this;
        }

        /**
         * Região estimada do endereço IP.
         */
        public Builder ipRegion(String ipRegion) {
            this.ipRegion = ipRegion;
            return this;
        }

        /**
         * Identificador da sessão no dispositivo a partir do qual este evento foi gerado.
         */
        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public DeviceInformation build() {
            if (ip == null || ip.isBlank()) {
                throw new IllegalArgumentException("ip é obrigatório para análise antifraude");
            }
            return new DeviceInformation(this);
        }
    }
}
