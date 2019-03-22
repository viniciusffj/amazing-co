package amazing.co.controllers;

import amazing.co.services.NonRootNodeService;
import amazing.co.services.RootNodeService;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NodeController.class)
public class NodeControllerTest extends BaseControllerTest {
    @MockBean
    private NonRootNodeService nonRootNodeService;

    @MockBean
    private RootNodeService rootNodeService;

    @Test
    public void shouldReturnBadRequestIfNamingIsMissingForRootNode() throws Exception {
        this.mvc.perform(
                    post("/companies/1/nodes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestIfNamingIsMissingForNonRootNode() throws Exception {
        this.mvc.perform(
                post("/companies/1/nodes/1/nodes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestIfNewParentIdIsMissig() throws Exception {
        this.mvc.perform(
                put("/companies/1/nodes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ }"))
                .andExpect(status().isBadRequest());
    }
}