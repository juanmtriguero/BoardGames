package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BusinessService;
import services.PlayerService;
import services.UserVoteService;
import domain.Business;
import domain.Player;

@Controller
@RequestMapping("/business/auth")
public class BusinessAuthController extends AbstractController {
	
	//Services
	
	@Autowired
	public BusinessService businessService;
	
	@Autowired
	public PlayerService playerService;
	
	@Autowired
	public UserVoteService userVoteService;
	
	//Constructor
	
	public BusinessAuthController() {
		super();
	}
	
	//Listing
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res = new ModelAndView("business/auth/list");
		Collection<Business> businesses = businessService.findAll();
		res.addObject("businesses", businesses);
		Map<Integer, Boolean> hasVoted = new HashMap<Integer, Boolean>();
		if (playerService.isPlayer()) {
			Player player = playerService.findByUserAccount(
					LoginService.getPrincipal());
			for (Business b: businesses) {
				if (userVoteService.hasVoted(player, b)) {
					hasVoted.put(b.getId(), true);
				} else {
					hasVoted.put(b.getId(), false);
				}
			}
		}
		res.addObject("hasVoted", hasVoted);
		res.addObject("requestURI", "business/auth/list.do");
		return res;
	}

}
