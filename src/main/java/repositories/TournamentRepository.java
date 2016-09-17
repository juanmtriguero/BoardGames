package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tournament;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
	
	@Query("select t from Tournament t where t.business.id = ?1")
	Collection<Tournament> findByBusinessId(int businessId);
	
	@Query("select t, i.gameList from Tournament t join t.inscriptions i where i.player.id = ?1")
	Collection<Object[]> findByPlayerId(int playerId);

}
