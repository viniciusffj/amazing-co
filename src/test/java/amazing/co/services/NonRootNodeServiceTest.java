package amazing.co.services;

import amazing.co.controllers.dtos.NodeDTO;
import amazing.co.exceptions.DuplicatedEntityException;
import amazing.co.exceptions.EntityNotFoundException;
import amazing.co.models.Company;
import amazing.co.models.Node;
import amazing.co.repositories.NodeRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NonRootNodeServiceTest {

    @Mock
    private NodeRepository nodeRepository;

    @InjectMocks
    private NonRootNodeService nonRootNodeService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void shouldThrowErrorWhenCreatingDuplicatedNode() {
        Company company = new Company("A company");
        when(nodeRepository.existsByNameAndCompany("existing-node", company)).thenReturn(true);

        exceptionRule.expect(DuplicatedEntityException.class);

        nonRootNodeService.create(new NodeDTO("existing-node", 1), company, "parent-node");
    }

    @Test
    public void shouldThrowErrorWhenCreatingNodeAndParentIsInvalid() {
        Company company = new Company("A company");
        when(nodeRepository.existsByNameAndCompany("existing-node", company)).thenReturn(false);
        when(nodeRepository.findByNameAndCompany("parent-node", company)).thenReturn(Optional.empty());

        exceptionRule.expect(EntityNotFoundException.class);
        exceptionRule.expectMessage("Node parent-node does not exist");

        nonRootNodeService.create(new NodeDTO("existing-node", 1), company, "parent-node");
    }

    @Test
    public void shouldThrowErrorWhenUpdatingAnExistingNode() {
        Company company = new Company("Company");

        when(nodeRepository.findByNameAndCompany("I-DO-NOT-EXIST", company))
                .thenReturn(Optional.empty());

        exceptionRule.expect(EntityNotFoundException.class);
        exceptionRule.expectMessage("Node I-DO-NOT-EXIST does not exist");

        nonRootNodeService.update("I-DO-NOT-EXIST", company, "ANOTHER-NODE");
    }

    @Test
    public void shouldThrowErrorWhenUpdatingRootNode() {
        Company company = new Company("Company");

        when(nodeRepository.findByNameAndCompany("Root", company))
                .thenReturn(Optional.of(Node.rootNode("Root", company)));

        exceptionRule.expect(IllegalStateException.class);

        nonRootNodeService.update("Root", company, "ANODE");
    }

    @Test
    public void shouldThrowErrorWhenUpdatingInvalidParentNode() {
        Company company = new Company("Company");
        Node root = Node.rootNode("Root", company);
        Node node = Node.nonRootNode("valid-node", root, root);

        when(nodeRepository.findByNameAndCompany("valid-node", company))
                .thenReturn(Optional.of(node));

        when(nodeRepository.findByNameAndCompany("not-a-valid-node", company))
                .thenReturn(Optional.empty());


        exceptionRule.expect(EntityNotFoundException.class);
        exceptionRule.expectMessage("Node not-a-valid-node does not exist");

        nonRootNodeService.update("valid-node", company, "not-a-valid-node");
    }

    @Test
    public void shouldThrowErrorWhenUpdatingANodeToBeItsOwnParent() {
        Company company = new Company("Company");

        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("A node cannot be its own parent");

        nonRootNodeService.update("valid-node", company, "valid-node");
    }
}