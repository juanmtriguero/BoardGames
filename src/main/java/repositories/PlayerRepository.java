package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
	
	@Query("select p, i.gameList from Player p join p.inscriptions i where i.event.id = ?1")
	Collection<Object[]> findByEventId(int eventId);
	
	@Query("select p from Player p where p.inscriptions.size = (select max(pl.inscriptions.size) from Player pl))")
	Collection<Player> joinedMostEvents();
	
	@Query("select p from Player p where p.inscriptions.size = (select min(pl.inscriptions.size) from Player pl))")
	Collection<Player> joinedLessEvents();

	@Query("select p from Player p where p.userAccount = ?1")
	Player findByUserAccount(UserAccount userAccount);
	
	@Query("select avg(p.followers.size) from Player p")
	Double averageFollowedPerPlayer();

}
