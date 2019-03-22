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
    @Getter
    private Long id;

    @Setter
    @Getter
    @NotNull
    private String name;

    @Getter
    private Long parentId;

    public NodeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public NodeDTO(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public Node toNode(Company company) {
        return Node.rootNode(name, company);
    }

    public Node toNonRootNode(Node parent) {
        return Node.nonRootNode(name, parent, parent.getRoot());
    }
}
