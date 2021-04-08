package ie.gmit.sw.ai.pathfinding;

public class Step {
	private int x, y;
	
	public Step(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
