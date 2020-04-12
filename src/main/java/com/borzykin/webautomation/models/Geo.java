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
public class Geo {
    @SerializedName("lng")
    private String lng;
    @SerializedName("lat")
    private String lat;

    public Geo(final Consumer<Geo> builder) {
        requireNonNull(builder).accept(this);
    }
}
