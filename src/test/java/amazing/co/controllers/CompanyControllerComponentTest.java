package amazing.co.controllers;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

public class CompanyControllerComponentTest extends ComponentTest {

    @Test
    public void shouldCreateAndDeleteCompany() {
        Integer companyId =
        given()
                .contentType("application/json")
                .body("{ \"name\": \"duda's\" }")
        .when()
                .post("/companies")
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
        .extract()
                .path("id");

        given()
                .pathParam("id", companyId)
        .when()
                .delete("/companies/{id}")
        .then()
                .statusCode(204);
    }
}