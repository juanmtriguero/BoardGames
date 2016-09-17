package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Categorization;

@Repository
public interface CategorizationRepository extends JpaRepository<Categorization, Integer> {

}
