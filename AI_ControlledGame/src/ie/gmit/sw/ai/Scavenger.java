package ie.gmit.sw.ai;

import java.util.concurrent.ThreadLocalRandom;

public class Scavenger implements Command {

	private GameModel model;
	private ScavengerActionProvider actionProvider;
	private ProgressManager pm = ProgressManager.getInstance();
	private char enemyID;
	private int row, col;
	private ProximityScanner proximityScanner;
	private static ThreadLocalRandom rand = ThreadLocalRandom.current();
	private double strength;
	
	public Scavenger(GameModel model, char enemyID, int row, int col) {
		this.model = model;
		this.enemyID = enemyID;
		this.row = row;
		this.col = col;
		proximityScanner = new ProximityScanner(model);
		actionProvider = new ScavengerActionProvider(model, enemyID, this);
	}

	@Override
	public boolean execute() {					
		// if touching player or hunter - die
		if (model.isTouchingCharacter('1', row, col) || model.isTouchingCharacter('5', row, col)) {
			if (model.isTouchingCharacter('5', row, col)) pm.ghostsEatenByHunter(); 
			pm.decrementGhostCount();
			model.set(row, col, '\u0020');
			return false;
		}
		
		int nearCharacter = proximityScanner.searchForHunter(row, col, 5);
			
		//Randomly pick a direction up, down, left or right
		int temp_row = row, temp_col = col;
		if (rand.nextBoolean()) {
    		temp_row += rand.nextBoolean() ? 1 : -1;
    	} else {
    		temp_col += rand.nextBoolean() ? 1 : -1;
    	}
		
		if (model.isValidMove(row, col, temp_row, temp_col, enemyID)) {
    		model.set(temp_row, temp_col, enemyID);
    		model.set(row, col, '\u0020');
    		setActivePosition(temp_row, temp_col);
    		return true;
		} 
							
		ScavengerAction action = actionProvider.getAction(nearCharacter, strength, pm.getGhostCount());
		double strengthGain = action.act(row, col, temp_row, temp_col);
		addStrength(strengthGain);
		return true;
	}	

	public void setActivePosition(int r, int c) {
		row = r;
		col = c;
	}
	
	private void addStrength(double s) {
		if (strength < 5) strength += s;
		if (strength < 0) strength = 0;
	}
}
