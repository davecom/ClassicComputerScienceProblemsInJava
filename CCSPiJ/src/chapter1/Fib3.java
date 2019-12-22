// Fib3.java
// From Classic Computer Science Problems in Java Chapter 1
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

package chapter1;

import java.util.HashMap;
import java.util.Map;

public class Fib3 {

	// Map.of() was introduced in Java 9 but returns
	// an immutable Map
	// This creates a map with 0->0 and 1->1
	// Which represent our base cases
	static Map<Integer, Integer> memo = new HashMap<>(Map.of(0, 0, 1, 1));

	private static int fib3(int n) {
		if (!memo.containsKey(n)) {
			// memoization step
			memo.put(n, fib3(n - 1) + fib3(n - 2));
		}
		return memo.get(n);
	}

	public static void main(String[] args) {
		System.out.println(Fib3.fib3(5));
		System.out.println(Fib3.fib3(40));
	}
}
