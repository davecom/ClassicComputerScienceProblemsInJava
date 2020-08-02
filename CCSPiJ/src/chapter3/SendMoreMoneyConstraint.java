// SendMoreMoneyConstraint.java
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class SendMoreMoneyConstraint extends Constraint<Character, Integer> {
	private List<Character> letters;

	public SendMoreMoneyConstraint(List<Character> letters) {
		super(letters);
		this.letters = letters;
	}

	@Override
	public boolean satisfied(Map<Character, Integer> assignment) {
		// if there are duplicate values then it's not a solution
		if ((new HashSet<>(assignment.values())).size() < assignment.size()) {
			return false;
		}

		// if all variables have been assigned, check if it adds correctly
		if (assignment.size() == letters.size()) {
			int s = assignment.get('S');
			int e = assignment.get('E');
			int n = assignment.get('N');
			int d = assignment.get('D');
			int m = assignment.get('M');
			int o = assignment.get('O');
			int r = assignment.get('R');
			int y = assignment.get('Y');
			int send = s * 1000 + e * 100 + n * 10 + d;
			int more = m * 1000 + o * 100 + r * 10 + e;
			int money = m * 10000 + o * 1000 + n * 100 + e * 10 + y;
			return send + more == money;
		}
		return true; // no conflicts
	}

	public static void main(String[] args) {
		List<Character> letters = List.of('S', 'E', 'N', 'D', 'M', 'O', 'R', 'Y');
		Map<Character, List<Integer>> possibleDigits = new HashMap<>();
		for (Character letter : letters) {
			possibleDigits.put(letter, List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		}
		// so we don't get answers starting with a 0
		possibleDigits.replace('M', List.of(1));
		CSP<Character, Integer> csp = new CSP<>(letters, possibleDigits);
		csp.addConstraint(new SendMoreMoneyConstraint(letters));
		Map<Character, Integer> solution = csp.backtrackingSearch();
		if (solution == null) {
			System.out.println("No solution found!");
		} else {
			System.out.println(solution);
		}
	}

}
