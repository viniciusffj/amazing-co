package amazing.co.models;

import amazing.co.controllers.dtos.NodeDTO;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class NodeTest {

    @Test
    public void shouldCreteRootNodeDTO() {
        Node node = Node.rootNode("root", new Company("company"));

        NodeDTO nodeDTO = node.toDTO();

        assertThat(nodeDTO.getName()).isEqualTo("root");
        assertThat(nodeDTO.getParent()).isNull();
    }

    @Test
    public void shouldCreateNonRootNodeDTO() {
        Node root = Node.rootNode("root", new Company("company"));
        Node node = Node.nonRootNode("A node", root, root);

        NodeDTO nodeDTO = node.toDTO();

        assertThat(nodeDTO.getName()).isEqualTo("A node");
        assertThat(nodeDTO.getParent()).isEqualTo(root.getName());
    }
}