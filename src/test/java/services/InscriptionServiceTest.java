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
import domain.Event;
import domain.Inscription;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class InscriptionServiceTest extends AbstractTest {
	
	//Supporting services

	@Autowired
	private InscriptionService inscriptionService;
	
	@Autowired
	private EventService eventService;
	
	// -------------------------------------------------------
	
	// A user who is authenticated as a player must be able
	// to join or disjoin an event (except deadline is over
	// or there are the maximum number of participants).
	
	// POSITIVE TEST CASE: A player can join a not closed event.
	
	@Test
	public void testJoinEvent() {
		authenticate("player4");
		Collection<Event> events = eventService.findAll();
		int eventId = 0;
		for (Event e: events) {
			if (e.getTitle().equals("Presentación Risk")) {
				eventId = e.getId();
				break;
			}
		}
		Inscription inscription = inscriptionService.create(eventId);
		inscriptionService.save(inscription);
	}
	
	// NEGATIVE TEST CASE: A player cannot join a closed event.
	
	@Test(expected=IllegalArgumentException.class)
	public void testJoinClosedEvent() {
		authenticate("player4");
		Collection<Event> events = eventService.findAll();
		int eventId = 0;
		for (Event e: events) {
			if (e.getTitle().equals("Torneo de Stratego")) {
				eventId = e.getId();
				break;
			}
		}
		Inscription inscription = inscriptionService.create(eventId);
		inscriptionService.save(inscription);
	}
	
	// NEGATIVE TEST CASE: A business cannot join an event.
	
	@Test(expected=IllegalArgumentException.class)
	public void testBusinessJoinEvent() {
		authenticate("business3");
		Collection<Event> events = eventService.findAll();
		int eventId = 0;
		for (Event e: events) {
			if (e.getTitle().equals("Presentación Risk")) {
				eventId = e.getId();
				break;
			}
		}
		Inscription inscription = inscriptionService.create(eventId);
		inscriptionService.save(inscription);
	}
	
	// -------------------------------------------------------

}
