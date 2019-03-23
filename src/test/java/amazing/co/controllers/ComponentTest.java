package amazing.co.controllers;

import amazing.co.controllers.helpers.NodeRequestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static amazing.co.controllers.helpers.CompanyRequestsHelper.createCompany;
import static amazing.co.controllers.helpers.CompanyRequestsHelper.deleteCompany;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ComponentTest extends BaseComponentTest {
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
                .pathParam("parent", "I-DO-NOT-EXIST")
        .when()
                .post("/companies/{companyId}/nodes/{parent}/children")
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
        String root = NodeRequestHelper.createRoot(companyId);
        String nodeA = NodeRequestHelper.createNode(companyId, root, "A");
        String nodeB = NodeRequestHelper.createNode(companyId, root, "B");

        String body = String.format("{ \"newParent\": \"%s\" }", nodeA);

        given()
                .contentType("application/json")
                .body(body)
                .pathParam("companyId", companyId)
                .pathParam("node", nodeB)
        .when()
                .put("/companies/{companyId}/nodes/{node}")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("parent", equalTo(nodeA));
    }

    @Test
    public void shouldGetChildrenOfNode() {
        String root = NodeRequestHelper.createRoot(companyId);
        String nodeA = NodeRequestHelper.createNode(companyId, root, "A");
        String nodeB = NodeRequestHelper.createNode(companyId, nodeA, "B");

        given()
                .contentType("application/json")
                .pathParam("companyId", companyId)
                .pathParam("node", root)
        .when()
                .get("/companies/{companyId}/nodes/{node}/children")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("[0].name", equalTo(nodeA))
                .body("[0].children[0].name", equalTo(nodeB));
    }
}