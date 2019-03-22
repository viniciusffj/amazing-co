package amazing.co.controllers;

import amazing.co.controllers.helpers.NodeRequestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static amazing.co.controllers.helpers.CompanyRequestsHelper.createCompany;
import static amazing.co.controllers.helpers.CompanyRequestsHelper.deleteCompany;
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
    @Test
    public void shouldUpdateNodeParent() {
        Integer rootId = NodeRequestHelper.createRoot(companyId);
        Integer nodeAId = NodeRequestHelper.createNode(companyId, rootId, "A");
        Integer nodeBId = NodeRequestHelper.createNode(companyId, rootId, "B");

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

}