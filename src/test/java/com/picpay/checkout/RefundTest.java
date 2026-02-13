package com.picpay.checkout;

import com.picpay.checkout.model.RefundRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RefundTest {

    @Test
    @DisplayName("RefundRequest.total() cria requisição sem amount")
    void refundTotal() {
        RefundRequest request = RefundRequest.total();
        assertNull(request.getAmount());
    }

    @Test
    @DisplayName("RefundRequest.partial(int) cria requisição parcial em centavos")
    void refundPartialCents() {
        RefundRequest request = RefundRequest.partial(5000);
        assertEquals(5000, request.getAmount());

        assertThrows(IllegalArgumentException.class, () -> RefundRequest.partial(0));
        assertThrows(IllegalArgumentException.class, () -> RefundRequest.partial(-1));
    }

    @Test
    @DisplayName("RefundRequest.partial(BigDecimal) converte para centavos")
    void refundPartialBigDecimal() {
        RefundRequest request = RefundRequest.partial(new BigDecimal("50.00"));
        assertEquals(5000, request.getAmount());

        assertThrows(IllegalArgumentException.class, () -> RefundRequest.partial(null));
        assertThrows(IllegalArgumentException.class, () -> RefundRequest.partial(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> RefundRequest.partial(new BigDecimal("-10.00")));
    }

    @Test
    @DisplayName("Refund requer OAuth")
    void refundRequiresOAuth() {
        PicPayCheckoutConfig config = PicPayCheckoutConfig.sellerToken("token").build();
        PicPayCheckoutClient client = new PicPayCheckoutClient(config);

        assertThrows(IllegalStateException.class, () ->
                client.refundCharge("charge-123", RefundRequest.total()));
    }
}
