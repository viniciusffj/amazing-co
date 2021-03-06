package amazing.co.repositories;

import amazing.co.controllers.dtos.NodeDTO;
import amazing.co.controllers.dtos.NodeWithChildrenDTO;
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
    public void shouldConvertToDTOWithChildren() {
        Node root = root();
        Node nodeA = nonRoot("A", root);
        Node nodeB = nonRoot("B", nodeA);

        assertThat(nodeA.getHeight()).isEqualTo(1);
        assertThat(nodeB.getHeight()).isEqualTo(2);

        NodeWithChildrenDTO actual = root.toDTOWithChildren(nodeRepository);

        List<NodeWithChildrenDTO> childrenOfRoot = actual.getChildren();

        assertThat(childrenOfRoot.size()).isEqualTo(1);

        assertThat(childrenOfRoot.get(0).getName()).isEqualTo(nodeA.getName());
        assertThat(childrenOfRoot.get(0).getHeight()).isEqualTo(nodeA.getHeight());
        assertThat(childrenOfRoot.get(0).getParent()).isEqualTo(root.getName());

        List<NodeWithChildrenDTO> childrenOfA = childrenOfRoot.get(0).getChildren();
        assertThat(childrenOfA.size()).isEqualTo(1);

        assertThat(childrenOfA.get(0).getName()).isEqualTo(nodeB.getName());
        assertThat(childrenOfA.get(0).getHeight()).isEqualTo(nodeB.getHeight());
        assertThat(childrenOfA.get(0).getParent()).isEqualTo(nodeA.getName());
    }

    @Test
    public void shouldSetParentAndRecalculateHeight() {
        Node root = root();
        Node nodeA = nonRoot("A", root);
        Node nodeB = nonRoot("B", nodeA);
        Node nodeC = nonRoot("C", nodeB);
        Node nodeD = nonRoot("D", nodeC);
        Node nodeE = nonRoot("E", nodeD);

        nodeB.setParent(root, nodeRepository);

        assertThat(nodeB.getParent()).isEqualTo(root);

        Node updatedNodeB = nodeRepository.findById(nodeB.getId()).get();
        Node updatedNodeC = nodeRepository.findById(nodeC.getId()).get();
        Node updatedNodeD = nodeRepository.findById(nodeD.getId()).get();
        Node updatedNodeE = nodeRepository.findById(nodeE.getId()).get();

        assertThat(updatedNodeB.getHeight()).isEqualTo(1);
        assertThat(updatedNodeC.getHeight()).isEqualTo(2);
        assertThat(updatedNodeD.getHeight()).isEqualTo(3);
        assertThat(updatedNodeE.getHeight()).isEqualTo(4);
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