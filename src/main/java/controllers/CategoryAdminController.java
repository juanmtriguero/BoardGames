package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import domain.Categorization;
import domain.Category;

@Controller
@RequestMapping("/category/admin")
public class CategoryAdminController extends AbstractController {
	
	//Services
	
	@Autowired
	public CategoryService categoryService;
	
	//Constructor
	
	public CategoryAdminController() {
		super();
	}
	
	//Listing
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res = new ModelAndView("category/admin/list");
		Collection<Category> categories = categoryService.findAll();
		res.addObject("categories", categories);
		res.addObject("requestURI", "category/admin/list.do");
		return res;
	}
	
	//Creation
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create() {
		Category category = categoryService.create();
		ModelAndView res = new ModelAndView("category/admin/create");
		res.addObject("category", category);
		return res;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST, params="save")
	public ModelAndView saveNew(@Valid Category category, 
			BindingResult bindingResult) {
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = new ModelAndView("category/admin/create");
			res.addObject("category", category);
		} else {
			try {
				categoryService.save(category);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				String message;
				if (oops instanceof DataIntegrityViolationException) {
					message = "category.error.usedName";
				} else {
					message = "category.error.create";
				}
				res = new ModelAndView("category/admin/create");
				res.addObject("category", category);
				res.addObject("message", message);
			}
		}
		return res;
	}
	
	//Edition
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int categoryId) {
		ModelAndView res;
		Category category;
		try {
			category = categoryService.findOne(categoryId);
			res = createEditModelAndView(category);
		} catch (Throwable oops) {
			category = categoryService.create();
			res = new ModelAndView("category/admin/create");
			res.addObject("category", category);
			res.addObject("message", "category.error.edit");
		}
		return res;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Category category, 
			BindingResult bindingResult) {
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = createEditModelAndView(category);
		} else {
			try {
				categoryService.save(category);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				if (oops instanceof DataIntegrityViolationException) {
					res = createEditModelAndView(category, "category.error.usedName");
				} else {
					res = createEditModelAndView(category, "category.error.edit");
				}
			}
		}
		return res;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(Category category, BindingResult bindingResult) {
		ModelAndView res;
		try {
			categoryService.delete(category);
			res = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			String message;
			if (oops.toString().contains("hasBoardGames")) {
				message = "category.error.delete.hasBoardGames";
			} else if (oops.toString().contains("isLastCategory")) {
				message = "category.error.delete.isLastCategory";
			} else {
				message = "category.error.delete";
			}
			res = createEditModelAndView(category, message);
		}
		return res;
	}
	
	//Ancillary methods

	protected ModelAndView createEditModelAndView(Category category,
			String message) {
		Collection<Categorization> categorizations = category.getCategorizations();
		ModelAndView res = new ModelAndView("category/admin/edit");
		res.addObject("category", category);
		res.addObject("categorizations", categorizations);
		res.addObject("message", message);
		return res;
	}

	protected ModelAndView createEditModelAndView(Category category) {
		return createEditModelAndView(category, null);
	}
	
}
