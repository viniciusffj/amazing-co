package amazing.co.controllers.helpers;

import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class NodeRequestHelper {
    public static Integer createRoot(Integer companyId) {
        return
            given()
                    .contentType("application/json")
                    .body("{ \"name\": \"Root\" }")
                    .pathParam("companyId", companyId)
            .when()
                    .post("/companies/{companyId}/nodes")
            .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .path("id");
    }

    public static Integer createNode(Integer companyId, Integer parentId, String name) {
        String body = String.format("{ \"name\": \"%s\" }", name);

        return
            given()
                    .contentType("application/json")
                    .body(body)
                    .pathParam("companyId", companyId)
                    .pathParam("parentId", parentId)
            .when()
                    .post("/companies/{companyId}/nodes/{parentId}/nodes")
            .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .path("id");
    }
}
