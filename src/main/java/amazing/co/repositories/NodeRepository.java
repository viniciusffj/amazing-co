package amazing.co.repositories;

import amazing.co.models.Company;
import amazing.co.models.Node;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NodeRepository extends JpaRepository<Node, Long> {
    boolean existsByNameAndCompany(String name, Company company);

    boolean existsWhereRootIsNullByCompany(Company company);

    Optional<Node> findByNameAndCompany(String name, Company company);

    List<Node> findAllByParent(Node parent);
}
