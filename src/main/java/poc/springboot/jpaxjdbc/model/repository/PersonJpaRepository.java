package poc.springboot.jpaxjdbc.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poc.springboot.jpaxjdbc.model.entity.PersonJpa;

/**
 * The {@link JpaRepository} for {@link PersonJpa} entity
 */
@Repository
public interface PersonJpaRepository extends JpaRepository<PersonJpa, Long> {
}
