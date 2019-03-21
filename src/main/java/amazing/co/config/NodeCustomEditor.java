package amazing.co.config;

import amazing.co.models.Node;
import amazing.co.repositories.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyEditorSupport;
import java.util.Optional;

@Service
public class NodeCustomEditor extends PropertyEditorSupport {
    private NodeRepository nodeRepository;

    @Autowired
    public NodeCustomEditor(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Optional<Node> optionalNode = nodeRepository.findById(Long.parseLong(text));

        setValue(optionalNode.get());
    }

}
