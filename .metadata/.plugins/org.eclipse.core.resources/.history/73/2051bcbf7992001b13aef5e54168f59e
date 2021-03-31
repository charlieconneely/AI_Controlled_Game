package ie.gmit.sw.ai;

public class Hunter implements Command {

	private GameModel model;
	private char enemyID;
	private int row, col;
	
	public Hunter(GameModel model, char enemyID, int row, int col) {
		this.model = model;
		this.enemyID = enemyID;
		this.row = row;
		this.col = col;
	}
	
	@Override
	public boolean execute() {
		if (isTouchingPlayer('1')) {
			model.set(row, col, '\u0020');
			return false;
		}
		return true;
	}
	
	// check if player is in any position surrounding character
	private boolean isTouchingPlayer(char c) {
		if (model.get(row - 1, col) == c || model.get(row + 1, col) == c
			|| model.get(row, col - 1) == c || model.get(row, col + 1) == c) {
			return true;
		}
		return false;
	}
}
