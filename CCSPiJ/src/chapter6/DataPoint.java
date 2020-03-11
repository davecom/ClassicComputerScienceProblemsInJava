// DataPoint.java
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

import java.util.ArrayList;
import java.util.List;

public class DataPoint {
	public final int numDimensions;
	private List<Double> originals;
	public List<Double> dimensions;

	public DataPoint(List<Double> initials) {
		originals = initials;
		dimensions = new ArrayList<>(initials);
		numDimensions = dimensions.size();
	}

	public double distance(DataPoint other) {
		double differences = 0.0;
		for (int i = 0; i < numDimensions; i++) {
			double difference = dimensions.get(i) - other.dimensions.get(i);
			differences += Math.pow(difference, 2);
		}
		return Math.sqrt(differences);
	}

	@Override
	public String toString() {
		return originals.toString();
	}

}
