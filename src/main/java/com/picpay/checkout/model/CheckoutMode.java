package com.picpay.checkout.model;

/**
 * Modo de exibição do checkout PicPay.
 * <p>
 * O backend utiliza a mesma API para ambos; a diferença está no frontend:
 * <ul>
 *   <li><b>DEFAULT</b> (Checkout Padrão): redirecionar o usuário para a URL de pagamento.</li>
 *   <li><b>LIGHTBOX</b>: exibir a URL de pagamento dentro de um iframe na página do lojista.</li>
 * </ul>
 *
 * @see <a href="https://developers-business.picpay.com/checkout/docs/default-checkout-and-lightbox-checkout">Checkout Padrão e Lightbox</a>
 */
public enum CheckoutMode {

    /**
     * Checkout Padrão: o consumidor é redirecionado para a página de pagamento do PicPay
     * e, após concluir, é redirecionado de volta ao site do lojista.
     */
    DEFAULT,

    /**
     * Checkout Lightbox: o pagamento ocorre em um iframe sobreposto à página do lojista,
     * mantendo o consumidor no site durante todo o processo.
     */
    LIGHTBOX
}
