package amazing.co.repositories;

import amazing.co.models.Company;
import amazing.co.models.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

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
    public void shouldCheckIfNodeExistsByNameAndCompany() {
        Node root = Node.rootNode("Root", awesomeCompany);
        nodeRepository.save(root);

        assertThat(nodeRepository.existsByNameAndCompany("Root", awesomeCompany)).isTrue();
    }

    @Test
    public void shouldCheckIfRootNodeExistsForACompany() {
        Node root = Node.rootNode("Root", awesomeCompany);
        nodeRepository.save(root);

        assertThat(nodeRepository.existsWhereRootIsNullByCompany(awesomeCompany)).isTrue();
    }

    @Test
    public void shouldFindNodeByNameAndCompany() {
        Node root = Node.rootNode("Root", awesomeCompany);
        nodeRepository.save(root);

        Optional<Node> optionalNode = nodeRepository.findByNameAndCompany("Root", awesomeCompany);
        assertThat(optionalNode.isPresent()).isTrue();
        assertThat(optionalNode.get().getName()).isEqualTo("Root");
    }
}