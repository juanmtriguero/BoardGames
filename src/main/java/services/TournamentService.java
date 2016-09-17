package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StageRepository;
import repositories.TournamentRepository;
import security.LoginService;
import domain.BoardGame;
import domain.Business;
import domain.Game;
import domain.Inscription;
import domain.Player;
import domain.Stage;
import domain.Tournament;
import forms.TournamentEditForm;
import forms.TournamentForm;

@Service
@Transactional
public class TournamentService {
	
	//Managed repository
	
	@Autowired
	private TournamentRepository tournamentRepository;
	
	@Autowired
	private StageRepository stageRepository;

	//Supporting services
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private EventService eventService;
	
	//Simple CRUD methods
	
	public Tournament findOne(int tournamentId) {
		return tournamentRepository.findOne(tournamentId);
	}
	
	public Collection<Tournament> findAll() {
		Assert.isTrue(adminService.isAdmin() 
				|| playerService.isPlayer() 
				|| businessService.isBusiness());
		return tournamentRepository.findAll();
	}
	
	public Tournament create() {
		Assert.isTrue(businessService.isBusiness());
		Tournament tournament = new Tournament();
		tournament.setCreationMoment(new Date(System.currentTimeMillis()-1));
		tournament.setBusiness(businessService.
				findByUserAccount(LoginService.getPrincipal()));
		List<Inscription> inscriptions = new ArrayList<Inscription>();
		tournament.setInscriptions(inscriptions);
		BoardGame boardGame = new BoardGame();
		tournament.setBoardGame(boardGame);
		List<Stage> stages = new ArrayList<Stage>();
		tournament.setStages(stages);
		return tournament;
	}
	
	public void save(Tournament tournament) {
		Assert.notNull(tournament);
		Assert.isTrue(businessService.isBusiness());
		Assert.isTrue(LoginService.getPrincipal()
				.equals(tournament.getBusiness().getUserAccount()));
		tournamentRepository.saveAndFlush(tournament);
	}
	
	public void delete(Tournament tournament) {
		Assert.notNull(tournament);
		Assert.isTrue(businessService.isBusiness());
		Assert.isTrue(LoginService.getPrincipal()
				.equals(tournament.getBusiness().getUserAccount()));
		Assert.isTrue(tournament.getInscriptions().isEmpty(), "hasInscriptions");
		tournamentRepository.delete(tournament);
	}
	
	//Other business methods
	
	public Collection<Tournament> findByBusinessId(int businessId) {
		Assert.isTrue(adminService.isAdmin() 
				|| playerService.isPlayer() 
				|| businessService.isBusiness());
		Business aux = businessService.findOne(businessId);
		Assert.notNull(aux);
		return tournamentRepository.findByBusinessId(businessId);
	}
	
	public Collection<Tournament> findMyTournaments() {
		Assert.isTrue(businessService.isBusiness());
		int businessId = businessService.
				findByUserAccount(LoginService.getPrincipal()).getId();
		return tournamentRepository.findByBusinessId(businessId);
	}
	
	public Collection<Object[]> findInscribed() {
		Assert.isTrue(playerService.isPlayer());
		int playerId = playerService.
				findByUserAccount(LoginService.getPrincipal()).getId();
		return tournamentRepository.findByPlayerId(playerId);
	}
	
	//Form methods
	
	public Tournament reconstruct(TournamentForm tournamentForm) {
		Assert.notNull(tournamentForm);
		Assert.isTrue(businessService.isBusiness());
		Tournament res = create();
		res.setTitle(tournamentForm.getTitle());
		res.setDescription(tournamentForm.getDescription());
		Date now = new Date(System.currentTimeMillis()-1);
		Date startMoment = tournamentForm.getStartMoment();
		Assert.isTrue(startMoment.after(now), "startInFuture");
		res.setStartMoment(startMoment);
		Date finishMoment = tournamentForm.getFinishMoment();
		Assert.isTrue(finishMoment.after(now), "finishInFuture");
		Assert.isTrue(finishMoment.after(startMoment), "finishAfterStart");
		res.setFinishMoment(finishMoment);
		Date inscriptionDeadline = tournamentForm.getInscriptionDeadline();
		Assert.isTrue(inscriptionDeadline.after(now), "deadlineInFuture");
		Assert.isTrue(inscriptionDeadline.before(startMoment), "deadlineBeforeStart");
		res.setInscriptionDeadline(inscriptionDeadline);
		res.setNumberMaxParticipants(tournamentForm.getNumberMaxParticipants());
		res.setPrice(tournamentForm.getPrice());
		res.setLocation(tournamentForm.getLocation());
		res.setBoardGame(tournamentForm.getBoardGame());
		res.setAward(tournamentForm.getAward());
		return res;
	}
	
	public Tournament reconstruct(TournamentEditForm tournamentEditForm) {
		Assert.notNull(tournamentEditForm);
		Assert.isTrue(businessService.isBusiness());
		Tournament res = findOne(tournamentEditForm.getId());
		Assert.notNull(res);
		res.setTitle(tournamentEditForm.getTitle());
		res.setDescription(tournamentEditForm.getDescription());
		Date startMoment = tournamentEditForm.getStartMoment();
		res.setStartMoment(startMoment);
		Date finishMoment = tournamentEditForm.getFinishMoment();
		Assert.isTrue(finishMoment.after(startMoment), "finishAfterStart");
		res.setFinishMoment(finishMoment);
		Date inscriptionDeadline = res.getInscriptionDeadline();
		Assert.isTrue(inscriptionDeadline.before(startMoment), "deadlineBeforeStart");
		res.setPrice(tournamentEditForm.getPrice());
		res.setLocation(tournamentEditForm.getLocation());
		res.setAward(tournamentEditForm.getAward());
		return res;
	}
	
	public TournamentForm constructForm(Tournament tournament) {
		Assert.notNull(tournament);
		Assert.isTrue(businessService.isBusiness());
		TournamentForm res = new TournamentForm();
		res.setTitle(tournament.getTitle());
		res.setDescription(tournament.getDescription());
		res.setStartMoment(tournament.getStartMoment());
		res.setFinishMoment(tournament.getFinishMoment());
		res.setInscriptionDeadline(tournament.getInscriptionDeadline());
		res.setNumberMaxParticipants(tournament.getNumberMaxParticipants());
		res.setPrice(tournament.getPrice());
		res.setLocation(tournament.getLocation());
		res.setBoardGame(tournament.getBoardGame());
		res.setAward(tournament.getAward());
		return res;
	}
	
	public TournamentEditForm constructEditForm(Tournament tournament) {
		Assert.notNull(tournament);
		Assert.isTrue(businessService.isBusiness());
		TournamentEditForm res = new TournamentEditForm();
		res.setId(tournament.getId());
		res.setTitle(tournament.getTitle());
		res.setDescription(tournament.getDescription());
		res.setStartMoment(tournament.getStartMoment());
		res.setFinishMoment(tournament.getFinishMoment());
		res.setPrice(tournament.getPrice());
		res.setLocation(tournament.getLocation());
		res.setAward(tournament.getAward());
		return res;
	}
	
	//Tournament organisation
	
	public List<Stage> organisation(int tournamentId) {
		Assert.isTrue(businessService.isBusiness());
		Tournament tournament = tournamentRepository.findOne(tournamentId);
		Assert.isTrue(LoginService.getPrincipal()
				.equals(tournament.getBusiness().getUserAccount()));
		Assert.isTrue(eventService.isClosed(tournament), "isNotClosed");
		Collection<Stage> stages = stageRepository.findByTournamentId(tournamentId);
		// Organisation is created automatically
		if (stages.isEmpty()) {
			stages = new ArrayList<Stage>();
			List<Player> participants = new ArrayList<Player>();
			for (Inscription i: tournament.getInscriptions()) {
				participants.add(i.getPlayer());
			}
			int numberWinners = participants.size();
			int numberMaxPlayers = tournament.getBoardGame().getNumberMaxPlayers();
			int cont = 1;
			while (numberWinners>1) {
				double div = ((double) numberWinners) / ((double) numberMaxPlayers);
				int numberGames = (int) Math.ceil(div);
				Stage stage = new Stage();
				stage.setNumber(cont);
				stage.setTournament(tournament);
				Collection<Game> games = new ArrayList<Game>();
				for (int i=0; i<numberGames; i++) {
					Game game = new Game();
					game.setStage(stage);
					game.setPlayers(new ArrayList<Player>());
					games.add(game);
				}
				stage.setGames(games);
				if (cont==1) {
					// Set the players of the first stage
					for (Game g: stage.getGames()) {
						Collection<Player> players;
						int aux = participants.size();
						if (aux>=numberMaxPlayers) {
							players = participants.subList(0, numberMaxPlayers);
						} else {
							players = participants;
						}
						g.setPlayers(new ArrayList<Player>(players));
						participants.removeAll(players);
					}
				}
				stages.add(stage);
				cont++;
				numberWinners = numberGames;
			}
			tournament.setStages(stages);
			tournamentRepository.saveAndFlush(tournament);
		}
		List<Stage> res = new ArrayList<Stage>(stages);
		Collections.sort(res, new Comparator<Stage>() {
			public int compare(Stage s1, Stage s2) {
				return s1.getNumber().compareTo(s2.getNumber());
			}
		});
		return res;
	}

	public void setWinner(Player player, Game game) {
		Assert.isTrue(businessService.isBusiness());
		Assert.isNull(game.getGameWinner());
		Stage stage = game.getStage();
		Tournament tournament = stage.getTournament();
		Assert.isTrue(LoginService.getPrincipal()
				.equals(tournament.getBusiness().getUserAccount()));
		Assert.isTrue(eventService.isClosed(tournament), "isNotClosed");		
		game.setGameWinner(player);
		if (isFinished(stage)) {
			// Set the players of next stage
			List<Player> winners = new ArrayList<Player>();
			for (Game g: stage.getGames()) {
				winners.add(g.getGameWinner());
			}
			Stage nextStage = null;
			for (Stage s: tournament.getStages()) {
				if (s.getNumber().equals(stage.getNumber()+1)) {
					nextStage = s;
					break;
				}
			}
			if (nextStage==null) {
				// There are no more stages
				tournament.setWinner(player);
			} else {
				int numberMaxPlayers = tournament.getBoardGame()
						.getNumberMaxPlayers();
				for (Game g: nextStage.getGames()) {
					Collection<Player> players;
					int aux = winners.size();
					if (aux>=numberMaxPlayers) {
						players = winners.subList(0, numberMaxPlayers);
					} else {
						players = winners.subList(0, aux+1);
					}
					g.setPlayers(new ArrayList<Player>(players));
					winners.removeAll(players);
				}
			}
		}
		tournamentRepository.saveAndFlush(tournament);
	}

	public Integer getLastStageFinished(int tournamentId) {
		Assert.isTrue(businessService.isBusiness());
		Tournament tournament = tournamentRepository.findOne(tournamentId);
		Assert.isTrue(LoginService.getPrincipal()
				.equals(tournament.getBusiness().getUserAccount()));
		Assert.isTrue(eventService.isClosed(tournament), "isNotClosed");
		Collection<Stage> stages = stageRepository.findByTournamentId(tournamentId);
		Integer res = 0;
		for (Stage s: stages) {
			if (isFinished(s)) {
				res = s.getNumber();
			} else {
				break;
			}
		}
		return res;
	}

	private boolean isFinished(Stage s) {
		boolean res = true;
		for (Game g: s.getGames()) {
			if (g.getGameWinner()==null) {
				res = false;
				break;
			}
		}
		return res;
	}

}
