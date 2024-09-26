package com.example.todoapp.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TodoResourceTest {

    @Test
    public void testGetAll() {
        given()
                .when().get("/todos")
                .then()
                .statusCode(200)
                .body(
                        containsString("Test task 1"),
                        containsString("Test task 2"));
    }

}
