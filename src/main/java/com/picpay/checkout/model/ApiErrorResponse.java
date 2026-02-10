package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Resposta de erro da API PicPay.
 */
public final class ApiErrorResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private Boolean success;

    @SerializedName("businessCode")
    private String businessCode;

    @SerializedName("errors")
    private List<ErrorDetail> errors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    public static final class ErrorDetail {
        @SerializedName("message")
        private String message;

        @SerializedName("field")
        private String field;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }
}
