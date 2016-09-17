package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bulletin;

@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, Integer> {

	@Query("select b from Bulletin b where b.business.id = ?1")
	Collection<Bulletin> findByBusinessId(int businessId);

}
