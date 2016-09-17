package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.BoardGame;

@Embeddable
@Access(AccessType.PROPERTY)
public class PromotionForm {
	
	private String title;
	private String description;
	private Date startMoment;
	private Date finishMoment;
	private Date inscriptionDeadline;
	private Integer numberMaxParticipants;
	private Double price;
	private String location;
	private BoardGame boardGame;
	private String sponsorName;
	private String sponsorLogo;
	private Double sponsorPayment;

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
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
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

	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getSponsorName() {
		return sponsorName;
	}
	
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	
	@URL
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getSponsorLogo() {
		return sponsorLogo;
	}
	
	public void setSponsorLogo(String sponsorLogo) {
		this.sponsorLogo = sponsorLogo;
	}
	
	@Min(0)
	public Double getSponsorPayment() {
		return sponsorPayment;
	}
	
	public void setSponsorPayment(Double sponsorPayment) {
		this.sponsorPayment = sponsorPayment;
	}

}
