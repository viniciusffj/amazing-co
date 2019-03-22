package amazing.co.services;

import amazing.co.exceptions.DuplicatedEntityException;
import amazing.co.models.Node;
import amazing.co.repositories.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodeService {

    private NodeRepository nodeRepository;

    @Autowired
    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public Node create(Node node) {
        if (nodeExists(node)) {
            throw new DuplicatedEntityException("Node already exists");
        }

        return nodeRepository.save(node);
    }

    private boolean nodeExists(Node node) {
        return nodeRepository.existsByNameAndCompany(node.getName(), node.getCompany());
    }

    public Node createRootNode(Node rootNode) {
        if (rootNodeExists(rootNode)) {
            throw new DuplicatedEntityException("Root node already exists");
        }

        return nodeRepository.save(rootNode);
    }

    private boolean rootNodeExists(Node node) {
        return nodeRepository.existsWhereRootIsNullByCompany(node.getCompany());
    }
}
