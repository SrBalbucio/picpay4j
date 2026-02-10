package com.picpay.checkout.webhook;

import com.google.gson.JsonParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class WebhookParserTest {

    private static final String PAYMENT_BODY = """
        {
          "type": "PAYMENT",
          "eventDate": "2024-07-01T16:00:00-03:00",
          "merchantDocument": "123456789012",
          "id": "21b91897-a897-49bc-b16e-6256f0bf2368",
          "merchantCode": "1234",
          "data": {
            "status": "AUTHORIZED",
            "amount": 10000,
            "originalAmount": 10000,
            "refundedAmount": 0,
            "customer": {
              "document": "123456789",
              "documentType": "CPF",
              "email": "pessoa.silva@email.com",
              "name": "Pessoa Da Silva"
            },
            "merchantChargeId": "61e35ec2-caa8-4598-bed5-d43e25e14151",
            "smartCheckoutId": null,
            "paymentSource": "GATEWAY",
            "lateCapture": false,
            "transactions": [
              {
                "paymentType": "WALLET",
                "transactionId": "07bef486-8d86-41b8-804b-f68e343a1791",
                "status": "CAPTURED",
                "amount": 10000,
                "wallet": {
                  "expiresAt": "2024-07-01T16:04:00-03:00",
                  "qrCode": "xpto",
                  "qrCodeBase64": "xpto64"
                }
              }
            ]
          }
        }
        """;

    private static final String THREE_DS_BODY = """
        {
          "type": "THREE_DS_CHALLENGE",
          "eventDate": "2025-04-16T11:30:04.157929932",
          "merchantDocument": "123456789012",
          "id": "21b91897-a897-49bc-b16e-6256f0bf2368",
          "merchantCode": "72425145000145",
          "data": {
            "status": "Approved",
            "paresStatus": "Y",
            "eciRaw": "05",
            "merchantChargeId": "61e35ec2-caa8-4598-bed5-d43e25e14151",
            "chargeId": "bac0ffd7-e4cb-48c7-9f3e-a05ff7eb40a1"
          }
        }
        """;

    @Test
    @DisplayName("Parse evento PAYMENT com header TransactionUpdateMessage")
    void parsePaymentEvent() {
        WebhookEvent event = WebhookParser.parse(PAYMENT_BODY, "TransactionUpdateMessage");

        assertEquals(WebhookEventType.TRANSACTION_UPDATE_MESSAGE, event.getEventTypeHeader());
        assertEquals("PAYMENT", event.getType());
        assertEquals("21b91897-a897-49bc-b16e-6256f0bf2368", event.getId());
        assertTrue(event.isTransactionUpdate());
        assertFalse(event.isChallenge3ds());

        ChargeEventData data = event.getChargeData();
        assertNotNull(data);
        assertEquals("AUTHORIZED", data.getStatus());
        assertEquals(10000, data.getAmount());
        assertEquals("61e35ec2-caa8-4598-bed5-d43e25e14151", data.getMerchantChargeId());
        assertNotNull(data.getCustomer());
        assertEquals("Pessoa Da Silva", data.getCustomer().getName());
        assertEquals("pessoa.silva@email.com", data.getCustomer().getEmail());
        assertEquals(1, data.getTransactions().size());
        assertEquals("WALLET", data.getTransactions().get(0).getPaymentType());
        assertEquals("CAPTURED", data.getTransactions().get(0).getStatus());
        assertNotNull(data.getTransactions().get(0).getWallet());
    }

    @Test
    @DisplayName("Parse evento 3DS com header Challenge3dsUpdateMessage")
    void parseChallenge3dsEvent() {
        WebhookEvent event = WebhookParser.parse(THREE_DS_BODY, "Challenge3dsUpdateMessage");

        assertEquals(WebhookEventType.CHALLENGE_3DS_UPDATE_MESSAGE, event.getEventTypeHeader());
        assertEquals("THREE_DS_CHALLENGE", event.getType());
        assertTrue(event.isChallenge3ds());
        assertFalse(event.isTransactionUpdate());

        Challenge3dsData data = event.getChallenge3dsData();
        assertNotNull(data);
        assertEquals("Approved", data.getStatus());
        assertEquals("Y", data.getParesStatus());
        assertEquals("05", data.getEciRaw());
        assertEquals("61e35ec2-caa8-4598-bed5-d43e25e14151", data.getMerchantChargeId());
        assertEquals("bac0ffd7-e4cb-48c7-9f3e-a05ff7eb40a1", data.getChargeId());
    }

    @Test
    @DisplayName("Inferência de tipo pelo body quando header é null")
    void inferTypeFromBody() {
        WebhookEvent payment = WebhookParser.parse(PAYMENT_BODY, null);
        assertNotNull(payment.getChargeData());
        assertEquals(WebhookEventType.TRANSACTION_UPDATE_MESSAGE, payment.getEventTypeHeader());

        WebhookEvent threeDs = WebhookParser.parse(THREE_DS_BODY, null);
        assertNotNull(threeDs.getChallenge3dsData());
        assertEquals(WebhookEventType.CHALLENGE_3DS_UPDATE_MESSAGE, threeDs.getEventTypeHeader());
    }

    @Test
    @DisplayName("Parse a partir de InputStream")
    void parseFromInputStream() throws IOException {
        InputStream in = new ByteArrayInputStream(PAYMENT_BODY.getBytes(StandardCharsets.UTF_8));
        WebhookEvent event = WebhookParser.parseFromStream(in, "TransactionUpdateMessage");
        assertNotNull(event.getChargeData());
    }

    @Test
    @DisplayName("Body nulo ou vazio lança exceção")
    void nullOrEmptyBodyThrows() {
        assertThrows(IllegalArgumentException.class, () -> WebhookParser.parse(null, "TransactionUpdateMessage"));
        assertThrows(IllegalArgumentException.class, () -> WebhookParser.parse("", "TransactionUpdateMessage"));
        assertThrows(IllegalArgumentException.class, () -> WebhookParser.parse("   ", "TransactionUpdateMessage"));
    }

    @Test
    @DisplayName("JSON inválido lança JsonParseException")
    void invalidJsonThrows() {
        assertThrows(JsonParseException.class, () -> WebhookParser.parse("not json", "TransactionUpdateMessage"));
    }
}
