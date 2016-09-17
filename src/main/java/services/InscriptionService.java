package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InscriptionRepository;
import security.LoginService;
import domain.Event;
import domain.Inscription;
import domain.Player;

@Service
@Transactional
public class InscriptionService {
	
	//Managed repository
	
	@Autowired
	private InscriptionRepository inscriptionRepository;

	//Supporting services
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private EventService eventService;
	
	//Simple CRUD methods
	
	public Inscription create(int eventId) {
		Assert.isTrue(playerService.isPlayer());
		Inscription inscription = new Inscription();
		Player player = playerService.findByUserAccount(LoginService.getPrincipal());
		inscription.setPlayer(player);
		Event event = eventService.findOne(eventId);
		inscription.setEvent(event);
		return inscription;
	}
	
	public void save(Inscription inscription) {
		Assert.notNull(inscription);
		Assert.isTrue(playerService.isPlayer());
		Player player = inscription.getPlayer();
		Assert.isTrue(LoginService.getPrincipal().equals(player.getUserAccount()));
		Event event = inscription.getEvent();
		Assert.isTrue(!eventService.isClosed(event), "isClosed");
		Assert.isTrue(!isInscribed(player, event), "isInscribed");
		inscriptionRepository.saveAndFlush(inscription);
	}

	public void delete(Inscription inscription) {
		Assert.notNull(inscription, "isNotInscribed");
		Assert.isTrue(playerService.isPlayer());
		Player player = inscription.getPlayer();
		Assert.isTrue(LoginService.getPrincipal().equals(player.getUserAccount()));
		Event event = inscription.getEvent();
		Assert.isTrue(!eventService.isClosed(event), "isClosed");
		Assert.isTrue(isInscribed(player, event), "isNotInscribed");
		inscriptionRepository.delete(inscription);
	}
	
	//Other business methods
	
	public Inscription findMineByEventId(int eventId) {
		Assert.isTrue(playerService.isPlayer());
		Inscription res = null;
		Player player = playerService.findByUserAccount(LoginService.getPrincipal());
		for (Inscription i: player.getInscriptions()) {
			if (i.getEvent().getId()==eventId) {
				res = i;
				break;
			}
		}
		return res;
	}
	
	//Auxiliary methods
	
	public boolean isInscribed(Player player, Event event) {
		boolean res = false;
		for (Inscription i: player.getInscriptions()) {
			if (i.getEvent().equals(event)) {
				res = true;
				break;
			}
		}
		return res;
	}

}
