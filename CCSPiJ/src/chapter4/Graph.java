package chapter4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import chapter2.GenericSearch;
import chapter2.GenericSearch.Node;

// V is the type of the vertices in the Graph
public class Graph<V> {

	private ArrayList<V> vertices;
	private ArrayList<ArrayList<Edge>> edges;

	public Graph() {
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}

	public Graph(List<V> vertices) {
		this.vertices = new ArrayList<>(vertices);
		edges = new ArrayList<>();
		for (V element : this.vertices) {
			edges.add(new ArrayList<Edge>());
		}
	}

	// Number of vertices
	public int getVertexCount() {
		return vertices.size();
	}

	// Number of edges
	public int getEdgeCount() {
		return edges.stream().mapToInt(al -> al.size()).sum();
	}

	// Add a vertex to the graph and return its index
	public int addVertex(V vertex) {
		vertices.add(vertex);
		edges.add(new ArrayList<>());
		return getVertexCount() - 1;
	}

	// This is an undirected graph, so we always add
	// edges in both directions
	public void addEdge(Edge edge) {
		edges.get(edge.u).add(edge);
		edges.get(edge.v).add(edge.reversed());
	}

	// Add an edge using vertex indices (convenience method)
	public void addEdge(int u, int v) {
		addEdge(new Edge(u, v));
	}

	// Add an edge by looking up vertex indices (convenience method)
	public void addEdge(V first, V second) {
		addEdge(new Edge(indexOf(first), indexOf(second)));
	}

	// Find the vertex at a specific index
	public V vertexAt(int index) {
		return vertices.get(index);
	}

	// Find the index of a vertex in the graph
	public int indexOf(V vertex) {
		return vertices.indexOf(vertex);
	}

	// Find the vertices that a vertex at some index is connected to
	public List<V> neighborsOf(int index) {
		return edges.get(index).stream()
				.map(edge -> vertexAt(edge.v))
				.collect(Collectors.toList());
	}

	// Look up a vertice's index and find its neighbors (convenience method)
	public List<V> neighborsOf(V vertex) {
		return neighborsOf(indexOf(vertex));
	}

	// Return all of the edges associated with a vertex at some index
	public List<Edge> edgesOf(int index) {
		return edges.get(index);
	}

	// Look up the index of a vertex and return its edges (convenience method)
	public List<Edge> edgesOf(V vertex) {
		return edgesOf(indexOf(vertex));
	}

	// Make it easy to pretty-print a Graph
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < vertices.size(); i++) {
			sb.append(vertexAt(i).toString());
			sb.append(" -> ");
			sb.append(Arrays.toString(neighborsOf(i).toArray()));
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	// Test basic Graph construction
	public static void main(String[] args) {
		// Represents the 15 largest MSAs in the United States
		Graph<String> cityGraph = new Graph<>(
				List.of("Seattle", "San Francisco", "Los Angeles", "Riverside", "Phoenix", "Chicago", "Boston",
						"New York", "Atlanta", "Miami", "Dallas", "Houston", "Detroit", "Philadelphia", "Washington"));

		cityGraph.addEdge("Seattle", "Chicago");
		cityGraph.addEdge("Seattle", "San Francisco");
		cityGraph.addEdge("San Francisco", "Riverside");
		cityGraph.addEdge("San Francisco", "Los Angeles");
		cityGraph.addEdge("Los Angeles", "Riverside");
		cityGraph.addEdge("Los Angeles", "Phoenix");
		cityGraph.addEdge("Riverside", "Phoenix");
		cityGraph.addEdge("Riverside", "Chicago");
		cityGraph.addEdge("Phoenix", "Dallas");
		cityGraph.addEdge("Phoenix", "Houston");
		cityGraph.addEdge("Dallas", "Chicago");
		cityGraph.addEdge("Dallas", "Atlanta");
		cityGraph.addEdge("Dallas", "Houston");
		cityGraph.addEdge("Houston", "Atlanta");
		cityGraph.addEdge("Houston", "Miami");
		cityGraph.addEdge("Atlanta", "Chicago");
		cityGraph.addEdge("Atlanta", "Washington");
		cityGraph.addEdge("Atlanta", "Miami");
		cityGraph.addEdge("Miami", "Washington");
		cityGraph.addEdge("Chicago", "Detroit");
		cityGraph.addEdge("Detroit", "Boston");
		cityGraph.addEdge("Detroit", "Washington");
		cityGraph.addEdge("Detroit", "New York");
		cityGraph.addEdge("Boston", "New York");
		cityGraph.addEdge("New York", "Philadelphia");
		cityGraph.addEdge("Philadelphia", "Washington");
		System.out.println(cityGraph.toString());

		Node<String> bfsResult = GenericSearch.bfs("Boston",
				v -> v.equals("Miami"),
				cityGraph::neighborsOf);
		if (bfsResult == null) {
			System.out.println("No solution found using breadth-first search!");
		} else {
			List<String> path = GenericSearch.nodeToPath(bfsResult);
			System.out.println("Path from Boston to Miami:");
			System.out.println(path);
		}
	}

}
