package amazing.co.controllers;

import amazing.co.controllers.dtos.NodeDTO;
import amazing.co.models.Company;
import amazing.co.models.Node;
import amazing.co.services.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class NodeController {

    private NodeService nodeService;

    @Autowired
    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping("/companies/{company}/nodes")
    @ResponseBody
    public ResponseEntity<NodeDTO> createRootNode(@PathVariable Company company,
                                                  @RequestBody @Valid NodeDTO nodeDTO) {
        Node node = nodeDTO.toNode(company);
        Node nodeWithId = nodeService.create(node);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nodeWithId.toDTO());
    }

    @PostMapping("/companies/{company}/nodes/{parent}")
    @ResponseBody
    public ResponseEntity<NodeDTO> createNonRootNode(@PathVariable Node parent,
                                                     @RequestBody @Valid NodeDTO nodeDTO) {
        Node node = nodeDTO.toNonRootNode(parent);
        Node nodeWithId = nodeService.create(node);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nodeWithId.toDTO());
    }
}
