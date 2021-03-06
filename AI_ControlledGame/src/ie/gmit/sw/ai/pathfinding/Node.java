package ie.gmit.sw.ai.pathfinding;

public class Node implements Comparable<Object> {
	
	private int x, y;
	private int cost, depth;
	private float heuristic;
	private boolean visited = false;
	private Node parent;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setHeuristic(float heuristic) {
		this.heuristic = heuristic;
	}
	
	public float getHeuristic() {
		return this.heuristic;
	}
	
	public boolean getVisited() {
		return this.visited;
	}
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public int setParent(Node parent) {
		this.depth = parent.depth + 1;
		this.parent = parent;
		return this.depth;
	}
	
	public void setParentNull() {
		this.parent = null;
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

	@Override
	public int compareTo(Object other) {
		Node otherNode = (Node) other;
		
		float val = heuristic + cost;
		float otherVal = otherNode.heuristic + otherNode.cost;
		
		if (val < otherVal) {
			return -1;
		} else if (val > otherVal) {
			return 1;
		} else {
			return 0;
		}
	}

}
