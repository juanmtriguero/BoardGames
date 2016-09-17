package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.BoardGame;
import domain.Business;
import domain.Promotion;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class PromotionServiceTest extends AbstractTest {
	
	//Supporting services

	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private BoardGameService boardGameService;
	
	// -------------------------------------------------------
	
	// A user who is authenticated must be able to
	// list the promotions that a business have created.
	
	// POSITIVE TEST CASE: A player can list the promotions of a business.
	
	@Test
	public void testListPromotionsOfBusiness() {
		authenticate("player1");
		Collection<Business> businesses = businessService.findAll();
		int businessId = 0;
		for (Business b: businesses) {
			if (b.getName().equals("Juegos Sevilla")) {
				businessId = b.getId();
				break;
			}
		}
		Collection<Promotion> promotions = promotionService
				.findByBusinessId(businessId);
		Assert.isTrue(promotions.size()==1);
	}
	
	// NEGATIVE TEST CASE: An anonymous user
	// cannot list the promotions of a business.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousListPromotionsOfBusiness() {
		Collection<Business> businesses = businessService.findAll();
		int businessId = 0;
		for (Business b: businesses) {
			if (b.getName().equals("Juegos Sevilla")) {
				businessId = b.getId();
				break;
			}
		}
		promotionService.findByBusinessId(businessId);
	}
	
	// NEGATIVE TEST CASE: Business ID must be valid.
	
	@Test(expected=IllegalArgumentException.class)
	public void testListPromotionsOfBusinessWrongId() {
		authenticate("player1");
		promotionService.findByBusinessId(-5);
	}
	
	// -------------------------------------------------------
	
	// A user who is authenticated must be able to list
	// the promotions that are available in the system.
	
	// POSITIVE TEST CASE: A player can list all promotions.
	
	@Test
	public void testListPromotions() {
		authenticate("player1");
		Collection<Promotion> all = promotionService.findAll();
		Assert.isTrue(all.size()==2);
	}
	
	// NEGATIVE TEST CASE: An anonymous user cannot list all promotions.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousListPromotions() {
		promotionService.findAll();
	}

	// -------------------------------------------------------
	
	// A user who is authenticated as a player must be
	// able to see the promotions that he or she has joined.
	
	// POSITIVE TEST CASE: A player can list his/her joined promotions.
	
	@Test
	public void testListPromotionsInscribed() {
		authenticate("player1");
		Collection<Object[]> inscribed = promotionService.findInscribed();
		Assert.isTrue(inscribed.size()==1);
	}
	
	// NEGATIVE TEST CASE: An anonymous user cannot list his/her joined promotions.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousListPromotionsInscribed() {
		promotionService.findInscribed();
	}
	
	// NEGATIVE TEST CASE: A business cannot list his/her joined promotions.
	
	@Test(expected=IllegalArgumentException.class)
	public void testBusinessListPromotionsInscribed() {
		authenticate("business1");
		promotionService.findInscribed();
	}

	// -------------------------------------------------------
	
	// A user who is authenticated as a business must be
	// able to manage their promotions. Managing implies
	// creating, listing, modifying, or deleting them.
	
	// POSITIVE TEST CASE: A business can create a promotion.
	
	@SuppressWarnings("deprecation")
	@Test
	public void testCreatePromotion() {
		authenticate("business1");
		Promotion promotion = promotionService.create();
		BoardGame boardGame = null;
		for (BoardGame bg: boardGameService.findAll()) {
			if (bg.getTitle().equals("Risk")) {
				boardGame = bg;
				break;
			}
		}
		promotion.setBoardGame(boardGame);
		promotion.setTitle("Nuevo Risk ESDLA");
		promotion.setDescription("Prueba el nuevo Risk de El Señor de los Anillos.");
		promotion.setStartMoment(new Date("25/11/2016 12:00"));
		promotion.setFinishMoment(new Date("25/11/2016 21:00"));
		promotion.setInscriptionDeadline(new Date("20/11/2016 12:00"));
		promotion.setLocation("ChIJc_iiI6RuEg0RbGU-Woe5QCY");
		promotion.setNumberMaxParticipants(24);
		promotion.setPrice(15.0);
		promotionService.save(promotion);
	}
	
	// NEGATIVE TEST CASE: A business cannot delete a
	// promotion if there is players that have joined to it.
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteJoinedPromotion() {
		authenticate("business1");
		Collection<Promotion> all = promotionService.findMyPromotions();
		Promotion promotion = null;
		for (Promotion p: all) {
			if (p.getTitle().equals("Presentación Risk")) {
				promotion = p;
				break;
			}
		}
		promotionService.delete(promotion);
	}
	
	// NEGATIVE TEST CASE: A player cannot create a promotion.

	@SuppressWarnings("deprecation")
	@Test(expected=IllegalArgumentException.class)
	public void testPlayerCreatePromotion() {
		authenticate("player1");
		Promotion promotion = promotionService.create();
		BoardGame boardGame = null;
		for (BoardGame bg: boardGameService.findAll()) {
			if (bg.getTitle().equals("Risk")) {
				boardGame = bg;
				break;
			}
		}
		promotion.setBoardGame(boardGame);
		promotion.setTitle("Nuevo Risk ESDLA");
		promotion.setDescription("Prueba el nuevo Risk de El Señor de los Anillos.");
		promotion.setStartMoment(new Date("25/11/2016 12:00"));
		promotion.setFinishMoment(new Date("25/11/2016 21:00"));
		promotion.setInscriptionDeadline(new Date("20/11/2016 12:00"));
		promotion.setLocation("ChIJc_iiI6RuEg0RbGU-Woe5QCY");
		promotion.setNumberMaxParticipants(24);
		promotion.setPrice(15.0);
		promotionService.save(promotion);
	}

	// -------------------------------------------------------

}
