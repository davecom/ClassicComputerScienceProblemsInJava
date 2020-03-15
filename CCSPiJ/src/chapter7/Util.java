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

}
