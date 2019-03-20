package amazing.co.services;

import amazing.co.exceptions.DuplicatedEntityException;
import amazing.co.models.Company;
import amazing.co.repositories.CompanyRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import java.util.Optional;

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
    public void shouldThrowWhenCreatingADuplicatedCompany() {
        when(companyRepository.existsByName("Great Corp"))
                .thenReturn(true);

        exceptionRule.expect(DuplicatedEntityException.class);

        companyService.insert(new Company("Great Corp"));
    }
}