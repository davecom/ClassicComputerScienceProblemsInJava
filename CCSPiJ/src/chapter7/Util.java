// Util.java
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class Util {

	public static double dotProduct(double[] xs, double[] ys) {
		double sum = 0.0;
		for (int i = 0; i < xs.length; i++) {
			sum += xs[i] * ys[i];
		}
		return sum;
	}

	// the classic sigmoid activation function
	public static double sigmoid(double x) {
		return 1.0 / (1.0 + Math.exp(-x));
	}

	public static double derivativeSigmoid(double x) {
		double sig = sigmoid(x);
		return sig * (1.0 - sig);
	}

	// Assume all rows are of equal length
	// and feature scale each column to be in the range 0 - 1
	public static void normalizeByFeatureScaling(List<double[]> dataset) {
		for (int colNum = 0; colNum < dataset.get(0).length; colNum++) {
			List<Double> column = new ArrayList<>();
			for (double[] row : dataset) {
				column.add(row[colNum]);
			}
			double maximum = Collections.max(column);
			double minimum = Collections.min(column);
			double difference = maximum - minimum;
			for (double[] row : dataset) {
				row[colNum] = (row[colNum] - minimum) / difference;
			}
		}
	}

	// Load a CSV file into a List of String arrays
	public static List<String[]> loadCSV(String filename) {
		try (InputStream inputStream = Util.class.getResourceAsStream(filename)) {
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			return bufferedReader.lines().map(line -> line.split(","))
					.collect(Collectors.toList());
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	// Find the maximum in an array of doubles
	public static double max(double[] numbers) {
		return Arrays.stream(numbers)
				.max()
				.orElse(Double.MIN_VALUE);
	}

}
