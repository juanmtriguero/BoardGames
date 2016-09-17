package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.BoardGame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class BoardGameServiceTest extends AbstractTest {
	
	//Supporting services

	@Autowired
	private BoardGameService boardGameService;
	
	// -------------------------------------------------------
	
	// A user who is authenticated as an administrator
	// must be able to manage the catalogue of board games.
	// Managing implies creating, modifying, or deleting them.
	
	// POSITIVE TEST CASE: An administrator can delete
	// a board game that is not used in any event.
	
	@Test
	public void testDeleteUnusedBoardGame() {
		authenticate("admin1");
		Collection<BoardGame> all = boardGameService.findAll();
		Integer size = all.size();
		BoardGame boardGame = null;
		for (BoardGame bg: all) {
			if (bg.getTitle().equals("Netrunner")) {
				boardGame = bg;
				break;
			}
		}
		boardGameService.delete(boardGame);
		Assert.isTrue(boardGameService.findAll().size()==size-1);
	}
	
	// NEGATIVE TEST CASE: An administrator cannot delete
	// a board game that is used in an event.
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteUsedBoardGame() {
		authenticate("admin1");
		BoardGame boardGame = null;
		for (BoardGame bg: boardGameService.findAll()) {
			if (bg.getTitle().equals("Risk")) {
				boardGame = bg;
				break;
			}
		}
		boardGameService.delete(boardGame);
	}
	
	// NEGATIVE TEST CASE: An user which is not an
	// administrator cannot create a board game.

	@Test(expected=IllegalArgumentException.class)
	public void testCreateBoardGame() {
		authenticate("player1");
		BoardGame boardGame = boardGameService.create();
		boardGame.setTitle("¡Rescate!");
		boardGame.setDescription("Conviértete en bombero y salva vidas.");
		boardGame.setNumberMaxPlayers(6);
		boardGameService.save(boardGame);
	}
	
	// -------------------------------------------------------

}
