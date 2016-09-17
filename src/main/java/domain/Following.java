package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Following extends DomainEntity {
	
	//Relationships
	
	private Player follower;
	private Player followed;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Player getFollower() {
		return follower;
	}

	public void setFollower(Player follower) {
		this.follower = follower;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Player getFollowed() {
		return followed;
	}

	public void setFollowed(Player followed) {
		this.followed = followed;
	}

}
