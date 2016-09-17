package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.BoardGame;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Integer> {
	
	@Query("select b from BoardGame b where b.events.size = (select max(bg.events.size) from BoardGame bg))")
	Collection<BoardGame> usedInMostEvents();
	
	@Query("select b from BoardGame b where b.events.size = (select min(bg.events.size) from BoardGame bg))")
	Collection<BoardGame> usedInLessEvents();

}
