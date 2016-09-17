package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.FollowingService;
import services.PlayerService;
import domain.Player;

@Controller
@RequestMapping("/player/auth")
public class PlayerAuthController extends AbstractController {
	
	//Services
	
	@Autowired
	public PlayerService playerService;
	
	@Autowired
	public FollowingService followingService;
	
	//Constructor
	
	public PlayerAuthController() {
		super();
	}
	
	//Listing
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res = new ModelAndView("player/auth/list");
		Collection<Player> players = playerService.findAll();
		res.addObject("players", players);
		Map<Integer, Boolean> follows = new HashMap<Integer, Boolean>();
		Integer principalId = null;
		if (playerService.isPlayer()) {
			Player me = playerService.findByUserAccount(
					LoginService.getPrincipal());
			for (Player p: players) {
				if (followingService.follows(me, p)) {
					follows.put(p.getId(), true);
				} else {
					follows.put(p.getId(), false);
				}
			}
			principalId = me.getId();
		}
		res.addObject("follows", follows);
		res.addObject("principalId", principalId);
		res.addObject("requestURI", "player/auth/list.do");
		return res;
	}
	
	@RequestMapping(value="/participants", method=RequestMethod.GET)
	public ModelAndView participants(@RequestParam int eventId) {
		ModelAndView res = new ModelAndView("player/auth/participants");
		Collection<Object[]> players = playerService.findByEventId(eventId);
		res.addObject("players", players);
		res.addObject("requestURI", "player/auth/participants.do");
		return res;
	}

}
