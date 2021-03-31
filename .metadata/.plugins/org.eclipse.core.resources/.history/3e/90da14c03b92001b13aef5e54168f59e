package ie.gmit.sw.ai;

public class Scavenge extends ScavengerAction {
	
	private final double STRENGTH_GAIN = 0.1;
	
	private GameModel model;
	
	public Scavenge(GameModel model) {
		this.model = model;
	}

	@Override
	public double act(int row, int col) {
		if (model.get(row, col) == '0') {
			model.set(row, col, '\u0020');
		}
		return STRENGTH_GAIN;
	}
}
