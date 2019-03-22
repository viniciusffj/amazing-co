package amazing.co.services;

import amazing.co.exceptions.DuplicatedEntityException;
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
    public void shouldThrowWhenUpdatingRootNode() {
        Node node = Node.rootNode("A node", new Company("Company"));

        exceptionRule.expect(IllegalStateException.class);

        nonRootNodeService.update(node, 90000L);
    }
}