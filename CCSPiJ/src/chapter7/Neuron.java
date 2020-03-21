// Neuron.java
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

import java.util.function.DoubleUnaryOperator;

public class Neuron {
	public double[] weights;
	public final double learningRate;
	public double outputCache;
	public double delta;
	public final DoubleUnaryOperator activationFunction;
	public final DoubleUnaryOperator derivativeActivationFunction;

	public Neuron(double[] weights, double learningRate, DoubleUnaryOperator activationFunction,
			DoubleUnaryOperator derivativeActivationFunction) {
		this.weights = weights;
		this.learningRate = learningRate;
		outputCache = 0.0;
		delta = 0.0;
		this.activationFunction = activationFunction;
		this.derivativeActivationFunction = derivativeActivationFunction;
	}

	public double output(double[] inputs) {
		outputCache = Util.dotProduct(inputs, weights);
		return activationFunction.applyAsDouble(outputCache);
	}

}
