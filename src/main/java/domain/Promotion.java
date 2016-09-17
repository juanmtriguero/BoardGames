package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Promotion extends Event {

	//Attributes
	
	private String sponsorName;
	private String sponsorLogo;
	private Double sponsorPayment;
	
	public String getSponsorName() {
		return sponsorName;
	}
	
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	
	@URL
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
