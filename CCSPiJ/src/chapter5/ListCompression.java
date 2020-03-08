// ListCompression.java
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPOutputStream;

public class ListCompression extends Chromosome<ListCompression> {

	private List<String> myList;
	private Random random;

	public ListCompression(List<String> list) {
		myList = new ArrayList<>(list);
		random = new Random();
	}

	public static ListCompression randomInstance() {
		ArrayList<String> tempList = new ArrayList<>(List.of("Michael", "Sarah", "Joshua", "Narine", "David", "Sajid",
				"Melanie", "Daniel", "Wei", "Dean", "Brian", "Murat", "Lisa"));
		Collections.shuffle(tempList);
		return new ListCompression(tempList);
	}

	private int bytesCompressed() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GZIPOutputStream gos = new GZIPOutputStream(baos);
			ObjectOutputStream oos = new ObjectOutputStream(gos);
			oos.writeObject(myList);
			oos.close();
			return baos.size();
		} catch (IOException ioe) {
			System.out.println("Could not compress list!");
			ioe.printStackTrace();
			return 0;
		}

	}

	@Override
	public double fitness() {
		return 1.0 / bytesCompressed();
	}

	@Override
	public List<ListCompression> crossover(ListCompression other) {
		ListCompression child1 = new ListCompression(new ArrayList<>(myList));
		ListCompression child2 = new ListCompression(new ArrayList<>(myList));
		int idx1 = random.nextInt(myList.size());
		int idx2 = random.nextInt(other.myList.size());
		String s1 = myList.get(idx1);
		String s2 = other.myList.get(idx2);
		int idx3 = myList.indexOf(s2);
		int idx4 = other.myList.indexOf(s1);
		Collections.swap(child1.myList, idx1, idx3);
		Collections.swap(child2.myList, idx2, idx4);
		return List.of(child1, child2);
	}

	@Override
	public void mutate() {
		int idx1 = random.nextInt(myList.size());
		int idx2 = random.nextInt(myList.size());
		Collections.swap(myList, idx1, idx2);
	}

	@Override
	public ListCompression copy() {
		return new ListCompression(new ArrayList<>(myList));
	}

	@Override
	public String toString() {
		return "Order: " + myList + " Bytes: " + bytesCompressed();
	}

	public static void main(String[] args) {
		ListCompression originalOrder = new ListCompression(
				List.of("Michael", "Sarah", "Joshua", "Narine", "David", "Sajid",
						"Melanie", "Daniel", "Wei", "Dean", "Brian", "Murat", "Lisa"));
		System.out.println(originalOrder);
		ArrayList<ListCompression> initialPopulation = new ArrayList<>();
		final int POPULATION_SIZE = 100;
		for (int i = 0; i < POPULATION_SIZE; i++) {
			initialPopulation.add(ListCompression.randomInstance());
		}
		GeneticAlgorithm<ListCompression> ga = new GeneticAlgorithm<>(
				initialPopulation,
				0.2, 0.7, GeneticAlgorithm.SelectionType.TOURNAMENT);
		ListCompression result = ga.run(100, 1.0);
		System.out.println(result);
	}
}
