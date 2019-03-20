package amazing.co.repositories;

import amazing.co.models.Company;
import amazing.co.models.Node;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class NodeRepositoryIntegrationTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private NodeRepository nodeRepository;

    private Company awesomeCompany = new Company("Awesome Company");

    @Before
    public void setUp() throws Exception {
        companyRepository.save(awesomeCompany);
    }

    @After
    public void tearDown() throws Exception {
        nodeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void shouldCreateNode() {
        Node node = new Node("Root", awesomeCompany);

        nodeRepository.save(node);

        ArrayList<Node> nodes = Lists.newArrayList(nodeRepository.findAll());
        assertThat(nodes).hasSize(1);
        assertThat(nodes.get(0)).isEqualTo(node);
    }
}