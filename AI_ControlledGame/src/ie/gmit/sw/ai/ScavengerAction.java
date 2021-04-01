package ie.gmit.sw.ai;

public abstract class ScavengerAction {
	public abstract double act(int row, int col, int temp_row, int temp_col);
	
	public boolean isInBounds(GameModel model, int row, int col) {
		if (row < 0 || col < 0 || row > model.size() - 1 || col > model.size() - 1) {
			return false;
		}
		return true;
	}
}
