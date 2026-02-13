package com.picpay.checkout;

import com.picpay.checkout.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChargePixTest {

    @Test
    @DisplayName("ChargePixRequest builder valida campos obrigatórios")
    void requestBuilderValidatesRequiredFields() {
        ChargeCustomer customer = ChargeCustomer.builder()
                .name("João Silva")
                .email("joao@example.com")
                .documentTypeCpf()
                .document("12345678909")
                .build();

        ChargePhone phone = ChargePhone.builder()
                .countryCode("55")
                .areaCode("11")
                .number("987654321")
                .typeMobile()
                .build();

        DeviceInformation device = DeviceInformation.builder()
                .ip("192.168.1.1")
                .build();

        ChargePixTransaction transaction = ChargePixTransaction.builder()
                .amount(10000)
                .build();

        assertThrows(IllegalArgumentException.class, () ->
                ChargePixRequest.builder()
                        .customer(customer)
                        .phone(phone)
                        .deviceInformation(device)
                        .transactions(List.of(transaction))
                        .build());

        assertThrows(IllegalArgumentException.class, () ->
                ChargePixRequest.builder()
                        .paymentSourceGateway()
                        .phone(phone)
                        .deviceInformation(device)
                        .transactions(List.of(transaction))
                        .build());

        ChargePixRequest req = ChargePixRequest.builder()
                .paymentSourceGateway()
                .customer(customer)
                .phone(phone)
                .deviceInformation(device)
                .transactions(List.of(transaction))
                .build();

        assertEquals("GATEWAY", req.getPaymentSource());
        assertEquals(customer, req.getCustomer());
        assertEquals(phone, req.getPhone());
    }

    @Test
    @DisplayName("ChargePixRequest requer smartCheckoutId quando paymentSource é CHECKOUT")
    void requiresSmartCheckoutIdForCheckout() {
        ChargeCustomer customer = ChargeCustomer.builder()
                .name("João").email("joao@example.com").documentTypeCpf().document("12345678909").build();
        ChargePhone phone = ChargePhone.builder().countryCode("55").areaCode("11").number("987654321").typeMobile().build();
        DeviceInformation device = DeviceInformation.builder().ip("192.168.1.1").build();
        ChargePixTransaction tx = ChargePixTransaction.builder().amount(10000).build();

        assertThrows(IllegalArgumentException.class, () ->
                ChargePixRequest.builder()
                        .paymentSourceCheckout()
                        .customer(customer)
                        .phone(phone)
                        .deviceInformation(device)
                        .transactions(List.of(tx))
                        .build());
    }

    @Test
    @DisplayName("ChargePixTransaction aceita amount em centavos ou BigDecimal")
    void transactionAmountFormats() {
        ChargePixTransaction tx1 = ChargePixTransaction.builder()
                .amount(10000)
                .build();
        assertEquals(10000, tx1.getAmount());

        ChargePixTransaction tx2 = ChargePixTransaction.builder()
                .amount(new BigDecimal("100.00"))
                .build();
        assertEquals(10000, tx2.getAmount());

        assertThrows(IllegalArgumentException.class, () ->
                ChargePixTransaction.builder().amount(0).build());
    }

    @Test
    @DisplayName("ChargeCustomer valida campos obrigatórios")
    void customerValidatesFields() {
        assertThrows(IllegalArgumentException.class, () ->
                ChargeCustomer.builder()
                        .email("joao@example.com")
                        .documentTypeCpf()
                        .document("12345678909")
                        .build());

        ChargeCustomer c = ChargeCustomer.builder()
                .name("João")
                .email("joao@example.com")
                .documentTypeCpf()
                .document("12345678909")
                .build();
        assertEquals("João", c.getName());
        assertEquals("CPF", c.getDocumentType());
    }

    @Test
    @DisplayName("ChargePhone valida campos obrigatórios")
    void phoneValidatesFields() {
        assertThrows(IllegalArgumentException.class, () ->
                ChargePhone.builder()
                        .countryCode("55")
                        .areaCode("11")
                        .build());

        ChargePhone p = ChargePhone.builder()
                .countryCode("55")
                .areaCode("11")
                .number("987654321")
                .typeMobile()
                .build();
        assertEquals("55", p.getCountryCode());
        assertEquals("MOBILE", p.getType());
    }

    @Test
    @DisplayName("DeviceInformation requer IP")
    void deviceRequiresIp() {
        assertThrows(IllegalArgumentException.class, () ->
                DeviceInformation.builder().build());

        DeviceInformation d = DeviceInformation.builder()
                .ip("192.168.1.1")
                .sessionId("session-123")
                .build();
        assertEquals("192.168.1.1", d.getIp());
    }

    @Test
    @DisplayName("Charge PIX requer OAuth")
    void chargePixRequiresOAuth() {
        PicPayCheckoutConfig config = PicPayCheckoutConfig.sellerToken("token").build();
        PicPayCheckoutClient client = new PicPayCheckoutClient(config);

        ChargeCustomer customer = ChargeCustomer.builder()
                .name("João").email("joao@example.com").documentTypeCpf().document("12345678909").build();
        ChargePhone phone = ChargePhone.builder().countryCode("55").areaCode("11").number("987654321").typeMobile().build();
        DeviceInformation device = DeviceInformation.builder().ip("192.168.1.1").build();
        ChargePixTransaction tx = ChargePixTransaction.builder().amount(10000).build();
        ChargePixRequest request = ChargePixRequest.builder()
                .paymentSourceGateway()
                .customer(customer)
                .phone(phone)
                .deviceInformation(device)
                .transactions(List.of(tx))
                .build();

        assertThrows(IllegalStateException.class, () -> client.createPixCharge(request));
    }
}
