package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ChronicleService;
import domain.Chronicle;

@Controller
@RequestMapping("/chronicle/auth")
public class ChronicleAuthController extends AbstractController {
	
	//Services
	
	@Autowired
	public ChronicleService chronicleService;
	
	//Constructor
	
	public ChronicleAuthController() {
		super();
	}
	
	//See chronicle
	
	@RequestMapping(value="/see", method=RequestMethod.GET)
	public ModelAndView see(@RequestParam int eventId) {
		ModelAndView res = new ModelAndView("chronicle/auth/see");
		try {
			Chronicle chronicle = chronicleService.findByEventId(eventId);
			res.addObject("chronicle", chronicle);
			boolean isMine = false;
			if (chronicle.getEvent().getBusiness().getUserAccount()
					.equals(LoginService.getPrincipal())) {
				isMine = true;
			}
			res.addObject("isMine", isMine);
		} catch (Throwable e) {
			res.addObject("chronicle", null);
			res.addObject("message", "chronicle.error.see");
		}
		res.addObject("requestURI", "chronicle/auth/see.do");
		return res;
	}

}
