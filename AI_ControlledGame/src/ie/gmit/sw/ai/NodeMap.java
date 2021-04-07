package ie.gmit.sw.ai;

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
		return 0;
	}
	
	public int getHeight() {
		return 0;
	}
	
	public boolean isBlocked(int r, int c) {
		return false;
	}
	
	public float getCost(int sr, int sc, int tr, int tc) {
		return 0;
	}
	
	public void pathFinderVisited(int r, int c) {
		nodes[r][c].setVisited(true);
	}

}
