package ie.gmit.sw.ai;

public class RunAway implements Command {
	
	private GameModel model;
	
	public RunAway(GameModel model) {
		this.model = model;
	}
		
	@Override
	public void execute(int row, int col) {
		
	}
}