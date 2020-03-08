// SendMoreMoney2.java
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

public class SendMoreMoney2 extends Chromosome<SendMoreMoney2> {

	private List<Character> letters;
	private Random random;

	public SendMoreMoney2(List<Character> letters) {
		this.letters = letters;
		random = new Random();
	}

	public static SendMoreMoney2 randomInstance() {
		List<Character> letters = new ArrayList<>(
				List.of('S', 'E', 'N', 'D', 'M', 'O', 'R', 'Y', ' ', ' '));
		Collections.shuffle(letters);
		return new SendMoreMoney2(letters);
	}

	@Override
	public double fitness() {
		int s = letters.indexOf('S');
		int e = letters.indexOf('E');
		int n = letters.indexOf('N');
		int d = letters.indexOf('D');
		int m = letters.indexOf('M');
		int o = letters.indexOf('O');
		int r = letters.indexOf('R');
		int y = letters.indexOf('Y');
		int send = s * 1000 + e * 100 + n * 10 + d;
		int more = m * 1000 + o * 100 + r * 10 + e;
		int money = m * 10000 + o * 1000 + n * 100 + e * 10 + y;
		int difference = Math.abs(money - (send + more));
		return 1.0 / (difference + 1.0);
	}

	@Override
	public List<SendMoreMoney2> crossover(SendMoreMoney2 other) {
		SendMoreMoney2 child1 = new SendMoreMoney2(new ArrayList<>(letters));
		SendMoreMoney2 child2 = new SendMoreMoney2(new ArrayList<>(other.letters));
		int idx1 = random.nextInt(letters.size());
		int idx2 = random.nextInt(other.letters.size());
		Character l1 = letters.get(idx1);
		Character l2 = other.letters.get(idx2);
		int idx3 = letters.indexOf(l2);
		int idx4 = other.letters.indexOf(l1);
		Collections.swap(child1.letters, idx1, idx3);
		Collections.swap(child2.letters, idx2, idx4);
		return List.of(child1, child2);
	}

	@Override
	public void mutate() {
		int idx1 = random.nextInt(letters.size());
		int idx2 = random.nextInt(letters.size());
		Collections.swap(letters, idx1, idx2);
	}

	@Override
	public SendMoreMoney2 copy() {
		return new SendMoreMoney2(new ArrayList<>(letters));
	}

	@Override
	public String toString() {
		int s = letters.indexOf('S');
		int e = letters.indexOf('E');
		int n = letters.indexOf('N');
		int d = letters.indexOf('D');
		int m = letters.indexOf('M');
		int o = letters.indexOf('O');
		int r = letters.indexOf('R');
		int y = letters.indexOf('Y');
		int send = s * 1000 + e * 100 + n * 10 + d;
		int more = m * 1000 + o * 100 + r * 10 + e;
		int money = m * 10000 + o * 1000 + n * 100 + e * 10 + y;
		int difference = Math.abs(money - (send + more));
		return (send + " + " + more + " = " + money + " Difference: " + difference);
	}

	public static void main(String[] args) {
		ArrayList<SendMoreMoney2> initialPopulation = new ArrayList<>();
		final int POPULATION_SIZE = 1000;
		for (int i = 0; i < POPULATION_SIZE; i++) {
			initialPopulation.add(SendMoreMoney2.randomInstance());
		}
		GeneticAlgorithm<SendMoreMoney2> ga = new GeneticAlgorithm<>(
				initialPopulation,
				0.2, 0.7, GeneticAlgorithm.SelectionType.ROULETTE);
		SendMoreMoney2 result = ga.run(1000, 1.0);
		System.out.println(result);
	}

}
