package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AdminRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Admin;

@Service
@Transactional
public class AdminService {
	
	//Managed repository
	
	@Autowired
	private AdminRepository adminRepository;
	
	//Other business methods
	
	public Admin findByUserAccount(UserAccount userAccount) {
		return adminRepository.findByUserAccount(userAccount);
	}
	
	//Auxiliary methods
	
	public boolean isAdmin() {
		boolean res = false;
		try {
			Collection<Authority> authorities = LoginService
					.getPrincipal().getAuthorities();
			for (Authority a: authorities) {
				if (a.getAuthority().equals(Authority.ADMIN)) {
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
