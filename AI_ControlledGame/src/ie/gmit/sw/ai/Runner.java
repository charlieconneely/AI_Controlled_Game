package ie.gmit.sw.ai;

import java.io.IOException;

import ie.gmit.sw.ai.fuzzy.ScoreCalculator;
import ie.gmit.sw.ai.nn.NeuralNetwork;
import javafx.application.Application;

public class Runner {
	public static void main(String[] args) throws IOException {
		/*
		 * PLEASE READ CAREFULLY
		 * ---------------------
		 * If you need to load FCL functions to the application or
		 * train, configure and load a neural network, then it is 
		 * best to do all of this before loading the UI. Launching
		 * a UI in any language or framework and then starting a 
		 * long running task in the same thread is guaranteed to
		 * freeze the screen and crash the programme.
		 * 
		 * NB: you can assume that the JavaFX 15 API is already
		 * available and configured on the MODULE-PATH (NOT THE 
		 * CLASSPATH). 
		 */
		
		// prepare neural network + fuzzy logic 
		NeuralNetwork nn = NeuralNetwork.getInstance();
		ScoreCalculator sc = ScoreCalculator.getInstance();
		nn.trainNeuralNetwork();
		sc.loadFuzzyLogic();
				
		/*
		 * Launch the JavaFX UI only when all the long-running AI 
		 * configuration tasks have been completed. Use the arrow 
		 * keys to move the player character and the 'Z' key to 
		 * toggle the zoom in / out.
		 */
		Application.launch(GameWindow.class, args);
	}
}