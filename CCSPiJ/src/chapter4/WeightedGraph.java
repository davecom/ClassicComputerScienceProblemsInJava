package chapter4;

import java.util.Arrays;
import java.util.List;

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
	}

}
