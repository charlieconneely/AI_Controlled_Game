package ie.gmit.sw.ai;

public class DepthLimitedSearch {
	
	private GameModel model;
	private static int limit = 10;
	
	public DepthLimitedSearch(GameModel model) {
		this.model = model;
	}
	
	public int[] search(int temp_row, int temp_col, int[] dir, int depth) {				
		if (depth > limit) {
			return dir;
		} 
		else if (model.get(temp_row, temp_col) == '1') {
			System.out.println("Near player");
			//int[] newDirs = {(dir[0] * -1), (dir[1] * -1)};
			//return newDirs;
			return dir;
		} 
		else {
			int [] newDirs;
			if (temp_row + (dir[0]) <= model.size() - 1 && temp_col + (dir[1]) <= model.size() - 1) {
				//newDirs = search(temp_row + (dir[0]), temp_col + (dir[1]), dir, depth + 1);
				newDirs = search(temp_row + (dir[0]*-1), temp_col + (dir[1]*-1), dir, depth + 1);
				return newDirs;
			}
			return dir;
		}	
	}
}
