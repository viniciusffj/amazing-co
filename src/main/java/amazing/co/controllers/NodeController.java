package amazing.co.controllers;

import amazing.co.controllers.dtos.NodeDTO;
import amazing.co.controllers.dtos.UpdateNodeDTO;
import amazing.co.models.Company;
import amazing.co.models.Node;
import amazing.co.services.NonRootNodeService;
import amazing.co.services.RootNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class NodeController {

    private NonRootNodeService nonRootNodeService;
    private RootNodeService rootNodeService;

    @Autowired
    public NodeController(NonRootNodeService nonRootNodeService,
                          RootNodeService rootNodeService) {
        this.nonRootNodeService = nonRootNodeService;
        this.rootNodeService = rootNodeService;
    }

    @PostMapping("/companies/{company}/nodes")
    @ResponseBody
    public ResponseEntity<NodeDTO> createRootNode(@PathVariable Company company,
                                                  @RequestBody @Valid NodeDTO nodeDTO) {
        Node node = nodeDTO.toNode(company);
        Node nodeWithId = rootNodeService.createNode(node);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nodeWithId.toDTO());
    }

    @PostMapping("/companies/{company}/nodes/{parent}/children")
    @ResponseBody
    public ResponseEntity<NodeDTO> createNonRootNode(@PathVariable Company company,
                                                     @PathVariable String parent,
                                                     @RequestBody @Valid NodeDTO nodeDTO) {
        Node node = nonRootNodeService.create(nodeDTO, company, parent);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(node.toDTO());
    }

    @PutMapping("companies/{company}/nodes/{node}")
    @ResponseBody
    public ResponseEntity<NodeDTO> updateNode(@PathVariable Company company,
                                              @PathVariable String node,
                                              @RequestBody @Valid UpdateNodeDTO updateNodeDTO) {
        try {
            Node updatedNode = nonRootNodeService.update(node, company, updateNodeDTO.getNewParent());
            return ResponseEntity.ok(updatedNode.toDTO());
        } catch (IllegalStateException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

    }
}
