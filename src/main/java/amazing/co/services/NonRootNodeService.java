package amazing.co.services;

import amazing.co.exceptions.DuplicatedEntityException;
import amazing.co.models.Node;
import amazing.co.repositories.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NonRootNodeService {

    private NodeRepository nodeRepository;

    @Autowired
    public NonRootNodeService(NodeRepository nodeRepository) {
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

    public Node update(Node node, Long newParentId) {
        if (node.isRootNode()) {
            throw new IllegalStateException("Cannot change root parent");
        }

        Node parent = nodeRepository.findById(newParentId).get();
        node.setParent(parent);
        return nodeRepository.save(node);
    }
}
