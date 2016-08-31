package game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import hanoi.Hanoi;
import qlearning.QLearning;
import treasureHunt.TreasureHunt;
import utils.Pair;
import utils.Position;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		/* Hanoi Towers*/
		Hanoi hanoi = new Hanoi(3,3);
		QLearning ql_hanoi = new QLearning(hanoi);
		
		/*Treasure Hunt*/
		TreasureHunt th = new TreasureHunt("resources/mapB");
		QLearning ql_th = new QLearning(th);
		
		ql_hanoi.applyAlgorithm();
		//ql_th.applyAlgorithm();
	}

}
