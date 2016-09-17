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

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Stage extends DomainEntity {
	
	//Attributes
	
	private Integer number;
	
	@NotNull
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	//Relationships
	
	private Tournament tournament;
	private Collection<Game> games;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	@NotEmpty
	@Valid
	@OneToMany(mappedBy="stage", cascade=CascadeType.ALL)
	public Collection<Game> getGames() {
		return games;
	}

	public void setGames(Collection<Game> games) {
		this.games = games;
	}

}
