package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PlayerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.BoardGame;
import domain.Event;
import domain.Following;
import domain.Inscription;
import domain.Player;
import domain.Tournament;
import domain.UserVote;
import forms.PlayerForm;

@Service
@Transactional
public class PlayerService {
	
	//Managed repository
	
	@Autowired
	private PlayerRepository playerRepository;

	//Supporting services
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private EventService eventService;
	
	//Simple CRUD methods
	
	public Player findOne(int playerId) {
		return playerRepository.findOne(playerId);
	}
	
	public Collection<Player> findAll() {
		Assert.isTrue(adminService.isAdmin() 
				|| isPlayer() 
				|| businessService.isBusiness());
		return playerRepository.findAll();
	}
	
	public Player create(){
		Assert.isTrue(!adminService.isAdmin()
				&& !businessService.isBusiness()
				&& !isPlayer());
		Player player = new Player();
		UserAccount userAccount = new UserAccount();
		List<Authority> authorities = new ArrayList<Authority>();
		Authority a = new Authority();
		a.setAuthority(Authority.PLAYER);
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		player.setUserAccount(userAccount);
		List<Inscription> inscriptions = new ArrayList<Inscription>();
		player.setInscriptions(inscriptions);
		List<Tournament> tournaments = new ArrayList<Tournament>();
		player.setTournaments(tournaments);
		List<Following> followeds = new ArrayList<Following>();
		player.setFolloweds(followeds);
		List<Following> followers = new ArrayList<Following>();
		player.setFollowers(followers);
		List<UserVote> userVotes = new ArrayList<UserVote>();
		player.setUserVotes(userVotes);
		return player;
	}

	public void save(Player player){
		Assert.notNull(player);
		Assert.isTrue(!adminService.isAdmin()
				&& !businessService.isBusiness()
				&& !isPlayer());
		playerRepository.saveAndFlush(player);
	}
	
	//Form methods
	
	public Player reconstruct(PlayerForm playerForm) {
		Assert.isTrue(playerForm.getPassword().equals(
				playerForm.getConfirmPassword()), "distinctPasswords");
		Assert.isTrue(playerForm.getAcceptConditions(), "acceptConditions");
		Player res = create();
		UserAccount userAccount = res.getUserAccount();
		userAccount.setUsername(playerForm.getUsername());
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword(playerForm.getPassword(), null);
		userAccount.setPassword(password);
		res.setUserAccount(userAccount);
		res.setName(playerForm.getName());
		res.setEmail(playerForm.getEmail());
		res.setAvatar(playerForm.getAvatar());
		return res;
	}
	
	//Other business methods
	
	public Collection<Object[]> findByEventId(int eventId) {
		Assert.isTrue(adminService.isAdmin() 
				|| isPlayer() 
				|| businessService.isBusiness());
		Event aux = eventService.findOne(eventId);
		Assert.notNull(aux);
		return playerRepository.findByEventId(eventId);
	}
	
	public Collection<Player> joinedMostEvents() {
		Assert.isTrue(adminService.isAdmin());
		return playerRepository.joinedMostEvents();
	}
	
	public Collection<Player> joinedLessEvents() {
		Assert.isTrue(adminService.isAdmin());
		return playerRepository.joinedLessEvents();
	}
	
	public Object[] playedMostBoardGames() {
		Assert.isTrue(adminService.isAdmin());
		Collection<Player> pmbg = new ArrayList<Player>();
		Integer max = 0;
		for (Player p: playerRepository.findAll()) {
			Set<BoardGame> bg = new HashSet<BoardGame>();
			for (Inscription i: p.getInscriptions()) {
				// Repeated board games aren't saved (it's a set)
				bg.add(i.getEvent().getBoardGame());
			}
			Integer played = bg.size();
			if (played >= max) {
				if (played > max) {
					max = played;
					pmbg.clear();
				}
				pmbg.add(p);
			}
		}
		Object[] res = new Object[2];
		res[0] = pmbg;
		res[1] = max;
		return res;
	}
	
	public Player findByUserAccount(UserAccount userAccount) {
		return playerRepository.findByUserAccount(userAccount);
	}
	
	public Collection<Player> followeds() {
		Assert.isTrue(isPlayer());
		Player player = findByUserAccount(LoginService.getPrincipal());
		Collection<Player> followeds = new ArrayList<Player>();
		for (Following f: player.getFolloweds()) {
			followeds.add(f.getFollowed());
		}
		return followeds;
	}
	
	public Collection<Player> followers() {
		Assert.isTrue(isPlayer());
		Player player = findByUserAccount(LoginService.getPrincipal());
		Collection<Player> followers = new ArrayList<Player>();
		for (Following f: player.getFollowers()) {
			followers.add(f.getFollower());
		}
		return followers;
	}
	
	public Collection<String[]> followedActivity() {
		Assert.isTrue(isPlayer());
		List<String[]> res = new ArrayList<String[]>();
		for (Player p: followeds()) {
			Collection<Following> followings = p.getFolloweds();
			for (Following f: followings) {
				String[] aux = new String[3];
				aux[0] = p.getName();
				aux[1] = "follows";
				aux[2] = f.getFollowed().getName();
				res.add(aux);
			}
			Collection<Inscription> inscriptions = p.getInscriptions();
			for (Inscription i: inscriptions) {
				String[] aux = new String[3];
				aux[0] = p.getName();
				aux[1] = "inscribe";
				aux[2] = i.getEvent().getTitle();
				res.add(aux);
			}
			Collection<UserVote> userVotes = p.getUserVotes();
			for (UserVote u: userVotes) {
				String[] aux = new String[3];
				aux[0] = p.getName();
				aux[1] = "votes";
				aux[2] = u.getBusiness().getName();
				res.add(aux);
			}
		}
		Collections.shuffle(res);
		return res;
	}
	
	public Double averageFollowedPerPlayer() {
		Assert.isTrue(adminService.isAdmin());
		return playerRepository.averageFollowedPerPlayer();
	}
	
	//Auxiliary methods
	
	public boolean isPlayer() {
		boolean res = false;
		try {
			Collection<Authority> authorities = LoginService
					.getPrincipal().getAuthorities();
			for (Authority a: authorities) {
				if (a.getAuthority().equals(Authority.PLAYER)) {
					res = true;
					break;
				}
			}
		} catch (Exception e) {
			// Is not authenticated
		}
		return res;
	}

}
