package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {
	
	//Attributes
	
	private String name;
	private String description;
	private String photo;
	
	@NotBlank
	@Column(unique=true)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@URL
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	//Relationships

	private Collection<Categorization> categorizations;
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="category")
	public Collection<Categorization> getCategorizations() {
		return categorizations;
	}

	public void setCategorizations(Collection<Categorization> categorizations) {
		this.categorizations = categorizations;
	}

}
