package ie.gmit.sw.ai;

public class Teleport extends ScavengerAction {
	
	private final double STRENGTH_GAIN = 0;
	
	private GameModel model;
	private char id;
	private Scavenger scavenger;
	
	public Teleport(GameModel model, char id, Scavenger scavenger) {
		this.id = id;
		this.model = model;
		this.scavenger = scavenger;
	}

	@Override
	public double act(int row, int col, int temp_row, int temp_col) {
		// if coordinate is out of bounds of the grid - return
		if (!super.isInBounds(model, temp_row, temp_col)) {
			return 0;
		}
		// if coordinate is a hedge - destroy and return strength increase
		int[][] destinations = {{5, 0}, {-5, 0}, {0, 5}, {0, -5}};
		
		for (int[] d : destinations) {
			int dRow = row + d[0];
			int dCol = col + d[1];
			if (super.isInBounds(model, dRow, dCol)) {
				// replace empty spaces or hedges 
				if (model.get(dRow, dCol) == ' ' || model.get(dRow, dCol) == '0') {
					model.set(row, col, '\u0020');
					model.set(dRow, dCol, id);
					scavenger.setActivePosition(dRow, dCol);
				}
			}
		}
	
		return STRENGTH_GAIN;
	}

}
