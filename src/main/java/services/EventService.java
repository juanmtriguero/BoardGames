package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EventRepository;
import security.LoginService;
import domain.Event;
import domain.Promotion;
import domain.Tournament;

@Service
@Transactional
public class EventService {
	
	//Managed repository
	
	@Autowired
	private EventRepository eventRepository;

	//Supporting services
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private BusinessService businessService;
	
	//Simple CRUD methods
	
	public Collection<Event> findAll() {
		return eventRepository.findAll();
	}
	
	public Event findOne(int eventId) {
		return eventRepository.findOne(eventId);
	}
	
	//Other business methods
	
	public Collection<Event> findMyEvents() {
		Assert.isTrue(businessService.isBusiness());
		int businessId = businessService.
				findByUserAccount(LoginService.getPrincipal()).getId();
		return eventRepository.findByBusinessId(businessId);
	}
	
	public Double averageJoinedPerPlayer() {
		Assert.isTrue(adminService.isAdmin());
		return eventRepository.averageJoinedPerPlayer();
	}
	
	public Double averageCreatedPerBusiness() {
		Assert.isTrue(adminService.isAdmin());
		return eventRepository.averageCreatedPerBusiness();
	}
	
	//Auxiliary methods
	
	public boolean isClosed(Event event) {
		boolean res = event.getInscriptionDeadline()
				.before(new Date(System.currentTimeMillis()));
		if (!res) {
			Integer participants = event.getInscriptions().size();
			res = participants.equals(event.getNumberMaxParticipants());
		}
		return res;
	}
	
	public Map<Integer, Object[]> getCountdown(Collection<Promotion> promotions, 
			Collection<Tournament> tournaments) {
		Collection<Event> events = new ArrayList<Event>();
		events.addAll(promotions);
		events.addAll(tournaments);
		return getCountdown(events);
	}
	
	public Map<Integer, Object[]> getCountdown(Collection<Event> events) {
		Map<Integer, Object[]> res = new HashMap<Integer, Object[]>();
		for (Event e: events) {
			Object[] aux;
			Date start = e.getStartMoment();
			Date now = new Date(System.currentTimeMillis());
			boolean started = now.compareTo(start)>=1;
			if (started) {
				aux = new Object[1];
				aux[0] = true;
			} else {
				long mindif = (start.getTime() - now.getTime()) / (60*1000);
				int mins = (int) (mindif % 60);
				long hourdif = mindif / 60;
				int hours = (int) (hourdif % 24);
				long daydif = hourdif / 24;
				int days = (int) daydif;
				aux = new Object[4];
				aux[0] = false;
				aux[1] = days;
				aux[2] = hours;
				aux[3] = mins;
			}
			res.put(e.getId(), aux);
		}
		return res;
	}

}
