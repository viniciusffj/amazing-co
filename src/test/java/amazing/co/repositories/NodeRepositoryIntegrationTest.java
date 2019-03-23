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

import java.util.List;
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
        root();

        assertThat(nodeRepository.existsByNameAndCompany("Root", awesomeCompany)).isTrue();
    }

    @Test
    public void shouldCheckIfRootNodeExistsForACompany() {
        root();

        assertThat(nodeRepository.existsWhereRootIsNullByCompany(awesomeCompany)).isTrue();
    }

    @Test
    public void shouldFindNodeByNameAndCompany() {
        root();

        Optional<Node> optionalNode = nodeRepository.findByNameAndCompany("Root", awesomeCompany);
        assertThat(optionalNode.isPresent()).isTrue();
        assertThat(optionalNode.get().getName()).isEqualTo("Root");
    }

    @Test
    public void shouldGetChildrenOfNode() {
        Node root = root();

        Node nodeA = nonRoot("A", root);
        Node nodeB = nonRoot("B", root);

        List<Node> children = root.getChildren(nodeRepository);

        assertThat(children.size()).isEqualTo(2);
        assertThat(children.get(0).getName()).isEqualTo(nodeA.getName());
        assertThat(children.get(1).getName()).isEqualTo(nodeB.getName());
    }

    private Node root() {
        Node root = Node.rootNode("Root", awesomeCompany);
        return nodeRepository.save(root);
    }

    private Node nonRoot(String name, Node parent) {
        Node node = Node.nonRootNode(name, parent, parent.getRoot());
        return nodeRepository.save(node);
    }
}