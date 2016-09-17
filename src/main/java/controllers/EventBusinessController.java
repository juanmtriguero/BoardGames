package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BoardGameService;
import services.EventService;
import services.PromotionService;
import services.TournamentService;
import domain.BoardGame;
import domain.Game;
import domain.Player;
import domain.Promotion;
import domain.Stage;
import domain.Tournament;
import forms.PromotionEditForm;
import forms.PromotionForm;
import forms.TournamentEditForm;
import forms.TournamentForm;

@Controller
@RequestMapping("/event/business")
public class EventBusinessController extends AbstractController {
	
	//Services
	
	@Autowired
	public EventService eventService;
	
	@Autowired
	public PromotionService promotionService;
	
	@Autowired
	public TournamentService tournamentService;
	
	@Autowired
	public BoardGameService boardGameService;
	
	//Constructor
	
	public EventBusinessController() {
		super();
	}
	
	//Listing
	
	@RequestMapping(value="/myEvents", method=RequestMethod.GET)
	public ModelAndView myEvents() {
		ModelAndView res = new ModelAndView("event/business/myEvents");
		Collection<Promotion> promotions = promotionService.findMyPromotions();
		res.addObject("promotions", promotions);
		Collection<Tournament> tournaments = tournamentService.findMyTournaments();
		res.addObject("tournaments", tournaments);
		Map<Integer, Object[]> countdown = eventService
				.getCountdown(promotions, tournaments);
		res.addObject("countdown", countdown);
		res.addObject("requestURI", "event/business/myEvents.do");
		return res;
	}
	
	//Promotion
	
	//Creation
	
	@RequestMapping(value="/createPromotion", method=RequestMethod.GET)
	public ModelAndView createPromotion() {
		PromotionForm promotionForm = new PromotionForm();
		Collection<BoardGame> boardGames = boardGameService.findAll();
		ModelAndView res = new ModelAndView("event/business/createPromotion");
		res.addObject("promotionForm", promotionForm);
		res.addObject("boardGames", boardGames);
		return res;
	}
	
	@RequestMapping(value="/createPromotion", method=RequestMethod.POST, params="save")
	public ModelAndView saveNewPromotion(@Valid PromotionForm promotionForm, 
			BindingResult bindingResult) {
		Collection<BoardGame> boardGames = boardGameService.findAll();
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = new ModelAndView("event/business/createPromotion");
			res.addObject("promotionForm", promotionForm);
			res.addObject("boardGames", boardGames);
		} else {
			try {
				Promotion promotion = promotionService.reconstruct(promotionForm);
				promotionService.save(promotion);
				res = new ModelAndView("redirect:myEvents.do");
			} catch (Throwable oops) {
				String message;
				if (oops.toString().contains("startInFuture")) {
					message = "event.error.startInFuture";
				} else if (oops.toString().contains("finishInFuture")) {
					message = "event.error.finishInFuture";
				} else if (oops.toString().contains("finishAfterStart")) {
					message = "event.error.finishAfterStart";
				} else if (oops.toString().contains("deadlineInFuture")) {
					message = "event.error.deadlineInFuture";
				} else if (oops.toString().contains("deadlineBeforeStart")) {
					message = "event.error.deadlineBeforeStart";
				} else if (oops.toString().contains("incompleteSponsorship")) {
					message = "event.error.incompleteSponsorship";
				} else {
					message = "event.error.createPromotion";
				}
				res = new ModelAndView("event/business/createPromotion");
				res.addObject("promotionForm", promotionForm);
				res.addObject("boardGames", boardGames);
				res.addObject("message", message);
			}
		}
		return res;
	}
	
	//Edition
	
	@RequestMapping(value="/editPromotion", method=RequestMethod.GET)
	public ModelAndView editPromotion(@RequestParam int promotionId) {
		ModelAndView res;
		try {
			Promotion promotion = promotionService.findOne(promotionId);
			PromotionEditForm promotionEditForm = promotionService
					.constructEditForm(promotion);
			res = createEditModelAndViewPromotionForm(promotionEditForm);
		} catch (Throwable oops) {
			PromotionForm promotionForm = new PromotionForm();
			Collection<BoardGame> boardGames = boardGameService.findAll();
			res = new ModelAndView("event/business/createPromotion");
			res.addObject("promotionForm", promotionForm);
			res.addObject("boardGames", boardGames);
			res.addObject("message", "event.error.editPromotion");
		}
		return res;
	}
	
	@RequestMapping(value="/editPromotion", method=RequestMethod.POST, params="save")
	public ModelAndView savePromotion(@Valid PromotionEditForm promotionEditForm,
			BindingResult bindingResult) {
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = createEditModelAndViewPromotionForm(promotionEditForm);
		} else {
			try {
				Promotion promotion = promotionService.reconstruct(promotionEditForm);
				promotionService.save(promotion);
				res = new ModelAndView("redirect:myEvents.do");
			} catch (Throwable oops) {
				String message;
				if (oops.toString().contains("finishAfterStart")) {
					message = "event.error.finishAfterStart";
				} else if (oops.toString().contains("deadlineBeforeStart")) {
					message = "event.error.deadlineBeforeStart";
				} else if (oops.toString().contains("incompleteSponsorship")) {
					message = "event.error.incompleteSponsorship";
				} else {
					message = "event.error.editPromotion";
				}
				res = createEditModelAndViewPromotionForm(promotionEditForm, message);
			}
		}
		return res;
	}
	
	@RequestMapping(value="/editPromotion", method=RequestMethod.POST, params="delete")
	public ModelAndView deletePromotion(PromotionEditForm promotionEditForm, 
			BindingResult bindingResult) {
		ModelAndView res;
		try {
			Promotion promotion = promotionService.reconstruct(promotionEditForm);
			promotionService.delete(promotion);
			res = new ModelAndView("redirect:myEvents.do");
		} catch (Throwable oops) {
			String message;
			if (oops.toString().contains("hasInscriptions")) {
				message = "event.error.delete.hasInscriptions";
			} else {
				message = "event.error.deletePromotion";
			}
			res = createEditModelAndViewPromotionForm(promotionEditForm, message);
		}
		return res;
	}
	
	//Remove from my events
	
	@RequestMapping(value="/removePromotion", method=RequestMethod.POST)
	public ModelAndView removePromotion(@RequestParam Promotion promotion) {
		ModelAndView res;
		try {
			promotionService.delete(promotion);
			res = new ModelAndView("redirect:myEvents.do");
		} catch (Throwable oops) {
			String message;
			if (oops.toString().contains("hasInscriptions")) {
				message = "event.error.delete.hasInscriptions";
			} else {
				message = "event.error.deletePromotion";
			}
			PromotionEditForm promotionEditForm = promotionService
					.constructEditForm(promotion);
			res = createEditModelAndViewPromotionForm(promotionEditForm, message);
		}
		return res;
	}
	
	//Ancillary methods

	protected ModelAndView createEditModelAndViewPromotionForm(
			PromotionEditForm promotionEditForm, String message) {
		Collection<BoardGame> boardGames = boardGameService.findAll();
		ModelAndView res = new ModelAndView("event/business/editPromotion");
		res.addObject("promotionEditForm", promotionEditForm);
		res.addObject("boardGames", boardGames);
		res.addObject("message", message);
		return res;
	}

	protected ModelAndView createEditModelAndViewPromotionForm(
			PromotionEditForm promotionEditForm) {
		return createEditModelAndViewPromotionForm(promotionEditForm, null);
	}
	
	//Tournament
	
	//Creation
	
	@RequestMapping(value="/createTournament", method=RequestMethod.GET)
	public ModelAndView createTournament() {
		TournamentForm tournamentForm = new TournamentForm();
		Collection<BoardGame> boardGames = boardGameService.findAll();
		ModelAndView res = new ModelAndView("event/business/createTournament");
		res.addObject("tournamentForm", tournamentForm);
		res.addObject("boardGames", boardGames);
		return res;
	}
	
	@RequestMapping(value="/createTournament", method=RequestMethod.POST, params="save")
	public ModelAndView saveNewTournament(@Valid TournamentForm tournamentForm, 
			BindingResult bindingResult) {
		Collection<BoardGame> boardGames = boardGameService.findAll();
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = new ModelAndView("event/business/createTournament");
			res.addObject("promotionForm", tournamentForm);
			res.addObject("boardGames", boardGames);
		} else {
			try {
				Tournament tournament = tournamentService.reconstruct(tournamentForm);
				tournamentService.save(tournament);
				res = new ModelAndView("redirect:myEvents.do");
			} catch (Throwable oops) {
				String message;
				if (oops.toString().contains("startInFuture")) {
					message = "event.error.startInFuture";
				} else if (oops.toString().contains("finishInFuture")) {
					message = "event.error.finishInFuture";
				} else if (oops.toString().contains("finishAfterStart")) {
					message = "event.error.finishAfterStart";
				} else if (oops.toString().contains("deadlineInFuture")) {
					message = "event.error.deadlineInFuture";
				} else if (oops.toString().contains("deadlineBeforeStart")) {
					message = "event.error.deadlineBeforeStart";
				} else {
					message = "event.error.createTournament";
				}
				res = new ModelAndView("event/business/createTournament");
				res.addObject("tournamentForm", tournamentForm);
				res.addObject("boardGames", boardGames);
				res.addObject("message", message);
			}
		}
		return res;
	}
	
	//Edition
	
	@RequestMapping(value="/editTournament", method=RequestMethod.GET)
	public ModelAndView editTournament(@RequestParam int tournamentId) {
		ModelAndView res;
		try {
			Tournament tournament = tournamentService.findOne(tournamentId);
			TournamentEditForm tournamentEditForm = tournamentService
					.constructEditForm(tournament);
			res = createEditModelAndViewTournamentForm(tournamentEditForm);
		} catch (Throwable oops) {
			TournamentForm tournamentForm = new TournamentForm();
			Collection<BoardGame> boardGames = boardGameService.findAll();
			res = new ModelAndView("event/business/createTournament");
			res.addObject("tournamentForm", tournamentForm);
			res.addObject("boardGames", boardGames);
			res.addObject("message", "event.error.editTournament");
		}
		return res;
	}
	
	@RequestMapping(value="/editTournament", method=RequestMethod.POST, params="save")
	public ModelAndView saveTournament(@Valid TournamentEditForm tournamentEditForm,
			BindingResult bindingResult) {
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			res = createEditModelAndViewTournamentForm(tournamentEditForm);
		} else {
			try {
				Tournament tournament = tournamentService.reconstruct(tournamentEditForm);
				tournamentService.save(tournament);
				res = new ModelAndView("redirect:myEvents.do");
			} catch (Throwable oops) {
				String message;
				if (oops.toString().contains("finishAfterStart")) {
					message = "event.error.finishAfterStart";
				} else if (oops.toString().contains("deadlineBeforeStart")) {
					message = "event.error.deadlineBeforeStart";
				} else {
					message = "event.error.editTournament";
				}
				res = createEditModelAndViewTournamentForm(tournamentEditForm, message);
			}
		}
		return res;
	}
		
	@RequestMapping(value="/editTournament", method=RequestMethod.POST, params="delete")
	public ModelAndView deleteTournament(TournamentEditForm tournamentEditForm, 
			BindingResult bindingResult) {
		ModelAndView res;
		try {
			Tournament tournament = tournamentService.reconstruct(tournamentEditForm);
			tournamentService.delete(tournament);
			res = new ModelAndView("redirect:myEvents.do");
		} catch (Throwable oops) {
			String message;
			if (oops.toString().contains("hasInscriptions")) {
				message = "event.error.delete.hasInscriptions";
			} else {
				message = "event.error.deleteTournament";
			}
			res = createEditModelAndViewTournamentForm(tournamentEditForm, message);
		}
		return res;
	}
	
	//Remove from my events
	
	@RequestMapping(value="/removeTournament", method=RequestMethod.POST)
	public ModelAndView removeTournament(@RequestParam Tournament tournament) {
		ModelAndView res;
		try {
			tournamentService.delete(tournament);
			res = new ModelAndView("redirect:myEvents.do");
		} catch (Throwable oops) {
			String message;
			if (oops.toString().contains("hasInscriptions")) {
				message = "event.error.delete.hasInscriptions";
			} else {
				message = "event.error.deleteTournament";
			}
			TournamentEditForm tournamentEditForm = tournamentService
					.constructEditForm(tournament);
			res = createEditModelAndViewTournamentForm(tournamentEditForm, message);
		}
		return res;
	}
	
	//Ancillary methods

	protected ModelAndView createEditModelAndViewTournamentForm(
			TournamentEditForm tournamentEditForm, String message) {
		Collection<BoardGame> boardGames = boardGameService.findAll();
		ModelAndView res = new ModelAndView("event/business/editTournament");
		res.addObject("tournamentEditForm", tournamentEditForm);
		res.addObject("boardGames", boardGames);
		res.addObject("message", message);
		return res;
	}

	protected ModelAndView createEditModelAndViewTournamentForm(
			TournamentEditForm tournamentEditForm) {
		return createEditModelAndViewTournamentForm(tournamentEditForm, null);
	}
	
	//Tournament organisation
	
	@RequestMapping(value="/organisation", method=RequestMethod.GET)
	public ModelAndView organisation(@RequestParam int tournamentId) {
		ModelAndView res = new ModelAndView("event/business/organisation");
		Collection<Stage> stages;
		try {
			stages = tournamentService.organisation(tournamentId);
			Player tournamentWinner = tournamentService
					.findOne(tournamentId).getWinner();
			if (tournamentWinner!=null) {
				res.addObject("tournamentWinner", tournamentWinner);
			}
			Integer lastStageFinished = tournamentService
					.getLastStageFinished(tournamentId);
			res.addObject("lastStageFinished", lastStageFinished);
		} catch (Throwable oops) {
			stages = new ArrayList<Stage>();
			String message;
			if (oops.toString().contains("isNotClosed")) {
				message = "event.org.error.isNotClosed";
			} else {
				message = "event.org.error";
			}
			res.addObject("message", message);
		}
		res.addObject("stages", stages);
		for (Stage s: stages) {
			for (Game g: s.getGames()) {
				res.addObject("game" + g.getId(), g.getPlayers());
			}
		}
		res.addObject("requestURI", "event/business/organisation.do");
		return res;
	}
	
	@RequestMapping(value="/setWinner", method=RequestMethod.POST)
	public ModelAndView setWinner(@RequestParam Player player, 
			@RequestParam Game game) {
		Integer id = game.getStage().getTournament().getId();
		ModelAndView res = new ModelAndView("redirect:organisation.do" +
				"?tournamentId=" + id);
		try {
			tournamentService.setWinner(player, game);
		} catch (Throwable oops) {
			String message;
			if (oops.toString().contains("isNotClosed")) {
				message = "event.org.error.isNotClosed";
			} else {
				message = "event.org.error.setWinner";
			}
			res.addObject("message", message);
		}
		return res;
	}

}
