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
        Node root = new Node("Root node", company);

        return new Node("Some node", root, root);
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
        Node root = new Node("Root node", company);
        Node parent = new Node("Parent node", root, root);

        return new Node("Some node", parent, root);
    }
}