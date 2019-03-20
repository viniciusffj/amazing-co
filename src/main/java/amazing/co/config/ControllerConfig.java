package amazing.co.config;

import amazing.co.models.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class ControllerConfig {

    private CompanyCustomEditor companyCustomEditor;

    @Autowired
    public ControllerConfig(CompanyCustomEditor companyCustomEditor) {
        this.companyCustomEditor = companyCustomEditor;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Company.class, companyCustomEditor);
    }

}
