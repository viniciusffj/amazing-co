package amazing.co.controllers.dtos;

import amazing.co.models.Company;
import amazing.co.models.Node;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
public class NodeDTO {
    @Getter
    private Long id;

    @Setter
    @Getter
    @NotNull
    private String name;

    public NodeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Node toNode(Company company) {
        return Node.rootNode(name, company);
    }

    public Node toNonRootNode(Node parent) {
        return Node.nonRootNode(name, parent, parent.getRoot());
    }
}
