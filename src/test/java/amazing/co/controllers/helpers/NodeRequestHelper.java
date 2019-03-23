package amazing.co.controllers.helpers;

import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class NodeRequestHelper {
    public static String createRoot(Integer companyId) {
        return
            given()
                    .contentType("application/json")
                    .body("{ \"name\": \"Root\" }")
                    .pathParam("companyId", companyId)
            .when()
                    .post("/companies/{companyId}/nodes")
            .then()
                    .body("name", equalTo("Root"))
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .path("name");
    }

    public static String createNode(Integer companyId, String parent, String name) {
        String body = String.format("{ \"name\": \"%s\" }", name);

        return
            given()
                    .contentType("application/json")
                    .body(body)
                    .pathParam("companyId", companyId)
                    .pathParam("parent", parent)
            .when()
                    .post("/companies/{companyId}/nodes/{parent}/nodes")
            .then()
                    .body("name", equalTo(name))
                    .body("parent", equalTo(parent))
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .path("name");
    }
}
