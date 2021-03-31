package ie.gmit.sw.ai;

public class Scavenge extends ScavengerAction {
	
	private final double STRENGTH_GAIN = 0.5;
	
	private GameModel model;
	
	public Scavenge(GameModel model) {
		this.model = model;
	}

	@Override
	public double act(int row, int col) {
		// if coordinate is out of bounds of the grid - return
		if (row < 0 || col < 0 || row > model.size() - 1 || col > model.size() - 1) {
			return 0;
		}
		// if coordinate is a hedge - destroy and return strength increase
		else if (model.get(row, col) == '0') {
			model.set(row, col, '\u0020');
		}
		return STRENGTH_GAIN;
	}
}