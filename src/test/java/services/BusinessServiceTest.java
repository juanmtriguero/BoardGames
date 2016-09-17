package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Business;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class BusinessServiceTest extends AbstractTest {
	
	//Supporting services

	@Autowired
	private BusinessService businessService;
	
	// -------------------------------------------------------
	
	// A user who is not authenticated must be able
	// to register to the system as a business.
	
	// POSITIVE TEST CASE: An anonymous user
	// can register to the system as a business.
	
	@Test
	public void testRegisterBusiness() {
		Business business = businessService.create();
		UserAccount acc = business.getUserAccount();
		acc.setUsername("businessTest");
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		acc.setPassword(encoder.encodePassword("passTest", null));
		business.setUserAccount(acc);
		business.setName("Alcalá juegos");
		business.setEmail("alcala_juegos@mail.com");
		business.setCif("U5789250G");
		business.setStreet("C/ Olivar de la huerta, 15");
		business.setZip(40060);
		business.setCity("Alcalá de Guadaira");
		business.setWeb("http://www.alcala-juegos.com");
		business.setPhone("987654321");
		businessService.save(business);
	}
	
	// NEGATIVE TEST CASE: An authenticated user
	// cannot register to the system as a business.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAuthenticatedRegisterBusiness() {
		authenticate("business1");
		Business business = businessService.create();
		UserAccount acc = business.getUserAccount();
		acc.setUsername("businessTest");
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		acc.setPassword(encoder.encodePassword("passTest", null));
		business.setUserAccount(acc);
		business.setName("Alcalá juegos");
		business.setEmail("alcala_juegos@mail.com");
		business.setCif("U5789250G");
		business.setStreet("C/ Olivar de la huerta, 15");
		business.setZip(40060);
		business.setCity("Alcalá de Guadaira");
		business.setWeb("http://www.alcala-juegos.com");
		business.setPhone("987654321");
		businessService.save(business);
	}
	
	// NEGATIVE TEST CASE: An anonymous user cannot register 
	// to the system as a business with an used email.

	@Test(expected=DataIntegrityViolationException.class)
	public void testRegisterBusinessUsedEmail() {
		Business business = businessService.create();
		UserAccount acc = business.getUserAccount();
		acc.setUsername("businessTest");
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		acc.setPassword(encoder.encodePassword("passTest", null));
		business.setUserAccount(acc);
		business.setName("Alcalá juegos");
		business.setEmail("info@agorajuegos.es");
		business.setCif("U5789250G");
		business.setStreet("C/ Olivar de la huerta, 15");
		business.setZip(40060);
		business.setCity("Alcalá de Guadaira");
		business.setWeb("http://www.alcala-juegos.com");
		business.setPhone("987654321");
		businessService.save(business);
	}

	// -------------------------------------------------------
	
	// A user who is authenticated must be able to
	// list and see the businesses of the system.
	
	// POSITIVE TEST CASE: A business can list all businesses.
	
	@Test
	public void testListBusinesses() {
		authenticate("business1");
		Collection<Business> all = businessService.findAll();
		Assert.isTrue(all.size()==3);
	}
	
	// NEGATIVE TEST CASE: An anonymous user cannot list all businesses.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousListBusinesses() {
		Collection<Business> all = businessService.findAll();
		Assert.isTrue(all.size()==3);
	}
	
	// -------------------------------------------------------

}
