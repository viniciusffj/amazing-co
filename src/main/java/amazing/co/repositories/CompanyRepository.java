package amazing.co.repositories;

import amazing.co.models.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {
    boolean existsByName(String name);
}
