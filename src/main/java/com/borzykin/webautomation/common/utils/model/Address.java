package com.borzykin.webautomation.common.utils.model;

public class Address {
    private final String from;

    public Address(String from) {
        this.from = from;
    }

    public javax.mail.Address getAddressObject() {
        return new javax.mail.Address() {
            @Override
            public String getType() {
                return "from";
            }

            @Override
            public String toString() {
                return from;
            }

            @Override
            public boolean equals(Object o) {
                return o.equals(this);
            }
        };
    }
}
