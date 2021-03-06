package ie.gmit.sw.ai.pathfinding;

public class NodeMap {
	
	private Node[][] nodes;
	
	public NodeMap(char[][] map) {
		nodes = new Node[map.length][map[0].length];
		initialize(map);
	}
	
	public Node getNode(int x, int y) {
		return nodes[x][y];
	}
	
	public void initialize(char[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				nodes[i][j] = new Node(i, j);
			}
		}
	}
		
	public int getWidth() {
		return nodes[0].length;
	}
	
	public int getHeight() {
		return nodes.length;
	}
	
	public boolean isVisited(int r, int c) {
		return nodes[r][c].getVisited();
	}
	
	public void resetVisited() {
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[i].length; j++) {
				nodes[i][j].setVisited(false);
			}
		}
	}
	
	public float getCost(int sr, int sc, int tr, int tc) {
		return nodes[sr][sc].getCost();
	}
	
	public void pathFinderVisited(int r, int c) {
		nodes[r][c].setVisited(true);
	}
}
