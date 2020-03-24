// Layer.java
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
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.DoubleUnaryOperator;

public class Layer {
	public Optional<Layer> previousLayer;
	public List<Neuron> neurons;
	public double[] outputCache;

	public Layer(Optional<Layer> previousLayer, int numNeurons, double learningRate,
			DoubleUnaryOperator activationFunction, DoubleUnaryOperator derivativeActivationFunction) {
		this.previousLayer = previousLayer;
		neurons = new ArrayList<>();
		for (int i = 0; i < numNeurons; i++) {
			double[] randomWeights = null;
			if (previousLayer.isPresent()) {
				Random random = new Random();
				randomWeights = random.doubles(previousLayer.get().neurons.size()).toArray();
			}
			Neuron neuron = new Neuron(randomWeights, learningRate, activationFunction, derivativeActivationFunction);
			neurons.add(neuron);
		}
		outputCache = new double[numNeurons];
	}

	public double[] outputs(double[] inputs) {
		if (previousLayer.isPresent()) {
			outputCache = neurons.stream().mapToDouble(n -> n.output(inputs)).toArray();
		} else {
			outputCache = inputs;
		}
		return outputCache;
	}

	// should only be called on output layer
	public void calculateDeltasForOutputLayer(double[] expected) {
		for (int n = 0; n < neurons.size(); n++) {
			neurons.get(n).delta = neurons.get(n).derivativeActivationFunction.applyAsDouble(neurons.get(n).outputCache)
					* (expected[n] - outputCache[n]);
		}
	}

	// should not be called on output layer
	public void calculateDeltasForHiddenLayer(Layer nextLayer) {
		for (int i = 0; i < neurons.size(); i++) {
			final int index = i;
			double[] nextWeights = nextLayer.neurons.stream().mapToDouble(n -> n.weights[index]).toArray();
			double[] nextDeltas = nextLayer.neurons.stream().mapToDouble(n -> n.delta).toArray();
			double sumWeightsAndDeltas = Util.dotProduct(nextWeights, nextDeltas);
			neurons.get(i).delta = neurons.get(i).derivativeActivationFunction
					.applyAsDouble(neurons.get(i).outputCache) * sumWeightsAndDeltas;
		}
	}

}
