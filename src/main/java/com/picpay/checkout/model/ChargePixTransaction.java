package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

/**
 * Transação PIX dentro da cobrança.
 */
public final class ChargePixTransaction {

    @SerializedName("amount")
    private final Integer amount;

    @SerializedName("pix")
    private final PixTransactionOptions pix;

    private ChargePixTransaction(Builder b) {
        this.amount = b.amount;
        this.pix = b.pix;
    }

    public Integer getAmount() {
        return amount;
    }

    public PixTransactionOptions getPix() {
        return pix;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer amount;
        private PixTransactionOptions pix;

        /**
         * Valor do pagamento em centavos (obrigatório, >= 1).
         */
        public Builder amount(int amountCents) {
            if (amountCents < 1) {
                throw new IllegalArgumentException("amount deve ser >= 1 centavo");
            }
            this.amount = amountCents;
            return this;
        }

        /**
         * Valor do pagamento em BigDecimal (convertido para centavos).
         */
        public Builder amount(java.math.BigDecimal amount) {
            if (amount == null || amount.compareTo(java.math.BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("amount deve ser maior que zero");
            }
            return amount(amount.multiply(new java.math.BigDecimal("100")).intValue());
        }

        /**
         * Opções da transação PIX (expiração do QRCode).
         */
        public Builder pix(PixTransactionOptions pix) {
            this.pix = pix;
            return this;
        }

        /**
         * Define a expiração do QRCode PIX em segundos (padrão: 600s = 10 minutos).
         */
        public Builder pixExpiration(int expirationSeconds) {
            if (expirationSeconds < 1) {
                throw new IllegalArgumentException("expiration deve ser >= 1 segundo");
            }
            this.pix = PixTransactionOptions.builder()
                    .expiration(expirationSeconds)
                    .build();
            return this;
        }

        public ChargePixTransaction build() {
            if (amount == null || amount < 1) {
                throw new IllegalArgumentException("amount é obrigatório e deve ser >= 1 centavo");
            }
            return new ChargePixTransaction(this);
        }
    }

    /**
     * Opções da transação PIX.
     */
    public static final class PixTransactionOptions {
        @SerializedName("expiration")
        private final Integer expiration;

        private PixTransactionOptions(Builder b) {
            this.expiration = b.expiration;
        }

        public Integer getExpiration() {
            return expiration;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            private Integer expiration;

            /**
             * Expiração do QRCode PIX em segundos (padrão: 600s = 10 minutos).
             */
            public Builder expiration(int expirationSeconds) {
                if (expirationSeconds < 1) {
                    throw new IllegalArgumentException("expiration deve ser >= 1 segundo");
                }
                this.expiration = expirationSeconds;
                return this;
            }

            public PixTransactionOptions build() {
                return new PixTransactionOptions(this);
            }
        }
    }
}
