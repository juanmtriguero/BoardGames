package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BulletinService;
import domain.Bulletin;

@Controller
@RequestMapping("/bulletin/auth")
public class BulletinAuthController extends AbstractController {
	
	//Services
	
	@Autowired
	public BulletinService bulletinService;
	
	//Constructor
	
	public BulletinAuthController() {
		super();
	}
	
	//Listing
	
	@RequestMapping(value="/byBusiness", method=RequestMethod.GET)
	public ModelAndView byBusiness(@RequestParam int businessId) {
		ModelAndView res = new ModelAndView("bulletin/auth/byBusiness");
		Collection<Bulletin> bulletins = 
				bulletinService.findByBusinessId(businessId);
		res.addObject("bulletins", bulletins);
		res.addObject("requestURI", "event/auth/byBusiness.do");
		return res;
	}

}
