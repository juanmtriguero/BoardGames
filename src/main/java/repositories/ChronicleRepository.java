package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Chronicle;

@Repository
public interface ChronicleRepository extends JpaRepository<Chronicle, Integer> {

}
