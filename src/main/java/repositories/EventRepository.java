package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
	
	@Query("select e from Event e where e.business.id = ?1")
	Collection<Event> findByBusinessId(int businessId);

	@Query("select avg(p.inscriptions.size) from Player p")
	Double averageJoinedPerPlayer();
	
	@Query("select avg(b.events.size) from Business b")
	Double averageCreatedPerBusiness();

}
