package ie.gmit.sw.ai;

public class ProximityScanner {
	
	private GameModel model;
	private int[] positionOfEnemy;
	
	public ProximityScanner(GameModel model) {
		this.model = model;
	}
	
	public boolean searchForScavenger(int row, int col, int depth) {
		for (int i = (row - depth); i <= (row + depth); i++) {
			for (int j = (col - depth); j <= (col + depth); j++) {
				if (i > model.size() - 1 || j > model.size() - 1 || i < 0 || j < 0) continue;
				// if player is in position...
				if (isEnemy(model.get(i, j))) {
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
			
	public int[] getTargetPosition() {
		return positionOfEnemy;
	}
	
	public void setTargetPosition(int r, int c) {
		int[] pos = {r, c};
		positionOfEnemy = pos;
	}
	
	private boolean isEnemy(char pos) {
		switch (pos) {
			case '1': 
			case '2': 
			case '3':
			case '4':
			case '6':
				return true;
			default:
				return false;
		}
	}

}
