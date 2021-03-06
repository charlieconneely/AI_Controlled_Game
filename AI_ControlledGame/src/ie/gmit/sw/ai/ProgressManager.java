package ie.gmit.sw.ai;

import ie.gmit.sw.ai.fuzzy.ScoreCalculator;

public class ProgressManager {
	
	private static final int MAX_STRIKES = 20;
	private static final int MAX_HEDGES = 200;
	
	private static ProgressManager pm = new ProgressManager();
	ScoreCalculator sc = ScoreCalculator.getInstance();
	private int hedgesEaten = 0;
	private int strikesFromHunter = 0;
	private int ghostsEatenByHunter = 0;
	private int ghostCount = 0;
	
	private ProgressManager() {}
	
	public void incrementHedgesEaten() {
		if (hedgesEaten < MAX_HEDGES) hedgesEaten++;
	}
	
	public void hunterHitPlayer() {
		strikesFromHunter++;
		if (strikesFromHunter >= MAX_STRIKES) gameOver();
	}
		
	public void ghostsEatenByHunter () {
		ghostsEatenByHunter++;
	}
	
	public void incrementGhostCount() {
		ghostCount++;
	}
		
	public void decrementGhostCount() {
		ghostCount--;
		if (ghostCount <= 0) gameOver();
	}
	
	public void gameOver() {
		System.out.println("\nGame Over!!");
		double score = sc.getFinalScore(hedgesEaten, strikesFromHunter, ghostsEatenByHunter);
		System.out.println("Your final score: " + String.format("%.2f", score) + "%");
		System.exit(0);
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
