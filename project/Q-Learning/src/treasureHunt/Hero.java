package treasureHunt;

import utils.Position;

public class Hero extends Character{

	public Hero(Position position) {
		super(position);
	}
	public Hero(Hero hero) {
		super(hero);
	}
	public String toString() {
		return "Hero["+ this.getPosition().toString() +"]";
	}
}
