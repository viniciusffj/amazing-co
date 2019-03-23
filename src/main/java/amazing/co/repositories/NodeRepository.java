package amazing.co.repositories;

import amazing.co.models.Company;
import amazing.co.models.Node;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NodeRepository extends CrudRepository<Node, Long> {
    boolean existsByNameAndCompany(String name, Company company);

    boolean existsWhereRootIsNullByCompany(Company company);

    Optional<Node> findByNameAndCompany(String name, Company company);
}
