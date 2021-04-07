package ie.gmit.sw.ai;

public class Teleport extends ScavengerAction {
	
	private final static double STRENGTH_GAIN = 0;
	private final static int DISTANCE = 5;
	
	private GameModel model;
	private Scavenger scavenger;
	private int[][] destinations = {{DISTANCE, 0}, {-DISTANCE, 0}, {0, DISTANCE}, {0, -DISTANCE}};
	
	public Teleport(GameModel model, Scavenger scavenger) {
		this.model = model;
		this.scavenger = scavenger;
	}

	@Override
	public double act(int row, int col, int temp_row, int temp_col) {
		// if coordinate is out of bounds of the grid - return
		if (!model.isInBounds(temp_row, temp_col)) {
			return 0;
		}
	
		for (int[] d : destinations) {
			int dRow = row + d[0];
			int dCol = col + d[1];
			if (model.isInBounds(dRow, dCol)) {
				// replace empty spaces or hedges 
				if (model.get(dRow, dCol) == ' ' || model.get(dRow, dCol) == '0') {
					scavenger.setActivePosition(dRow, dCol);
					model.set(row, col, '\u0020');
				}
			}
		}
		return STRENGTH_GAIN;
	}

}

