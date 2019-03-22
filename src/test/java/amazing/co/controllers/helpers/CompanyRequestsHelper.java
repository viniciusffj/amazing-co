package amazing.co.controllers.helpers;

import static io.restassured.RestAssured.given;

public class CompanyRequestsHelper {

    public static Integer createCompany(String name) {
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

    public static void deleteCompany(Integer companyId) {
        given()
                .pathParam("id", companyId)
        .when()
                .delete("/companies/{id}")
        .then()
                .statusCode(204);
    }
}
