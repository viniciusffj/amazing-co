package amazing.co.services;

import amazing.co.models.Company;
import amazing.co.repositories.CompanyRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyServiceTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @Test
    public void shouldSaveCompany() {
        Company company = new Company("Awesome company");

        companyService.insert(company);

        List<Company> companies = Lists.newArrayList(companyRepository.findAll());
        assertThat(companies).hasSize(1);
        assertThat(companies.get(0).getName()).isEqualTo(company.getName());

    }
}