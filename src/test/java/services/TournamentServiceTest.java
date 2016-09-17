package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import domain.Stage;
import domain.Tournament;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TournamentServiceTest extends AbstractTest {
	
	//Supporting services

	@Autowired
	private TournamentService tournamentService;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private BoardGameService boardGameService;
	
	// -------------------------------------------------------
	
	// A user who is authenticated must be able to
	// list the tournaments that a business have created.
	
	// POSITIVE TEST CASE: A player can list the tournaments of a business.
	
	@Test
	public void testListTournamentsOfBusiness() {
		authenticate("player1");
		Collection<Business> businesses = businessService.findAll();
		int businessId = 0;
		for (Business b: businesses) {
			if (b.getName().equals("Juegos Sevilla")) {
				businessId = b.getId();
				break;
			}
		}
		Collection<Tournament> tournaments = tournamentService
				.findByBusinessId(businessId);
		Assert.isTrue(tournaments.size()==2);
	}
	
	// NEGATIVE TEST CASE: An anonymous user
	// cannot list the tournaments of a business.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousListTournamentsOfBusiness() {
		Collection<Business> businesses = businessService.findAll();
		int businessId = 0;
		for (Business b: businesses) {
			if (b.getName().equals("Juegos Sevilla")) {
				businessId = b.getId();
				break;
			}
		}
		tournamentService.findByBusinessId(businessId);
	}
	
	// NEGATIVE TEST CASE: Business ID must be valid.
	
	@Test(expected=IllegalArgumentException.class)
	public void testListTournamentsOfBusinessWrongId() {
		authenticate("player1");
		tournamentService.findByBusinessId(-5);
	}
	
	// -------------------------------------------------------
	
	// A user who is authenticated must be able to list
	// the tournaments that are available in the system.
	
	// POSITIVE TEST CASE: A player can list all tournaments.
	
	@Test
	public void testListTournaments() {
		authenticate("player1");
		Collection<Tournament> all = tournamentService.findAll();
		Assert.isTrue(all.size()==2);
	}
	
	// NEGATIVE TEST CASE: An anonymous user cannot list all tournaments.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousListTournaments() {
		tournamentService.findAll();
	}

	// -------------------------------------------------------
	
	// A user who is authenticated as a player must be
	// able to see the tournaments that he or she has joined.
	
	// POSITIVE TEST CASE: A player can list his/her joined tournaments.
	
	@Test
	public void testListTournamentsInscribed() {
		authenticate("player1");
		Collection<Object[]> inscribed = tournamentService.findInscribed();
		Assert.isTrue(inscribed.size()==1);
	}
	
	// NEGATIVE TEST CASE: An anonymous user cannot list his/her joined tournaments.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousListTournamentsInscribed() {
		tournamentService.findInscribed();
	}
	
	// NEGATIVE TEST CASE: A business cannot list his/her joined tournaments.
	
	@Test(expected=IllegalArgumentException.class)
	public void testBusinessListTournamentsInscribed() {
		authenticate("business1");
		tournamentService.findInscribed();
	}

	// -------------------------------------------------------
	
	// A user who is authenticated as a business must be
	// able to manage their tournaments. Managing implies
	// creating, listing, modifying, or deleting them.
	
	// POSITIVE TEST CASE: A business can create a tournament.
	
	@SuppressWarnings("deprecation")
	@Test
	public void testCreateTournament() {
		authenticate("business1");
		Tournament tournament = tournamentService.create();
		BoardGame boardGame = null;
		for (BoardGame bg: boardGameService.findAll()) {
			if (bg.getTitle().equals("Risk")) {
				boardGame = bg;
				break;
			}
		}
		tournament.setBoardGame(boardGame);
		tournament.setTitle("Segundo torneo de Risk");
		tournament.setDescription("¿Conquistarás el mundo?");
		tournament.setStartMoment(new Date("25/11/2016 12:00"));
		tournament.setFinishMoment(new Date("25/11/2016 21:00"));
		tournament.setInscriptionDeadline(new Date("20/11/2016 12:00"));
		tournament.setLocation("ChIJc_iiI6RuEg0RbGU-Woe5QCY");
		tournament.setNumberMaxParticipants(24);
		tournament.setPrice(15.0);
		tournament.setAward("Un vale de 20 euros en Fnac");
		tournamentService.save(tournament);
	}
	
	// NEGATIVE TEST CASE: A business cannot delete a
	// tournament if there is players that have joined to it.
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteJoinedTournament() {
		authenticate("business1");
		Collection<Tournament> all = tournamentService.findMyTournaments();
		Tournament Tournament = null;
		for (Tournament p: all) {
			if (p.getTitle().equals("Torneo de Stratego")) {
				Tournament = p;
				break;
			}
		}
		tournamentService.delete(Tournament);
	}
	
	// NEGATIVE TEST CASE: A player cannot create a tournament.

	@SuppressWarnings("deprecation")
	@Test(expected=IllegalArgumentException.class)
	public void testPlayerCreateTournament() {
		authenticate("player1");
		Tournament tournament = tournamentService.create();
		BoardGame boardGame = null;
		for (BoardGame bg: boardGameService.findAll()) {
			if (bg.getTitle().equals("Risk")) {
				boardGame = bg;
				break;
			}
		}
		tournament.setBoardGame(boardGame);
		tournament.setTitle("Segundo torneo de Risk");
		tournament.setDescription("¿Conquistarás el mundo?");
		tournament.setStartMoment(new Date("25/11/2016 12:00"));
		tournament.setFinishMoment(new Date("25/11/2016 21:00"));
		tournament.setInscriptionDeadline(new Date("20/11/2016 12:00"));
		tournament.setLocation("ChIJc_iiI6RuEg0RbGU-Woe5QCY");
		tournament.setNumberMaxParticipants(24);
		tournament.setPrice(15.0);
		tournament.setAward("Un vale de 20 euros en Fnac");
		tournamentService.save(tournament);
	}

	// -------------------------------------------------------
	
	// A user who is authenticated as a business must be able to
	// see the organisation of one of its tournaments. This
	// organisation only will be available if the inscription is
	// closed, and will change automatically during the tournament.
	
	// POSITIVE TEST CASE: A business can see the
	// organisation of one of its closed tournaments.
	
	@Test
	public void testOrganisation() {
		authenticate("business1");
		Collection<Tournament> tournaments = tournamentService.findMyTournaments();
		int tournamentId = 0;
		for (Tournament t: tournaments) {
			if (t.getTitle().equals("Torneo de Stratego")) {
				tournamentId = t.getId();
				break;
			}
		}
		List<Stage> stages = tournamentService.organisation(tournamentId);
		Assert.isTrue(stages.size()==1);
	}
	
	// NEGATIVE TEST CASE: A business cannot see the
	// organisation of one of its not closed tournaments.
	
	@Test(expected=IllegalArgumentException.class)
	public void testOrganisationNotClosed() {
		authenticate("business1");
		Collection<Tournament> tournaments = tournamentService.findMyTournaments();
		int tournamentId = 0;
		for (Tournament t: tournaments) {
			if (t.getTitle().equals("Torneo de Risk")) {
				tournamentId = t.getId();
				break;
			}
		}
		tournamentService.organisation(tournamentId);
	}
	
	// NEGATIVE TEST CASE: A business cannot see the
	// organisation of a tournament that it has not created.
	
	@Test(expected=IllegalArgumentException.class)
	public void testOrganisationNotMine() {
		authenticate("business2");
		Collection<Tournament> tournaments = tournamentService.findAll();
		int tournamentId = 0;
		for (Tournament t: tournaments) {
			if (t.getTitle().equals("Torneo de Stratego")) {
				tournamentId = t.getId();
				break;
			}
		}
		tournamentService.organisation(tournamentId);
	}

	// -------------------------------------------------------

}
