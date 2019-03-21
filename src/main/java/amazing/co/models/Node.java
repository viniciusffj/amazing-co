package amazing.co.models;

import amazing.co.controllers.dtos.NodeDTO;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
public class Node {
    public Node(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public Node(String name, Node parent, Node root) {
        this.name = name;
        this.parent = parent;
        this.root = root;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToOne
    private Company company;

    @OneToOne(optional = true)
    private Node parent;

    @OneToOne(optional = true)
    private Node root;

    public NodeDTO toDTO() {
        return new NodeDTO(id, name);
    }
}
