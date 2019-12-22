// GenericSearch.java
// From Classic Computer Science Problems in Java Chapter 2
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

package chapter2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class GenericSearch {

	public static <T extends Comparable<T>> boolean linearContains(List<T> list, T key) {
		for (T item : list) {
			if (item.compareTo(key) == 0) {
				return true; // found a match
			}
		}
		return false;
	}

	// assumes *list* is already sorted
	public static <T extends Comparable<T>> boolean binaryContains(List<T> list, T key) {
		int low = 0;
		int high = list.size() - 1;
		while (low <= high) { // while there is still a search space
			int middle = (low + high) / 2;
			int comparison = list.get(middle).compareTo(key);
			if (comparison < 0) { // middle codon is less than key
				low = middle + 1;
			} else if (comparison > 0) { // middle codon is greater than key
				high = middle - 1;
			} else { // middle codon is equal to key
				return true;
			}
		}
		return false;
	}

	public static class Node<T> implements Comparable<Node<T>> {
		final T state;
		Node<T> parent;
		double cost;
		double heuristic;

		// for dfs and bfs we won't use cost and heuristic
		Node(T state, Node<T> parent) {
			this.state = state;
			this.parent = parent;
		}

		// for astar we will use cost and heuristic
		Node(T state, Node<T> parent, double cost, double heuristic) {
			this.state = state;
			this.parent = parent;
			this.cost = cost;
			this.heuristic = heuristic;
		}

		@Override
		public int compareTo(Node<T> other) {
			Double mine = cost + heuristic;
			Double theirs = other.cost + other.heuristic;
			return mine.compareTo(theirs);
		}
	}

	public static <T> Node<T> dfs(T initial, Predicate<T> goalTest,
			Function<T, List<T>> successors) {
		// frontier is where we've yet to go
		Stack<Node<T>> frontier = new Stack<>();
		frontier.push(new Node<>(initial, null));
		// explored is where we've been
		Set<T> explored = new HashSet<>();
		explored.add(initial);

		// keep going while there is more to explore
		while (!frontier.isEmpty()) {
			Node<T> currentNode = frontier.pop();
			T currentState = currentNode.state;
			// if we found the goal, we're done
			if (goalTest.test(currentState)) {
				return currentNode;
			}
			// check where we can go next and haven't explored
			for (T child : successors.apply(currentState)) {
				if (explored.contains(child)) {
					continue; // skip children we already explored
				}
				explored.add(child);
				frontier.push(new Node<>(child, currentNode));
			}
		}
		return null; // went through everything and never found goal
	}

	public static <T> List<T> nodeToPath(Node<T> node) {
		ArrayList<T> path = new ArrayList<>();
		path.add(node.state);
		// work backwards from end to front
		while (node.parent != null) {
			node = node.parent;
			path.add(0, node.state); // add to front
		}
		return path;
	}

	public static <T> Node<T> bfs(T initial, Predicate<T> goalTest,
			Function<T, List<T>> successors) {
		// frontier is where we've yet to go
		Queue<Node<T>> frontier = new LinkedList<>();
		frontier.offer(new Node<>(initial, null));
		// explored is where we've been
		Set<T> explored = new HashSet<>();
		explored.add(initial);

		// keep going while there is more to explore
		while (!frontier.isEmpty()) {
			Node<T> currentNode = frontier.poll();
			T currentState = currentNode.state;
			// if we found the goal, we're done
			if (goalTest.test(currentState)) {
				return currentNode;
			}
			// check where we can go next and haven't explored
			for (T child : successors.apply(currentState)) {
				if (explored.contains(child)) {
					continue; // skip children we already explored
				}
				explored.add(child);
				frontier.offer(new Node<>(child, currentNode));
			}
		}
		return null; // went through everything and never found goal
	}

	public static <T> Node<T> astar(T initial, Predicate<T> goalTest,
			Function<T, List<T>> successors, ToDoubleFunction<T> heuristic) {
		// frontier is where we've yet to go
		PriorityQueue<Node<T>> frontier = new PriorityQueue<>();
		frontier.offer(new Node<>(initial, null, 0.0, heuristic.applyAsDouble(initial)));
		// explored is where we've been
		HashMap<T, Double> explored = new HashMap<>();
		explored.put(initial, 0.0);
		// keep going while there is more to explore
		while (!frontier.isEmpty()) {
			Node<T> currentNode = frontier.poll();
			T currentState = currentNode.state;
			// if we found the goal, we're done
			if (goalTest.test(currentState)) {
				return currentNode;
			}
			// check where we can go next and haven't explored
			for (T child : successors.apply(currentState)) {
				// 1 here assumes a grid, need a cost function for more sophisticated apps
				double newCost = currentNode.cost + 1;
				if (!explored.containsKey(child) || explored.get(child) > newCost) {
					explored.put(child, newCost);
					frontier.offer(new Node<>(child, currentNode, newCost, heuristic.applyAsDouble(child)));
				}
			}
		}

		return null; // went through everything and never found goal
	}

	public static void main(String[] args) {
		System.out.println(GenericSearch.linearContains(List.of(1, 5, 15, 15, 15, 15, 20), 5)); // true
		System.out.println(GenericSearch.binaryContains(List.of("a", "d", "e", "f", "z"), "f")); // true
		System.out.println(GenericSearch.binaryContains(List.of("john", "mark", "ronald", "sarah"), "sheila")); // false
	}

}
