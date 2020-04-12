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
public class Company {
    @SerializedName("bs")
    private String bs;
    @SerializedName("catchPhrase")
    private String catchPhrase;
    @SerializedName("name")
    private String name;

    public Company(final Consumer<Company> builder) {
        requireNonNull(builder).accept(this);
    }
}
