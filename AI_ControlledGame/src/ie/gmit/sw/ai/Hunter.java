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
		if (model.isTouchingCharacter('1', row, col)) {
			model.set(row, col, '\u0020');
			return false;
		}
		return true;
	}
}