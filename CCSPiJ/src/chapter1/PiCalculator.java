// PiCalculator.java
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

public class PiCalculator {

	public static double calculatePi(int nTerms) {
		final double numerator = 4.0;
		double denominator = 1.0;
		double operation = 1.0;
		double pi = 0.0;
		for (int i = 0; i < nTerms; i++) {
			pi += operation * (numerator / denominator);
			denominator += 2.0;
			operation *= -1.0;
		}
		return pi;
	}

	public static void main(String[] args) {
		System.out.println(calculatePi(1000000));
	}
}
