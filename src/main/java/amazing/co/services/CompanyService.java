package amazing.co.services;

import amazing.co.exceptions.DuplicatedEntityException;
import amazing.co.exceptions.EntityNotFoundException;
import amazing.co.models.Company;
import amazing.co.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company create(Company company) {
        if (companyExists(company)) {
            throw new DuplicatedEntityException("Company is already registered");
        }
        return companyRepository.save(company);
    }

    private boolean companyExists(Company company) {
        return companyRepository.existsByName(company.getName());
    }

    public void delete(Long id) {
        if (companyDoesNotExists(id)) {
            throw new EntityNotFoundException("Company does not exist");
        }
        companyRepository.deleteById(id);
    }

    private boolean companyDoesNotExists(Long id) {
        return !companyRepository.existsById(id);
    }
}
