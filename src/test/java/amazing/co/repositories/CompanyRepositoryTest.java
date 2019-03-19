package amazing.co.repositories;

import amazing.co.models.Company;
import org.assertj.core.util.Lists;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;
}