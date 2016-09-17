package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Bulletin extends DomainEntity {

	//Attributes
	
	private String title;
	private Date releaseDate;
	private String text;
	private String photo;

	@NotBlank
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@NotBlank
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	@URL
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	//Relationships
	
	private Business business;
	private Collection<Event> events;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Business getBusiness() {
		return business;
	}
	
	public void setBusiness(Business business) {
		this.business = business;
	}

	@Valid
	@ManyToMany
	public Collection<Event> getEvents() {
		return events;
	}
	
	public void setEvents(Collection<Event> events) {
		this.events = events;
	}

}
