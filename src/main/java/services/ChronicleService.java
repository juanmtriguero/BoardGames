package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
import domain.Chronicle;
import domain.Event;

@Service
@Transactional
public class ChronicleService {

	//Supporting services
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private EventService eventService;
	
	//Simple CRUD methods
	
	public Chronicle create(int eventId) {
		Assert.isTrue(businessService.isBusiness());
		Chronicle chronicle = new Chronicle();
		Event event = eventService.findOne(eventId);
		chronicle.setEvent(event);
		return chronicle;
	}
	
	public void save(Chronicle chronicle) {
		Assert.notNull(chronicle);
		Assert.isTrue(businessService.isBusiness());
		Event event = chronicle.getEvent();
		Assert.isTrue(LoginService.getPrincipal()
				.equals(event.getBusiness().getUserAccount()));
		event.setChronicle(chronicle);
	}
	
	public void delete(Chronicle chronicle) {
		Assert.notNull(chronicle);
		Assert.isTrue(businessService.isBusiness());
		Event event = chronicle.getEvent();
		Assert.isTrue(LoginService.getPrincipal()
				.equals(event.getBusiness().getUserAccount()));
		event.setChronicle(null);
	}
	
	//Other business methods
	
	public Chronicle findByEventId(int eventId) {
		Assert.isTrue(adminService.isAdmin()
				|| playerService.isPlayer()
				|| businessService.isBusiness());
		Event event = eventService.findOne(eventId);
		Assert.notNull(event);
		Chronicle chronicle = event.getChronicle();
		Assert.notNull(chronicle);
		return chronicle;
	}

}
