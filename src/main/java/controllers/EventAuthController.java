package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BulletinService;
import services.EventService;
import services.InscriptionService;
import services.PlayerService;
import services.PromotionService;
import services.TournamentService;
import domain.Event;
import domain.Inscription;
import domain.Promotion;
import domain.Tournament;

@Controller
@RequestMapping("/event/auth")
public class EventAuthController extends AbstractController {
	
	//Services
	
	@Autowired
	public EventService eventService;
	
	@Autowired
	public PromotionService promotionService;
	
	@Autowired
	public TournamentService tournamentService;
	
	@Autowired
	public PlayerService playerService;
	
	@Autowired
	public InscriptionService inscriptionService;
	
	@Autowired
	public BulletinService bulletinService;
	
	//Constructor
	
	public EventAuthController() {
		super();
	}
	
	//Listing
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res = new ModelAndView("event/auth/list");
		Collection<Promotion> promotions = promotionService.findAll();
		res.addObject("promotions", promotions);
		Collection<Tournament> tournaments = tournamentService.findAll();
		res.addObject("tournaments", tournaments);
		Map<Integer, Boolean> isClosed = getIsClosed(promotions, tournaments);
		res.addObject("isClosed", isClosed);
		Map<Integer, Boolean> isJoined;
		if (playerService.isPlayer()) {
			isJoined = getIsJoined(promotions, tournaments);
		} else {
			isJoined = new HashMap<Integer, Boolean>();
		}
		res.addObject("isJoined", isJoined);
		Map<Integer, Object[]> countdown = eventService
				.getCountdown(promotions, tournaments);
		res.addObject("countdown", countdown);
		res.addObject("requestURI", "event/auth/list.do");
		return res;
	}
	
	@RequestMapping(value="/byBusiness", method=RequestMethod.GET)
	public ModelAndView byBusiness(@RequestParam int businessId) {
		ModelAndView res = new ModelAndView("event/auth/byBusiness");
		Collection<Promotion> promotions = 
				promotionService.findByBusinessId(businessId);
		res.addObject("promotions", promotions);
		Collection<Tournament> tournaments = 
				tournamentService.findByBusinessId(businessId);
		res.addObject("tournaments", tournaments);
		Map<Integer, Boolean> isClosed = getIsClosed(promotions, tournaments);
		res.addObject("isClosed", isClosed);
		Map<Integer, Boolean> isJoined;
		if (playerService.isPlayer()) {
			isJoined = getIsJoined(promotions, tournaments);
		} else {
			isJoined = new HashMap<Integer, Boolean>();
		}
		res.addObject("isJoined", isJoined);
		Map<Integer, Object[]> countdown = eventService
				.getCountdown(promotions, tournaments);
		res.addObject("countdown", countdown);
		res.addObject("requestURI", "event/auth/byBusiness.do");
		return res;
	}
	
	@RequestMapping(value="/announced", method=RequestMethod.GET)
	public ModelAndView announced(@RequestParam int bulletinId) {
		ModelAndView res = new ModelAndView("event/auth/announced");
		Collection<Event> events = bulletinService.findOne(bulletinId).getEvents();
		Collection<Promotion> promotions = new ArrayList<Promotion>();
		Collection<Tournament> tournaments = new ArrayList<Tournament>();
		for (Event e: events) {
			if (e instanceof Promotion) {
				Promotion p = (Promotion) e;
				promotions.add(p);
			} else if (e instanceof Tournament) {
				Tournament t = (Tournament) e;
				tournaments.add(t);
			}
		}
		res.addObject("promotions", promotions);
		res.addObject("tournaments", tournaments);
		Map<Integer, Boolean> isClosed = getIsClosed(promotions, tournaments);
		res.addObject("isClosed", isClosed);
		Map<Integer, Boolean> isJoined;
		if (playerService.isPlayer()) {
			isJoined = getIsJoined(promotions, tournaments);
		} else {
			isJoined = new HashMap<Integer, Boolean>();
		}
		res.addObject("isJoined", isJoined);
		Map<Integer, Object[]> countdown = eventService
				.getCountdown(promotions, tournaments);
		res.addObject("countdown", countdown);
		res.addObject("requestURI", "event/auth/announced.do");
		return res;
	}
	
	//Auxiliary methods

	private Map<Integer, Boolean> getIsClosed(Collection<Promotion> promotions, 
			Collection<Tournament> tournaments) {
		Map<Integer, Boolean> isClosed = new HashMap<Integer, Boolean>();
		Collection<Event> events = new ArrayList<Event>();
		events.addAll(promotions);
		events.addAll(tournaments);
		for (Event e: events) {
			Boolean b = eventService.isClosed(e);
			isClosed.put(e.getId(), b);
		}
		return isClosed;
	}
	
	private Map<Integer, Boolean> getIsJoined(Collection<Promotion> promotions,
			Collection<Tournament> tournaments) {
		Map<Integer, Boolean> isJoined = new HashMap<Integer, Boolean>();
		Collection<Event> events = new ArrayList<Event>();
		events.addAll(promotions);
		events.addAll(tournaments);
		for (Event e: events) {
			Integer id = e.getId();
			Inscription i = inscriptionService.findMineByEventId(id);
			Boolean b = false;
			if (i!=null) {
				b = true;
			}
			isJoined.put(id, b);
		}
		return isJoined;
	}

}
