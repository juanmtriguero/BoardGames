package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BusinessRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Bulletin;
import domain.Business;
import domain.Event;
import domain.UserVote;
import forms.BusinessForm;

@Service
@Transactional
public class BusinessService {
	
	//Managed repository
	
	@Autowired
	private BusinessRepository businessRepository;

	//Supporting services
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private PlayerService playerService;
	
	//Simple CRUD methods
	
	public Business findOne(int businessId) {
		return businessRepository.findOne(businessId);
	}
	
	public Collection<Business> findAll() {
		Assert.isTrue(adminService.isAdmin() 
				|| playerService.isPlayer() 
				|| isBusiness());
		return businessRepository.findAll();
	}
	
	public Business create(){
		Assert.isTrue(!adminService.isAdmin()
				&& !isBusiness()
				&& !playerService.isPlayer());
		Business business = new Business();
		UserAccount userAccount = new UserAccount();
		List<Authority> authorities = new ArrayList<Authority>();
		Authority a = new Authority();
		a.setAuthority(Authority.BUSINESS);
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		business.setUserAccount(userAccount);
		List<Event> events = new ArrayList<Event>();
		business.setEvents(events);
		business.setRating(0.0);
		List<UserVote> userVotes = new ArrayList<UserVote>();
		business.setUserVotes(userVotes);
		List<Bulletin> bulletins = new ArrayList<Bulletin>();
		business.setBulletins(bulletins);
		return business;
	}

	public void save(Business business){
		Assert.notNull(business);
		Assert.isTrue(!adminService.isAdmin()
				&& !isBusiness()
				&& !playerService.isPlayer());
		businessRepository.saveAndFlush(business);
	}
	
	//Form methods
	
	public Business reconstruct(BusinessForm businessForm) {
		Assert.isTrue(businessForm.getPassword().equals(
				businessForm.getConfirmPassword()), "distinctPasswords");
		Assert.isTrue(businessForm.getAcceptConditions(), "acceptConditions");
		Business res = create();
		UserAccount userAccount = res.getUserAccount();
		userAccount.setUsername(businessForm.getUsername());
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword(businessForm.getPassword(), null);
		userAccount.setPassword(password);
		res.setUserAccount(userAccount);
		res.setName(businessForm.getName());
		res.setEmail(businessForm.getEmail());
		res.setAvatar(businessForm.getAvatar());
		res.setCif(businessForm.getCif());
		res.setStreet(businessForm.getStreet());
		res.setZip(businessForm.getZip());
		res.setCity(businessForm.getCity());
		res.setWeb(businessForm.getWeb());
		res.setPhone(businessForm.getPhone());
		return res;
	}
	
	//Other business methods
	
	public Business findByUserAccount(UserAccount userAccount) {
		return businessRepository.findByUserAccount(userAccount);
	}
	
	public void updateRating(Business business) {
		Collection<UserVote> userVotes = business.getUserVotes();
		Double sum = 0.0;
		for (UserVote v: userVotes) {
			sum += v.getScore();
		}
		Double rating = sum / userVotes.size();
		business.setRating(rating);
	}
	
	public Collection<Business> bestRated() {
		Assert.isTrue(adminService.isAdmin());
		return businessRepository.bestRated();
	}
	
	public Collection<Business> worstRated() {
		Assert.isTrue(adminService.isAdmin());
		return businessRepository.worstRated();
	}
	
	//Auxiliary methods
	
	public boolean isBusiness() {
		boolean res = false;
		try {
			Collection<Authority> authorities = LoginService
					.getPrincipal().getAuthorities();
			for (Authority a: authorities) {
				if (a.getAuthority().equals(Authority.BUSINESS)) {
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
