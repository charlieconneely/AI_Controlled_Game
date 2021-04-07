package ie.gmit.sw.ai;

public class DepthLimitedSearch {
	
	private GameModel model;
	private int[] positionOfEnemy;
	
	public DepthLimitedSearch(GameModel model) {
		this.model = model;
	}
	
	public boolean searchForScavenger(int row, int col, int depth) {
		for (int i = (row - depth); i <= (row + depth); i++) {
			for (int j = (col - depth); j <= (col + depth); j++) {
				if (i > model.size() - 1 || j > model.size() - 1 || i < 0 || j < 0) continue;
				if (isScavenger(model.get(i, j))) {
					setTargetPosition(i, j);
					return true;
				}
			}
		}
		return false;
	}
	
	public int searchForHunter(int row, int col, int depth) {
		for (int i = (row - depth); i <= (row + depth); i++) {
			for (int j = (col - depth); j <= (col + depth); j++) {
				if (i > model.size() - 1 || j > model.size() - 1 || i < 0 || j < 0) continue;
				if (model.get(i, j) == '1') {
					return 1;
				} else if (model.get(i, j) == '5') {
					return 2;
				}
			}
		}
		return 0;
	}
	
	private boolean isScavenger(char pos) {
		switch (pos) {
			case '1': // PLAYER - TAKE OUT 
			case '2': // scavengers
			case '3':
			case '4':
			case '6':
				return true;
			default:
				return false;
		}
	}
	
	public int[] getTargetPosition() {
		return positionOfEnemy;
	}
	
	public void setTargetPosition(int r, int c) {
		int[] pos = {r, c};
		positionOfEnemy = pos;
	}

}
