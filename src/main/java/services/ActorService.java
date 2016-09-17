package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {
	
	//Managed repository
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private ActorRepository actorRepository;
	
	//Auxiliary methods
	
	public boolean existsUsername(String username) {
		boolean res = false;
		for (UserAccount user: userAccountRepository.findAll()) {
			if (user.getUsername().equals(username)) {
				res = true;
				break;
			}
		}
		return res;
	}

	public boolean existsEmail(String email) {
		boolean res = false;
		for (Actor actor: actorRepository.findAll()) {
			if (actor.getEmail().equals(email)) {
				res = true;
				break;
			}
		}
		return res;
	}

}
