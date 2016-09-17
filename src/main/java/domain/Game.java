package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Game extends DomainEntity {
	
	//Relationships
	
	private Stage stage;
	private Collection<Player> players;
	private Player gameWinner;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Collection<Player> players) {
		this.players = players;
	}

	@Valid
	@ManyToOne(optional=true)
	public Player getGameWinner() {
		return gameWinner;
	}
	
	public void setGameWinner(Player gameWinner) {
		this.gameWinner = gameWinner;
	}

}
