package ie.gmit.sw.ai.fuzzy;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class ScoreCalculator {
	private static ScoreCalculator sc = new ScoreCalculator();
	
	private static final String FILE = "./resources/fuzzy/scorer.fcl";
	private FIS fis;
	private FunctionBlock fb;
	
	private ScoreCalculator() {}
	
	public static void main(String[] args) {
		ScoreCalculator sc = new ScoreCalculator();
		double score = sc.getFinalScore(10, 7, 6);
		System.out.println("Score: " + score);
	}
	
	public void loadFuzzyLogic() {
		fis = FIS.load(FILE, true);
		fb = fis.getFunctionBlock("getFinalScore");
	}
	
	public double getFinalScore(int hedgesEaten, int strikesFromHunter, int ghostsEatenByHunter) {		
		fis.setVariable("hedgesEaten", hedgesEaten);
		fis.setVariable("strikesTaken", strikesFromHunter);
		fis.setVariable("ghostsEatenByHunter", ghostsEatenByHunter);
		fis.evaluate();
		
	    Variable score = fb.getVariable("score");
		return score.getValue();
	}
	
	public static ScoreCalculator getInstance() {
		return sc;
	}
}
