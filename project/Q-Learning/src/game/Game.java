package game;

public abstract class Game {
	public abstract void restart();
	public abstract void applyAction(String action);
	public abstract boolean isOver();
	public abstract String[] getActions();
	public abstract String getState();
	public abstract Float getReward();
	public abstract void generateStats();
}
