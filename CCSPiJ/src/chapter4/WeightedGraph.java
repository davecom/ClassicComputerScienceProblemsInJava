// WeightedGraph.java
// From Classic Computer Science Problems in Java Chapter 4
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

package chapter4;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.IntConsumer;

public class WeightedGraph<V> extends Graph<V, WeightedEdge> {

	public WeightedGraph(List<V> vertices) {
		super(vertices);
	}

	// This is an undirected graph, so we always add
	// edges in both directions
	public void addEdge(WeightedEdge edge) {
		edges.get(edge.u).add(edge);
		edges.get(edge.v).add(edge.reversed());
	}

	public void addEdge(int u, int v, float weight) {
		addEdge(new WeightedEdge(u, v, weight));
	}

	public void addEdge(V first, V second, float weight) {
		addEdge(indexOf(first), indexOf(second), weight);
	}

	// Make it easy to pretty-print a Graph
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getVertexCount(); i++) {
			sb.append(vertexAt(i).toString());
			sb.append(" -> ");
			sb.append(Arrays.toString(edgesOf(i).stream()
					.map(we -> "(" + vertexAt(we.v) + ", " + we.weight + ")").toArray()));
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	public static double totalWeight(List<WeightedEdge> path) {
		return path.stream().mapToDouble(we -> we.weight).sum();
	}

	// Find the minimum-spanning tree of this graph using Jarnik's algorithm
	// *start* is the vertex index to start the search at
	public List<WeightedEdge> mst(int start) {
		LinkedList<WeightedEdge> result = new LinkedList<>(); // final mst
		if (start < 0 || start > (getVertexCount() - 1)) {
			return result;
		}
		PriorityQueue<WeightedEdge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[getVertexCount()]; // where we've been

		// this is like a "visit" inner function
		IntConsumer visit = index -> {
			visited[index] = true; // mark as visited
			for (WeightedEdge edge : edgesOf(index)) {
				// add all edges coming from here to pq
				if (!visited[edge.v]) {
					pq.offer(edge);
				}
			}
		};

		visit.accept(start); // the first vertex is where everything begins
		while (!pq.isEmpty()) { // keep going while there are edges to process
			WeightedEdge edge = pq.poll();
			if (visited[edge.v]) {
				continue; // don't ever revisit
			}
			// this is the current smallest, so add it to solution
			result.add(edge);
			visit.accept(edge.v); // visit where this connects
		}

		return result;
	}

	public void printWeightedPath(List<WeightedEdge> wp) {
		for (WeightedEdge edge : wp) {
			System.out.println(vertexAt(edge.u) + " " + edge.weight + "> " + vertexAt(edge.v));
		}
		System.out.println("Total Weight: " + totalWeight(wp));
	}

	public static final class DijkstraNode implements Comparable<DijkstraNode> {
		public final int vertex;
		public final double distance;

		public DijkstraNode(int vertex, double distance) {
			this.vertex = vertex;
			this.distance = distance;
		}

		@Override
		public int compareTo(DijkstraNode other) {
			Double mine = distance;
			Double theirs = other.distance;
			return mine.compareTo(theirs);
		}
	}

	public static final class DijkstraResult {
		public final double[] distances;
		public final Map<Integer, WeightedEdge> pathMap;

		public DijkstraResult(double[] distances, Map<Integer, WeightedEdge> pathMap) {
			this.distances = distances;
			this.pathMap = pathMap;
		}
	}

	public DijkstraResult dijkstra(V root) {
		int first = indexOf(root); // find starting index
		// distances are unknown at first
		double[] distances = new double[getVertexCount()];
		distances[first] = 0; // root's distance to root is 0
		boolean[] visited = new boolean[getVertexCount()]; // where we've been
		visited[first] = true;
		// how we got to each vertex
		HashMap<Integer, WeightedEdge> pathMap = new HashMap<>();
		PriorityQueue<DijkstraNode> pq = new PriorityQueue<>();
		pq.offer(new DijkstraNode(first, 0));

		while (!pq.isEmpty()) {
			int u = pq.poll().vertex; // explore the next closest vertex
			double distU = distances[u]; // should already have seen it
			// look at every edge/vertex from the vertex in question
			for (WeightedEdge we : edgesOf(u)) {
				// the old distance to this vertex
				double distV = distances[we.v];
				// the new distance to this vertex
				double pathWeight = we.weight + distU;
				// new vertex or found shorter path?
				if (!visited[we.v] || (distV > pathWeight)) {
					visited[we.v] = true;
					// update the distance to this vertex
					distances[we.v] = pathWeight;
					// update the edge on the shortest path to this vertex
					pathMap.put(we.v, we);
					// explore it in the future
					pq.offer(new DijkstraNode(we.v, pathWeight));
				}
			}
		}

		return new DijkstraResult(distances, pathMap);
	}

	// Helper function to get easier access to dijkstra results
	public Map<V, Double> distanceArrayToDistanceMap(double[] distances) {
		HashMap<V, Double> distanceMap = new HashMap<>();
		for (int i = 0; i < distances.length; i++) {
			distanceMap.put(vertexAt(i), distances[i]);
		}
		return distanceMap;
	}

	// Takes a map of edges to reach each node and return a list of
	// edges that goes from *start* to *end*
	public static List<WeightedEdge> pathMapToPath(int start, int end, Map<Integer, WeightedEdge> pathMap) {
		if (pathMap.size() == 0) {
			return List.of();
		}
		LinkedList<WeightedEdge> path = new LinkedList<>();
		WeightedEdge edge = pathMap.get(end);
		path.add(edge);
		while (edge.u != start) {
			edge = pathMap.get(edge.u);
			path.add(edge);
		}
		Collections.reverse(path);
		return path;
	}

	// Test basic Graph construction
	public static void main(String[] args) {
		// Represents the 15 largest MSAs in the United States
		WeightedGraph<String> cityGraph2 = new WeightedGraph<>(
				List.of("Seattle", "San Francisco", "Los Angeles", "Riverside", "Phoenix", "Chicago", "Boston",
						"New York", "Atlanta", "Miami", "Dallas", "Houston", "Detroit", "Philadelphia", "Washington"));

		cityGraph2.addEdge("Seattle", "Chicago", 1737);
		cityGraph2.addEdge("Seattle", "San Francisco", 678);
		cityGraph2.addEdge("San Francisco", "Riverside", 386);
		cityGraph2.addEdge("San Francisco", "Los Angeles", 348);
		cityGraph2.addEdge("Los Angeles", "Riverside", 50);
		cityGraph2.addEdge("Los Angeles", "Phoenix", 357);
		cityGraph2.addEdge("Riverside", "Phoenix", 307);
		cityGraph2.addEdge("Riverside", "Chicago", 1704);
		cityGraph2.addEdge("Phoenix", "Dallas", 887);
		cityGraph2.addEdge("Phoenix", "Houston", 1015);
		cityGraph2.addEdge("Dallas", "Chicago", 805);
		cityGraph2.addEdge("Dallas", "Atlanta", 721);
		cityGraph2.addEdge("Dallas", "Houston", 225);
		cityGraph2.addEdge("Houston", "Atlanta", 702);
		cityGraph2.addEdge("Houston", "Miami", 968);
		cityGraph2.addEdge("Atlanta", "Chicago", 588);
		cityGraph2.addEdge("Atlanta", "Washington", 543);
		cityGraph2.addEdge("Atlanta", "Miami", 604);
		cityGraph2.addEdge("Miami", "Washington", 923);
		cityGraph2.addEdge("Chicago", "Detroit", 238);
		cityGraph2.addEdge("Detroit", "Boston", 613);
		cityGraph2.addEdge("Detroit", "Washington", 396);
		cityGraph2.addEdge("Detroit", "New York", 482);
		cityGraph2.addEdge("Boston", "New York", 190);
		cityGraph2.addEdge("New York", "Philadelphia", 81);
		cityGraph2.addEdge("Philadelphia", "Washington", 123);

		System.out.println(cityGraph2);

		List<WeightedEdge> mst = cityGraph2.mst(0);
		cityGraph2.printWeightedPath(mst);

		System.out.println(); // spacing

		DijkstraResult dijkstraResult = cityGraph2.dijkstra("Los Angeles");
		Map<String, Double> nameDistance = cityGraph2.distanceArrayToDistanceMap(dijkstraResult.distances);
		System.out.println("Distances from Los Angeles:");
		nameDistance.forEach((name, distance) -> System.out.println(name + " : " + distance));

		System.out.println(); // spacing

		System.out.println("Shortest path from Los Angeles to Boston:");
		List<WeightedEdge> path = pathMapToPath(cityGraph2.indexOf("Los Angeles"), cityGraph2.indexOf("Boston"),
				dijkstraResult.pathMap);
		cityGraph2.printWeightedPath(path);
	}

}
