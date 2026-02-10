package com.picpay.checkout.exception;

/**
 * Exceção lançada quando a API PicPay retorna erro (4xx/5xx) ou resposta inválida.
 */
public class PicPayApiException extends RuntimeException {

    private final int httpStatus;
    private final String responseBody;
    private final String businessCode;

    public PicPayApiException(String message, int httpStatus, String responseBody) {
        this(message, httpStatus, responseBody, null);
    }

    public PicPayApiException(String message, int httpStatus, String responseBody, String businessCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
        this.businessCode = businessCode;
    }

    public PicPayApiException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = -1;
        this.responseBody = null;
        this.businessCode = null;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public String getBusinessCode() {
        return businessCode;
    }
}
