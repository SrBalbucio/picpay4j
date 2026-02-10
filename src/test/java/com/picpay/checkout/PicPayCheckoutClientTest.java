package com.picpay.checkout;

import com.picpay.checkout.model.Buyer;
import com.picpay.checkout.model.CreatePaymentRequest;
import com.picpay.checkout.model.CreatePaymentResponse;
import com.picpay.checkout.model.LightboxCheckoutSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PicPayCheckoutClientTest {

    @Test
    @DisplayName("CreatePaymentRequest builder valida campos obrigatórios")
    void requestBuilderValidatesRequiredFields() {
        Buyer buyer = Buyer.builder()
                .firstName("João")
                .lastName("Teste")
                .document("12345678909")
                .build();

        assertThrows(IllegalArgumentException.class, () ->
                CreatePaymentRequest.builder()
                        .callbackUrl("https://callback.com")
                        .returnUrl("https://return.com")
                        .value(BigDecimal.TEN)
                        .buyer(buyer)
                        .build());

        assertThrows(IllegalArgumentException.class, () ->
                CreatePaymentRequest.builder()
                        .referenceId("ref-1")
                        .returnUrl("https://return.com")
                        .value(BigDecimal.TEN)
                        .buyer(buyer)
                        .build());

        CreatePaymentRequest req = CreatePaymentRequest.builder()
                .referenceId("ref-1")
                .callbackUrl("https://callback.com")
                .returnUrl("https://return.com")
                .value(BigDecimal.TEN)
                .buyer(buyer)
                .build();

        assertEquals("ref-1", req.getReferenceId());
        assertEquals(BigDecimal.TEN, req.getValue());
        assertEquals(buyer, req.getBuyer());
    }

    @Test
    @DisplayName("Config OAuth exige clientId e clientSecret")
    void configOAuthRequiresCredentials() {
        assertThrows(IllegalArgumentException.class, () ->
                PicPayCheckoutConfig.oauth("", "secret").build());
        assertThrows(IllegalArgumentException.class, () ->
                PicPayCheckoutConfig.oauth("id", "").build());

        PicPayCheckoutConfig config = PicPayCheckoutConfig.oauth("id", "secret").build();
        assertTrue(config.isUseOAuth());
        assertEquals("https://checkout-api.picpay.com", config.getBaseUrl());
    }

    @Test
    @DisplayName("Config sellerToken usa baseUrl appws")
    void configSellerTokenUsesAppwsBaseUrl() {
        PicPayCheckoutConfig config = PicPayCheckoutConfig.sellerToken("token").build();
        assertFalse(config.isUseOAuth());
        assertEquals("https://appws.picpay.com", config.getBaseUrl());
        assertEquals("token", config.getSellerToken());
    }

    @Test
    @DisplayName("CreatePaymentResponse getters/setters")
    void createPaymentResponseFields() {
        CreatePaymentResponse r = new CreatePaymentResponse();
        r.setPaymentUrl("https://pay.picpay.com/xxx");
        r.setReferenceId("ref-1");
        r.setPicpayTransactionId("picpay-123");

        assertEquals("https://pay.picpay.com/xxx", r.getPaymentUrl());
        assertEquals("ref-1", r.getReferenceId());
        assertEquals("picpay-123", r.getPicpayTransactionId());
    }

    @Test
    @DisplayName("LightboxCheckoutSession expõe URL e IDs para iframe")
    void lightboxSessionWrapsResponse() {
        CreatePaymentResponse r = new CreatePaymentResponse();
        r.setPaymentUrl("https://pay.picpay.com/lightbox-xxx");
        r.setReferenceId("ref-lightbox");
        r.setPicpayTransactionId("picpay-456");

        LightboxCheckoutSession session = new LightboxCheckoutSession(r);

        assertEquals("https://pay.picpay.com/lightbox-xxx", session.getIframePaymentUrl());
        assertEquals("ref-lightbox", session.getReferenceId());
        assertEquals("picpay-456", session.getPicpayTransactionId());
    }
}
