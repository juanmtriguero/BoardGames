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

import services.BulletinService;
import services.EventService;
import domain.Bulletin;
import domain.Event;
import forms.BulletinEditForm;
import forms.BulletinForm;

@Controller
@RequestMapping("/bulletin/business")
public class BulletinBusinessController extends AbstractController {

	// Services

	@Autowired
	public BulletinService bulletinService;

	@Autowired
	public EventService eventService;

	// Constructor

	public BulletinBusinessController() {
		super();
	}

	// Listing

	@RequestMapping(value="/myBulletins", method=RequestMethod.GET)
	public ModelAndView myBulletins() {
		ModelAndView res = new ModelAndView("bulletin/business/myBulletins");
		Collection<Bulletin> bulletins = bulletinService.findMyBulletins();
		res.addObject("bulletins", bulletins);
		res.addObject("requestURI", "bulletin/business/myBulletins.do");
		return res;
	}

	// Creation

	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res = new ModelAndView("bulletin/business/create");
		BulletinForm bulletinForm = new BulletinForm();
		res.addObject("bulletinForm", bulletinForm);
		Collection<Event> events = eventService.findMyEvents();
		res.addObject("events", events);
		return res;
	}

	@RequestMapping(value="/create", method=RequestMethod.POST, params="save")
	public ModelAndView saveNew(@Valid BulletinForm bulletinForm, 
			BindingResult bindingResult) {
		Collection<Event> events = eventService.findMyEvents();
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = new ModelAndView("bulletin/business/create");
			res.addObject("bulletinForm", bulletinForm);
			res.addObject("events", events);
		} else {
			try {
				Bulletin bulletin = bulletinService.reconstruct(bulletinForm);
				bulletinService.save(bulletin);
				res = new ModelAndView("redirect:myBulletins.do");
			} catch (Throwable oops) {
				res = new ModelAndView("bulletin/business/create");
				res.addObject("bulletinForm", bulletinForm);
				res.addObject("events", events);
				res.addObject("message", "bulletin.error.create");
			}
		}
		return res;
	}

	// Edition

	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int bulletinId) {
		ModelAndView res;
		try {
			Bulletin bulletin = bulletinService.findOne(bulletinId);
			BulletinEditForm bulletinEditForm = bulletinService
					.constructEditForm(bulletin);
			res = createEditModelAndViewForm(bulletinEditForm);
		} catch (Throwable oops) {
			res = new ModelAndView("bulletin/business/create");
			BulletinForm bulletinForm = new BulletinForm();
			res.addObject("bulletinForm", bulletinForm);
			Collection<Event> events = eventService.findMyEvents();
			res.addObject("events", events);
			res.addObject("message", "boardGame.error.edit");
		}
		return res;
	}

	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid BulletinEditForm bulletinEditForm,
			BindingResult bindingResult) {
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = createEditModelAndViewForm(bulletinEditForm);
		} else {
			try {
				Bulletin bulletin = bulletinService.reconstruct(bulletinEditForm);
				bulletinService.save(bulletin);
				res = new ModelAndView("redirect:myBulletins.do");
			} catch (Throwable oops) {
				res = createEditModelAndViewForm(bulletinEditForm, "bulletin.error.edit");
			}
		}
		return res;
	}

	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(BulletinEditForm bulletinEditForm, 
			BindingResult bindingResult) {
		ModelAndView res;
		try {
			Bulletin bulletin = bulletinService.reconstruct(bulletinEditForm);
			bulletinService.delete(bulletin);
			res = new ModelAndView("redirect:myBulletins.do");
		} catch (Throwable oops) {
			res = createEditModelAndViewForm(bulletinEditForm,
					"bulletin.error.delete");
		}
		return res;
	}
	
	//Remove from my bulletins
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public ModelAndView remove(@RequestParam Bulletin bulletin) {
		ModelAndView res;
		try {
			bulletinService.delete(bulletin);
			res = new ModelAndView("redirect:myBulletins.do");
		} catch (Throwable oops) {
			BulletinEditForm bulletinEditForm = bulletinService
					.constructEditForm(bulletin);
			res = createEditModelAndViewForm(bulletinEditForm, 
					"bulletin.error.delete");
		}
		return res;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndViewForm(
			BulletinEditForm bulletinEditForm, String message) {
		Collection<Event> events = eventService.findMyEvents();
		ModelAndView res = new ModelAndView("bulletin/business/edit");
		res.addObject("bulletinEditForm", bulletinEditForm);
		res.addObject("events", events);
		res.addObject("message", message);
		return res;
	}

	protected ModelAndView createEditModelAndViewForm(
			BulletinEditForm bulletinEditForm) {
		return createEditModelAndViewForm(bulletinEditForm, null);
	}

}
