package amazing.co.services;

import amazing.co.controllers.dtos.NodeDTO;
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

    public Node create(NodeDTO nodeDTO, Company company, String parentName) {
        if (nodeExists(nodeDTO, company)) {
            throw new DuplicatedEntityException("Node already exists");
        }

        Node parentNode = findNode(parentName, company);

        return nodeRepository.save(nodeDTO.toNonRootNode(parentNode));
    }

    private boolean nodeExists(NodeDTO nodeDTO, Company company) {
        return nodeRepository.existsByNameAndCompany(nodeDTO.getName(), company);
    }

    private Node findNode(String nodeName, Company company) {
        Optional<Node> optionalNode = nodeRepository.findByNameAndCompany(nodeName, company);

        if (!optionalNode.isPresent()) {
            throw new EntityNotFoundException("Node does not exist");
        }

        return optionalNode.get();
    }

    public Node update(String nodeName, Company company, String newParent) {
        Node node = findNode(nodeName, company);

        if (node.isRootNode()) {
            throw new IllegalStateException("Cannot change root parent");
        }

        Node parentNode = findNode(newParent, company);
        node.setParent(parentNode);

        return nodeRepository.save(node);
    }
}
