package ie.gmit.sw.ai.nn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

	private static final String TRAINING_DATA_FILE = "./resources/neural/input_data.csv";
	private static final String EXPECTED_DATA_FILE = "./resources/neural/expected.csv";

	private static NeuralNetwork nn = new NeuralNetwork();
	private BasicNetwork network = new BasicNetwork();
	private static double[][] data;
	private static double[][] expected;

	private NeuralNetwork() {
	}

	public BasicNetwork createNeuralNetwork(int inputNodes, int outputNodes) {
		BasicNetwork bn = new BasicNetwork();
		bn.addLayer(new BasicLayer(null, true, inputNodes));
		bn.addLayer(new BasicLayer(new ActivationSigmoid(), true, 16));
		bn.addLayer(new BasicLayer(new ActivationSigmoid(), false, outputNodes));
		bn.getStructure().finalizeStructure();
		bn.reset();

		return bn;
	}

	public void trainNeuralNetwork() throws IOException {
		System.out.println("[INFO] Creating neural network");
		network = createNeuralNetwork(3, 4);

		nn.initiliaseData(TRAINING_DATA_FILE);
		nn.initiliaseData(EXPECTED_DATA_FILE);
	
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

	private void initiliaseData(String path) throws IOException {
		// ==== Establish length of 2d array ====
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = "";
		int rows = 0;
		int cols = 0;
		while ((line = br.readLine()) != null) {
			rows++;
			String[] item = line.split(",");
			cols = item.length;
		}

		switch (path) 
		{
		case TRAINING_DATA_FILE:
			initialiseTrainingData(rows, cols);
			break;
		case EXPECTED_DATA_FILE:
			initialiseExpectedData(rows, cols);
			break;
		} 
	}
	
	private void initialiseTrainingData(int rows, int cols) throws IOException {
		// ==== Reinitilise variables and read in data from file ====
		BufferedReader br = new BufferedReader(new FileReader(TRAINING_DATA_FILE));
		data = new double[rows][cols];
		String line = "";
		int i = 0;
		while ((line = br.readLine()) != null) {
			String[] item = line.split(",");
			for (int j = 0; j < item.length; j++) {
				data[i][j] = Double.valueOf(item[j]);
			}
			i++;
		}
	}

	private void initialiseExpectedData(int rows, int cols) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(EXPECTED_DATA_FILE));
		expected = new double[rows][cols];
		String line = "";
		int i = 0;
		while ((line = br.readLine()) != null) {
			String[] item = line.split(",");
			for (int j = 0; j < item.length; j++) {
				expected[i][j] = Double.valueOf(item[j]);
			}
			i++;
		}
	}

	private void testNetwork(MLDataSet trainingSet) {
		int errorCount = 0;
		int totalCount = 0;

		System.out.println("[INFO] Testing the network:");
		for (MLDataPair pair : trainingSet) {
			MLData output = network.compute(pair.getInput());
			System.out.println(pair.getInput().getData(0) + "," + pair.getInput().getData(1) + ","
					+ pair.getInput().getData(2) + ": Y=" + (int) Math.round(output.getData(0)) + ", Yd="
					+ (int) pair.getIdeal().getData(0));

			if ((int) Math.round(output.getData(0)) != (int) pair.getIdeal().getData(0)) {
				errorCount++;
			}
			totalCount++;
		}
		System.out.println("Error rate: " + (errorCount / totalCount) * 100);
	}

	public static NeuralNetwork getInstance() {
		return nn;
	}
}
