// WineTest.java
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

public class WineTest {
	private List<double[]> wineParameters = new ArrayList<>();
	private List<double[]> wineClassifications = new ArrayList<>();
	private List<Integer> wineSpecies = new ArrayList<>();

	public WineTest() {
		// make sure wine.csv is in the right place in your path
		List<String[]> wineDataset = Util.loadCSV("/chapter7/data/wine.csv");
		// get our lines of data in random order
		Collections.shuffle(wineDataset);
		for (String[] wine : wineDataset) {
			// last thirteen items are parameters (doubles)
			double[] parameters = Arrays.stream(wine)
					.skip(1)
					.mapToDouble(Double::parseDouble)
					.toArray();
			wineParameters.add(parameters);
			// first item is species
			int species = Integer.parseInt(wine[0]);
			switch (species) {
			case 1:
				wineClassifications.add(new double[] { 1.0, 0.0, 0.0 });
				break;
			case 2:
				wineClassifications.add(new double[] { 0.0, 1.0, 0.0 });
				break;
			default:
				wineClassifications.add(new double[] { 0.0, 0.0, 1.0 });
				;
				break;
			}
			wineSpecies.add(species);
		}
		Util.normalizeByFeatureScaling(wineParameters);
	}

	public Integer wineInterpretOutput(double[] output) {
		double max = Util.max(output);
		if (max == output[0]) {
			return 1;
		} else if (max == output[1]) {
			return 2;
		} else {
			return 3;
		}
	}

	public Network<Integer>.Results classify() {
		// 13, 7, 3 layer structure; 0.9 learning rate; sigmoid activation function
		Network<Integer> wineNetwork = new Network<>(new int[] { 13, 7, 3 }, 0.9, Util::sigmoid,
				Util::derivativeSigmoid);
		// train over the first 150 wines in the data set 50 times
		List<double[]> wineTrainers = wineParameters.subList(0, 150);
		List<double[]> wineTrainersCorrects = wineClassifications.subList(0, 150);
		int trainingIterations = 10;
		for (int i = 0; i < trainingIterations; i++) {
			wineNetwork.train(wineTrainers, wineTrainersCorrects);
		}
		// test over the last 28 of the wines in the data set
		List<double[]> wineTesters = wineParameters.subList(150, 178);
		List<Integer> wineTestersCorrects = wineSpecies.subList(150, 178);
		return wineNetwork.validate(wineTesters, wineTestersCorrects, this::wineInterpretOutput);
	}

	public static void main(String[] args) {
		WineTest wineTest = new WineTest();
		Network<Integer>.Results results = wineTest.classify();
		System.out.println(results.correct + " correct of " + results.trials + " = " +
				results.percentage * 100 + "%");
	}

}
