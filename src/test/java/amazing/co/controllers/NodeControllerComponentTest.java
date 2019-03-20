package amazing.co.controllers;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.*;

public class NodeControllerComponentTest extends ComponentTest {

    @Test
    public void shouldCreateRootNode() {
        Integer companyId =
                given()
                        .contentType("application/json")
                        .body("{ \"name\": \"Orquestra\" }")
                .when()
                        .post("/companies")
                .then()
                        .extract()
                        .path("id");

        given()
                .contentType("application/json")
                .body("{ \"name\": \"Orquestra\" }")
                .pathParam("companyId", companyId)
        .when()
                .post("/companies/{companyId}/nodes")
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"));
    }

    @Test
    public void shouldReturn404WhenCompanyDoesNotExist() {
        given()
                .contentType("application/json")
                .body("{ \"name\": \"Will not work\" }")
                .pathParam("companyId", 10000)
        .when()
                .post("/companies/{companyId}/nodes")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}