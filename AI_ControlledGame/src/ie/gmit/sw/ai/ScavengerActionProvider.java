package ie.gmit.sw.ai;

public class ScavengerActionProvider {
	
	private Scavenger scavenger;
	private ScavengerAction scavenge;
	private ScavengerAction cloneSelf;
	private ScavengerAction teleport;
	private ScavengerAction hideSelf;
	private NeuralNetwork nn = NeuralNetwork.getInstance();

	public ScavengerActionProvider(GameModel model, char enemyID, Scavenger scavenger) {
		scavenge = new Scavenge(model);
		cloneSelf = new CloneSelf(model, enemyID);
		teleport = new Teleport(model, enemyID, scavenger);
		hideSelf = new HideSelf(model);
		this.scavenger = scavenger;
	}
	
	public ScavengerAction getAction(int nearCharacter, double strength, int ghostCount) {
		if (nearCharacter == 0) // 0 - no character is near 
			return scavenge;
		
		double strengthRoundedDown = Math.floor(strength);
		// pass state of game character to nn to determine action
		double[] nnInput = {(double) nearCharacter, strengthRoundedDown, (double) ghostCount};
		int action = nn.classifyAction(nnInput);
		
		if (action == 1) {
			return scavenge;
		} else if (action == 2) {
			return cloneSelf;
		} else if (action == 3) {
			return teleport;
		} else if (action == 4) {
			return hideSelf;
		}

		return scavenge;
	}
		
}
