package amazing.co.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(exclude = "id")
public class Node {
    public Node(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToOne
    private Company company;

    @OneToOne(optional = true)
    private Node parent;
}
