package com.borzykin.webautomation.rest;

import com.borzykin.webautomation.models.User;
import com.google.inject.Inject;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.log4j.Log4j2;

/**
 * @author Oleksii B
 */
@Log4j2
public class RestService {
    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;

    @Inject
    public RestService() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();
        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    private static RequestSpecification given() {
        return RestAssured.given()
                .config(RestAssured.config()
                        .objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON)));
    }

    public User getUser(final int id) {
        final Response response = given()
                .spec(requestSpec)
                .when()
                .get(Endpoints.USERS, id)
                .then()
                .spec(responseSpec)
                .extract().response();
        return response.getBody().as(User.class);
    }
}
