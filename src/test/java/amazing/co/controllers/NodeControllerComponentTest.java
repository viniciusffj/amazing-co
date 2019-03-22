package amazing.co.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.hasKey;

public class NodeControllerComponentTest extends ComponentTest {

    private Integer companyId;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        companyId = createCompany("Orquestra");
    }

    @After
    public void tearDown() throws Exception {
        deleteCompany(companyId);
    }

    private void deleteCompany(Integer companyId) {
        given()
                .pathParam("id", companyId)
        .when()
                .delete("/companies/{id}")
        .then()
                .statusCode(204);
    }

    @Test
    public void shouldCreateRootNode() {
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

    @Test
    public void shouldUpdateNodeParent() {
        /*
        *  Before:
        *
        *       root
        *       / \
        *      A   B
        *
        *  After:
        *
        *       root
        *       /
        *      A
        *     /
        *    B
        *
        * */
        Integer rootId = createRoot(companyId);
        Integer nodeAId = createNode(companyId, rootId, "A");
        Integer nodeBId = createNode(companyId, rootId, "B");

        String body = String.format("{ \"newParentId\": \"%s\" }", nodeAId);

        Integer actualParent = given()
                .contentType("application/json")
                .body(body)
                .pathParam("companyId", companyId)
                .pathParam("nodeId", nodeBId)
        .when()
                .put("/companies/{companyId}/nodes/{nodeId}")
        .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("parentId");

        assertThat(actualParent).isEqualTo(nodeAId);
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

    public Integer createNode(Integer companyId, Integer parentId, String name) {
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
                    .extract()
                    .path("id");
    }
}