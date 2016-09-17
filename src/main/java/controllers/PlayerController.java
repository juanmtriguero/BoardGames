package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.PlayerService;
import domain.Player;
import forms.PlayerForm;

@Controller
@RequestMapping("/player")
public class PlayerController extends AbstractController {
	
	//Services
	
	@Autowired
	public PlayerService playerService;
	
	@Autowired
	public ActorService actorService;
	
	//Constructor
	
	public PlayerController() {
		super();
	}
	
	//Registration
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res = new ModelAndView("player/register");
		PlayerForm playerForm = new PlayerForm();
		res.addObject("playerForm", playerForm);
		return res;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid PlayerForm playerForm, 
			BindingResult bindingResult) {
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = new ModelAndView("player/register");
			res.addObject("playerForm", playerForm);
		} else {
			try {
				Player player = playerService.reconstruct(playerForm);
				playerService.save(player);
				res = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				String message;
				if (oops.toString().contains("distinctPasswords")) {
					message = "player.error.distinctPasswords";
				} else if (oops.toString().contains("acceptConditions")) {
					message = "player.error.acceptConditions";
				} else if (oops instanceof DataIntegrityViolationException) {
					if (actorService.existsUsername(playerForm.getUsername())) {
						message = "player.error.usedUsername";
					} else if (actorService.existsEmail(playerForm.getEmail())) {
						message = "player.error.usedEmail";
					} else {
						message = "player.error.register";
					}
				} else {
					message = "player.error.register";
				}
				res = new ModelAndView("player/register");
				res.addObject("playerForm", playerForm);
				res.addObject("message", message);
			}
		}
		return res;
	}

}
