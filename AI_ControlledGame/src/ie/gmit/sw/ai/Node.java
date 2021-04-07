package ie.gmit.sw.ai;

public class Node {
	
	private int x, y;
	private int cost, depth, heuristic;
	private boolean visited = false;
	private Node parent;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
	
	public boolean getVisited() {
		return this.visited;
	}
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}