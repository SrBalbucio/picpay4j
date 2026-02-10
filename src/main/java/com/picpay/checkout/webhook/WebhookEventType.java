package com.picpay.checkout.webhook;

/**
 * Tipo do evento de webhook PicPay, correspondente ao header {@code event-type} da requisição.
 *
 * @see <a href="https://developers-business.picpay.com/checkout/docs/webhook">Webhook PicPay</a>
 */
public enum WebhookEventType {

    /**
     * Atualização de transação (Wallet, Credit, PIX).
     * Corresponde ao header {@code event-type: TransactionUpdateMessage}.
     */
    TRANSACTION_UPDATE_MESSAGE,

    /**
     * Resultado do desafio 3DS.
     * Corresponde ao header {@code event-type: Challenge3dsUpdateMessage}.
     */
    CHALLENGE_3DS_UPDATE_MESSAGE;

    /**
     * Interpreta o valor do header {@code event-type} (case-insensitive).
     *
     * @param headerValue valor do header (ex.: "TransactionUpdateMessage")
     * @return tipo do evento ou null se não reconhecido
     */
    public static WebhookEventType fromHeader(String headerValue) {
        if (headerValue == null || headerValue.isBlank()) {
            return null;
        }
        return switch (headerValue.trim()) {
            case "TransactionUpdateMessage" -> TRANSACTION_UPDATE_MESSAGE;
            case "Challenge3dsUpdateMessage" -> CHALLENGE_3DS_UPDATE_MESSAGE;
            default -> null;
        };
    }
}
