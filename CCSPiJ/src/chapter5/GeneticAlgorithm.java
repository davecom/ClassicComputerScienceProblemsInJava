// GeneticAlgorithm.java
// From Classic Computer Science Problems in Java Chapter 5
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

package chapter5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm<C extends Chromosome<C>> {

	public enum SelectionType {
		ROULETTE, TOURNAMENT;
	}

	private ArrayList<C> population;
	private double mutationChance;
	private double crossoverChance;
	private SelectionType selectionType;
	private Random random;

	public GeneticAlgorithm(List<C> initialPopulation,
			double mutationChance, double crossoverChance, SelectionType selectionType) {
		this.population = new ArrayList<>(initialPopulation);
		this.mutationChance = mutationChance;
		this.crossoverChance = crossoverChance;
		this.selectionType = selectionType;
		this.random = new Random();
	}

	// Use the probability distribution wheel to pick numPicks individuals
	private List<C> pickRoulette(double[] wheel, int numPicks) {
		List<C> picks = new ArrayList<>();
		for (int i = 0; i < numPicks; i++) {
			double pick = random.nextDouble();
			for (int j = 0; j < wheel.length; j++) {
				pick -= wheel[j];
				if (pick <= 0) { // we had one that took us over, leads to a pick
					picks.add(population.get(j));
					break;
				}
			}
		}
		return picks;
	}

	// Pick a certain number of individuals via a tournament
	private List<C> pickTournament(int numParticipants, int numPicks) {
		// Find numParticipants random participants to be in the tournament
		Collections.shuffle(population);
		List<C> tournament = population.subList(0, numParticipants);
		// Find the numPicks highest fitnesses in the tournament
		Collections.sort(tournament, Collections.reverseOrder());
		return tournament.subList(0, numPicks);
	}

	// Replace the population with a new generation of individuals
	private void reproduceAndReplace() {
		ArrayList<C> nextPopulation = new ArrayList<>();
		// keep going until we've filled the new generation
		while (nextPopulation.size() < population.size()) {
			// pick the two parents
			List<C> parents;
			if (selectionType == SelectionType.ROULETTE) {
				// create the probability distribution wheel
				double totalFitness = population.stream()
						.mapToDouble(C::fitness).sum();
				double[] wheel = population.stream()
						.mapToDouble(C -> C.fitness()
								/ totalFitness)
						.toArray();
				parents = pickRoulette(wheel, 2);
			} else { // tournament
				parents = pickTournament(population.size() / 2, 2);
			}
			// potentially crossover the 2 parents
			if (random.nextDouble() < crossoverChance) {
				C parent1 = parents.get(0);
				C parent2 = parents.get(1);
				nextPopulation.addAll(parent1.crossover(parent2));
			} else { // just add the two parents
				nextPopulation.addAll(parents);
			}
		}
		// if we have an odd number, we'll have 1 exra, so we remove it
		if (nextPopulation.size() > population.size()) {
			nextPopulation.remove(0);
		}
		// replace the reference/generation
		population = nextPopulation;
	}

	// With mutationChance probability, mutate each individual
	private void mutate() {
		for (C individual : population) {
			if (random.nextDouble() < mutationChance) {
				individual.mutate();
			}
		}
	}

	// Run the genetic algorithm for maxGenerations iterations
	// and return the best individual found
	public C run(int maxGenerations, double threshold) {
		C best = Collections.max(population).copy();
		for (int generation = 0; generation < maxGenerations; generation++) {
			// early exit if we beat threshold
			if (best.fitness() >= threshold) {
				return best;
			}
			// Debug printout
			System.out.println("Generation " + generation +
					" Best " + best.fitness() +
					" Avg " + population.stream()
							.mapToDouble(C::fitness).average().orElse(0.0));
			reproduceAndReplace();
			mutate();
			C highest = Collections.max(population);
			if (highest.fitness() > best.fitness()) {
				best = highest.copy();
			}
		}
		return best;
	}
}
