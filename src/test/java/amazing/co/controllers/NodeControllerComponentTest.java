package amazing.co.controllers;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.hasKey;

public class NodeControllerComponentTest extends ComponentTest {

    @Test
    public void shouldCreateRootNode() {
        Integer companyId = createCompany("Orquestra");

        Integer nodeId = given()
                .contentType("application/json")
                .body("{ \"name\": \"Root\" }")
                .pathParam("companyId", companyId)
        .when()
                .post("/companies/{companyId}/nodes")
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .extract()
                .path("id");

        assertThat(nodeId).isNotNull();
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

    @Test
    public void shouldCreateNonRootNode() {
        Integer companyId = createCompany("Awesome Company");
        Integer parentId = createRoot(companyId);

        given()
                .contentType("application/json")
                .body("{ \"name\": \"A\" }")
                .pathParam("companyId", companyId)
                .pathParam("parentId", parentId)
        .when()
                .post("/companies/{companyId}/nodes/{parentId}/nodes")
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"));
    }

    @Test
    public void shouldReturn404WhenParentDoesNotExist() {
        Integer companyId = createCompany("Valid company");

        given()
                .contentType("application/json")
                .body("{ \"name\": \"Will not work\" }")
                .pathParam("companyId", companyId)
                .pathParam("parentId", 10000)
        .when()
                .post("/companies/{companyId}/nodes/{parentId}/nodes")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    public Integer createCompany(String name) {
        String body = String.format("{ \"name\": \"%s\" }", name);

        return
            given()
                    .contentType("application/json")
                    .body(body)
            .when()
                    .post("/companies")
            .then()
                    .extract()
                    .path("id");
    }

    public Integer createRoot(Integer companyId) {
        return
            given()
                    .contentType("application/json")
                    .body("{ \"name\": \"Root\" }")
                    .pathParam("companyId", companyId)
            .when()
                    .post("/companies/{companyId}/nodes")
            .then()
                    .extract()
                    .path("id");
    }
}