package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Requisição de criação de pagamento (Checkout Padrão - API E-commerce).
 * <p>
 * O lojista envia esta requisição do backend; o PicPay retorna a URL para redirecionar o consumidor.
 *
 * @see <a href="https://developers-business.picpay.com/checkout/docs/default-checkout-and-lightbox-checkout">Checkout Padrão</a>
 */
public final class CreatePaymentRequest {

    @SerializedName("referenceId")
    private final String referenceId;

    @SerializedName("callbackUrl")
    private final String callbackUrl;

    @SerializedName("returnUrl")
    private final String returnUrl;

    @SerializedName("value")
    private final BigDecimal value;

    @SerializedName("expiresAt")
    private final String expiresAt;

    @SerializedName("buyer")
    private final Buyer buyer;

    @SerializedName("additionalInfo")
    private final List<AdditionalInfo> additionalInfo;

    private CreatePaymentRequest(Builder b) {
        this.referenceId = b.referenceId;
        this.callbackUrl = b.callbackUrl;
        this.returnUrl = b.returnUrl;
        this.value = b.value;
        this.expiresAt = b.expiresAt;
        this.buyer = b.buyer;
        this.additionalInfo = b.additionalInfo;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public List<AdditionalInfo> getAdditionalInfo() {
        return additionalInfo;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String referenceId;
        private String callbackUrl;
        private String returnUrl;
        private BigDecimal value;
        private String expiresAt;
        private Buyer buyer;
        private List<AdditionalInfo> additionalInfo;

        public Builder referenceId(String referenceId) {
            this.referenceId = referenceId;
            return this;
        }

        public Builder callbackUrl(String callbackUrl) {
            this.callbackUrl = callbackUrl;
            return this;
        }

        public Builder returnUrl(String returnUrl) {
            this.returnUrl = returnUrl;
            return this;
        }

        public Builder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public Builder expiresAt(OffsetDateTime expiresAt) {
            this.expiresAt = expiresAt != null ? expiresAt.toString() : null;
            return this;
        }

        public Builder expiresAt(String expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Builder buyer(Buyer buyer) {
            this.buyer = buyer;
            return this;
        }

        public Builder additionalInfo(List<AdditionalInfo> additionalInfo) {
            this.additionalInfo = additionalInfo;
            return this;
        }

        public CreatePaymentRequest build() {
            if (referenceId == null || referenceId.isBlank()) {
                throw new IllegalArgumentException("referenceId é obrigatório");
            }
            if (callbackUrl == null || callbackUrl.isBlank()) {
                throw new IllegalArgumentException("callbackUrl é obrigatório");
            }
            if (returnUrl == null || returnUrl.isBlank()) {
                throw new IllegalArgumentException("returnUrl é obrigatório");
            }
            if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("value é obrigatório e deve ser maior que zero");
            }
            if (buyer == null) {
                throw new IllegalArgumentException("buyer é obrigatório");
            }
            return new CreatePaymentRequest(this);
        }
    }

    /**
     * Informação adicional opcional (ex.: itens do pedido).
     */
    public static final class AdditionalInfo {
        @SerializedName("key")
        private final String key;

        @SerializedName("value")
        private final String value;

        public AdditionalInfo(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
