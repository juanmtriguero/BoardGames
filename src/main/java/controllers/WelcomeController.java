/* WelcomeController.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.AdminService;
import services.BulletinService;
import services.BusinessService;
import services.EventService;
import services.PlayerService;
import services.PromotionService;
import services.TournamentService;
import domain.Actor;
import domain.Bulletin;
import domain.Event;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private TournamentService tournamentService;
	
	@Autowired
	private BulletinService bulletinService;

	// Constructors -----------------------------------------------------------
	
	public WelcomeController() {
		super();
	}
		
	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required=false,
			defaultValue="") String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		
		Actor actor = null;
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context == null ? null : context
				.getAuthentication();
		Object principal = authentication == null ? null : authentication
				.getPrincipal();
		UserAccount ua = principal instanceof UserAccount ? (UserAccount) principal
				: null;
		if (ua != null) {
			actor = adminService.findByUserAccount(ua);
			if (actor == null) {
				actor = playerService.findByUserAccount(ua);
				if (actor == null) {
					actor = businessService.findByUserAccount(ua);
				}
			}
		}
		name = actor == null ? name : actor.getName();
		name = name.equals("") ? name : ", " + name;
		
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
				
		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		
		if (playerService.isPlayer()) {

			// Next event inscribed
			Long now = System.currentTimeMillis();
			Long diff = null;
			Collection<Object[]> events = new ArrayList<Object[]>();
			Collection<Object[]> promotions = promotionService.findInscribed();
			events.addAll(promotions);
			Collection<Object[]> tournaments = tournamentService.findInscribed();
			events.addAll(tournaments);
			Collection<Event> nextEvents = new ArrayList<Event>();
			for (Object[] obj: events) {
				Event e = (Event) obj[0];
				Long start = e.getStartMoment().getTime();
				Long newDiff = start - now;
				if ((diff==null || newDiff<=diff) && newDiff>0) {
					if (newDiff!=diff) {
						diff = newDiff;
						nextEvents.clear();
					}
					nextEvents.add(e);
				}
			}
			result.addObject("nextEvents", nextEvents);
			Map<Integer, Object[]> countdown = eventService
					.getCountdown(nextEvents);
			result.addObject("countdown", countdown);
			
			// Last bulletins
			
			List<Bulletin> lastBulletins = bulletinService.lastPublished(3);
			result.addObject("lastBulletins", lastBulletins);
			
			// Followed activity
			
			Collection<String[]> followedActivity = playerService.followedActivity();
			for (String[] aux: followedActivity) {
				System.out.println(aux[0]+" "+aux[1]+" "+aux[2]);
			}
			result.addObject("followedActivity", followedActivity);
			
		}

		return result;
	}
}