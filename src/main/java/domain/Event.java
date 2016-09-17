package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Event extends DomainEntity {
	
	//Attributes
	
	private String title;
	private String description;
	private Date creationMoment;
	private Date startMoment;
	private Date finishMoment;
	private Date inscriptionDeadline;
	private Integer numberMaxParticipants;
	private Double price;
	private String location;
	
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
	@Past
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getCreationMoment() {
		return creationMoment;
	}
	
	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	@NotNull
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return startMoment;
	}
	
	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getFinishMoment() {
		return finishMoment;
	}
	
	public void setFinishMoment(Date finishMoment) {
		this.finishMoment = finishMoment;
	}

	@NotNull
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getInscriptionDeadline() {
		return inscriptionDeadline;
	}
	
	public void setInscriptionDeadline(Date inscriptionDeadline) {
		this.inscriptionDeadline = inscriptionDeadline;
	}

	@NotNull
	@Min(2)
	public Integer getNumberMaxParticipants() {
		return numberMaxParticipants;
	}
	
	public void setNumberMaxParticipants(Integer numberMaxParticipants) {
		this.numberMaxParticipants = numberMaxParticipants;
	}
	
	@DecimalMin("0.0")
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}

	@NotBlank
	@Pattern(regexp="^ChIJ.{23}$")
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	//Relationships
	
	private Business business;
	private BoardGame boardGame;
	private Collection<Inscription> inscriptions;
	private Chronicle chronicle;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public BoardGame getBoardGame() {
		return boardGame;
	}

	public void setBoardGame(BoardGame boardGame) {
		this.boardGame = boardGame;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy="event")
	public Collection<Inscription> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(Collection<Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}

	@Valid
	@OneToOne(optional=true, cascade=CascadeType.ALL)
	public Chronicle getChronicle() {
		return chronicle;
	}

	public void setChronicle(Chronicle chronicle) {
		this.chronicle = chronicle;
	}

}
