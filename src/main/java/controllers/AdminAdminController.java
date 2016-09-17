package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BoardGameService;
import services.BusinessService;
import services.EventService;
import services.PlayerService;
import domain.BoardGame;
import domain.Business;
import domain.Player;

@Controller
@RequestMapping("/admin/admin")
public class AdminAdminController extends AbstractController {
	
	//Services
	
	@Autowired
	public BoardGameService boardGameService;
	
	@Autowired
	public PlayerService playerService;
	
	@Autowired
	public EventService eventService;
	
	@Autowired
	public BusinessService businessService;
	
	//Constructor
	
	public AdminAdminController() {
		super();
	}
	
	//Dashboard
	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView res = new ModelAndView("admin/admin/dashboard");
		
		Collection<BoardGame> usedInMostEvents = boardGameService.usedInMostEvents();
		res.addObject("usedInMostEvents", usedInMostEvents);
		Collection<BoardGame> usedInLessEvents = boardGameService.usedInLessEvents();
		res.addObject("usedInLessEvents", usedInLessEvents);
		
		Collection<Player> joinedMostEvents = playerService.joinedMostEvents();
		res.addObject("joinedMostEvents", joinedMostEvents);
		Collection<Player> joinedLessEvents = playerService.joinedLessEvents();
		res.addObject("joinedLessEvents", joinedLessEvents);
		
		Object[] playedMostBoardGames = playerService.playedMostBoardGames();
		res.addObject("playedMostBoardGames", playedMostBoardGames[0]);
		res.addObject("boardGamesPlayed", playedMostBoardGames[1]);
		
		Collection<Business> businessBestRated = businessService.bestRated();
		res.addObject("businessBestRated", businessBestRated);
		Collection<Business> businessWorstRated = businessService.worstRated();
		res.addObject("businessWorstRated", businessWorstRated);
		
		Collection<Object[]> averages = new ArrayList<Object[]>();
		Object[] ajpp = new Object[2];
		ajpp[0] = "averageJoinedPerPlayer";
		ajpp[1] = eventService.averageJoinedPerPlayer();
		averages.add(ajpp);
		Object[] acpb = new Object[2];
		acpb[0] = "averageCreatedPerBusiness";
		acpb[1] = eventService.averageCreatedPerBusiness();
		averages.add(acpb);
		Object[] afpp = new Object[2];
		afpp[0] = "averageFollowedPerPlayer";
		afpp[1] = playerService.averageFollowedPerPlayer();
		averages.add(afpp);
		res.addObject("averages", averages);
		
		res.addObject("requestURI", "admin/admin/dashboard.do");
		return res;
	}

}
