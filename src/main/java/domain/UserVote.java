package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class UserVote extends DomainEntity {
	
	//Attributes
	
	private Integer score;

	@NotNull
	@Range(min=0, max=5)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	//Relationships
	
	private Player player;
	private Business business;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

}
