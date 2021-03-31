package ie.gmit.sw.ai;

public class DepthLimitedSearch {
	
	private GameModel model;
	
	public DepthLimitedSearch(GameModel model) {
		this.model = model;
	}
	
	public int search(int row, int col, int depth) {
		for (int i = (row - depth); i <= (row + depth); i++) {
			for (int j = (col - depth); j <= (col + depth); j++) {
				if (i <= model.size() - 1 && j <= model.size() - 1) {
					// if player
					if (model.get(i, j) == '1') {
						return 1;
					} 
					// if red green 
					else if (model.get(i, j) == '5') {
						return 5;
					}
				}
			}
		}	
		return 0;
	}
}
