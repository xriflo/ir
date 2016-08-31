package treasureHunt;

import utils.Position;

public class Character {
	private Position position;

	public Character(Position position) {
		this.position = position;
	}
	
	public Character(Character character) {
		this.position = new Position();
		this.position.setCol(character.getPosition().getCol());
		this.position.setRow(character.getPosition().getRow());
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
