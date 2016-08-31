package hanoi;

public class Picker {
	Disk disk;
	int pointer;
	public Picker() {
		disk = null;
		pointer = 0;
	}
	public Picker(Disk disk, int pointer) {
		this.disk = disk;
		this.pointer = pointer;
	}
}
