package amazing.co.services;

import amazing.co.exceptions.DuplicatedEntityException;
import amazing.co.exceptions.EntityNotFoundException;
import amazing.co.models.Company;
import amazing.co.models.Node;
import amazing.co.repositories.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Node create(String nodeName, Company company, String parentName) {
        if (nodeExists(nodeName, company)) {
            throw new DuplicatedEntityException("Node already exists");
        }

        Node parentNode = findNode(parentName, company);

        return parentNode;
    }

    private boolean nodeExists(String nodeName, Company company) {
        return nodeRepository.existsByNameAndCompany(nodeName, company);
    }

    public Node update(Node node, Long newParentId) {
        if (node.isRootNode()) {
            throw new IllegalStateException("Cannot change root parent");
        }

        Optional<Node> optionalNode = nodeRepository.findById(newParentId);

        if (!optionalNode.isPresent()) {
            throw new EntityNotFoundException("New parent node does not exist");
        }

        Node parent = optionalNode.get();
        node.setParent(parent);
        return nodeRepository.save(node);
    }

    public Node update(String nodeName, Company company, String newParent) {
        Node node = findNode(nodeName, company);

        if (node.isRootNode()) {
            throw new IllegalStateException("Cannot change root parent");
        }

        Node parentNode = findNode(newParent, company);

        return null;
    }

    private Node findNode(String nodeName, Company company) {
        Optional<Node> optionalNode = nodeRepository.findByNameAndCompany(nodeName, company);

        if (!optionalNode.isPresent()) {
            throw new EntityNotFoundException("Node does not exist");
        }

        return optionalNode.get();
    }
}
