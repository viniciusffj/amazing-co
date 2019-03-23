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
public class RootNodeServiceTest {
    @Mock
    private NodeRepository nodeRepository;

    @InjectMocks
    private RootNodeService rootNodeService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void shouldThrowErrorWhenCreatingDuplicatedRootNode() {
        Company company = new Company("A company");
        when(nodeRepository.existsWhereRootIsNullByCompany(company)).thenReturn(true);

        exceptionRule.expect(DuplicatedEntityException.class);

        rootNodeService.createNode(Node.rootNode("duplicated-root", company));
    }

}