package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserVoteRepository;
import security.LoginService;
import domain.Business;
import domain.Player;
import domain.UserVote;

@Service
@Transactional
public class UserVoteService {
	
	//Managed repository
	
	@Autowired
	private UserVoteRepository userVoteRepository;

	//Supporting services
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private BusinessService businessService;
	
	//Simple CRUD methods
	
	public UserVote create(int businessId) {
		Assert.isTrue(playerService.isPlayer());
		UserVote userVote = new UserVote();
		Player player = playerService.findByUserAccount(LoginService.getPrincipal());
		userVote.setPlayer(player);
		Business business = businessService.findOne(businessId);
		userVote.setBusiness(business);
		return userVote;
	}
	
	public void save(UserVote userVote) {
		Assert.notNull(userVote);
		Assert.isTrue(playerService.isPlayer());
		Player player = userVote.getPlayer();
		Assert.isTrue(LoginService.getPrincipal().equals(player.getUserAccount()));
		Business business = userVote.getBusiness();
		Assert.isTrue(!hasVoted(player, business), "hasVoted");
		userVoteRepository.saveAndFlush(userVote);
		businessService.updateRating(business);
	}
	
	//Auxiliary methods
	
	public boolean hasVoted(Player player, Business business) {
		boolean res = false;
		for (UserVote u: player.getUserVotes()) {
			if (u.getBusiness().equals(business)) {
				res = true;
				break;
			}
		}
		return res;
	}

}
