package ie.gmit.sw.ai;

public class ActionProvider {

	private ScavengerAction scavenge;
	private ScavengerAction cloneSelf;
	private NeuralNetwork nn = NeuralNetwork.getInstance();

	public ActionProvider(GameModel model, char enemyID) {
		scavenge = new Scavenge(model);
		cloneSelf = new CloneSelf(model, enemyID);
	}
	
	public ScavengerAction getAction(int nearCharacter, double strength, int ghostCount) {
		if (nearCharacter == 0)
			return scavenge;
		
		double strengthRoundedDown = Math.floor(strength);
		// pass state of game character to nn to determine action
		double[] nnInput = {(double) nearCharacter, strengthRoundedDown, (double) ghostCount};
		int action = nn.classifyAction(nnInput);
		
		if (action == 1) {
			return scavenge;
		} else if (action == 2) {
			System.out.println("Clone action returned");
			return cloneSelf;
		}

		return scavenge;
	}
		
}