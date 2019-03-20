package amazing.co.controllers;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.*;

public class NodeControllerComponentTest extends ComponentTest {

    @Test
    public void shouldCreateRootNoode() {
        // create company
        Integer companyId =
                given()
                        .contentType("application/json")
                        .body("{ \"name\": \"Orquestra\" }")
                .when()
                        .post("/companies")
                .then()
                        .extract()
                        .path("id");

        // post "companies/1/nodes"
        given()
                .contentType("application/json")
                .body("{ \"name\": \"Orquestra\" }")
                .pathParam("companyId", companyId)
        .when()
                .post("/companies/{companyId}/nodes")
        .then()
                // check if response code is 201
                .statusCode(HttpStatus.CREATED.value())
                // check if response has id
                .body("$", hasKey("id"));
    }
}