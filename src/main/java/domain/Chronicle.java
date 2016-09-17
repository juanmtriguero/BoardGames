package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Chronicle extends DomainEntity {

	//Attributes
	
	private String title;
	private String text;
	private Collection<String> photos;

	@NotBlank
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@NotNull
	@ElementCollection
	public Collection<String> getPhotos() {
		return photos;
	}

	public void setPhotos(Collection<String> photos) {
		this.photos = photos;
	}
	
	//Relationships

	private Event event;

	@OneToOne(optional=false, mappedBy="chronicle")
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
