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

import services.BoardGameService;
import services.CategoryService;
import domain.BoardGame;
import domain.Category;
import forms.BoardGameEditForm;
import forms.BoardGameForm;

@Controller
@RequestMapping("/boardGame/admin")
public class BoardGameAdminController extends AbstractController {
	
	//Services
	
	@Autowired
	private BoardGameService boardGameService;
	
	@Autowired
	private CategoryService categoryService;
	
	//Constructor
	
	public BoardGameAdminController() {
		super();
	}
	
	//Creation
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create() {
		BoardGameForm boardGameForm = new BoardGameForm();
		Collection<Category> categories = categoryService.findAll();
		ModelAndView res = new ModelAndView("boardGame/admin/create");
		res.addObject("boardGameForm", boardGameForm);
		res.addObject("categories", categories);
		return res;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST, params="save")
	public ModelAndView saveNew(@Valid BoardGameForm boardGameForm, 
			BindingResult bindingResult) {
		Collection<Category> categories = categoryService.findAll();
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = new ModelAndView("boardGame/admin/create");
			res.addObject("boardGameForm", boardGameForm);
			res.addObject("categories", categories);
		} else {
			try {
				BoardGame boardGame = boardGameService.reconstruct(boardGameForm);
				boardGameService.save(boardGame);
				res = new ModelAndView("redirect:/boardGame/catalogue.do");
			} catch (Throwable oops) {
				res = new ModelAndView("boardGame/admin/create");
				res.addObject("boardGameForm", boardGameForm);
				res.addObject("categories", categories);
				res.addObject("message", "boardGame.error.create");
			}
		}
		return res;
	}
	
	//Edition
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int boardGameId) {
		ModelAndView res;
		try {
			BoardGame boardGame = boardGameService.findOne(boardGameId);
			BoardGameEditForm boardGameEditForm = boardGameService
					.constructEditForm(boardGame);
			res = createEditModelAndViewForm(boardGameEditForm);
		} catch (Throwable oops) {
			BoardGameForm boardGameForm = new BoardGameForm();
			Collection<Category> categories = categoryService.findAll();
			res = new ModelAndView("boardGame/admin/create");
			res.addObject("boardGameForm", boardGameForm);
			res.addObject("categories", categories);
			res.addObject("message", "boardGame.error.edit");
		}
		return res;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid BoardGameEditForm boardGameEditForm,
			BindingResult bindingResult) {
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = createEditModelAndViewForm(boardGameEditForm);
		} else {
			try {
				BoardGame boardGame = boardGameService.reconstruct(boardGameEditForm);
				boardGameService.save(boardGame);
				res = new ModelAndView("redirect:/boardGame/catalogue.do");
			} catch (Throwable oops) {
				res = createEditModelAndViewForm(boardGameEditForm, "boardGame.error.edit");
			}
		}
		return res;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(BoardGameEditForm boardGameEditForm, 
			BindingResult bindingResult) {
		ModelAndView res;
		try {
			BoardGame boardGame = boardGameService.reconstruct(boardGameEditForm);
			boardGameService.delete(boardGame);
			res = new ModelAndView("redirect:/boardGame/catalogue.do");
		} catch (Throwable oops) {
			String message;
			if (oops.toString().contains("hasEvents")) {
				message = "boardGame.error.delete.hasEvents";
			} else {
				message = "boardGame.error.delete";
			}
			res = createEditModelAndViewForm(boardGameEditForm, message);
		}
		return res;
	}
	
	//Remove from catalogue
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public ModelAndView remove(@RequestParam BoardGame boardGame) {
		ModelAndView res;
		try {
			boardGameService.delete(boardGame);
			res = new ModelAndView("redirect:/boardGame/catalogue.do");
		} catch (Throwable oops) {
			String message;
			if (oops.toString().contains("hasEvents")) {
				message = "boardGame.error.delete.hasEvents";
			} else {
				message = "boardGame.error.delete";
			}
			BoardGameEditForm boardGameEditForm = boardGameService
					.constructEditForm(boardGame);
			res = createEditModelAndViewForm(boardGameEditForm, message);
		}
		return res;
	}
	
	//Ancillary methods

	protected ModelAndView createEditModelAndViewForm(
			BoardGameEditForm boardGameEditForm, String message) {
		Collection<Category> categories = categoryService.findAll();
		ModelAndView res = new ModelAndView("boardGame/admin/edit");
		res.addObject("boardGameEditForm", boardGameEditForm);
		res.addObject("categories", categories);
		res.addObject("message", message);
		return res;
	}

	protected ModelAndView createEditModelAndViewForm(
			BoardGameEditForm boardGameEditForm) {
		return createEditModelAndViewForm(boardGameEditForm, null);
	}

}
