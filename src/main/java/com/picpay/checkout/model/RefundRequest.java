package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Requisição de cancelamento (refund) de uma cobrança.
 * <p>
 * Para cancelamento <b>total</b>: não informe {@code amount} ou informe o valor total da cobrança.
 * Para cancelamento <b>parcial</b>: informe o valor em centavos a ser cancelado.
 *
 * @see <a href="https://developers-business.picpay.com/checkout/docs/api/charge-refund">Cancelamento de cobrança</a>
 * @see <a href="https://developers-business.picpay.com/checkout/docs/features/total_and_parcial_cancelation">Cancelamento Parcial e Total</a>
 */
public final class RefundRequest {

    @SerializedName("amount")
    private final Integer amount;

    private RefundRequest(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    /**
     * Cria requisição de cancelamento total (sem especificar amount).
     */
    public static RefundRequest total() {
        return new RefundRequest(null);
    }

    /**
     * Cria requisição de cancelamento parcial com o valor especificado em centavos.
     *
     * @param amountCents valor a ser cancelado em centavos (deve ser >= 1)
     */
    public static RefundRequest partial(int amountCents) {
        if (amountCents < 1) {
            throw new IllegalArgumentException("amount deve ser >= 1 centavo");
        }
        return new RefundRequest(amountCents);
    }

    /**
     * Cria requisição de cancelamento parcial com o valor especificado em BigDecimal.
     * O valor é convertido para centavos (multiplicado por 100).
     *
     * @param amount valor a ser cancelado (ex.: new BigDecimal("50.00") para R$ 50,00)
     */
    public static RefundRequest partial(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount deve ser maior que zero");
        }
        int cents = amount.multiply(new BigDecimal("100")).intValue();
        return partial(cents);
    }
}
