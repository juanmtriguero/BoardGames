package forms;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

import domain.Category;

@Embeddable
@Access(AccessType.PROPERTY)
public class BoardGameForm {
	
	private String title;
	private String description;
	private Integer numberMaxPlayers;
	private String photo;
	private Collection<Category> categories;

	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@Min(2)
	public Integer getNumberMaxPlayers() {
		return numberMaxPlayers;
	}
	
	public void setNumberMaxPlayers(Integer numberMaxPlayers) {
		this.numberMaxPlayers = numberMaxPlayers;
	}

	@URL
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@NotEmpty
	public Collection<Category> getCategories() {
		return categories;
	}
	
	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}

}
