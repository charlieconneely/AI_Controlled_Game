package ie.gmit.sw.ai;

public class ActionProvider {

	private ScavengerAction scavenge;
	private NeuralNetwork nn = NeuralNetwork.getInstance();

	public ActionProvider(GameModel model) {
		scavenge = new Scavenge(model);
	}
	
	public ScavengerAction getAction(int nearCharacter, double strength) {
		if (nearCharacter == 0)
			return scavenge;

		return scavenge;
	}
		
}
