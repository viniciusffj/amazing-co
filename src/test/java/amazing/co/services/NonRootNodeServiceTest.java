package amazing.co.services;

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
    public void shouldThrowWhenCreatingDuplicatedNode() {
        Company company = new Company("A company");
        when(nodeRepository.existsByNameAndCompany("existing-node", company)).thenReturn(true);

        exceptionRule.expect(DuplicatedEntityException.class);

        nonRootNodeService.create(Node.rootNode("existing-node", company));
    }

    @Test
    public void shouldThrowWhenUpdatingRootNode_Old() {
        Node node = Node.rootNode("Root", new Company("Company"));

        exceptionRule.expect(IllegalStateException.class);

        nonRootNodeService.update(node, 90000L);
    }

    @Test
    public void shouldThrowWhenUpdatingInvalidNode_Old() {
        long invalidNewParentId = 90000L;
        when(nodeRepository.findById(invalidNewParentId)).thenReturn(Optional.empty());

        Node root = Node.rootNode("Root", new Company("Company"));
        Node node = Node.nonRootNode("A node", root, root);

        exceptionRule.expect(EntityNotFoundException.class);

        nonRootNodeService.update(node, invalidNewParentId);
    }

    @Test
    public void shouldThrowWhenUpdatingAnExistingNode() {
        Company company = new Company("Company");

        when(nodeRepository.findByNameAndCompany("I-DO-NOT-EXIST", company))
                .thenReturn(Optional.empty());

        exceptionRule.expect(EntityNotFoundException.class);

        nonRootNodeService.update("I-DO-NOT-EXIST", company, "ANOTHER-NODE");
    }

    @Test
    public void shouldThrowWhenUpdatingRootNode() {
        Company company = new Company("Company");

        when(nodeRepository.findByNameAndCompany("Root", company))
                .thenReturn(Optional.of(Node.rootNode("Root", company)));

        exceptionRule.expect(IllegalStateException.class);

        nonRootNodeService.update("Root", company, "ANODE");
    }

    @Test
    public void shouldThrowWhenUpdatingInvalidParentNode() {
        Company company = new Company("Company");
        Node root = Node.rootNode("Root", company);
        Node node = Node.nonRootNode("valid-node", root, root);

        when(nodeRepository.findByNameAndCompany("valid-node", company))
                .thenReturn(Optional.of(node));

        when(nodeRepository.findByNameAndCompany("not-a-valid-node", company))
                .thenReturn(Optional.empty());


        exceptionRule.expect(EntityNotFoundException.class);

        nonRootNodeService.update("valid-node", company, "not-a-valid-node");
    }
}