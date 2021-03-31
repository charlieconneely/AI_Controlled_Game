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
	public double act(int row, int col) {
		// if coordinate is out of bounds of the grid - return
		if (row < 0 || col < 0 || row > model.size() - 1 || col > model.size() - 1) {
			return 0;
		}
		// if coordinate is a hedge - destroy and return strength increase
		else if (model.get(row, col) == '0') {
			model.set(row, col, id);
		}
		return STRENGTH_GAIN;
	}

}
