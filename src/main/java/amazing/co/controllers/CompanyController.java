package amazing.co.controllers;

import amazing.co.models.Company;
import amazing.co.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyController {
    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Company company) {
        this.companyService.create(company);
    }
}
