package com.picpay.checkout.model;

/**
 * Sessão de checkout para uso no <b>Checkout Lightbox</b>.
 * <p>
 * Contém os dados retornados pela API para que o frontend exiba o pagamento
 * dentro de um iframe (em vez de redirecionar). O backend usa a mesma chamada
 * de criação de pagamento; esta classe representa o contrato para integração
 * Lightbox.
 * <p>
 * No frontend, use {@link #getIframePaymentUrl()} como {@code src} do iframe
 * ou abra em um modal/overlay.
 *
 * @see PicPayCheckoutClient#createPaymentForLightbox(CreatePaymentRequest)
 * @see <a href="https://developers-business.picpay.com/checkout/docs/default-checkout-and-lightbox-checkout">Checkout Lightbox</a>
 */
public final class LightboxCheckoutSession {

    private final String iframePaymentUrl;
    private final String referenceId;
    private final String picpayTransactionId;
    private final String expiresAt;
    private final String qrcode;
    private final String qrcodeBase64;

    public LightboxCheckoutSession(CreatePaymentResponse response) {
        this.iframePaymentUrl = response.getPaymentUrl();
        this.referenceId = response.getReferenceId();
        this.picpayTransactionId = response.getPicpayTransactionId();
        this.expiresAt = response.getExpiresAt();
        this.qrcode = response.getQrcode();
        this.qrcodeBase64 = response.getQrcodeBase64();
    }

    /**
     * URL do checkout para exibir no iframe (Lightbox).
     * No frontend: use como {@code src} do iframe ou abra em overlay/modal.
     */
    public String getIframePaymentUrl() {
        return iframePaymentUrl;
    }

    /**
     * Identificador do pedido no sistema do lojista.
     */
    public String getReferenceId() {
        return referenceId;
    }

    /**
     * ID da transação no PicPay (para consultas e webhook).
     */
    public String getPicpayTransactionId() {
        return picpayTransactionId;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public String getQrcode() {
        return qrcode;
    }

    public String getQrcodeBase64() {
        return qrcodeBase64;
    }
}
