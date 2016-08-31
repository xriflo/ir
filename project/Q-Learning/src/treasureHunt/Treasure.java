package treasureHunt;

import utils.Position;

public class Treasure extends Character{

	public Treasure(Position position) {
		super(position);
	}
	public Treasure(Treasure treasure) {
		super(treasure);
	}
	public String toString() {
		return "Treasure["+ this.getPosition().toString() +"]";
	}
}
