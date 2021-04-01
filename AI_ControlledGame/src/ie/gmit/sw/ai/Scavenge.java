package ie.gmit.sw.ai;

public class Scavenge extends ScavengerAction {
	
	private final double STRENGTH_GAIN = 0.5;
	
	private GameModel model;
	
	public Scavenge(GameModel model) {
		this.model = model;
	}

	@Override
	public double act(int row, int col, int temp_row, int temp_col) {
		// if coordinate is out of bounds of the grid - return
		if (!super.isInBounds(model, temp_row, temp_col)) {
			return 0;
		}
		// if coordinate is a hedge - destroy and return strength increase
		else if (model.get(temp_row, temp_col) == '0') {
			model.set(temp_row, temp_col, '\u0020');
		}
		return STRENGTH_GAIN;
	}
}
