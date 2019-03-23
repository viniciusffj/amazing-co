package amazing.co.controllers.dtos;

import amazing.co.models.Company;
import amazing.co.models.Node;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NodeDTO {
    @Setter
    @Getter
    @NotNull
    private String name;

    @Getter
    private String parent;

    public NodeDTO(String name) {
        this.name = name;
    }

    public NodeDTO(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

    public Node toNode(Company company) {
        return Node.rootNode(name, company);
    }

    public Node toNonRootNode(Node parent) {
        return Node.nonRootNode(name, parent, parent.getRoot());
    }
}
