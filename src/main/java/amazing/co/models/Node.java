package amazing.co.models;

import amazing.co.controllers.dtos.NodeDTO;
import amazing.co.repositories.NodeRepository;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

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
    }

    public static Node nonRootNode(String name, Node parent, Node root) {
        return new Node(name, parent, root);
    }

    private Node(String name, Node parent, Node root) {
        this.name = name;
        this.parent = parent;
        this.root = root;
        this.company = parent.getCompany();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    @OneToOne(optional = true)
    @Getter
    @Setter
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
            return new NodeDTO(name);
        }
        return new NodeDTO(name, parent.getName());
    }

    public List<Node> getChildren(NodeRepository nodeRepository) {
        return nodeRepository.findAllByParent(this);
    }
}
