package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Event;
import domain.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class PlayerServiceTest extends AbstractTest {
	
	//Supporting services

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private EventService eventService;
	
	// -------------------------------------------------------
	
	// A user who is not authenticated must be able
	// to register to the system as a player.
	
	// POSITIVE TEST CASE: An anonymous user
	// can register to the system as a player.
	
	@Test
	public void testRegisterPlayer() {
		Player player = playerService.create();
		UserAccount acc = player.getUserAccount();
		acc.setUsername("playerTest");
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		acc.setPassword(encoder.encodePassword("passTest", null));
		player.setUserAccount(acc);
		player.setName("Manuel martínez");
		player.setEmail("manmar@mail.com");
		playerService.save(player);
	}
	
	// NEGATIVE TEST CASE: An authenticated user
	// cannot register to the system as a player.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAuthenticatedRegisterPlayer() {
		authenticate("player1");
		Player player = playerService.create();
		UserAccount acc = player.getUserAccount();
		acc.setUsername("playerTest");
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		acc.setPassword(encoder.encodePassword("passTest", null));
		player.setUserAccount(acc);
		player.setName("Manuel martínez");
		player.setEmail("manmar@mail.com");
		playerService.save(player);
	}
	
	// NEGATIVE TEST CASE: An anonymous user cannot register 
	// to the system as a player with an used email.

	@Test(expected=DataIntegrityViolationException.class)
	public void testRegisterPlayerUsedEmail() {
		Player player = playerService.create();
		UserAccount acc = player.getUserAccount();
		acc.setUsername("playerTest");
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		acc.setPassword(encoder.encodePassword("passTest", null));
		player.setUserAccount(acc);
		player.setName("Manuel martínez");
		player.setEmail("aperez@mail.com");
		playerService.save(player);
	}

	// -------------------------------------------------------
	
	// A user who is authenticated must be able to
	// list and see the players of the system.
	
	// POSITIVE TEST CASE: A player can list all players.
	
	@Test
	public void testListPlayers() {
		authenticate("player1");
		Collection<Player> all = playerService.findAll();
		Assert.isTrue(all.size()==4);
	}
	
	// NEGATIVE TEST CASE: An anonymous user cannot list all players.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousListPlayers() {
		Collection<Player> all = playerService.findAll();
		Assert.isTrue(all.size()==4);
	}

	// -------------------------------------------------------
	
	// A user who is authenticated must be able to
	// list the players that have joined to an event.
	
	// POSITIVE TEST CASE: A player can list the participants of an event.
	
	@Test
	public void testListParticipantsOfEvent() {
		authenticate("player1");
		Collection<Event> events = eventService.findAll();
		int eventId = 0;
		for (Event e: events) {
			if (e.getTitle().equals("Torneo de Stratego")) {
				eventId = e.getId();
				break;
			}
		}
		Collection<Object[]> players = playerService
				.findByEventId(eventId);
		Assert.isTrue(players.size()==2);
	}
	
	// NEGATIVE TEST CASE: An anonymous user
	// cannot list the participants of an event.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousListParticipantsOfEvent() {
		Collection<Event> events = eventService.findAll();
		int eventId = 0;
		for (Event e: events) {
			if (e.getTitle().equals("Torneo de Stratego")) {
				eventId = e.getId();
				break;
			}
		}
		Collection<Object[]> players = playerService
				.findByEventId(eventId);
		Assert.isTrue(players.size()==2);
	}
	
	// NEGATIVE TEST CASE: Business ID must be valid.
	
	@Test(expected=IllegalArgumentException.class)
	public void testListParticipantsOfEventWrongId() {
		authenticate("player1");
		playerService.findByEventId(-5);
	}
	
	// -------------------------------------------------------
	
	// A user who is authenticated as a player must be able
	// to list the players that he or she is following.
	
	// POSITIVE TEST CASE: A player can list his/her followed.
	
	@Test
	public void testListFolloweds() {
		authenticate("player1");
		Collection<Player> followeds = playerService.followeds();
		Assert.isTrue(followeds.size()==2);
	}
	
	// NEGATIVE TEST CASE: A business cannot list its followed.
	
	@Test(expected=IllegalArgumentException.class)
	public void testBusinessListFolloweds() {
		authenticate("business1");
		playerService.followeds();
	}
	
	// NEGATIVE TEST CASE: An anonymous user cannot list his/her followed.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousListFolloweds() {
		playerService.followeds();
	}
	
	// -------------------------------------------------------
	
	// A user who is authenticated as a player must be
	// able to list the players that follows him or her.
	
	// POSITIVE TEST CASE: A player can list his/her followers.
	
	@Test
	public void testListFollowers() {
		authenticate("player2");
		Collection<Player> followers = playerService.followers();
		Assert.isTrue(followers.size()==1);
	}
	
	// NEGATIVE TEST CASE: A business cannot list its followers.
	
	@Test(expected=IllegalArgumentException.class)
	public void testBusinessListFollowers() {
		authenticate("business1");
		playerService.followers();
	}
	
	// NEGATIVE TEST CASE: An anonymous user cannot list his/her followers.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousListFollowers() {
		playerService.followers();
	}
	
	// -------------------------------------------------------
	
}
