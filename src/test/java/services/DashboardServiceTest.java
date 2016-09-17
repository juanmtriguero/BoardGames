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
import domain.Business;
import domain.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class DashboardServiceTest extends AbstractTest {
	
	//Supporting services
	
	@Autowired
	private BoardGameService boardGameService;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private BusinessService businessService;
	

	// -------------------------------------------------------
	
	// A user who is authenticated as an administrator must be
	// able to display a dash board with the following information:
	// - The board game which has been used in more events.
	// - The board game which has been used in less events.
	// - The player who has joined more events.
	// - The player who has joined less events.
	// - The average of events joined per player.
	// - The average of events created per business.
	// - The player who has played more different board games (in events).
	// - The business which has the best rating.
	// - The business which has the worst rating.
	// - The average of players followed per player.
	
	// POSITIVE USE CASE: An administrator can see the dash board
	
	@Test
	public void testAdminDashboard() {
		authenticate("admin1");
		testDashboard();
	}

	// NEGATIVE USE CASE: A player cannot see the dash board
	
	@Test(expected=IllegalArgumentException.class)
	public void testPlayerDashboard() {
		authenticate("player1");
		testDashboard();
	}
	
	// NEGATIVE USE CASE: An anonymous user cannot see the dash board
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousDashboard() {
		testDashboard();
	}
	
	@SuppressWarnings("unchecked")
	private void testDashboard() {
		
		Collection<BoardGame> usedInMostEvents = 
				boardGameService.usedInMostEvents();
		Assert.isTrue(usedInMostEvents.size()==1);
		boolean aux1 = false;
		for (BoardGame bg: usedInMostEvents) {
			if (bg.getTitle().equals("Risk")) {
				aux1 = true;
			}
		}
		Assert.isTrue(aux1);
		
		Collection<BoardGame> usedInLessEvents = 
				boardGameService.usedInLessEvents();
		Assert.isTrue(usedInLessEvents.size()==1);
		boolean aux2 = false;
		for (BoardGame bg: usedInLessEvents) {
			if (bg.getTitle().equals("Netrunner")) {
				aux2 = true;
			}
		}
		Assert.isTrue(aux2);
		
		Collection<Player> joinedMostEvents = 
				playerService.joinedMostEvents();
		Assert.isTrue(joinedMostEvents.size()==1);
		boolean aux3 = false;
		for (Player p: joinedMostEvents) {
			if (p.getName().equals("Antonio Pérez")) {
				aux3 = true;
			}
		}
		Assert.isTrue(aux3);
		
		Collection<Player> joinedLessEvents = 
				playerService.joinedLessEvents();
		Assert.isTrue(joinedLessEvents.size()==1);
		boolean aux4 = false;
		for (Player p: joinedLessEvents) {
			if (p.getName().equals("Natalia Domínguez")) {
				aux4 = true;
			}
		}
		Assert.isTrue(aux4);
		
		Assert.isTrue(eventService.averageJoinedPerPlayer()==1.0);
		Assert.isTrue(eventService.averageCreatedPerBusiness()==1.3333);
		
		Object[] aux = playerService.playedMostBoardGames();
		Collection<Player> playedMostBoardGames = (Collection<Player>) aux[0];
		boolean aux5 = false;
		for (Player p: playedMostBoardGames) {
			if (p.getName().equals("Antonio Pérez")) {
				aux5 = true;
			}
		}
		Assert.isTrue(aux5);
		Integer max = (Integer) aux[1];
		Assert.isTrue(max==2);
		
		Collection<Business> bestRated = businessService.bestRated();
		Assert.isTrue(bestRated.size()==1);
		boolean aux6 = false;
		for (Business b: bestRated) {
			if (b.getName().equals("Mesa 291")) {
				aux6 = true;
			}
		}
		Assert.isTrue(aux6);
		
		Collection<Business> worstRated = businessService.worstRated();
		Assert.isTrue(worstRated.size()==1);
		boolean aux7 = false;
		for (Business b: worstRated) {
			if (b.getName().equals("Ágora Juegos")) {
				aux7 = true;
			}
		}
		Assert.isTrue(aux7);
		
		Assert.isTrue(playerService.averageFollowedPerPlayer()==1.0);
		
	}
	
	// -------------------------------------------------------

}
