package amazing.co.repositories;

import amazing.co.models.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyRepositoryIntegrationTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void shouldCheckIfCompanyExistsWithAGivenName() {
        Company newCompany = new Company("awesome company");
        this.companyRepository.save(newCompany);

        assertThat(companyRepository.existsByName("awesome company")).isTrue();
    }
}