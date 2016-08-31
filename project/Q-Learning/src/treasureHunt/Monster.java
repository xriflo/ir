package treasureHunt;

import utils.Position;

public class Monster extends Character{
	public Monster(Position position) {
		super(position);
	}
	public Monster(Monster monster) {
		super(monster);
	}
	@Override
	public String toString() {
		return "Monster["+ this.getPosition().toString() +"]";
	}
	
}
