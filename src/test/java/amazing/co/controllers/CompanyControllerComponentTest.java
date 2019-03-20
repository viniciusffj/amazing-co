package amazing.co.controllers;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerComponentTest {

    @LocalServerPort
    int serverPort;


    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = serverPort;
    }

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