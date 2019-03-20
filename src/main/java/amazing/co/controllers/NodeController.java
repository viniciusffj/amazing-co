package amazing.co.controllers;

import amazing.co.controllers.dtos.NodeDTO;
import amazing.co.models.Company;
import amazing.co.models.Node;
import amazing.co.repositories.CompanyRepository;
import amazing.co.services.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NodeController {

    private NodeService nodeService;
    private CompanyRepository companyRepository;

    @Autowired
    public NodeController(NodeService nodeService,
                          CompanyRepository companyRepository) {
        this.nodeService = nodeService;
        this.companyRepository = companyRepository;
    }

    @PostMapping("/companies/{company}/nodes")
    @ResponseBody
    public ResponseEntity<NodeDTO> createRootNode(@PathVariable Company company,
                                               @RequestBody NodeDTO nodeDTO) {
        Node node = nodeDTO.toNode(company);
        Node nodeWithId = nodeService.create(node);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nodeWithId.toDTO());
    }
}
