package ie.gmit.sw.ai;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import ie.gmit.sw.ai.pathfinding.Path;
import ie.gmit.sw.ai.pathfinding.PathFinder;
import ie.gmit.sw.ai.pathfinding.Step;

public class Hunter implements Command {
	
	private static final char RED_GREEN_GHOST_ID = '\u0035';
	private ProgressManager pm = ProgressManager.getInstance();
	private GameModel model;
	private ProximityScanner proximityScanner;
	private PathFinder pathFinder;
	private int row, col;
	private static ThreadLocalRandom rand = ThreadLocalRandom.current();
	private Deque<Step> stepsToScavenger = new LinkedList<Step>(); 
	
	public Hunter(GameModel model, int row, int col) {
		this.model = model;
		this.row = row;
		this.col = col;
		pathFinder = new PathFinder(model);
		proximityScanner = new ProximityScanner(model);
	}
	
	@Override
	public boolean execute() {
		if (model.isTouchingCharacter('1', row, col)) {
			pm.hunterHitPlayer();
		}
		
		if (!stepsToScavenger.isEmpty()) {
			Step step = stepsToScavenger.poll(); 		
			// if about to run over player - game over 
			if (model.get(step.getX(), step.getY()) == '1') pm.gameOver();
    		model.set(step.getX(), step.getY(), RED_GREEN_GHOST_ID);
    		model.set(row, col, '\u0020');
    		setActivePosition(step.getX(), step.getY());
			return true;
		}	
		
		boolean nearScavenger = proximityScanner.searchForScavenger(row, col, 10);
		
		if (nearScavenger) {
			findPath(proximityScanner.getTargetPosition());
		} else {
			//Randomly pick a direction up, down, left or right
			int temp_row = row, temp_col = col;
			if (rand.nextBoolean()) {
	    		temp_row += rand.nextBoolean() ? 1 : -1;
	    	} else {
	    		temp_col += rand.nextBoolean() ? 1 : -1;
	    	}
			
			if (model.isValidMove(row, col, temp_row, temp_col, RED_GREEN_GHOST_ID)) {
	    		model.set(temp_row, temp_col, RED_GREEN_GHOST_ID);
	    		model.set(row, col, '\u0020');
	    		setActivePosition(temp_row, temp_col);
	    		return true;
			} 
		}
		return true;
	}
	
	public void setActivePosition(int r, int c) {
		row = r;
		col = c;
	}
	
	private void findPath(int[] target) {
		Path path = pathFinder.findPath(row, col, target[0], target[1]);
				
		if (path != null) {
			stepsToScavenger = path.getPath(); 			
		}
	}
}
