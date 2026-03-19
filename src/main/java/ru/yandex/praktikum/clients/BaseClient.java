package ru.yandex.praktikum.clients;

import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class BaseClient {
    protected static final String BASE_URL = "https://stellarburgers.education-services.ru";

    protected RequestSpecification getSpec() {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL);
    }
}