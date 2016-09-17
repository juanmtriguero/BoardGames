package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.Business;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Integer> {

	@Query("select b from Business b where b.userAccount = ?1")
	Business findByUserAccount(UserAccount userAccount);
	
	@Query("select b from Business b where b.rating = (select max(bus.rating) from Business bus))")
	Collection<Business> bestRated();
	
	@Query("select b from Business b where b.rating = (select min(bus.rating) from Business bus))")
	Collection<Business> worstRated();

}
