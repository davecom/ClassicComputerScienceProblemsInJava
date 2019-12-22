// Fib2.java
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

public class Fib2 {
	private static int fib2(int n) {
		if (n < 2) {
			return n;
		}
		return fib2(n - 1) + fib2(n - 2);
	}

	public static void main(String[] args) {
		System.out.println(Fib2.fib2(5));
		System.out.println(Fib2.fib2(10));
	}
}
