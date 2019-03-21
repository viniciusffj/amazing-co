package amazing.co.controllers.dtos;

import amazing.co.models.Company;
import amazing.co.models.Node;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class NodeDTOTest {

    @Test
    public void shouldGenerateNonRootNodeWhenParentIsRoot() {
        NodeDTO nodeDTO = new NodeDTO();
        nodeDTO.setName("Some node");


        Node exceptedNode = createChildOfRootNode();

        Node actualNode = nodeDTO.toNonRootNode(exceptedNode.getParent());
        assertThat(actualNode).isEqualTo(exceptedNode);
    }

    private Node createChildOfRootNode() {
        Company company = new Company("A company");
        Node root = Node.rootNode("Root node", company);

        return Node.nonRootNode("Some node", root, root);
    }

    @Test
    public void shouldGenerateNonRootNodeWhenParentIsNotRoot() {
        NodeDTO nodeDTO = new NodeDTO();
        nodeDTO.setName("Some node");

        Node exceptedNode = createGranChildOfRootNode();

        Node actualNode = nodeDTO.toNonRootNode(exceptedNode.getParent());
        assertThat(actualNode).isEqualTo(exceptedNode);
    }

    private Node createGranChildOfRootNode() {
        Company company = new Company("A company");
        Node root = Node.rootNode("Root node", company);
        Node parent = Node.nonRootNode("Parent node", root, root);

        return Node.nonRootNode("Some node", parent, root);
    }
}