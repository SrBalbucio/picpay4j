package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Resposta do cancelamento (refund) de uma cobrança.
 * <p>
 * Contém os dados atualizados da cobrança após o cancelamento, incluindo o status
 * atualizado (ex.: REFUNDED, PARTIAL) e os valores de estorno.
 *
 * @see <a href="https://developers-business.picpay.com/checkout/docs/api/charge-refund">Cancelamento de cobrança</a>
 */
public final class RefundResponse {

    @SerializedName("merchantChargeId")
    private String merchantChargeId;

    @SerializedName("id")
    private String id;

    @SerializedName("chargeStatus")
    private String chargeStatus;

    @SerializedName("amount")
    private Integer amount;

    @SerializedName("originalAmount")
    private Integer originalAmount;

    @SerializedName("refundedAmount")
    private Integer refundedAmount;

    @SerializedName("transactions")
    private List<RefundTransaction> transactions;

    public String getMerchantChargeId() {
        return merchantChargeId;
    }

    public void setMerchantChargeId(String merchantChargeId) {
        this.merchantChargeId = merchantChargeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Status da cobrança após o cancelamento.
     * Valores possíveis: CANCELED, DENIED, ERROR, PAID, PARTIAL, PRE_AUTHORIZED, REFUNDED, CHARGEBACK
     */
    public String getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(String chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Integer originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Integer getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(Integer refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    public List<RefundTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<RefundTransaction> transactions) {
        this.transactions = transactions;
    }
}
