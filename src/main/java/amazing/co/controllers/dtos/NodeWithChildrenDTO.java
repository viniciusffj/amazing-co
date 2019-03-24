package amazing.co.controllers.dtos;

import lombok.Getter;

import java.util.List;

public class NodeWithChildrenDTO extends NodeDTO {
    @Getter
    private List<NodeWithChildrenDTO> children;

    public static NodeWithChildrenDTO rootNode(String name,
                                               List<NodeWithChildrenDTO> children,
                                               Integer height) {
        return new NodeWithChildrenDTO(name, children, height);
    }

    public static NodeWithChildrenDTO nonRootNode(String name,
                                                  String parent,
                                                  List<NodeWithChildrenDTO> children,
                                                  Integer height) {
        return new NodeWithChildrenDTO(name, parent, children, height);
    }

    private NodeWithChildrenDTO(String name,
                                List<NodeWithChildrenDTO> children,
                                Integer height) {
        super(name, height);
        this.children = children;
    }

    private NodeWithChildrenDTO(String name,
                                String parent,
                                List<NodeWithChildrenDTO> children,
                                Integer height) {
        super(name, parent, height);
        this.children = children;
    }
}
