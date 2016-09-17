package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Business;
import domain.UserVote;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class UserVoteServiceTest extends AbstractTest {
	
	//Supporting services

	@Autowired
	private UserVoteService userVoteService;
	
	@Autowired
	private BusinessService businessService;
	
	// -------------------------------------------------------
	
	// A user who is authenticated as a player must be able to vote a business.
	
	// POSITIVE TEST CASE: A player can vote a business.
	
	@Test
	public void testVoteBusiness() {
		authenticate("player1");
		Collection<Business> businesses = businessService.findAll();
		int businessId = 0;
		for (Business b: businesses) {
			if (b.getName().equals("Mesa 291")) {
				businessId = b.getId();
				break;
			}
		}
		UserVote userVote = userVoteService.create(businessId);
		userVote.setScore(3);
		userVoteService.save(userVote);
	}
	
	// NEGATIVE TEST CASE: A player cannot vote
	// a business if he/she already has voted it.
	
	@Test(expected=IllegalArgumentException.class)
	public void testVoteBusinessAgain() {
		authenticate("player3");
		Collection<Business> businesses = businessService.findAll();
		int businessId = 0;
		for (Business b: businesses) {
			if (b.getName().equals("Mesa 291")) {
				businessId = b.getId();
				break;
			}
		}
		UserVote userVote = userVoteService.create(businessId);
		userVote.setScore(3);
		userVoteService.save(userVote);
	}
	
	// NEGATIVE TEST CASE: A business cannot vote a business.
	
	@Test(expected=IllegalArgumentException.class)
	public void testBusinessVoteBusiness() {
		authenticate("business1");
		Collection<Business> businesses = businessService.findAll();
		int businessId = 0;
		for (Business b: businesses) {
			if (b.getName().equals("Mesa 291")) {
				businessId = b.getId();
				break;
			}
		}
		UserVote userVote = userVoteService.create(businessId);
		userVote.setScore(3);
		userVoteService.save(userVote);
	}
	
	// -------------------------------------------------------

}
