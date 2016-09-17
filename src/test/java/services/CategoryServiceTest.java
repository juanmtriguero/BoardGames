package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.BoardGame;
import domain.Categorization;
import domain.Category;

import utilities.AbstractTest;
import utilities.MinimumDatabase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class CategoryServiceTest extends AbstractTest {
	
	//Supporting services

	@Autowired
	private CategoryService categoryService;
	
	// -------------------------------------------------------
	
	// A user must be able to list the catalogue of
	// board games grouped by their categories.
	
	// POSITIVE TEST CASE: An anonymous user can list the catalogue.
	
	@Test
	public void testCatalogue() {
		Collection<Category> categories = categoryService.findAll();
		Assert.notEmpty(categories);
		Assert.isTrue(categories.size()==4);
		for (Category c: categories) {
			String name = c.getName();
			Collection<BoardGame> boardGames = new ArrayList<BoardGame>();
			for (Categorization aux: c.getCategorizations()) {
				BoardGame bg = aux.getBoardGame();
				Assert.notNull(bg);
				boardGames.add(bg);
			}
			boolean opt1 = name.equals("Estrategia") && boardGames.size()==2;
			boolean opt2 = name.equals("LCG") && boardGames.size()==2;
			boolean opt3 = name.equals("Party") && boardGames.size()==0;
			boolean opt4 = name.equals("Gestión de recursos") && boardGames.size()==1;
			Assert.isTrue(opt1 || opt2 || opt3 || opt4);
		}
	}
	
	// -------------------------------------------------------
	
	// IMPORTANT: This is the use case selected to implement
	// at least 10 test cases that provide a good coverage of
	// the statements and the parameter boundaries in the code.
	
	// A user who is authenticated as an administrator must be able to
	// manage the categories. Managing implies creating, listing, modifying,
	// or deleting them. A category only can be deleted if there isn't
	// any board game which belongs to it.
	
	// POSITIVE TEST CASE: An administrator can list all categories.
	
	@Test
	public void testListCategories() {
		authenticate("admin1");
		Collection<Category> all = categoryService.findAll();
		Assert.isTrue(all.size()==4);
	}
	
	// POSITIVE TEST CASE: An administrator can find a category by its ID.
	
	@Test
	public void testFindCategory() {
		authenticate("admin1");
		Collection<Category> all = categoryService.findAll();
		Category category = null;
		for (Category c: all) {
			if (c.getName().equals("Estrategia")) {
				category = c;
				break;
			}
		}
		Category foundCategory = categoryService.findOne(category.getId());
		Assert.isTrue(category.equals(foundCategory));
	}
	
	// NEGATIVE TEST CASE: An anonymous user cannot find a category.
	
	@Test(expected=IllegalArgumentException.class)
	public void testAnonymousFindCategory() {
		Collection<Category> all = categoryService.findAll();
		Category category = null;
		for (Category c: all) {
			if (c.getName().equals("Estrategia")) {
				category = c;
				break;
			}
		}
		categoryService.findOne(category.getId());
	}
	
	// NEGATIVE TEST CASE: Category ID must be valid.
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindCategoryWrongId() {
		authenticate("admin1");
		categoryService.findOne(-5);
	}
	
	// POSITIVE TEST CASE: An administrator can create a category.
	
	@Test
	public void testCreateCategory() {
		authenticate("admin1");
		Category category = categoryService.create();
		category.setName("Category");
		category.setDescription("This is a category");
		categoryService.save(category);
	}
	
	// NEGATIVE TEST CASE: A business cannot create a category.
	
	@Test(expected=IllegalArgumentException.class)
	public void testBusinessCreateCategory() {
		authenticate("business1");
		Category category = categoryService.create();
		category.setName("Category");
		category.setDescription("This is a category");
		categoryService.save(category);
	}
	
	// POSITIVE TEST CASE: An administrator can edit a category.
	
	@Test
	public void testEditCategory() {
		authenticate("admin1");
		Collection<Category> all = categoryService.findAll();
		Category category = null;
		for (Category c: all) {
			if (c.getName().equals("Estrategia")) {
				category = c;
				break;
			}
		}
		category.setName("Estrategia y wargames");
		categoryService.save(category);
	}
	
	// NEGATIVE TEST CASE: A player cannot edit a category.
	
	@Test(expected=IllegalArgumentException.class)
	public void testPlayerEditCategory() {
		authenticate("player1");
		Collection<Category> all = categoryService.findAll();
		Category category = null;
		for (Category c: all) {
			if (c.getName().equals("Estrategia")) {
				category = c;
				break;
			}
		}
		category.setName("Estrategia y wargames");
		categoryService.save(category);
	}
	
	// NEGATIVE TEST CASE: An administrator cannot save a null category.
	
	@Test(expected=IllegalArgumentException.class)
	public void testSaveNullCategory() {
		authenticate("admin1");
		categoryService.save(null);
	}
	
	// POSITIVE TEST CASE: An administrator can delete a unused category.
	
	@Test
	public void testDeleteCategory() {
		authenticate("admin1");
		Collection<Category> all = categoryService.findAll();
		Category category = null;
		for (Category c: all) {
			if (c.getName().equals("Party")) {
				category = c;
				break;
			}
		}
		categoryService.delete(category);
	}
	
	// NEGATIVE TEST CASE: A player cannot delete a category.
	
	@Test(expected=IllegalArgumentException.class)
	public void testPlayerDeleteCategory() {
		authenticate("player1");
		Collection<Category> all = categoryService.findAll();
		Category category = null;
		for (Category c: all) {
			if (c.getName().equals("Party")) {
				category = c;
				break;
			}
		}
		categoryService.delete(category);
	}
	
	// NEGATIVE TEST CASE: An administrator cannot delete a null category.
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteNullCategory() {
		authenticate("admin1");
		categoryService.delete(null);
	}
	
	// NEGATIVE TEST CASE: An administrator cannot delete a used category.
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteUsedCategory() {
		authenticate("admin1");
		Collection<Category> all = categoryService.findAll();
		Category category = null;
		for (Category c: all) {
			if (c.getName().equals("Estrategia")) {
				category = c;
				break;
			}
		}
		categoryService.delete(category);
	}
	
	// NEGATIVE TEST CASE: An administrator cannot delete the last category.
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteLastCategory() {
		MinimumDatabase.main(null);
		authenticate("admin1");
		Collection<Category> all = categoryService.findAll();
		Category category = null;
		for (Category c: all) {
			if (c.getName().equals("Default category")) {
				category = c;
				break;
			}
		}
		categoryService.delete(category);
	}
	
	// -------------------------------------------------------

}
