package com.borzykin.webautomation.rest;

import com.borzykin.webautomation.models.User;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

import static io.restassured.RestAssured.given;

/**
 * @author Oleksii B
 */
@Log4j2
public class RestService {
    public User getUser(final int id) {
        RestAssured.config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON));
        final Response response = given()
                .get(String.format("https://jsonplaceholder.typicode.com/users/%d", id))
                .then()
                .statusCode(200)
                .extract().response();
        return response.getBody().as(User.class);
    }
}
