// Statistics.java
// From Classic Computer Science Problems in Java Chapter 6
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

package chapter6;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public final class Statistics {
	private List<Double> list;
	private DoubleSummaryStatistics dss;

	public Statistics(List<Double> list) {
		this.list = list;
		dss = list.stream().collect(Collectors.summarizingDouble(d -> d));
	}

	public double sum() {
		return dss.getSum();
	}

	// Find the average (mean)
	public double mean() {
		return dss.getAverage();
	}

	// Find the variance sum((Xi - mean)^2) / N
	public double variance() {
		double mean = mean();
		return list.stream().mapToDouble(x -> Math.pow((x - mean), 2))
				.average().getAsDouble();
	}

	// Find the standard deviation sqrt(variance)
	public double std() {
		return Math.sqrt(variance());
	}

	// Convert elements to respective z-scores (formula z-score =
	// (x - mean) / std)
	public List<Double> zscored() {
		double mean = mean();
		double std = std();
		return list.stream()
				.map(x -> std != 0 ? ((x - mean) / std) : 0.0)
				.collect(Collectors.toList());
	}

	public double max() {
		return dss.getMax();
	}

	public double min() {
		return dss.getMin();
	}
}
