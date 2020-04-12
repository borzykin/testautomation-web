package com.borzykin.webautomation.models;

import java.util.function.Consumer;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import static java.util.Objects.requireNonNull;

/**
 * @author Oleksii B
 */
@Data
@AllArgsConstructor
public class Address {
    @SerializedName("zipcode")
    private String zipCode;
    @SerializedName("geo")
    private Geo geo;
    @SerializedName("suite")
    private String suite;
    @SerializedName("city")
    private String city;
    @SerializedName("street")
    private String street;

    public Address(final Consumer<Address> builder) {
        requireNonNull(builder).accept(this);
    }
}
