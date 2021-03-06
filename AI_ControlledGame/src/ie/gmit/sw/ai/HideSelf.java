package ie.gmit.sw.ai;

public class HideSelf extends ScavengerAction {
	
	private final double STRENGTH_GAIN = 0;
	
	private GameModel model;
	
	public HideSelf(GameModel model) {
		this.model = model;
	}

	@Override
	public double act(int row, int col, int temp_row, int temp_col) {
		// if coordinate is out of bounds of the grid - return
		if (!model.isInBounds(temp_row, temp_col)) {
			return 0;
		}
		
		int[][] adjPositions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		
		for (int[] a : adjPositions) {
			int dRow = row + a[0];
			int dCol = col + a[1];
			
			if (model.isInBounds(dRow, dCol)) {
				if (model.get(dRow, dCol) == ' ') {
					model.set(dRow, dCol, '0');
				}
			}
		}
				
		return STRENGTH_GAIN;
	}
}
