// CSP.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSP<V, D> {
	private List<V> variables;
	private Map<V, List<D>> domains;
	private Map<V, List<Constraint<V, D>>> constraints;

	public CSP(List<V> variables, Map<V, List<D>> domains) {
		this.variables = variables;
		this.domains = domains;
		constraints = new HashMap<>();
		for (V variable : variables) {
			constraints.put(variable, new ArrayList<>());
			if (!domains.containsKey(variable)) {
				throw new IllegalArgumentException("Every variable should have a domain assigned to it.");
			}
		}
	}

	public void addConstraint(Constraint<V, D> constraint) {
		for (V variable : constraint.variables) {
			if (!variables.contains(variable)) {
				throw new IllegalArgumentException("Variable in constraint not in CSP");
			} else {
				constraints.get(variable).add(constraint);
			}
		}
	}

	// Check if the value assignment is consistent by checking all constraints
	// for the given variable against it
	public boolean consistent(V variable, Map<V, D> assignment) {
		for (Constraint<V, D> constraint : constraints.get(variable)) {
			if (!constraint.satisfied(assignment)) {
				return false;
			}
		}
		return true;
	}

	public Map<V, D> backtrackingSearch(Map<V, D> assignment) {
		// assignment is complete if every variable is assigned (our base case)
		if (assignment.size() == variables.size()) {
			return assignment;
		}
		// get the first variable in the CSP but not in the assignment
		V unassigned = variables.stream().filter(v -> (!assignment.containsKey(v))).findFirst().get();
		// get the every possible domain value of the first unassigned variable
		for (D value : domains.get(unassigned)) {
			// shallow copy of assignment that we can change
			Map<V, D> localAssignment = new HashMap<>(assignment);
			localAssignment.put(unassigned, value);
			// if we're still consistent, we recurse (continue)
			if (consistent(unassigned, localAssignment)) {
				Map<V, D> result = backtrackingSearch(localAssignment);
				// if we didn't find the result, we will end up backtracking
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}

	// helper for backtrackingSearch when nothing known yet
	public Map<V, D> backtrackingSearch() {
		return backtrackingSearch(new HashMap<>());
	}
}
