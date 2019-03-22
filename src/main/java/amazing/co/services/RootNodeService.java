package amazing.co.services;

import amazing.co.exceptions.DuplicatedEntityException;
import amazing.co.models.Node;
import amazing.co.repositories.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RootNodeService {

    private NodeRepository nodeRepository;

    @Autowired
    public RootNodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public Node createNode(Node rootNode) {
        if (rootNodeExists(rootNode)) {
            throw new DuplicatedEntityException("Root node already exists");
        }

        return nodeRepository.save(rootNode);
    }

    private boolean rootNodeExists(Node node) {
        return nodeRepository.existsWhereRootIsNullByCompany(node.getCompany());
    }
}
