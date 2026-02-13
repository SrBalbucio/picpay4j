package com.picpay.checkout.model;

import com.google.gson.annotations.SerializedName;

/**
 * Dados do cliente/comprador para cobrança PIX.
 */
public final class ChargeCustomer {

    @SerializedName("name")
    private final String name;

    @SerializedName("email")
    private final String email;

    @SerializedName("documentType")
    private final String documentType;

    @SerializedName("document")
    private final String document;

    private ChargeCustomer(Builder b) {
        this.name = b.name;
        this.email = b.email;
        this.documentType = b.documentType;
        this.document = b.document;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getDocument() {
        return document;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String email;
        private String documentType;
        private String document;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder documentType(String documentType) {
            this.documentType = documentType;
            return this;
        }

        public Builder documentTypeCpf() {
            return documentType("CPF");
        }

        public Builder documentTypeCnpj() {
            return documentType("CNPJ");
        }

        public Builder documentTypePassport() {
            return documentType("PASSPORT");
        }

        public Builder document(String document) {
            this.document = document;
            return this;
        }

        public ChargeCustomer build() {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("name é obrigatório");
            }
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("email é obrigatório");
            }
            if (documentType == null || documentType.isBlank()) {
                throw new IllegalArgumentException("documentType é obrigatório (CPF, CNPJ ou PASSPORT)");
            }
            if (document == null || document.isBlank()) {
                throw new IllegalArgumentException("document é obrigatório");
            }
            return new ChargeCustomer(this);
        }
    }
}
