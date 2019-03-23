package amazing.co.controllers.dtos;

import amazing.co.models.Company;
import amazing.co.models.Node;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NodeDTO {
    @Setter
    @Getter
    @NotNull
    private String name;

    @Getter
    private String parent;

    @Getter
    private List<NodeDTO> children;

    public NodeDTO(String name) {
        this.name = name;
    }

    public NodeDTO(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

    public NodeDTO(String name, List<NodeDTO> children) {
        this.name = name;
        this.children = children;
    }

    public NodeDTO(String name, String parent, List<NodeDTO> children) {
        this.name = name;
        this.parent = parent;
        this.children = children;
    }

    public Node toNode(Company company) {
        return Node.rootNode(name, company);
    }

    public Node toNonRootNode(Node parent) {
        return Node.nonRootNode(name, parent, parent.getRoot());
    }
}
