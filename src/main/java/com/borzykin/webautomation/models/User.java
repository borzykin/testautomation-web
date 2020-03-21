package com.borzykin.webautomation.models;

import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Data;

import static java.util.Objects.requireNonNull;

/**
 * @author Oleksii B
 */
@Data
@AllArgsConstructor
public class User {
    private String email;
    private String username;
    private String password;

    public User(final Consumer<User> builder) {
        requireNonNull(builder).accept(this);
    }
}
