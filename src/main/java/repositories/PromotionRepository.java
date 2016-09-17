package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
	
	@Query("select p from Promotion p where p.business.id = ?1")
	Collection<Promotion> findByBusinessId(int businessId);
	
	@Query("select p, i.gameList from Promotion p join p.inscriptions i where i.player.id = ?1")
	Collection<Object[]> findByPlayerId(int playerId);

}
