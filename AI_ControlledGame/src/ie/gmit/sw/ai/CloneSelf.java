package ie.gmit.sw.ai;

public class CloneSelf extends ScavengerAction {
	
	private final double STRENGTH_GAIN = 0;
	
	private GameModel model;
	private char id;
	
	public CloneSelf(GameModel model, char id) {
		this.model = model;
		this.id = id;
	}

	@Override
	public double act(int row, int col, int temp_row, int temp_col) {
		// if coordinate is out of bounds of the grid - return
		if (!model.isInBounds(temp_row, temp_col)) {
			return 0;
		}
		// if coordinate contains hedge - clone self in that pos
		else if (model.get(temp_row, temp_col) == '0') {
			model.set(temp_row, temp_col, id);
		}
		return STRENGTH_GAIN;
	}
}
