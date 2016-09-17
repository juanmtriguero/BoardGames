package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Player extends Actor {

	//Relationships
	
	private Collection<Inscription> inscriptions;
	private Collection<Tournament> tournaments;
	private Collection<Following> followers;
	private Collection<Following> followeds;
	private Collection<UserVote> userVotes;

	@NotNull
	@Valid
	@OneToMany(mappedBy="player")
	public Collection<Inscription> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(Collection<Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy="winner")
	public Collection<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(Collection<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy="followed")
	public Collection<Following> getFollowers() {
		return followers;
	}

	public void setFollowers(Collection<Following> followers) {
		this.followers = followers;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy="follower")
	public Collection<Following> getFolloweds() {
		return followeds;
	}

	public void setFolloweds(Collection<Following> followeds) {
		this.followeds = followeds;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy="player")
	public Collection<UserVote> getUserVotes() {
		return userVotes;
	}

	public void setUserVotes(Collection<UserVote> userVotes) {
		this.userVotes = userVotes;
	}

}
