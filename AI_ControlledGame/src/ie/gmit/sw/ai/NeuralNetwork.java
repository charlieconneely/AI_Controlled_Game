package ie.gmit.sw.ai;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

public class NeuralNetwork {
	
	private static NeuralNetwork nn = new NeuralNetwork();
	private BasicNetwork network = new BasicNetwork();
	
	private NeuralNetwork() {}
	
	@SuppressWarnings("exports")
	public BasicNetwork createNeuralNetwork(int inputNodes, int outputNodes) {	
		BasicNetwork bn = new BasicNetwork();
		bn.addLayer(new BasicLayer(null, true, inputNodes));
		bn.addLayer(new BasicLayer(new ActivationSigmoid(), true, 16));
		bn.addLayer(new BasicLayer(new ActivationSigmoid(), false, outputNodes));
		bn.getStructure().finalizeStructure();
		bn.reset();
		
		return bn;
	}

	public void trainNeuralNetwork() {
		System.out.println("[INFO] Creating neural network");
		network = createNeuralNetwork(3, 4);

		System.out.println("[INFO] Creating training set");
		MLDataSet trainingSet = new BasicMLDataSet(data, expected);

		System.out.println("[INFO] Training the network...");
		ResilientPropagation train = new ResilientPropagation(network, trainingSet);

		double minError = 0.01;
		int epoch = 1;
		do {
			train.iteration();
			epoch++;
		} while (train.getError() > minError);
		train.finishTraining();
		System.out.println("[INFO] training complete in " + (epoch - 1) + " epochs with error=" + train.getError());
		
		// TESTING THE NETWORK
		boolean testing = false;
		if (testing) {
			testNetwork(trainingSet);
		}
		
		System.out.println("[INFO] Shutting down.");
		Encog.getInstance().shutdown();
	}
			
	public int classifyAction(double[] input) {
		MLData data = new BasicMLData(input);
		return ((int) network.classify(data) + 1);
	}
	
	private void testNetwork(MLDataSet trainingSet) {
		int errorCount = 0;
		int totalCount = 0;
		
		System.out.println("[INFO] Testing the network:");
		for (MLDataPair pair : trainingSet) {
			MLData output = network.compute(pair.getInput());
			System.out.println(pair.getInput().getData(0) + "," + pair.getInput().getData(1) +
					"," + pair.getInput().getData(2) + 
					": Y="
					+ (int) Math.round(output.getData(0)) + ", Yd=" + (int) pair.getIdeal().getData(0));	
			
			if ((int) Math.round(output.getData(0)) != (int) pair.getIdeal().getData(0)) {
				errorCount++;
			}
			totalCount++;
		}
		System.out.println("Error rate: " + (errorCount/totalCount) * 100);
		
	}
	
	public static NeuralNetwork getInstance() {
		return nn;
	}
	
	// TRAINING DATA 
	// { close player ID, strength, no. of remaining ghosts } 
	double[][] data = { { 1.0, 1.0, 1.0 }, { 1.0, 1.0, 2.0 }, { 1.0, 1.0, 3.0 }, { 1.0, 1.0, 4.0 }, { 1.0, 1.0, 5.0 },
			{ 1.0, 1.0, 6.0 }, { 1.0, 1.0, 7.0 }, { 1.0, 1.0, 8.0 }, { 1.0, 2.0, 1.0 }, { 1.0, 2.0, 2.0 },
			{ 1.0, 2.0, 3.0 }, { 1.0, 2.0, 4.0 }, { 1.0, 2.0, 5.0 }, { 1.0, 2.0, 6.0 }, { 1.0, 2.0, 7.0 },
			{ 1.0, 2.0, 8.0 }, { 1.0, 3.0, 1.0 }, { 1.0, 3.0, 2.0 }, { 1.0, 3.0, 3.0 }, { 1.0, 3.0, 4.0 },
			{ 1.0, 3.0, 5.0 }, { 1.0, 3.0, 6.0 }, { 1.0, 3.0, 7.0 }, { 1.0, 3.0, 8.0 }, { 1.0, 4.0, 1.0 },
			{ 1.0, 4.0, 2.0 }, { 1.0, 4.0, 3.0 }, { 1.0, 4.0, 4.0 }, { 1.0, 4.0, 5.0 }, { 1.0, 4.0, 6.0 },
			{ 1.0, 4.0, 7.0 }, { 1.0, 4.0, 8.0 }, { 1.0, 5.0, 1.0 }, { 1.0, 5.0, 2.0 }, { 1.0, 5.0, 3.0 },
			{ 1.0, 5.0, 4.0 }, { 1.0, 5.0, 5.0 }, { 1.0, 5.0, 6.0 }, { 1.0, 5.0, 7.0 }, { 1.0, 5.0, 8.0 },

			{ 2.0, 1.0, 1.0 }, { 2.0, 1.0, 2.0 }, { 2.0, 1.0, 3.0 }, { 2.0, 1.0, 4.0 }, { 2.0, 1.0, 5.0 },
			{ 2.0, 1.0, 6.0 }, { 2.0, 1.0, 7.0 }, { 2.0, 1.0, 8.0 }, { 2.0, 2.0, 1.0 }, { 2.0, 2.0, 2.0 },
			{ 2.0, 2.0, 3.0 }, { 2.0, 2.0, 4.0 }, { 2.0, 2.0, 5.0 }, { 2.0, 2.0, 6.0 }, { 2.0, 2.0, 7.0 },
			{ 2.0, 2.0, 8.0 }, { 2.0, 3.0, 1.0 }, { 2.0, 3.0, 2.0 }, { 2.0, 3.0, 3.0 }, { 2.0, 3.0, 4.0 },
			{ 2.0, 3.0, 5.0 }, { 2.0, 3.0, 6.0 }, { 2.0, 3.0, 7.0 }, { 2.0, 3.0, 8.0 }, { 2.0, 4.0, 1.0 },
			{ 2.0, 4.0, 2.0 }, { 2.0, 4.0, 3.0 }, { 2.0, 4.0, 4.0 }, { 2.0, 4.0, 5.0 }, { 2.0, 4.0, 6.0 },
			{ 2.0, 4.0, 7.0 }, { 2.0, 4.0, 8.0 }, { 2.0, 5.0, 1.0 }, { 2.0, 5.0, 2.0 }, { 2.0, 5.0, 3.0 },
			{ 2.0, 5.0, 4.0 }, { 2.0, 5.0, 5.0 }, { 2.0, 5.0, 6.0 }, { 2.0, 5.0, 7.0 }, { 2.0, 5.0, 8.0 } };

	// actions = { scavenge, clone self, clone player, hide }
	double[][] expected = { { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 },
			{ 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 0, 1.0, 0, 0 }, { 0, 1.0, 0, 0 }, { 1.0, 0, 0, 0 },
			{ 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 0, 1.0, 0, 0 },
			{ 0, 1.0, 0, 0 }, { 0, 1.0, 0, 0 }, { 0, 1.0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 },
			{ 1.0, 0, 0, 0 }, { 0, 0, 1.0, 0 }, { 0, 0, 1.0, 0 }, { 0, 0, 1.0, 0 }, { 0, 0, 1.0, 0 }, { 0, 1.0, 0, 0 },
			{ 0, 1.0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 0, 0, 0, 1.0 }, { 0, 0, 0, 1.0 }, { 0, 0, 0, 1.0 },
			{ 0, 0, 1.0, 0 }, { 0, 0, 1.0, 0 }, { 0, 0, 1.0, 0 }, { 0, 1.0, 0, 0 }, { 0, 1.0, 0, 0 }, { 1.0, 0, 0, 0 },
			{ 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 },
			{ 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 },
			{ 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 0, 1.0, 0, 0 }, { 0, 1.0, 0, 0 }, { 1.0, 0, 0, 0 },
			{ 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 0, 1.0, 0, 0 },
			{ 0, 1.0, 0, 0 }, { 0, 1.0, 0, 0 }, { 0, 1.0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0 },
			{ 1.0, 0, 0, 0 }, { 0, 0, 0, 1.0 }, { 0, 0, 0, 1.0 }, { 0, 1.0, 0, 0 }, { 0, 1.0, 0, 0 }, { 0, 1.0, 0, 0 },
			{ 0, 1.0, 0, 0 }, { 0, 1.0, 0, 0 }, { 0, 1.0, 0, 0 } };
}