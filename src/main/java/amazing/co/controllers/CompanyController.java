package amazing.co.controllers;

import amazing.co.models.Company;
import amazing.co.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyController {
    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    @ResponseBody
    public ResponseEntity<Company> create(@RequestBody Company company) {
        Company companyWithId = companyService.create(company);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(companyWithId);
    }
}
