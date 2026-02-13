package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

/**
 * Requisição de criação de cobrança PIX com QRCode.
 * <p>
 * Permite gerar um QR Code PIX para que o consumidor realize o pagamento via leitura
 * do código ou pela funcionalidade "Pix Copia e Cola". O pagamento pode ser realizado
 * uma única vez, respeitando o prazo de expiração configurado.
 *
 * @see <a href="https://developers-business.picpay.com/checkout/docs/api/charge-pix">Cobrança PIX</a>
 * @see <a href="https://developers-business.picpay.com/checkout/docs/features/pix">PIX</a>
 */
public final class ChargePixRequest {

    @SerializedName("paymentSource")
    private final String paymentSource;

    @SerializedName("smartCheckoutId")
    private final String smartCheckoutId;

    @SerializedName("merchantChargeId")
    private final String merchantChargeId;

    @SerializedName("customer")
    private final ChargeCustomer customer;

    @SerializedName("phone")
    private final ChargePhone phone;

    @SerializedName("deviceInformation")
    private final DeviceInformation deviceInformation;

    @SerializedName("transactions")
    private final List<ChargePixTransaction> transactions;

    private ChargePixRequest(Builder b) {
        this.paymentSource = b.paymentSource;
        this.smartCheckoutId = b.smartCheckoutId;
        this.merchantChargeId = b.merchantChargeId;
        this.customer = b.customer;
        this.phone = b.phone;
        this.deviceInformation = b.deviceInformation;
        this.transactions = b.transactions;
    }

    public String getPaymentSource() {
        return paymentSource;
    }

    public String getSmartCheckoutId() {
        return smartCheckoutId;
    }

    public String getMerchantChargeId() {
        return merchantChargeId;
    }

    public ChargeCustomer getCustomer() {
        return customer;
    }

    public ChargePhone getPhone() {
        return phone;
    }

    public DeviceInformation getDeviceInformation() {
        return deviceInformation;
    }

    public List<ChargePixTransaction> getTransactions() {
        return transactions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String paymentSource;
        private String smartCheckoutId;
        private String merchantChargeId;
        private ChargeCustomer customer;
        private ChargePhone phone;
        private DeviceInformation deviceInformation;
        private List<ChargePixTransaction> transactions;

        /**
         * Define a origem do pagamento: CHECKOUT (checkout padrão/lightbox) ou GATEWAY (API direta).
         */
        public Builder paymentSource(String paymentSource) {
            this.paymentSource = paymentSource;
            return this;
        }

        public Builder paymentSourceCheckout() {
            return paymentSource("CHECKOUT");
        }

        public Builder paymentSourceGateway() {
            return paymentSource("GATEWAY");
        }

        /**
         * SmartCheckoutID definido pelo sistema (obrigatório para checkout padrão ou lightBox).
         */
        public Builder smartCheckoutId(UUID smartCheckoutId) {
            this.smartCheckoutId = smartCheckoutId != null ? smartCheckoutId.toString() : null;
            return this;
        }

        public Builder smartCheckoutId(String smartCheckoutId) {
            this.smartCheckoutId = smartCheckoutId;
            return this;
        }

        /**
         * Identificador externo único da cobrança (6-36 caracteres, alfanumérico com hífens).
         * Deve ser único para cada cobrança. Se não informado, será gerado internamente.
         */
        public Builder merchantChargeId(String merchantChargeId) {
            this.merchantChargeId = merchantChargeId;
            return this;
        }

        public Builder customer(ChargeCustomer customer) {
            this.customer = customer;
            return this;
        }

        public Builder phone(ChargePhone phone) {
            this.phone = phone;
            return this;
        }

        public Builder deviceInformation(DeviceInformation deviceInformation) {
            this.deviceInformation = deviceInformation;
            return this;
        }

        public Builder transactions(List<ChargePixTransaction> transactions) {
            this.transactions = transactions;
            return this;
        }

        public ChargePixRequest build() {
            if (paymentSource == null || paymentSource.isBlank()) {
                throw new IllegalArgumentException("paymentSource é obrigatório (CHECKOUT ou GATEWAY)");
            }
            if ("CHECKOUT".equals(paymentSource) && (smartCheckoutId == null || smartCheckoutId.isBlank())) {
                throw new IllegalArgumentException("smartCheckoutId é obrigatório quando paymentSource é CHECKOUT");
            }
            if (customer == null) {
                throw new IllegalArgumentException("customer é obrigatório");
            }
            if (phone == null) {
                throw new IllegalArgumentException("phone é obrigatório");
            }
            if (transactions == null || transactions.isEmpty()) {
                throw new IllegalArgumentException("transactions é obrigatório e deve conter pelo menos 1 item");
            }
            return new ChargePixRequest(this);
        }
    }
}
