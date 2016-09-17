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
import domain.Chronicle;
import domain.Event;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class ChronicleServiceTest extends AbstractTest {
	
	//Supporting services
	
	@Autowired
	private ChronicleService chronicleService;
	
	@Autowired
	private EventService eventService;
	
	// -------------------------------------------------------
	
	// A user who is authenticated must be able to
	// see the chronicle of an event (if exists).
	
	// POSITIVE TEST CASE: A player can see the the chronicle of an event.
	
	@Test
	public void testSeeChronicleOfEvent() {
		authenticate("player1");
		Collection<Event> events = eventService.findAll();
		int eventId = 0;
		for (Event e: events) {
			if (e.getTitle().equals("Avacyn Restaurado")) {
				eventId = e.getId();
				break;
			}
		}
		Chronicle chronicle = chronicleService.findByEventId(eventId);
		Assert.isTrue(chronicle.getTitle().equals(
				"Buen ambiente en la presentación"));
	}
	
	// NEGATIVE TEST CASE: An anonymous user 
	// cannot see the the chronicle of an event.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousSeeChronicleOfEvent() {
		Collection<Event> events = eventService.findAll();
		int eventId = 0;
		for (Event e: events) {
			if (e.getTitle().equals("Avacyn Restaurado")) {
				eventId = e.getId();
				break;
			}
		}
		chronicleService.findByEventId(eventId);
	}
	
	// NEGATIVE TEST CASE: Event ID must be valid.
	
	@Test(expected=IllegalArgumentException.class)
	public void testSeeChronicleOfEventWrongId() {
		authenticate("player1");
		chronicleService.findByEventId(-5);
	}

	// -------------------------------------------------------
	
	// A user who is authenticated as a business must be able to
	// write (or delete) a chronicle about one of their events.
	
	// POSITIVE TEST CASE: A business can write a chronicle.
	
	@Test
	public void testWriteChronicle() {
		authenticate("business1");
		Collection<Event> events = eventService.findMyEvents();
		int eventId = 0;
		for (Event e: events) {
			if (e.getTitle().equals("Presentación Risk")) {
				eventId = e.getId();
				break;
			}
		}
		Chronicle chronicle = chronicleService.create(eventId);
		chronicle.setTitle("Crónica de prueba");
		chronicle.setText("Esto es una crónica de prueba, sin información.");
		chronicleService.save(chronicle);
	}
	
	// NEGATIVE TEST CASE: A business cannot write a
	// chronicle about an event of another business.
	
	@Test(expected=IllegalArgumentException.class)
	public void testWriteChronicleNotMineEvent() {
		authenticate("business2");
		Collection<Event> events = eventService.findAll();
		int eventId = 0;
		for (Event e: events) {
			if (e.getTitle().equals("Presentación Risk")) {
				eventId = e.getId();
				break;
			}
		}
		Chronicle chronicle = chronicleService.create(eventId);
		chronicle.setTitle("Crónica de prueba");
		chronicle.setText("Esto es una crónica de prueba, sin información.");
		chronicleService.save(chronicle);
	}
	
	// NEGATIVE TEST CASE: A player cannot write a chronicle.

	@Test(expected=IllegalArgumentException.class)
	public void testPlayerWriteChronicle() {
		authenticate("player1");
		Collection<Event> events = eventService.findMyEvents();
		int eventId = 0;
		for (Event e: events) {
			if (e.getTitle().equals("Presentación Risk")) {
				eventId = e.getId();
				break;
			}
		}
		Chronicle chronicle = chronicleService.create(eventId);
		chronicle.setTitle("Crónica de prueba");
		chronicle.setText("Esto es una crónica de prueba, sin información.");
		chronicleService.save(chronicle);
	}

	// -------------------------------------------------------

}
