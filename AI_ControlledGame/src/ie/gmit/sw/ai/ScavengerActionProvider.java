package ie.gmit.sw.ai;

import ie.gmit.sw.ai.nn.NeuralNetwork;

public class ScavengerActionProvider {

	private ScavengerAction scavenge;
	private ScavengerAction cloneSelf;
	private ScavengerAction teleport;
	private ScavengerAction hideSelf;
	private NeuralNetwork nn = NeuralNetwork.getInstance();

	public ScavengerActionProvider(GameModel model, char enemyID, Scavenger scavenger) {
		scavenge = new Scavenge(model);
		cloneSelf = new CloneSelf(model, enemyID);
		teleport = new Teleport(model, scavenger);
		hideSelf = new HideSelf(model);
	}

	public ScavengerAction getAction(int nearCharacter, double strength, int ghostCount) {
		if (nearCharacter == 0) // 0 = no character is near
			return scavenge;

		// pass state of game character to nn to determine action
		double[] nnInput = { (double)nearCharacter, (double)Math.floor(strength), (double)ghostCount };
		int action = nn.classifyAction(nnInput);

		switch (action) {
		case 1:
			return scavenge;
		case 2:
			return cloneSelf;
		case 3:
			return teleport;
		case 4:
			return hideSelf;
		default:
			return scavenge;
		}
	}
}
