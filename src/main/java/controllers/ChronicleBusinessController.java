package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChronicleService;
import domain.Chronicle;

@Controller
@RequestMapping("/chronicle/business")
public class ChronicleBusinessController extends AbstractController {
	
	//Services
	
	@Autowired
	public ChronicleService chronicleService;
	
	//Constructor
	
	public ChronicleBusinessController() {
		super();
	}
	
	//Creation
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public ModelAndView write(@RequestParam int eventId) {
		Chronicle chronicle = chronicleService.create(eventId);
		ModelAndView res = new ModelAndView("chronicle/business/write");
		res.addObject("chronicle", chronicle);
		return res;
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Chronicle chronicle, 
			BindingResult bindingResult) {
		ModelAndView res;
		Collection<String> photos = chronicle.getPhotos();
		for (String photo: photos) {
			if (photo.contains(",")) {
				String[] parts = photo.split(",");
				for (String p: parts) {
					p = p.trim();
					photos.add(p);
				}
				photos.remove(photo);
			}
		}
		for (String photo: photos) {
			if (!photo.startsWith("http://") && !photo.startsWith("https://")) {
				bindingResult.rejectValue("photos", "chronicle.error.url");
				break;
			}
		}
		if (bindingResult.hasErrors()) {
			res = new ModelAndView("chronicle/business/write");
			res.addObject("chronicle", chronicle);
		} else {
			try {
				chronicleService.save(chronicle);
				res = new ModelAndView("redirect:/chronicle/auth/see.do?eventId="
						+ chronicle.getEvent().getId());
			} catch (Throwable oops) {
				System.out.println(oops);
				res = new ModelAndView("chronicle/business/write");
				res.addObject("chronicle", chronicle);
				res.addObject("message", "chronicle.error.create");
			}
		}
		return res;
	}
	
	//Deletion
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public ModelAndView delete(Chronicle chronicle) {
		ModelAndView res;
		try {
			chronicleService.delete(chronicle);
			res = new ModelAndView("redirect:/event/business/myEvents.do");
		} catch (Throwable oops) {
			res = new ModelAndView("redirect:/chronicle/auth/see.do?eventId="
					+ chronicle.getEvent().getId());
			res.addObject("message", "chronicle.error.delete");
		}
		return res;
	}
	
}
