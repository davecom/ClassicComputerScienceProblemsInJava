package chapter4;

import java.util.ArrayList;
import java.util.List;

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
		for (int i = 0; i < this.vertices.size(); i++) {
			edges.add(new ArrayList<Edge>());
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
