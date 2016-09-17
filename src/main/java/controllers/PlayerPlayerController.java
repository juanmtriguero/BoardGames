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
import domain.Following;
import domain.Player;

@Controller
@RequestMapping("/player/player")
public class PlayerPlayerController extends AbstractController {
	
	//Services
	
	@Autowired
	public FollowingService followingService;
	
	@Autowired
	public PlayerService playerService;
	
	//Constructor
	
	public PlayerPlayerController() {
		super();
	}
	
	//Listing
	
	@RequestMapping(value="/followers", method=RequestMethod.GET)
	public ModelAndView followers() {
		ModelAndView res = new ModelAndView("player/player/followers");
		Collection<Player> followers = playerService.followers();
		res.addObject("players", followers);
		Map<Integer, Boolean> follows = new HashMap<Integer, Boolean>();
		Player me = playerService.findByUserAccount(
				LoginService.getPrincipal());
		for (Player p: followers) {
			if (followingService.follows(me, p)) {
				follows.put(p.getId(), true);
			} else {
				follows.put(p.getId(), false);
			}
		}
		res.addObject("follows", follows);
		res.addObject("requestURI", "player/player/followers.do");
		return res;
	}
	
	@RequestMapping(value="/followeds", method=RequestMethod.GET)
	public ModelAndView followeds(String message) {
		ModelAndView res = new ModelAndView("player/player/followeds");
		Collection<Player> followeds = playerService.followeds();
		res.addObject("players", followeds);
		Map<Integer, Boolean> follows = new HashMap<Integer, Boolean>();
		for (Player p: followeds) {
			follows.put(p.getId(), true);
		}
		res.addObject("follows", follows);
		if (message!=null) {
			res.addObject("message", message);
		}
		res.addObject("requestURI", "player/player/followeds.do");
		return res;
	}
	
	//Inscriptions

	@RequestMapping(value="/follow", method=RequestMethod.GET)
	public ModelAndView follow(@RequestParam int playerId) {
		ModelAndView res = new ModelAndView("redirect:followeds.do");
		try {
			Following following = followingService.create(playerId);
			followingService.save(following);
		} catch (Throwable oops) {
			String message;
			if (oops.toString().contains("follows")) {
				message = "following.error.follows";
			} else {
				message = "following.error.follow";
			}
			res.addObject("message", message);
		}
		return res;
	}
	
	@RequestMapping(value="/unfollow", method=RequestMethod.GET)
	public ModelAndView unfollow(@RequestParam int playerId) {
		ModelAndView res = new ModelAndView("redirect:followeds.do");
		try {
			Following following = followingService.findMineByFollowedId(playerId);
			followingService.delete(following);
		} catch (Throwable oops) {
			String message;
			if (oops.toString().contains("notFollows")) {
				message = "following.error.notFollows";
			} else {
				message = "following.error.follow";
			}
			res.addObject("message", message);
		}
		return res;
	}

}
