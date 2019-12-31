// WordSearchConstraint.java
// From Classic Computer Science Problems in Java Chapter 3
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

package chapter3;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import chapter3.WordGrid.GridLocation;

public class WordSearchConstraint extends Constraint<String, List<GridLocation>> {

	public WordSearchConstraint(List<String> words) {
		super(words);
	}

	@Override
	public boolean satisfied(Map<String, List<GridLocation>> assignment) {
		// combine all GridLocations into one giant List
		List<GridLocation> allLocations = assignment.values().stream()
				.flatMap(Collection::stream).collect(Collectors.toList());
		// a set will eliminate duplicates using equals()
		Set<GridLocation> allLocationsSet = new HashSet<>(allLocations);
		// if there are any duplicate grid locations then there is an overlap
		return allLocations.size() == allLocationsSet.size();
	}

	public static void main(String[] args) {
		WordGrid grid = new WordGrid(9, 9);
		List<String> words = List.of("MATTHEW", "JOE", "MARY", "SARAH", "SALLY");
		// generate domains for all words
		Map<String, List<List<GridLocation>>> domains = new HashMap<>();
		for (String word : words) {
			domains.put(word, grid.generateDomain(word));
		}
		CSP<String, List<GridLocation>> csp = new CSP<>(words, domains);
		csp.addConstraint(new WordSearchConstraint(words));
		Map<String, List<GridLocation>> solution = csp.backtrackingSearch();
		if (solution == null) {
			System.out.println("No solution found!");
		} else {
			Random random = new Random();
			for (Entry<String, List<GridLocation>> item : solution.entrySet()) {
				String word = item.getKey();
				List<GridLocation> locations = item.getValue();
				// random reverse half the time
				if (random.nextBoolean()) {
					Collections.reverse(locations);
				}
				grid.mark(word, locations);
			}
			System.out.println(grid);
		}
	}

}
