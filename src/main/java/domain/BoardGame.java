package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class BoardGame extends DomainEntity {
	
	//Attributes
	
	private String title;
	private String description;
	private Integer numberMaxPlayers;
	private String photo;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
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
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	//Relationships
	
	private Collection<Event> events;
	private Collection<Categorization> categorizations;

	@NotNull
	@Valid
	@OneToMany(mappedBy="boardGame")
	public Collection<Event> getEvents() {
		return events;
	}
	
	public void setEvents(Collection<Event> events) {
		this.events = events;
	}
	
	@NotEmpty
	@Valid
	@OneToMany(mappedBy="boardGame", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public Collection<Categorization> getCategorizations() {
		return categorizations;
	}

	public void setCategorizations(Collection<Categorization> categorizations) {
		this.categorizations = categorizations;
	}
	
}
