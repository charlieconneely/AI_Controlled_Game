package ie.gmit.sw.ai;

import java.util.concurrent.ThreadLocalRandom;

public class Scavenger implements Command {

	private GameModel model;
	private ActionProvider actionProvider;
	private char enemyID;
	private int row, col;
	private DepthLimitedSearch dls;
	private static ThreadLocalRandom rand = ThreadLocalRandom.current();
	private double strength;
	
	public Scavenger(GameModel model, char enemyID, int row, int col) {
		this.model = model;
		this.enemyID = enemyID;
		this.row = row;
		this.col = col;
		dls = new DepthLimitedSearch(model);
		actionProvider = new ActionProvider(model);
	}

	@Override
	public boolean execute() {
		
		// if touching player or hunter - die
		if (isTouchingPlayer('1') || isTouchingPlayer('5')) {
			model.set(row, col, '\u0020');
			return false;
		}
		
		int nearCharacter = dls.search(row, col, 5);
		
		//Randomly pick a direction up, down, left or right
		int temp_row = row, temp_col = col;
		if (rand.nextBoolean()) {
    		temp_row += rand.nextBoolean() ? 1 : -1;
    	}else {
    		temp_col += rand.nextBoolean() ? 1 : -1;
    	}
		
		if (model.isValidMove(row, col, temp_row, temp_col, enemyID)) {
    		model.set(temp_row, temp_col, enemyID);
    		model.set(row, col, '\u0020');
    		row = temp_row;
    		col = temp_col;
    		return true;
		} 
					
		// call to nn here 
		ScavengerAction action = actionProvider.getAction(nearCharacter, strength);
		if (strength < 5) strength += action.act(temp_row, temp_col);
		System.out.println(strength);
		return true;
	}
		
	// check if player or hunter is in any position surrounding character
	private boolean isTouchingPlayer(char c) {
		if (model.get(row - 1, col) == c || model.get(row + 1, col) == c
			|| model.get(row, col - 1) == c || model.get(row, col + 1) == c) {
			return true;
		}
		return false;
	}
}
