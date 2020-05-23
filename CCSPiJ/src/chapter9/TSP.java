// TSP.java
// From Classic Computer Science Problems in Java Chapter 9
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

package chapter9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TSP {
	private final Map<String, Map<String, Integer>> distances;

	public TSP(Map<String, Map<String, Integer>> distances) {
		this.distances = distances;
	}

	public static <T> void swap(T[] array, int first, int second) {
		T temp = array[first];
		array[first] = array[second];
		array[second] = temp;
	}

	private static <T> void allPermutationsHelper(T[] permutation, List<T[]> permutations, int n) {
		// Base case - we found a new permutation, add it and return
		if (n <= 0) {
			permutations.add(permutation);
			return;
		}
		// Recursive case - find more permutations by doing swaps
		T[] tempPermutation = Arrays.copyOf(permutation, permutation.length);
		for (int i = 0; i < n; i++) {
			swap(tempPermutation, i, n - 1); // move element at i to the end
			// move everything else around, holding the end constant
			allPermutationsHelper(tempPermutation, permutations, n - 1);
			swap(tempPermutation, i, n - 1); // backtrack
		}
	}

	private static <T> List<T[]> permutations(T[] original) {
		List<T[]> permutations = new ArrayList<>();
		allPermutationsHelper(original, permutations, original.length);
		return permutations;
	}

	public int pathDistance(String[] path) {
		String last = path[0];
		int distance = 0;
		for (String next : Arrays.copyOfRange(path, 1, path.length)) {
			distance += distances.get(last).get(next);
			// distance to get back from last city to first city
			last = next;
		}
		return distance;
	}

	public String[] findShortestPath() {
		String[] cities = distances.keySet().toArray(String[]::new);
		List<String[]> paths = permutations(cities);
		String[] shortestPath = null;
		int minDistance = Integer.MAX_VALUE; // arbitrarily high
		for (String[] path : paths) {
			int distance = pathDistance(path);
			// distance from last to first must be added
			distance += distances.get(path[path.length - 1]).get(path[0]);
			if (distance < minDistance) {
				minDistance = distance;
				shortestPath = path;
			}
		}
		// add first city on to end and return
		shortestPath = Arrays.copyOf(shortestPath, shortestPath.length + 1);
		shortestPath[shortestPath.length - 1] = shortestPath[0];
		return shortestPath;
	}

	public static void main(String[] args) {
		Map<String, Map<String, Integer>> vtDistances = Map.of(
				"Rutland", Map.of(
						"Burlington", 67,
						"White River Junction", 46,
						"Bennington", 55,
						"Brattleboro", 75),
				"Burlington", Map.of(
						"Rutland", 67,
						"White River Junction", 91,
						"Bennington", 122,
						"Brattleboro", 153),
				"White River Junction", Map.of(
						"Rutland", 46,
						"Burlington", 91,
						"Bennington", 98,
						"Brattleboro", 65),
				"Bennington", Map.of(
						"Rutland", 55,
						"Burlington", 122,
						"White River Junction", 98,
						"Brattleboro", 40),
				"Brattleboro", Map.of(
						"Rutland", 75,
						"Burlington", 153,
						"White River Junction", 65,
						"Bennington", 40));
		TSP tsp = new TSP(vtDistances);
		String[] shortestPath = tsp.findShortestPath();
		int distance = tsp.pathDistance(shortestPath);
		System.out.println("The shortest path is " + Arrays.toString(shortestPath) + " in " +
				distance + " miles.");
	}
}
