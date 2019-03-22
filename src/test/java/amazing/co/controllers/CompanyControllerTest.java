package amazing.co.controllers;

import amazing.co.exceptions.DuplicatedEntityException;
import amazing.co.exceptions.EntityNotFoundException;
import amazing.co.models.Company;
import amazing.co.services.CompanyService;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
public class CompanyControllerTest extends BaseControllerTest {
    @MockBean
    private CompanyService companyService;

    @Test
    public void shouldReturn409ConflictIfCompanyAlreadyExists() throws Exception {
        doThrow(new DuplicatedEntityException("Company already exists"))
            .when(companyService).create(any(Company.class));

        this.mvc.perform(
                    post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Existing Company\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldReturn404IfCompanyDoesNotExists() throws Exception {
        doThrow(new EntityNotFoundException("Company does not exist"))
            .when(companyService).delete(1914L);

        this.mvc.perform(
                    delete("/companies/{id}", 1914L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequestIfNamingIsMissing() throws Exception {
        this.mvc.perform(
                    post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ }"))
                .andExpect(status().isBadRequest());
    }
}