package forms;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Event;

@Embeddable
@Access(AccessType.PROPERTY)
public class BulletinForm {

	private String title;
	private String text;
	private String photo;
	private Collection<Event> events;
	
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
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	@URL
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
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
