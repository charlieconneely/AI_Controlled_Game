package ie.gmit.sw.ai;

public class DepthLimitedSearch {
	
	private GameModel model;
	
	public DepthLimitedSearch(GameModel model) {
		this.model = model;
	}
	
	public boolean search(int row, int col, int depth) {
		for (int i = (row - depth); i <= (row + depth); i++) {
			for (int j = (col - depth); j <= (col + depth); j++) {
				if (i <= model.size() - 1 && j <= model.size() - 1) {
					if (model.get(i, j) == '1') return true;
				}
			}
		}	
		return false;
	}
}
