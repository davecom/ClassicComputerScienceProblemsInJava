package chapter7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IrisTest {
	private List<double[]> irisParameters = new ArrayList<>();
	private List<double[]> irisClassifications = new ArrayList<>();
	private List<String> irisSpecies = new ArrayList<>();

	public IrisTest() {
		// make sure iris.csv is in the right place in your path
		List<String[]> irisDataset = Util.loadCSV("/chapter7/data/iris.csv");
		// get our lines of data in random order
		Collections.shuffle(irisDataset);
		for (String[] iris : irisDataset) {
			// first four items are parameters (doubles)
			double[] parameters = new double[4];
			for (int i = 0; i < parameters.length; i++) {
				parameters[i] = Double.parseDouble(iris[i]);
			}
			irisParameters.add(parameters);
			// last item is species
			String species = iris[4];
			if (species.equals("Iris-setosa")) {
				irisClassifications.add(new double[] { 1.0, 0.0, 0.0 });
			} else if (species.equals("Iris-versicolor")) {
				irisClassifications.add(new double[] { 0.0, 1.0, 0.0 });
			} else { // Iris-virginica
				irisClassifications.add(new double[] { 0.0, 0.0, 1.0 });
			}
			irisSpecies.add(species);
		}
		Util.normalizeByFeatureScaling(irisParameters);
	}

	public String irisInterpretOutput(double[] output) {
		double max = Util.max(output);
		if (max == output[0]) {
			return "Iris-setosa";
		} else if (max == output[1]) {
			return "Iris-versicolor";
		} else {
			return "Iris-virginica";
		}
	}

	public Network<String>.Results classify() {
		// 4, 6, 3 layer structure; 0.3 learning rate; sigmoid activation function
		Network<String> irisNetwork = new Network<>(new int[] { 4, 6, 3 }, 0.3, Util::sigmoid, Util::derivativeSigmoid);
		// train over the first 140 irises in the data set 50 times
		List<double[]> irisTrainers = irisParameters.subList(0, 140);
		List<double[]> irisTrainersCorrects = irisClassifications.subList(0, 140);
		int trainingIterations = 50;
		for (int i = 0; i < trainingIterations; i++) {
			irisNetwork.train(irisTrainers, irisTrainersCorrects);
		}
		// test over the last 10 of the irises in the data set
		List<double[]> irisTesters = irisParameters.subList(140, 150);
		List<String> irisTestersCorrects = irisSpecies.subList(140, 150);
		return irisNetwork.validate(irisTesters, irisTestersCorrects, this::irisInterpretOutput);
	}

	public static void main(String[] args) {
		IrisTest irisTest = new IrisTest();
		Network<String>.Results results = irisTest.classify();
		System.out.println(results.correct + " correct of " + results.trials + " = " +
				results.percentage * 100 + "%");
	}

}
