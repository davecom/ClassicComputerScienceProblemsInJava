// MapColoringContraint.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MapColoringConstraint extends Constraint<String, String> {

	private static final String WESTERN = "Western Australia";
	private static final String NORTHERN = "Northern Territory";
	private static final String SOUTH = "South Australia";
	private static final String QUEENSLAND = "Queensland";
	private static final String NEW_SOUTH_WALES = "New South Wales";
	private static final String VICTORIA = "Victoria";
	private static final String TASMANIA =  "Tasmania";

	private String place1, place2;

	public MapColoringConstraint(String place1, String place2) {
		super(List.of(place1, place2));
		this.place1 = place1;
		this.place2 = place2;
	}

	@Override
	public boolean satisfied(Map<String, String> assignment) {
		// if either place is not in the assignment, then it is not
		// yet possible for their colors to be conflicting
		if (!assignment.containsKey(place1) || !assignment.containsKey(place2)) {
			return true;
		}
		// check the color assigned to place1 is not the same as the
		// color assigned to place2
		return !assignment.get(place1).equals(assignment.get(place2));
	}

	public static void main(String[] args) {
		List<String> variables = List.of(WESTERN, NORTHERN, SOUTH, QUEENSLAND,
				NEW_SOUTH_WALES, VICTORIA, TASMANIA);
		Map<String, List<String>> domains = new HashMap<>();
		for (String variable : variables) {
			domains.put(variable, List.of("red", "green", "blue"));
		}
		CSP<String, String> csp = new CSP<>(variables, domains);
		csp.addConstraint(new MapColoringConstraint(WESTERN, NORTHERN));
		csp.addConstraint(new MapColoringConstraint(WESTERN, SOUTH));
		csp.addConstraint(new MapColoringConstraint(SOUTH, NORTHERN));
		csp.addConstraint(new MapColoringConstraint(QUEENSLAND, NORTHERN));
		csp.addConstraint(new MapColoringConstraint(QUEENSLAND, SOUTH));
		csp.addConstraint(new MapColoringConstraint(QUEENSLAND, NEW_SOUTH_WALES));
		csp.addConstraint(new MapColoringConstraint(NEW_SOUTH_WALES, SOUTH));
		csp.addConstraint(new MapColoringConstraint(VICTORIA, SOUTH));
		csp.addConstraint(new MapColoringConstraint(VICTORIA, NEW_SOUTH_WALES));
		csp.addConstraint(new MapColoringConstraint(VICTORIA, TASMANIA));
		Map<String, String> solution = csp.backtrackingSearch();
		if (solution == null) {
			System.out.println("No solution found!");
		} else {
			System.out.println(solution);
		}
	}

}
