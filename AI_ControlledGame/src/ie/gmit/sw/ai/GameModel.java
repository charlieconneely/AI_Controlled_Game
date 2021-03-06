package ie.gmit.sw.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import javafx.concurrent.Task;

/*
 * [READ THIS CAREFULLY]
 * You will need to change the method addGameCharacter() below and configure each 
 * instance of CharacterTask with a Command object. The implementation below uses
 * a lambda expression ()-> System.out.println("Action executing!") as the default
 * logic for the execute() method.
 * 
 * [WARNING] Don't mess with anything else in this class unless you know exactly 
 * what you're at... If you break it, you own it.
 */
public class GameModel {
	private static final int MAX_CHARACTERS = 10;
	private static final int NO_OF_CHARACTERS = 5;
	private static final char RED_GHOST_ID = '\u0032';
	private static final char PINK_GHOST_ID = '\u0033';
	private static final char BLUE_GHOST_ID = '\u0034';
	private static final char RED_GREEN_GHOST_ID = '\u0035';
	private static final char ORANGE_GHOST_ID = '\u0036'; 
	private ThreadLocalRandom rand = ThreadLocalRandom.current();
	private char[][] model;
	private ProgressManager pm = ProgressManager.getInstance();

	private final ExecutorService exec = Executors.newFixedThreadPool(MAX_CHARACTERS, e -> {
		Thread t = new Thread(e);
		t.setDaemon(true);
		return t;
	});

	public GameModel(int dimension) {
		model = new char[dimension][dimension];
		init();
		carve();
		addGameCharacters();
	}

	public void tearDown() {
		exec.shutdownNow();
	}

	/*
	 * Initialises the game model by creating an n x m array filled with hedge
	 */
	private void init() {
		for (int row = 0; row < model.length; row++) {
			for (int col = 0; col < model[row].length; col++) {
				model[row][col] = '\u0030'; // \u0030 = 0x30 = 0 (base 10) = A hedge
			}
		}
	}

	/*
	 * Carve paths through the hedge to create passages.
	 */
	public void carve() {
		for (int row = 0; row < model.length; row++) {
			for (int col = 0; col < model[row].length - 1; col++) {
				if (row == 0) {
					model[row][col + 1] = '\u0020';
				} else if (col == model.length - 1) {
					model[row - 1][col] = '\u0020';
				} else if (rand.nextBoolean()) {
					model[row][col + 1] = '\u0020';
				} else {
					model[row - 1][col] = '\u0020';
				}
			}
		}
	}

	private void addGameCharacters() {
		Collection<Task<Void>> tasks = new ArrayList<>();
		addGameCharacter(tasks, RED_GHOST_ID, '0', MAX_CHARACTERS / NO_OF_CHARACTERS); 
		addGameCharacter(tasks, PINK_GHOST_ID, '0', MAX_CHARACTERS / NO_OF_CHARACTERS); 
		addGameCharacter(tasks, BLUE_GHOST_ID, '0', MAX_CHARACTERS / NO_OF_CHARACTERS); 
		addGameCharacter(tasks, RED_GREEN_GHOST_ID, '0', MAX_CHARACTERS / NO_OF_CHARACTERS); 
		addGameCharacter(tasks, ORANGE_GHOST_ID, '0', MAX_CHARACTERS / NO_OF_CHARACTERS); 
		tasks.forEach(exec::execute);
	}

	private void addGameCharacter(Collection<Task<Void>> tasks, char enemyID, char replace, int number) {
		int counter = 0;
		while (counter < number) {
			int row = rand.nextInt(model.length);
			int col = rand.nextInt(model[0].length);

			if (model[row][col] == replace) {
				model[row][col] = enemyID;
				switch (enemyID) {
				case RED_GHOST_ID:
				case PINK_GHOST_ID:
				case BLUE_GHOST_ID:
				case ORANGE_GHOST_ID:
					tasks.add(new CharacterTask(this, new Scavenger(this, enemyID, row, col)));
					pm.incrementGhostCount();
					break;
				case RED_GREEN_GHOST_ID:
					tasks.add(new CharacterTask(this, new Hunter(this, row, col)));
					break;
				}
				counter++;
			}
		}
	}

	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, char character) {
		if (toRow >= 0 && toCol >= 0 && toRow <= this.size() - 1 && toCol <= this.size() - 1) {
			if (this.get(toRow, toCol) == ' ') {
				this.set(fromRow, fromCol, '\u0020');
				this.set(toRow, toCol, character);
				return true;
			} else {
				return false;
			}
		} else {
			return false; // Can't move
		}
	}
	
	public boolean isTouchingCharacter(char id, int r, int c) {
		int[][] adjPositions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		
		for (int[] a : adjPositions) {
			int dRow = r + a[0];
			int dCol = c + a[1];
			
			if (isInBounds(dRow, dCol)) {
				if (this.get(dRow, dCol) == id) return true;
			}
		}
		
		return false;
	}
	
	public boolean isInBounds(int row, int col) {
		if (row < 0 || col < 0 || row > this.size() - 1 || col > this.size() - 1) {
			return false;
		}
		return true;
	}
		
	public char[][] getModel() {
		return this.model;
	}

	public char get(int row, int col) {
		return this.model[row][col];
	}

	public void set(int row, int col, char c) {
		this.model[row][col] = c;
	}

	public int size() {
		return this.model.length;
	}
}