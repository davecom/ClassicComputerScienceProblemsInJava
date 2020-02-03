package chapter4;

public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {
	public final double weight;

	public WeightedEdge(int u, int v, double weight) {
		super(u, v);
		this.weight = weight;
	}

	@Override
	public WeightedEdge reversed() {
		return new WeightedEdge(v, u, weight);
	}

	// so that we can order edges by weight to find the minimum weight edge
	@Override
	public int compareTo(WeightedEdge other) {
		Double mine = weight;
		Double theirs = other.weight;
		return mine.compareTo(theirs);
	}

	@Override
	public String toString() {
		return u + " " + weight + "> " + v;
	}
}
