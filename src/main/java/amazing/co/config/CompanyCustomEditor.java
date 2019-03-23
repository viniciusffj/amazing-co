package amazing.co.config;

import amazing.co.exceptions.EntityNotFoundException;
import amazing.co.models.Company;
import amazing.co.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyEditorSupport;
import java.util.Optional;

@Service
public class CompanyCustomEditor extends PropertyEditorSupport {
    private CompanyRepository companyRepository;

    @Autowired
    public CompanyCustomEditor(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Optional<Company> optionalCompany = companyRepository.findById(Long.parseLong(text));

        if (optionalCompany.isPresent()) {
            setValue(optionalCompany.get());
        } else {
            throw new EntityNotFoundException(String.format("Company %s does not exist", text));
        }
    }

}
