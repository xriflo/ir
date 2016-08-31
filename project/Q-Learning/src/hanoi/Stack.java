package hanoi;

import java.util.ArrayList;

public class Stack {
	public ArrayList<Disk> disks;
	int no;
	Hanoi game;
	public Stack(int no, Hanoi game) {
		this.no = no;
		this.game = game;
		this.disks = new ArrayList<Disk>();
	}
}
