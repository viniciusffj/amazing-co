package amazing.co.models;

import amazing.co.controllers.dtos.NodeDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private Long id;

    @Getter
    private String name;

    @OneToOne
    @Getter
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
        return new NodeDTO(id, name);
    }
}
