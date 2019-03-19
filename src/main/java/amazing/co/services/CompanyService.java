package amazing.co.services;

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

    public void insert(Company company) {
        this.companyRepository.save(company);
    }
}
