package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PromotionRepository;
import security.LoginService;
import domain.BoardGame;
import domain.Business;
import domain.Inscription;
import domain.Promotion;
import forms.PromotionEditForm;
import forms.PromotionForm;

@Service
@Transactional
public class PromotionService {
	
	//Managed repository
	
	@Autowired
	private PromotionRepository promotionRepository;

	//Supporting services
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private PlayerService playerService;
	
	//Simple CRUD methods
	
	public Promotion findOne(int promotionId) {
		return promotionRepository.findOne(promotionId);
	}
	
	public Collection<Promotion> findAll() {
		Assert.isTrue(adminService.isAdmin() 
				|| playerService.isPlayer() 
				|| businessService.isBusiness());
		return promotionRepository.findAll();
	}
	
	public Promotion create() {
		Assert.isTrue(businessService.isBusiness());
		Promotion promotion = new Promotion();
		promotion.setCreationMoment(new Date(System.currentTimeMillis()-1));
		promotion.setBusiness(businessService.
				findByUserAccount(LoginService.getPrincipal()));
		List<Inscription> inscriptions = new ArrayList<Inscription>();
		promotion.setInscriptions(inscriptions);
		BoardGame boardGame = new BoardGame();
		promotion.setBoardGame(boardGame);
		return promotion;
	}
	
	public void save(Promotion promotion) {
		Assert.notNull(promotion);
		Assert.isTrue(businessService.isBusiness());
		Assert.isTrue(LoginService.getPrincipal()
				.equals(promotion.getBusiness().getUserAccount()));
		promotionRepository.saveAndFlush(promotion);
	}
	
	public void delete(Promotion promotion) {
		Assert.notNull(promotion);
		Assert.isTrue(businessService.isBusiness());
		Assert.isTrue(LoginService.getPrincipal()
				.equals(promotion.getBusiness().getUserAccount()));
		Assert.isTrue(promotion.getInscriptions().isEmpty(), "hasInscriptions");
		promotionRepository.delete(promotion);
	}
	
	//Other business methods
	
	public Collection<Promotion> findByBusinessId(int businessId) {
		Assert.isTrue(adminService.isAdmin() 
				|| playerService.isPlayer() 
				|| businessService.isBusiness());
		Business aux = businessService.findOne(businessId);
		Assert.notNull(aux);
		return promotionRepository.findByBusinessId(businessId);
	}
	
	public Collection<Promotion> findMyPromotions() {
		Assert.isTrue(businessService.isBusiness());
		int businessId = businessService.
				findByUserAccount(LoginService.getPrincipal()).getId();
		return promotionRepository.findByBusinessId(businessId);
	}
	
	public Collection<Object[]> findInscribed() {
		Assert.isTrue(playerService.isPlayer());
		int playerId = playerService.
				findByUserAccount(LoginService.getPrincipal()).getId();
		return promotionRepository.findByPlayerId(playerId);
	}
	
	//Form methods
	
	public Promotion reconstruct(PromotionForm promotionForm) {
		Assert.notNull(promotionForm);
		Assert.isTrue(businessService.isBusiness());
		Promotion res = create();
		res.setTitle(promotionForm.getTitle());
		res.setDescription(promotionForm.getDescription());
		Date now = new Date(System.currentTimeMillis()-1);
		Date startMoment = promotionForm.getStartMoment();
		Assert.isTrue(startMoment.after(now), "startInFuture");
		res.setStartMoment(startMoment);
		Date finishMoment = promotionForm.getFinishMoment();
		Assert.isTrue(finishMoment.after(now), "finishInFuture");
		Assert.isTrue(finishMoment.after(startMoment), "finishAfterStart");
		res.setFinishMoment(finishMoment);
		Date inscriptionDeadline = promotionForm.getInscriptionDeadline();
		Assert.isTrue(inscriptionDeadline.after(now), "deadlineInFuture");
		Assert.isTrue(inscriptionDeadline.before(startMoment), "deadlineBeforeStart");
		res.setInscriptionDeadline(inscriptionDeadline);
		res.setNumberMaxParticipants(promotionForm.getNumberMaxParticipants());
		res.setPrice(promotionForm.getPrice());
		res.setLocation(promotionForm.getLocation());
		res.setBoardGame(promotionForm.getBoardGame());
		String sponsorName = promotionForm.getSponsorName();
		String sponsorLogo = promotionForm.getSponsorLogo();
		Double sponsorPayment = promotionForm.getSponsorPayment();
		boolean all = sponsorName!=null && !sponsorName.isEmpty()
				&& sponsorLogo!=null && !sponsorLogo.isEmpty()
				&& sponsorPayment!=null;
		boolean none = (sponsorName==null || sponsorName.isEmpty())
				&& (sponsorLogo==null || sponsorLogo.isEmpty())
				&& sponsorPayment==null;;
		Assert.isTrue(all || none, "incompleteSponsorship");
		res.setSponsorName(sponsorName);
		res.setSponsorLogo(sponsorLogo);
		res.setSponsorPayment(sponsorPayment);
		return res;
	}
	
	public Promotion reconstruct(PromotionEditForm promotionEditForm) {
		Assert.notNull(promotionEditForm);
		Assert.isTrue(businessService.isBusiness());
		Promotion res = findOne(promotionEditForm.getId());
		Assert.notNull(res);
		res.setTitle(promotionEditForm.getTitle());
		res.setDescription(promotionEditForm.getDescription());
		Date startMoment = promotionEditForm.getStartMoment();
		res.setStartMoment(startMoment);
		Date finishMoment = promotionEditForm.getFinishMoment();
		Assert.isTrue(finishMoment.after(startMoment), "finishAfterStart");
		res.setFinishMoment(finishMoment);
		Date inscriptionDeadline = res.getInscriptionDeadline();
		Assert.isTrue(inscriptionDeadline.before(startMoment), "deadlineBeforeStart");
		res.setPrice(promotionEditForm.getPrice());
		res.setLocation(promotionEditForm.getLocation());
		String sponsorName = promotionEditForm.getSponsorName();
		String sponsorLogo = promotionEditForm.getSponsorLogo();
		Double sponsorPayment = promotionEditForm.getSponsorPayment();
		boolean all = sponsorName!=null && !sponsorName.isEmpty()
				&& sponsorLogo!=null && !sponsorLogo.isEmpty()
				&& sponsorPayment!=null;
		boolean none = (sponsorName==null || sponsorName.isEmpty())
				&& (sponsorLogo==null || sponsorLogo.isEmpty())
				&& sponsorPayment==null;;
		Assert.isTrue(all || none, "incompleteSponsorship");
		res.setSponsorName(sponsorName);
		res.setSponsorLogo(sponsorLogo);
		res.setSponsorPayment(sponsorPayment);
		return res;
	}
	
	public PromotionForm constructForm(Promotion promotion) {
		Assert.notNull(promotion);
		Assert.isTrue(businessService.isBusiness());
		PromotionForm res = new PromotionForm();
		res.setTitle(promotion.getTitle());
		res.setDescription(promotion.getDescription());
		res.setStartMoment(promotion.getStartMoment());
		res.setFinishMoment(promotion.getFinishMoment());
		res.setInscriptionDeadline(promotion.getInscriptionDeadline());
		res.setNumberMaxParticipants(promotion.getNumberMaxParticipants());
		res.setPrice(promotion.getPrice());
		res.setLocation(promotion.getLocation());
		res.setBoardGame(promotion.getBoardGame());
		res.setSponsorName(promotion.getSponsorName());
		res.setSponsorLogo(promotion.getSponsorLogo());
		res.setSponsorPayment(promotion.getSponsorPayment());
		return res;
	}
	
	public PromotionEditForm constructEditForm(Promotion promotion) {
		Assert.notNull(promotion);
		Assert.isTrue(businessService.isBusiness());
		PromotionEditForm res = new PromotionEditForm();
		res.setId(promotion.getId());
		res.setTitle(promotion.getTitle());
		res.setDescription(promotion.getDescription());
		res.setStartMoment(promotion.getStartMoment());
		res.setFinishMoment(promotion.getFinishMoment());
		res.setPrice(promotion.getPrice());
		res.setLocation(promotion.getLocation());
		res.setSponsorName(promotion.getSponsorName());
		res.setSponsorLogo(promotion.getSponsorLogo());
		res.setSponsorPayment(promotion.getSponsorPayment());
		return res;
	}

}
