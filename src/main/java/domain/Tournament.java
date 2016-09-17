package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Tournament extends Event {
	
	//Attributes
	
	private String award;
	
	@NotBlank
	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	//Relationships
	
	private Collection<Stage> stages;
	private Player winner;
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="tournament", cascade=CascadeType.ALL)
	public Collection<Stage> getStages() {
		return stages;
	}

	public void setStages(Collection<Stage> stages) {
		this.stages = stages;
	}

	@Valid
	@ManyToOne(optional=true)
	public Player getWinner() {
		return winner;
	}
	
	public void setWinner(Player winner) {
		this.winner = winner;
	}

}
