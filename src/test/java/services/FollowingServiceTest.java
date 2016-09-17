package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Following;
import domain.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class FollowingServiceTest extends AbstractTest {
	
	//Supporting services

	@Autowired
	private FollowingService followingService;
	
	@Autowired
	private PlayerService playerService;
	
	// -------------------------------------------------------
	
	// A user who is authenticated as a player must
	// be able to follow or unfollow a player.
	
	// POSITIVE TEST CASE: A player can follow a player.
	
	@Test
	public void testFollowPlayer() {
		authenticate("player1");
		Collection<Player> players = playerService.findAll();
		int playerId = 0;
		for (Player p: players) {
			if (p.getName().equals("Natalia Domínguez")) {
				playerId = p.getId();
				break;
			}
		}
		Following following = followingService.create(playerId);
		followingService.save(following);
	}
	
	// NEGATIVE TEST CASE: A player cannot follow himself.
	
	@Test(expected=IllegalArgumentException.class)
	public void testFollowHimself() {
		authenticate("player4");
		Collection<Player> players = playerService.findAll();
		int playerId = 0;
		for (Player p: players) {
			if (p.getName().equals("Natalia Domínguez")) {
				playerId = p.getId();
				break;
			}
		}
		Following following = followingService.create(playerId);
		followingService.save(following);
	}
	
	// NEGATIVE TEST CASE: A business cannot follow a player.
	
	@Test(expected=IllegalArgumentException.class)
	public void testBusinessFollowPlayer() {
		authenticate("business3");
		Collection<Player> players = playerService.findAll();
		int playerId = 0;
		for (Player p: players) {
			if (p.getName().equals("Natalia Domínguez")) {
				playerId = p.getId();
				break;
			}
		}
		Following following = followingService.create(playerId);
		followingService.save(following);
	}
	
	// -------------------------------------------------------

}
