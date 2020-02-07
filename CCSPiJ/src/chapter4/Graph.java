// Graph.java
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// V is the type of the vertices in the Graph
// E is the type of the edges
public abstract class Graph<V, E extends Edge> {

	private ArrayList<V> vertices;
	protected ArrayList<ArrayList<E>> edges;

	public Graph() {
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}

	public Graph(List<V> vertices) {
		this.vertices = new ArrayList<>(vertices);
		edges = new ArrayList<>();
		for (V vertice : vertices) {
			edges.add(new ArrayList<>());
		}
	}

	// Number of vertices
	public int getVertexCount() {
		return vertices.size();
	}

	// Number of edges
	public int getEdgeCount() {
		return edges.stream().mapToInt(ArrayList::size).sum();
	}

	// Add a vertex to the graph and return its index
	public int addVertex(V vertex) {
		vertices.add(vertex);
		edges.add(new ArrayList<>());
		return getVertexCount() - 1;
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
	public List<E> edgesOf(int index) {
		return edges.get(index);
	}

	// Look up the index of a vertex and return its edges (convenience method)
	public List<E> edgesOf(V vertex) {
		return edgesOf(indexOf(vertex));
	}

	// Make it easy to pretty-print a Graph
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getVertexCount(); i++) {
			sb.append(vertexAt(i).toString());
			sb.append(" -> ");
			sb.append(Arrays.toString(neighborsOf(i).toArray()));
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
}
