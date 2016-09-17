package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import domain.BoardGame;
import domain.Categorization;
import domain.Category;

@Controller
@RequestMapping("/boardGame")
public class BoardGameController extends AbstractController {
	
	//Services
	
	@Autowired
	private CategoryService categoryService;
	
	//Constructor
	
	public BoardGameController() {
		super();
	}
	
	//Catalogue
	
	@RequestMapping(value="/catalogue", method=RequestMethod.GET)
	public ModelAndView catalogue() {
		ModelAndView res = new ModelAndView("boardGame/catalogue");
		Collection<Category> categories = categoryService.findAll();
		res.addObject("categories", categories);
		for (Category cat: categories) {
			Collection<BoardGame> boardGames = new ArrayList<BoardGame>();
			for (Categorization c: cat.getCategorizations()) {
				boardGames.add(c.getBoardGame());
			}
			res.addObject(cat.getName(), boardGames);
		}
		res.addObject("requestURI", "boardGame/catalogue.do");
		return res;
	}

}
