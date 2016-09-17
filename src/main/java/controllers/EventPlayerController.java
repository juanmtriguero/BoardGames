package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import services.InscriptionService;
import services.PlayerService;
import services.PromotionService;
import services.TournamentService;
import domain.Event;
import domain.Inscription;
import domain.Player;

@Controller
@RequestMapping("/event/player")
public class EventPlayerController extends AbstractController {
	
	//Services
	
	@Autowired
	public EventService eventService;
	
	@Autowired
	public PromotionService promotionService;
	
	@Autowired
	public TournamentService tournamentService;
	
	@Autowired
	public InscriptionService inscriptionService;
	
	@Autowired
	public PlayerService playerService;
	
	//Constructor
	
	public EventPlayerController() {
		super();
	}
	
	//Listing
	
	@RequestMapping(value="/inscribed", method=RequestMethod.GET)
	public ModelAndView inscribed() {
		ModelAndView res = new ModelAndView("event/player/inscribed");
		Collection<Object[]> promotions = promotionService.findInscribed();
		res.addObject("promotions", promotions);
		Collection<Object[]> tournaments = tournamentService.findInscribed();
		res.addObject("tournaments", tournaments);
		Map<Integer, Boolean> isClosed = new HashMap<Integer, Boolean>();
		for (Object[] obj: promotions) {
			Event e = (Event) obj[0];
			Boolean b = eventService.isClosed(e);
			isClosed.put(e.getId(), b);
		}
		for (Object[] obj: tournaments) {
			Event e = (Event) obj[0];
			Boolean b = eventService.isClosed(e);
			isClosed.put(e.getId(), b);
		}
		res.addObject("isClosed", isClosed);
		res.addObject("requestURI", "event/player/inscribed.do");
		return res;
	}
	
	//Inscriptions

	@RequestMapping(value="/join", method=RequestMethod.GET)
	public ModelAndView join(@RequestParam int eventId) {
		ModelAndView res = new ModelAndView("event/player/join");
		Inscription inscription = inscriptionService.create(eventId);
		try {
			Player player = inscription.getPlayer();
			Event event = inscription.getEvent();
			Assert.isTrue(!eventService.isClosed(event), "isClosed");
			Assert.isTrue(!inscriptionService.isInscribed
					(player, event), "isInscribed");
		} catch (Throwable oops) {
			String message;
			if (oops.toString().contains("isClosed")) {
				message = "inscription.error.isClosed";
			} else if (oops.toString().contains("isInscribed")) {
				message = "inscription.error.isInscribed";
			} else {
				message = "inscription.error.join";
			}
			res.addObject("message", message);
		}
		res.addObject("inscription", inscription);
		return res;
	}

	@RequestMapping(value="/join", method=RequestMethod.POST, params="save")
	public ModelAndView saveInscription(@Valid Inscription inscription, 
			BindingResult bindingResult) {
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = new ModelAndView("event/player/join");
			res.addObject("inscription", inscription);
		} else {
			try {
				inscriptionService.save(inscription);
				res = new ModelAndView("redirect:inscribed.do");
			} catch (Throwable oops) {
				String message;
				if (oops.toString().contains("isClosed")) {
					message = "inscription.error.isClosed";
				} else if (oops.toString().contains("isInscribed")) {
					message = "inscription.error.isInscribed";
				} else {
					message = "inscription.error.join";
				}
				res = new ModelAndView("event/player/join");
				res.addObject("inscription", inscription);
				res.addObject("message", message);
			}
		}
		return res;
	}
	
	@RequestMapping(value="/unjoin", method=RequestMethod.GET)
	public ModelAndView unjoin(@RequestParam int eventId) {
		ModelAndView res;
		Inscription inscription;
		try {
			inscription = inscriptionService.findMineByEventId(eventId);
			inscriptionService.delete(inscription);
			res = new ModelAndView("redirect:inscribed.do");
		} catch (Throwable oops) {
			String message;
			if (oops.toString().contains("isClosed")) {
				message = "inscription.error.isClosed";
			} else if (oops.toString().contains("isNotInscribed")) {
				message = "inscription.error.isNotInscribed";
			} else {
				message = "inscription.error.unjoin";
			}
			res = new ModelAndView("event/player/join");
			inscription = inscriptionService.create(eventId);
			res.addObject("inscription", inscription);
			res.addObject("message", message);
		}
		return res;
	}

}
