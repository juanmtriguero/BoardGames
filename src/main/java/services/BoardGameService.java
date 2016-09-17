package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoardGameRepository;
import repositories.CategorizationRepository;
import domain.BoardGame;
import domain.Categorization;
import domain.Category;
import domain.Event;
import forms.BoardGameEditForm;
import forms.BoardGameForm;

@Service
@Transactional
public class BoardGameService {
	
	//Managed repository
	
	@Autowired
	private BoardGameRepository boardGameRepository;
	
	@Autowired
	private CategorizationRepository categorizationRepository;

	//Supporting services
	
	@Autowired
	private AdminService adminService;
	
	//Simple CRUD methods
	
	public Collection<BoardGame> findAll() {
		return boardGameRepository.findAll();
	}
	
	public BoardGame findOne(int boardGameId) {
		return boardGameRepository.findOne(boardGameId);
	}
	
	public BoardGame create() {
		Assert.isTrue(adminService.isAdmin());
		BoardGame boardGame = new BoardGame();
		List<Categorization> categorizations = new ArrayList<Categorization>();
		boardGame.setCategorizations(categorizations);
		List<Event> events = new ArrayList<Event>();
		boardGame.setEvents(events);
		return boardGame;
	}
	
	public void save(BoardGame boardGame) {
		Assert.notNull(boardGame);
		Assert.isTrue(adminService.isAdmin());
		boardGameRepository.saveAndFlush(boardGame);
	}
	
	public void delete(BoardGame boardGame) {
		Assert.notNull(boardGame);
		Assert.isTrue(adminService.isAdmin());
		Assert.isTrue(boardGame.getEvents().isEmpty(), "hasEvents");
		boardGameRepository.delete(boardGame);
	}
	
	//Form methods
	
	public BoardGame reconstruct(BoardGameForm boardGameForm) {
		Assert.notNull(boardGameForm);
		Assert.isTrue(adminService.isAdmin());
		BoardGame res = create();
		res.setTitle(boardGameForm.getTitle());
		res.setDescription(boardGameForm.getDescription());
		res.setNumberMaxPlayers(boardGameForm.getNumberMaxPlayers());
		res.setPhoto(boardGameForm.getPhoto());
		Collection<Category> categories = boardGameForm.getCategories();
		Collection<Categorization> categorizations = res.getCategorizations();
		for (Category category: categories) {
			Categorization categorization = new Categorization();
			categorization.setCategory(category);
			categorization.setBoardGame(res);
			categorizations.add(categorization);
		}
		res.setCategorizations(categorizations);
		return res;
	}
	
	public BoardGame reconstruct(BoardGameEditForm boardGameEditForm) {
		Assert.notNull(boardGameEditForm);
		Assert.isTrue(adminService.isAdmin());
		BoardGame res = findOne(boardGameEditForm.getId());
		Assert.notNull(res);
		res.setTitle(boardGameEditForm.getTitle());
		res.setDescription(boardGameEditForm.getDescription());
		res.setNumberMaxPlayers(boardGameEditForm.getNumberMaxPlayers());
		res.setPhoto(boardGameEditForm.getPhoto());
		Collection<Category> categories = boardGameEditForm.getCategories();
		Collection<Categorization> categorizations = res.getCategorizations();
		// Create new categorisations
		Collection<Category> toCreate = new ArrayList<Category>();
		for (Category category: categories) {
			boolean isNew = true;
			for (Categorization c: categorizations) {
				if (c.getCategory().equals(category)) {
					isNew = false;
					break;
				}
			}
			if (isNew) {
				toCreate.add(category);
			}
		}
		for (Category aux: toCreate) {
			Categorization newCategorization = new Categorization();
			newCategorization.setCategory(aux);
			newCategorization.setBoardGame(res);
			res.getCategorizations().add(newCategorization);
		}
		// Remove categorisations of unselected categories
		Collection<Categorization> toRemove = new ArrayList<Categorization>();
		for (Categorization categorization: categorizations) {
			boolean eliminate = true;
			for (Category c: categories) {
				if (categorization.getCategory().equals(c)) {
					eliminate = false;
					break;
				}
			}
			if (eliminate) {
				toRemove.add(categorization);
			}
		}
		for (Categorization aux: toRemove) {
			categorizationRepository.delete(aux);
			res.getCategorizations().remove(aux);
		}
		return res;
	}
	
	public BoardGameForm constructForm(BoardGame boardGame) {
		Assert.notNull(boardGame);
		Assert.isTrue(adminService.isAdmin());
		BoardGameForm res = new BoardGameForm();
		Collection<Category> categories = new ArrayList<Category>();
		for (Categorization c: boardGame.getCategorizations()) {
			categories.add(c.getCategory());
		}
		res.setCategories(categories);
		res.setTitle(boardGame.getTitle());
		res.setDescription(boardGame.getDescription());
		res.setNumberMaxPlayers(boardGame.getNumberMaxPlayers());
		res.setPhoto(boardGame.getPhoto());
		return res;
	}
	
	public BoardGameEditForm constructEditForm(BoardGame boardGame) {
		Assert.notNull(boardGame);
		Assert.isTrue(adminService.isAdmin());
		BoardGameEditForm res = new BoardGameEditForm();
		res.setId(boardGame.getId());
		Collection<Category> categories = new ArrayList<Category>();
		for (Categorization c: boardGame.getCategorizations()) {
			categories.add(c.getCategory());
		}
		res.setCategories(categories);
		res.setTitle(boardGame.getTitle());
		res.setDescription(boardGame.getDescription());
		res.setNumberMaxPlayers(boardGame.getNumberMaxPlayers());
		res.setPhoto(boardGame.getPhoto());
		return res;
	}
	
	//Other business methods
	
	public Collection<BoardGame> usedInMostEvents() {
		Assert.isTrue(adminService.isAdmin());
		return boardGameRepository.usedInMostEvents();
	}
	
	public Collection<BoardGame> usedInLessEvents() {
		Assert.isTrue(adminService.isAdmin());
		return boardGameRepository.usedInLessEvents();
	}

}
