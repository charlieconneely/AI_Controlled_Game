package ie.gmit.sw.ai;

public class ProgressManager {
	
	private int hedgesEaten = 0;
	private int strikesFromHunter = 0;
	private int ghostsEatenByHunter = 0;
	private int ghostCount = 0;
	
	private static ProgressManager pm = new ProgressManager();
	
	private ProgressManager() {}
	
	public void incrementHedgesEaten() {
		hedgesEaten++;
	}
	
	public void incrementGhostCount() {
		ghostCount++;
	}
	
	public void ghostsEatenByHunter () {
		ghostsEatenByHunter++;
	}
	
	public void hunterHitPlayer() {
		strikesFromHunter++;
	}
	
	public void decrementGhostCount() {
		ghostCount--;
		if (ghostCount <= 0) {
			System.out.println("All ghosts killed!");
			System.exit(0);
		}
	}
	
	public int getHedgesEaten() {
		return hedgesEaten;
	}
	
	public int getGhostCount() {
		return ghostCount;
	}
	
	public int getStrikeCount() {
		return strikesFromHunter;
	}
	
	public int getEatenByHunterCount() {
		return ghostsEatenByHunter;
	}
	
	public static ProgressManager getInstance() {
		return pm;
	}
}
