// IrisTest.java
// From Classic Computer Science Problems in Java Chapter 7
// Copyright 2020 David Kopec
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package chapter7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IrisTest {
	public static final String IRIS_SETOSA = "Iris-setosa";
	public static final String IRIS_VERSICOLOR = "Iris-versicolor";
	public static final String IRIS_VIRGINICA = "Iris-virginica";

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
			double[] parameters = Arrays.stream(iris)
					.limit(4)
					.mapToDouble(Double::parseDouble)
					.toArray();
			irisParameters.add(parameters);
			// last item is species
			String species = iris[4];
			switch (species) {
			case IRIS_SETOSA:
				irisClassifications.add(new double[] { 1.0, 0.0, 0.0 });
				break;
			case IRIS_VERSICOLOR:
				irisClassifications.add(new double[] { 0.0, 1.0, 0.0 });
				break;
			default:
				irisClassifications.add(new double[] { 0.0, 0.0, 1.0 });
				break;
			}
			irisSpecies.add(species);
		}
		Util.normalizeByFeatureScaling(irisParameters);
	}

	public String irisInterpretOutput(double[] output) {
		double max = Util.max(output);
		if (max == output[0]) {
			return IRIS_SETOSA;
		} else if (max == output[1]) {
			return IRIS_VERSICOLOR;
		} else {
			return IRIS_VIRGINICA;
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
