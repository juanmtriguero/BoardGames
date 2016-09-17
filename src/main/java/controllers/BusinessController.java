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
import services.BusinessService;
import domain.Business;
import forms.BusinessForm;

@Controller
@RequestMapping("/business")
public class BusinessController extends AbstractController {
	
	//Services
	
	@Autowired
	public BusinessService businessService;
	
	@Autowired
	public ActorService actorService;
	
	//Constructor
	
	public BusinessController() {
		super();
	}
	
	//Registration
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res = new ModelAndView("business/register");
		BusinessForm businessForm = new BusinessForm();
		res.addObject("businessForm", businessForm);
		return res;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid BusinessForm businessForm, 
			BindingResult bindingResult) {
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = new ModelAndView("business/register");
			res.addObject("businessForm", businessForm);
		} else {
			try {
				Business business = businessService.reconstruct(businessForm);
				businessService.save(business);
				res = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				String message;
				if (oops.toString().contains("distinctPasswords")) {
					message = "business.error.distinctPasswords";
				} else if (oops.toString().contains("acceptConditions")) {
					message = "business.error.acceptConditions";
				} else if (oops instanceof DataIntegrityViolationException) {
					if (actorService.existsUsername(businessForm.getUsername())) {
						message = "business.error.usedUsername";
					} else if (actorService.existsEmail(businessForm.getEmail())) {
						message = "business.error.usedEmail";
					} else {
						message = "business.error.register";
					}
				} else {
					message = "business.error.register";
				}
				res = new ModelAndView("business/register");
				res.addObject("businessForm", businessForm);
				res.addObject("message", message);
			}
		}
		return res;
	}

}
