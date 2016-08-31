package treasureHunt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import game.Game;
import utils.Position;

public class TreasureHunt extends Game{
	Integer hero_wins=0, monster_wins=0, no_moves=0, old_no_moves, no_episodes=0;
	Hero hero, init_hero;
	Monster monster, init_monster;
	Treasure treasure, init_treasure;
	Integer rows, columns;
	char[][] map;
	public TreasureHunt(String map) throws FileNotFoundException, IOException {
		readMap(map);
	}
	public void readMap(String filename) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			Integer row=0;
			String line = br.readLine();
			rows = Integer.parseInt(line.split(" ")[0]);
			columns = Integer.parseInt(line.split(" ")[1]);
		    map = new char[rows][columns];
		    while ((line = br.readLine()) != null) {
		    	for(Integer col=0; col<line.length(); col++) {
		    		char ch = line.charAt(col);
		    		switch(ch) {
		    			case 'H':
		    				hero = new Hero(new Position(row, col));
		    				init_hero = new Hero(new Position(row, col));
		    				map[row][col] = ' ';
		    				break;
		    			case 'M':
		    				monster = new Monster(new Position(row, col));
		    				init_monster = new Monster(new Position(row, col));
		    				map[row][col] = ' ';
		    				break;
		    			case 'T':
		    				treasure  = new Treasure(new Position(row, col));
		    				init_treasure  = new Treasure(new Position(row, col));
		    				map[row][col] = ' ';
		    				break;
	    				default:
	    					map[row][col] = ch;
			    			break;
		    		}
		    	}
		    	row++;
		    }
		}
	}
	@Override
	public void restart() {
		no_episodes++;
		hero = new Hero(init_hero);
		monster = new Monster(init_monster);
		treasure = new Treasure(init_treasure);
	}
	public void applyValidAction(String action, Character character) {
		Integer r, c;
		r = character.getPosition().getRow();
		c = character.getPosition().getCol();
		switch(action) {
			case "UP":
				character.setPosition(new Position(r-1, c));
				break;
			case "DOWN":
				character.setPosition(new Position(r+1, c));
				break;
			case "LEFT":
				character.setPosition(new Position(r, c-1));
				break;
			case "RIGHT":
				character.setPosition(new Position(r, c+1));
				break;
			default:
				System.out.println("ERROR");
		}
	}
	@Override
	public void applyAction(String action) {
		no_moves++;
		Random rand = new Random();
		applyValidAction(action, hero);
		//select action for Monster
		String[] monster_move = getValidMoves(monster);
		applyValidAction(monster_move[rand.nextInt(monster_move.length)], monster);
	}
	@Override
	public boolean isOver() {
		if(hero.getPosition().equals(treasure.getPosition()) ||
				hero.getPosition().equals(monster.getPosition()))
			return true;
		else
			return false;
	}
	public String[] getValidMoves(Character character) {
		ArrayList<String> validMoves = new ArrayList<String>();
		String[] validMoves_aux;
		Integer r, c;
		r = character.getPosition().getRow();
		c = character.getPosition().getCol();
		//UP
		if(r-1>=0 && map[r-1][c]!='#')
			validMoves.add("UP");
		//DOWN
		if(r+1<rows && map[r+1][c]!='#')
			validMoves.add("DOWN");
		//LEFT
		if(c-1>=0 && map[r][c-1]!='#')
			validMoves.add("LEFT");
		//RIGHT
		if(c+1<rows && map[r][c+1]!='#')
			validMoves.add("RIGHT");
		validMoves_aux = new String[validMoves.size()];
		validMoves_aux = validMoves.toArray(validMoves_aux);
		return validMoves_aux;
	}
	@Override
	public String[] getActions() {
		return getValidMoves(hero);
	}
	@Override
	public String getState() {
		String str = "";
		str += hero.toString() + "\n";
		str += monster.toString() + "\n";
		return str;
	}
	public String printBoard() {
		String str = "";
		char[][] aux_map = new char[rows][columns];
		for(int i=0; i<rows; i++) {
			aux_map[i] = map[i].clone();
		}
		aux_map[hero.getPosition().getRow()][hero.getPosition().getCol()] = 'H';
		aux_map[monster.getPosition().getRow()][monster.getPosition().getCol()] = 'M';
		aux_map[treasure.getPosition().getRow()][treasure.getPosition().getCol()] = 'T';
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				str += aux_map[i][j];
			}
			str+="\n";
		}
		return str;
	}
	@Override
	public Float getReward() {
		if(isOver()) {
			old_no_moves = no_moves;
			no_moves = 0;
		}
		
		if(hero.getPosition().equals(monster.getPosition()) &&
				hero.getPosition().equals(treasure.getPosition())) {
			hero_wins++;
			return 50.0f;
		}
		else if(hero.getPosition().equals(monster.getPosition())) {
			monster_wins++;
			return -50.f;
		}
			
		else if(hero.getPosition().equals(treasure.getPosition())) {
			hero_wins++;
			return 50.0f;
		}
		else
			return 0.0f;
	}
	@Override
	public void generateStats() {
		System.out.println("Episode " + no_episodes + " ended with "+
				"(Hero)"+hero_wins+"-"+monster_wins+"(Monster) with "+old_no_moves + " moves");
		hero_wins = 0;
		monster_wins = 0;
		
	}
}
