package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Categorization;
import domain.Category;

@Service
@Transactional
public class CategoryService {
	
	//Managed repository
	
	@Autowired
	private CategoryRepository categoryRepository;

	//Supporting services
	
	@Autowired
	private AdminService adminService;
	
	//Simple CRUD methods
	
	public Collection<Category> findAll() {
		return categoryRepository.findAll();
	}
	
	public Category findOne(int categoryId) {
		Assert.isTrue(adminService.isAdmin());
		Category category = categoryRepository.findOne(categoryId);
		Assert.notNull(category);
		return category;
	}
	
	public Category create() {
		Assert.isTrue(adminService.isAdmin());
		Category category = new Category();
		Collection<Categorization> categorizations = new ArrayList<Categorization>();
		category.setCategorizations(categorizations);
		return category;
	}
	
	public void save(Category category) {
		Assert.notNull(category);
		Assert.isTrue(adminService.isAdmin());
		categoryRepository.saveAndFlush(category);
	}
	
	public void delete(Category category) {
		Assert.notNull(category);
		Assert.isTrue(adminService.isAdmin());
		Assert.isTrue(category.getCategorizations().isEmpty(), "hasBoardGames");
		Assert.isTrue(findAll().size()>1, "isLastCategory");
		categoryRepository.delete(category);
	}

}
