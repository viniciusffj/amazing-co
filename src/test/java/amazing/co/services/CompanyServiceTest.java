package amazing.co.services;

import amazing.co.exceptions.DuplicatedEntityException;
import amazing.co.exceptions.EntityNotFoundException;
import amazing.co.models.Company;
import amazing.co.repositories.CompanyRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void shouldThrowErrorWhenCreatingADuplicatedCompany() {
        when(companyRepository.existsByName("Great Corp"))
                .thenReturn(true);

        exceptionRule.expect(DuplicatedEntityException.class);

        companyService.create(new Company("Great Corp"));
    }

    @Test
    public void shouldThrowErrorWhenDeletingInvalidCompany() {
        when(companyRepository.existsById(1914L))
                .thenReturn(false);

        exceptionRule.expect(EntityNotFoundException.class);
        exceptionRule.expectMessage("Company 1914 does not exist");

        companyService.delete(1914L);
    }
}