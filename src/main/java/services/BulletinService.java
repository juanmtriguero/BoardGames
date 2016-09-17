package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BulletinRepository;
import security.LoginService;
import domain.Bulletin;
import domain.Business;
import domain.Event;
import forms.BulletinEditForm;
import forms.BulletinForm;

@Service
@Transactional
public class BulletinService {
	
	//Managed repository
	
	@Autowired
	private BulletinRepository bulletinRepository;

	//Supporting services
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private BusinessService businessService;
	
	//Simple CRUD methods
	
	public Bulletin findOne(int bulletinId) {
		Assert.isTrue(adminService.isAdmin() 
				|| playerService.isPlayer() 
				|| businessService.isBusiness());
		Bulletin bulletin = bulletinRepository.findOne(bulletinId);
		Assert.notNull(bulletin);
		return bulletin;
	}
	
	public Collection<Bulletin> findAll() {
		Assert.isTrue(adminService.isAdmin() 
				|| playerService.isPlayer() 
				|| businessService.isBusiness());
		return bulletinRepository.findAll();
	}
	
	public Bulletin create() {
		Assert.isTrue(businessService.isBusiness());
		Bulletin bulletin = new Bulletin();
		bulletin.setReleaseDate(new Date(System.currentTimeMillis()-1));
		bulletin.setBusiness(businessService.
				findByUserAccount(LoginService.getPrincipal()));
		Collection<Event> events = new ArrayList<Event>();
		bulletin.setEvents(events);
		return bulletin;
	}
	
	public void save(Bulletin bulletin) {
		Assert.notNull(bulletin);
		Assert.isTrue(businessService.isBusiness());
		Assert.isTrue(LoginService.getPrincipal()
				.equals(bulletin.getBusiness().getUserAccount()));
		bulletinRepository.saveAndFlush(bulletin);
	}
	
	public void delete(Bulletin bulletin) {
		Assert.notNull(bulletin);
		Assert.isTrue(businessService.isBusiness());
		Assert.isTrue(LoginService.getPrincipal()
				.equals(bulletin.getBusiness().getUserAccount()));
		bulletinRepository.delete(bulletin);
	}
	
	//Form methods
	
	public Bulletin reconstruct(BulletinForm bulletinForm) {
		Assert.notNull(bulletinForm);
		Assert.isTrue(businessService.isBusiness());
		Bulletin res = create();
		res.setTitle(bulletinForm.getTitle());
		res.setText(bulletinForm.getText());
		res.setPhoto(bulletinForm.getPhoto());
		res.setEvents(bulletinForm.getEvents());
		return res;
	}
	
	public Bulletin reconstruct(BulletinEditForm bulletinEditForm) {
		Assert.notNull(bulletinEditForm);
		Assert.isTrue(businessService.isBusiness());
		Bulletin res = findOne(bulletinEditForm.getId());
		Assert.notNull(res);
		res.setTitle(bulletinEditForm.getTitle());
		res.setText(bulletinEditForm.getText());
		res.setPhoto(bulletinEditForm.getPhoto());
		res.setEvents(bulletinEditForm.getEvents());
		return res;
	}
	
	public BulletinForm constructForm(Bulletin bulletin) {
		Assert.notNull(bulletin);
		Assert.isTrue(businessService.isBusiness());
		BulletinForm res = new BulletinForm();
		res.setTitle(bulletin.getTitle());
		res.setText(bulletin.getText());
		res.setPhoto(bulletin.getPhoto());
		res.setEvents(bulletin.getEvents());
		return res;
	}
	
	public BulletinEditForm constructEditForm(Bulletin bulletin) {
		Assert.notNull(bulletin);
		Assert.isTrue(businessService.isBusiness());
		BulletinEditForm res = new BulletinEditForm();
		res.setId(bulletin.getId());
		res.setTitle(bulletin.getTitle());
		res.setText(bulletin.getText());
		res.setPhoto(bulletin.getPhoto());
		res.setEvents(bulletin.getEvents());
		return res;
	}
	
	//Other business methods
	
	public Collection<Bulletin> findByBusinessId(int businessId) {
		Assert.isTrue(adminService.isAdmin() 
				|| playerService.isPlayer() 
				|| businessService.isBusiness());
		Business aux = businessService.findOne(businessId);
		Assert.notNull(aux);
		return bulletinRepository.findByBusinessId(businessId);
	}
	
	public Collection<Bulletin> findMyBulletins() {
		Assert.isTrue(businessService.isBusiness());
		int businessId = businessService.
				findByUserAccount(LoginService.getPrincipal()).getId();
		return bulletinRepository.findByBusinessId(businessId);
	}
	
	public List<Bulletin> lastPublished(int number) {
		Assert.isTrue(playerService.isPlayer());
		List<Bulletin> all = new ArrayList<Bulletin>();
		all.addAll(findAll());
		Collections.sort(all, new Comparator<Bulletin>() {
			public int compare(Bulletin b1, Bulletin b2) {
				return b2.getReleaseDate().compareTo(b1.getReleaseDate());
			}
		});
		List<Bulletin> last = new ArrayList<Bulletin>();
		if (all.size()<=number) {
			last.addAll(all);
		} else {
			for (int i=0; i<number; i++) {
				last.add(i, all.get(i));
			}
		}
		return last;
	}

}
