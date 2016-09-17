package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FollowingRepository;
import security.LoginService;
import domain.Following;
import domain.Player;

@Service
@Transactional
public class FollowingService {
	
	//Managed repository
	
	@Autowired
	private FollowingRepository followingRepository;

	//Supporting services
	
	@Autowired
	private PlayerService playerService;
	
	//Simple CRUD methods
	
	public Following create(int playerId) {
		Assert.isTrue(playerService.isPlayer());
		Following following = new Following();
		Player follower = playerService.findByUserAccount(LoginService.getPrincipal());
		following.setFollower(follower);
		Player followed = playerService.findOne(playerId);
		following.setFollowed(followed);
		return following;
	}
	
	public void save(Following following) {
		Assert.notNull(following);
		Assert.isTrue(playerService.isPlayer());
		Player follower = following.getFollower();
		Assert.isTrue(LoginService.getPrincipal()
				.equals(follower.getUserAccount()));
		Player followed = following.getFollowed();
		Assert.isTrue(!follows(follower, followed), "follows");
		Assert.isTrue(!follower.equals(followed));
		followingRepository.saveAndFlush(following);
	}

	public void delete(Following following) {
		Assert.notNull(following, "notFollows");
		Assert.isTrue(playerService.isPlayer());
		Player follower = following.getFollower();
		Assert.isTrue(LoginService.getPrincipal()
				.equals(follower.getUserAccount()));
		Player followed = following.getFollowed();
		Assert.isTrue(follows(follower, followed), "notFollows");
		followingRepository.delete(following);
	}
	
	//Other business methods

	public Following findMineByFollowedId(int playerId) {
		Assert.isTrue(playerService.isPlayer());
		Player follower = playerService.findByUserAccount(
				LoginService.getPrincipal());
		Player followed = playerService.findOne(playerId);
		Following res = null;
		Collection<Following> myFollowings = follower.getFolloweds();
		for (Following f: myFollowings) {
			if (f.getFollowed().equals(followed)) {
				res = f;
				break;
			}
		}
		return res;
	}
	
	//Auxiliary methods

	public boolean follows(Player follower, Player followed) {
		boolean res = false;
		for (Following f: follower.getFolloweds()) {
			if (f.getFollowed().equals(followed)) {
				res = true;
				break;
			}
		}
		return res;
	}

}
