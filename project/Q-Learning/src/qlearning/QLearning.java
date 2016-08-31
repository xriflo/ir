package qlearning;

import java.util.HashMap;
import java.util.Random;

import game.Game;
import treasureHunt.TreasureHunt;
import utils.Pair;

public class QLearning {
	Game game;
	Float eps, gamma, alpha;
	HashMap<Pair<String, String>, Float> Q;
	public QLearning(Game game) {
		this.Q = new HashMap<Pair<String, String>, Float>();
		this.game = game;
		this.eps = 1.0f;
		this.gamma = 0.0f;
		this.alpha = 0.1f;
	}
	public void checkQ() {
		String state = game.getState();
		for(String action:game.getActions()) {
			Pair<String,String> state_act = new Pair<String, String>(state, action);
			
			if(Q.get(state_act)==null) {
				Q.put(state_act, 0.0f);
			}
		}
	}
	public Pair<String, Float> chooseAction() {
		Random rand = new Random();
		Float best_score = Float.NEGATIVE_INFINITY;
		String state = game.getState();
		String action = "";
		//for current state, initialize hash with 0 for actions that are not in hash
		checkQ();
		//pick random action
		if (rand.nextFloat()<eps) {
			action = game.getActions()[rand.nextInt(game.getActions().length)];
			best_score = Q.get(new Pair<String, String>(state, action));
		}
		//pick best action
		else {
			for(String act:game.getActions()) {
				Pair<String,String> state_act = new Pair<String, String>(state, act);
				Float score = Q.get(state_act);
				if(score>best_score) {
					action = act;
					best_score = score;
				}
			}
		}
		return new Pair<String, Float>(action, best_score);
	}
	public void applyAlgorithm() {
		int no_moves=0, no_episodes=0;
		Pair<String, String> old_state_action;
		String action, old_state;
		Float reward, best_score;
		while(true) {
			if(game.isOver()) {
				if(no_episodes%100==0) {
					System.out.println("Epsilon used: " + eps);
					game.generateStats();
					eps -= 0.1f;
					if(eps<0.0f) {
						eps = 0.0f;
					}
				}
				no_episodes++;
				game.restart();
			}
			else {
				old_state = game.getState();
				action = chooseAction().getFirst();
				game.applyAction(action);
				reward = game.getReward();
				best_score = chooseAction().getSecond();
				old_state_action = new Pair<String, String>(old_state, action);
				
				Q.put(old_state_action, Q.get(old_state_action) + alpha*(reward+gamma*
						best_score-Q.get(old_state_action)));

			}
		}
	}
	public String getQ() {
		String str = "";
		for(Pair<String, String> state_action : Q.keySet()){
			str += Q.get(state_action) + " ";
		}
		str += "\n";
		return str;
	}
}
