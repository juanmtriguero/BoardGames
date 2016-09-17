package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Bulletin;
import domain.Business;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class BulletinServiceTest extends AbstractTest {
	
	//Supporting services
	
	@Autowired
	private BulletinService bulletinService;
	
	@Autowired
	private BusinessService businessService;
	
	// -------------------------------------------------------
	
	// A user who is authenticated must be able to
	// list the bulletins that a business have published.
	
	// POSITIVE TEST CASE: A player can list the bulletins of a business.
	
	@Test
	public void testListBulletinsOfBusiness() {
		authenticate("player1");
		Collection<Business> businesses = businessService.findAll();
		int businessId = 0;
		for (Business b: businesses) {
			if (b.getName().equals("Ágora Juegos")) {
				businessId = b.getId();
				break;
			}
		}
		Collection<Bulletin> bulletins = bulletinService
				.findByBusinessId(businessId);
		Assert.isTrue(bulletins.size()==2);
	}
	
	// NEGATIVE TEST CASE: An anonymous user
	// cannot list the bulletins of a business.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousListBulletinsOfBusiness() {
		Collection<Business> businesses = businessService.findAll();
		int businessId = 0;
		for (Business b: businesses) {
			if (b.getName().equals("Juegos Sevilla")) {
				businessId = b.getId();
				break;
			}
		}
		bulletinService.findByBusinessId(businessId);
	}
	
	// NEGATIVE TEST CASE: Business ID must be valid.
	
	@Test(expected=IllegalArgumentException.class)
	public void testListBulletinsOfBusinessWrongId() {
		authenticate("player1");
		bulletinService.findByBusinessId(-5);
	}

	// -------------------------------------------------------
	
	// A user who is authenticated as a business must be
	// able to manage their bulletins. Managing implies
	// creating, listing, modifying, or deleting them.
	
	// POSITIVE TEST CASE: A business can create a bulletin.
	
	@Test
	public void testCreateBulletin() {
		authenticate("business1");
		Bulletin bulletin = bulletinService.create();
		bulletin.setTitle("Boletín de prueba");
		bulletin.setText("Esto es un boletín de prueba, " +
				"sin foto y que no anuncia ningún evento.");
		bulletinService.save(bulletin);
	}
	
	// NEGATIVE TEST CASE: A business cannot delete
	// a bulletin that other business has created.
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteNotMineBulletin() {
		authenticate("business1");
		Collection<Business> businesses = businessService.findAll();
		int businessId = 0;
		for (Business b: businesses) {
			if (b.getName().equals("Ágora Juegos")) {
				businessId = b.getId();
				break;
			}
		}
		Collection<Bulletin> bulletins = bulletinService
				.findByBusinessId(businessId);
		bulletinService.delete(bulletins.iterator().next());
	}
	
	// NEGATIVE TEST CASE: A player cannot create a bulletin.

	@Test(expected=IllegalArgumentException.class)
	public void testPlayerCreateBulletin() {
		authenticate("player1");
		Bulletin bulletin = bulletinService.create();
		bulletin.setTitle("Boletín de prueba");
		bulletin.setText("Esto es un boletín de prueba, " +
				"sin foto y que no anuncia ningún evento.");
		bulletinService.save(bulletin);
	}

	// -------------------------------------------------------

}
