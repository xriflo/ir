package hanoi;

import java.util.ArrayList;

import game.Game;

public class Hanoi extends Game{
	Integer no_episodes=0, no_moves=0, old_no_moves;
	static final String[] actions = {"UP", "DOWN", "LEFT", "RIGHT"};
	ArrayList<Stack> stacks;
	Picker picker;
	int no_stacks, no_disks;
	public Hanoi(int no_stacks, int no_disks) {
		this.no_stacks = no_stacks;
		this.no_disks = no_disks;
		restart();
	}
	@Override
	public void restart() {
		stacks = new ArrayList<Stack>();
		picker = new Picker();
		for(int i=0; i<no_stacks; i++) {
			stacks.add(new Stack(i+1, this));
		}
		for(int i=0; i<no_disks; i++) {
			stacks.get(0).disks.add(new Disk(0, no_disks-i));
		}
	}
	@Override
	public String getState() {
		String str = "[picker]: disk=" + (picker.disk==null ? "null" : picker.disk.size);
		str += " pointing=" + picker.pointer + "\n";
		for(int i=0; i<no_stacks; i++) {
			str += "[" + i + "]: ";
			ArrayList<Disk> disks = stacks.get(i).disks;
			for(int j=0; j<disks.size(); j++) {
				str += stacks.get(i).disks.get(j).size + " ";
			}
			str += "\n";
		}
		str += "---------------------------";
		return str;
	}
	@Override
	public void applyAction(String action) {
		no_moves++;
		switch(action) {
			case "UP":
				if(picker.disk==null &&
						stacks.get(picker.pointer).disks.size()!=0) {
					ArrayList<Disk> disks_stack = stacks.get(picker.pointer).disks;
					//picker.disk = stacks.get(picker.pointer).disks.get(index)
					picker.disk = disks_stack.get(disks_stack.size()-1);
					disks_stack.remove(disks_stack.size()-1);
				}
				break;
			case "DOWN":
				if(picker.disk!=null) {
					ArrayList<Disk> disks_stack = stacks.get(picker.pointer).disks;
					if(disks_stack.size()==0 ||
							picker.disk.size<disks_stack.get(disks_stack.size()-1).size) {
						disks_stack.add(picker.disk);
						picker.disk = null;
					}
				}
				break;
			case "LEFT":
				picker.pointer = (--picker.pointer>=0 ? picker.pointer : no_stacks-1);
				break;
			case "RIGHT":
				picker.pointer = (++picker.pointer>=no_stacks ? 0 : picker.pointer);
				break;
			default:
				System.out.println("ERROR");
				
		}
		
	}
	@Override
	public boolean isOver() {
		if(stacks.get(stacks.size()-1).disks.size()==no_disks)
			return true;
		/*
		for(int i=1; i<stacks.size(); i++) {
			if(stacks.get(i).disks.size()==no_disks){
				return true;
			}
		}*/
		return false;
	}
	@Override
	public String[] getActions() {
		return actions;
	}
	@Override
	public Float getReward() {
		if(isOver()) {
			old_no_moves = no_moves;
			no_moves = 0;
			no_episodes++;
			return 100.0f;
		}
		else
			return 0.0f;
	}
	@Override
	public void generateStats() {
		System.out.println("Episode " + (no_episodes-1) + " ended in "+old_no_moves + " moves.");
		
	}
	
}
