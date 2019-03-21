package amazing.co.config;

import amazing.co.models.Company;
import amazing.co.models.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditor;

@ControllerAdvice
public class ControllerConfig {

    private CompanyCustomEditor companyCustomEditor;
    private PropertyEditor nodeCustomEditor;

    @Autowired
    public ControllerConfig(CompanyCustomEditor companyCustomEditor,
                            PropertyEditor nodeCustomEditor) {
        this.companyCustomEditor = companyCustomEditor;
        this.nodeCustomEditor = nodeCustomEditor;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Company.class, companyCustomEditor);
        binder.registerCustomEditor(Node.class, nodeCustomEditor);
    }

}
