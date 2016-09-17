package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Business extends Actor {
	
	//Attributes
	
	private String cif;
	private String street;
	private Integer zip;
	private String city;
	private String web;
	private String phone;
	private Double rating;

	@NotBlank
	@Size(min=9, max=9)
	public String getCif() {
		return cif;
	}
	
	public void setCif(String cif) {
		this.cif = cif;
	}

	@NotBlank
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	@NotNull
	@Range(min=0, max=99999)
	public Integer getZip() {
		return zip;
	}
	
	public void setZip(Integer zip) {
		this.zip = zip;
	}
	
	@NotBlank
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	@URL
	@NotBlank
	public String getWeb() {
		return web;
	}
	
	public void setWeb(String web) {
		this.web = web;
	}

	@NotBlank
	@Pattern(regexp="^[9|6|7][0-9]{8}$")
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@NotNull
	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	//Relationships
	
	private Collection<Event> events;
	private Collection<Bulletin> bulletins;
	private Collection<UserVote> userVotes;

	@NotNull
	@Valid
	@OneToMany(mappedBy="business")
	public Collection<Event> getEvents() {
		return events;
	}
	
	public void setEvents(Collection<Event> events) {
		this.events = events;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy="business")
	public Collection<Bulletin> getBulletins() {
		return bulletins;
	}

	public void setBulletins(Collection<Bulletin> bulletins) {
		this.bulletins = bulletins;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy="business")
	public Collection<UserVote> getUserVotes() {
		return userVotes;
	}

	public void setUserVotes(Collection<UserVote> userVotes) {
		this.userVotes = userVotes;
	}

}
