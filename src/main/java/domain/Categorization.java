package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Categorization extends DomainEntity {
	
	//Relationships
	
	private BoardGame boardGame;
	private Category category;

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
	@ManyToOne(optional=false)
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

}
