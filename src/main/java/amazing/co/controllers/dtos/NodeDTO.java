package amazing.co.controllers.dtos;

import amazing.co.models.Company;
import amazing.co.models.Node;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NodeDTO {
    @Setter
    @Getter
    @NotBlank
    private String name;

    @Getter
    private String parent;

    @Getter
    private Integer height;

    @Getter
    private List<NodeDTO> children;

    public NodeDTO(String name, Integer height) {
        this.name = name;
        this.height = height;
    }

    public NodeDTO(String name, String parent, Integer height) {
        this.name = name;
        this.parent = parent;
        this.height = height;
    }

    public NodeDTO(String name, List<NodeDTO> children, Integer height) {
        this.name = name;
        this.children = children;
        this.height = height;
    }

    public NodeDTO(String name, String parent, List<NodeDTO> children, Integer height) {
        this.name = name;
        this.parent = parent;
        this.children = children;
        this.height = height;
    }

    public Node toNode(Company company) {
        return Node.rootNode(name, company);
    }

    public Node toNonRootNode(Node parent) {
        return Node.nonRootNode(name, parent, parent.getRoot());
    }

}
