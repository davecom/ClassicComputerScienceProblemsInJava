// Hanoi.java
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

import java.util.Stack;

public class Hanoi {
	private final int NUM_DISCS;
	public final Stack<Integer> towerA = new Stack<>();
	public final Stack<Integer> towerB = new Stack<>();
	public final Stack<Integer> towerC = new Stack<>();

	public Hanoi(int numDiscs) {
		NUM_DISCS = numDiscs;
		for (int i = 1; i <= numDiscs; i++) {
			towerA.push(i);
		}
	}

	private void move(Stack<Integer> begin, Stack<Integer> end, Stack<Integer> temp, int n) {
		if (n == 1) {
			end.push(begin.pop());
		} else {
			move(begin, temp, end, n - 1);
			move(begin, end, temp, 1);
			move(temp, end, begin, n - 1);
		}
	}

	public void solve() {
		move(towerA, towerC, towerB, NUM_DISCS);
	}

	public static void main(String[] args) {
		Hanoi hanoi = new Hanoi(3);
		hanoi.solve();
		System.out.println(hanoi.towerA);
		System.out.println(hanoi.towerB);
		System.out.println(hanoi.towerC);
	}

}
