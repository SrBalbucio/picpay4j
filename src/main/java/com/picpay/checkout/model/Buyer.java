package com.picpay.checkout.model;

/**
 * Dados do comprador (API E-commerce).
 */
public final class Buyer {

    private final String firstName;
    private final String lastName;
    private final String document;

    public Buyer(String firstName, String lastName, String document) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.document = document;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDocument() {
        return document;
    }

    public static final class Builder {
        private String firstName;
        private String lastName;
        private String document;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder document(String document) {
            this.document = document;
            return this;
        }

        public Buyer build() {
            if (firstName == null || lastName == null || document == null) {
                throw new IllegalArgumentException("firstName, lastName e document são obrigatórios");
            }
            return new Buyer(firstName, lastName, document);
        }
    }
}
