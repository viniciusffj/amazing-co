package amazing.co.models;

import amazing.co.controllers.dtos.NodeDTO;
import amazing.co.controllers.dtos.NodeWithChildrenDTO;
import amazing.co.repositories.NodeRepository;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
public class Node {
    public static Node rootNode(String name, Company company) {
        return new Node(name, company);
    }

    private Node(String name, Company company) {
        this.name = name;
        this.company = company;
        this.height = 0;
    }

    public static Node nonRootNode(String name, Node parent, Node root) {
        return new Node(name, parent, root);
    }

    private Node(String name, Node parent, Node root) {
        this.name = name;
        this.parent = parent;
        this.root = root;
        this.company = parent.getCompany();
        this.height = parent.getHeight() + 1;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    private Integer height;

    @Getter
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    @OneToOne(optional = true)
    @Getter
    private Node parent;

    @OneToOne(optional = true)
    private Node root;

    public Node getRoot() {
        return isRootNode() ? this : root;
    }

    public boolean isRootNode() {
        return root == null;
    }

    public NodeDTO toDTO() {
        if (isRootNode()) {
            return NodeDTO.rootNode(name, height);
        }
        return NodeDTO.nonRootNode(name, parent.getName(), height);
    }

    public NodeWithChildrenDTO toDTOWithChildren(NodeRepository nodeRepository) {
        List<NodeWithChildrenDTO> children = getChildren(nodeRepository)
                .stream()
                .map(node -> node.toDTOWithChildren(nodeRepository))
                .collect(Collectors.toList());

        if (isRootNode()) {
            return NodeWithChildrenDTO.rootNode(name, children, height);
        }

        return NodeWithChildrenDTO.nonRootNode(name, parent.getName(), children, height);
    }

    private List<Node> getChildren(NodeRepository nodeRepository) {
        return nodeRepository.findAllByParent(this);
    }

    public void setParent(Node newParent, NodeRepository nodeRepository) {
        this.parent = newParent;
        this.updateHeight(newParent, nodeRepository);
    }

    private void updateHeight(Node newParent, NodeRepository nodeRepository) {
        this.height = newParent.getHeight() + 1;

        List<Node> children = getChildren(nodeRepository);

        children.forEach(node -> node.updateHeight(this, nodeRepository));
        nodeRepository.saveAll(children);
    }
}
