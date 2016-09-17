package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.UserVoteService;
import domain.Business;
import domain.Player;
import domain.UserVote;

@Controller
@RequestMapping("/business/player")
public class BusinessPlayerController extends AbstractController {
	
	//Services
	
	@Autowired
	public UserVoteService userVoteService;
	
	//Constructor
	
	public BusinessPlayerController() {
		super();
	}
	
	//Voting

	@RequestMapping(value="/vote", method=RequestMethod.GET)
	public ModelAndView vote(@RequestParam int businessId) {
		ModelAndView res = new ModelAndView("business/player/vote");
		UserVote userVote = userVoteService.create(businessId);
		try {
			Player player = userVote.getPlayer();
			Business business = userVote.getBusiness();
			Assert.isTrue(!userVoteService.hasVoted(player, business), "hasVoted");
		} catch (Throwable oops) {
			String message;
			if (oops.toString().contains("hasVoted")) {
				message = "vote.error.hasVoted";
			} else {
				message = "vote.error.vote";
			}
			res.addObject("message", message);
		}
		res.addObject("userVote", userVote);
		return res;
	}

	@RequestMapping(value="/vote", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid UserVote userVote, 
			BindingResult bindingResult) {
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = new ModelAndView("business/player/vote");
			res.addObject("userVote", userVote);
		} else {
			try {
				userVoteService.save(userVote);
				res = new ModelAndView("redirect:/business/auth/list.do");
			} catch (Throwable oops) {
				String message;
				if (oops.toString().contains("hasVoted")) {
					message = "vote.error.hasVoted";
				} else {
					message = "vote.error.vote";
				}
				res = new ModelAndView("business/player/vote");
				res.addObject("userVote", userVote);
				res.addObject("message", message);
			}
		}
		return res;
	}

}
