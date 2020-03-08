// Chromosome.java
// From Classic Computer Science Problems in Java Chapter 5
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

package chapter5;

import java.util.List;

public abstract class Chromosome<T extends Chromosome<T>> implements Comparable<T> {
	public abstract double fitness();

	public abstract List<T> crossover(T other);

	public abstract void mutate();

	public abstract T copy();

	@Override
	public int compareTo(T other) {
		Double mine = this.fitness();
		Double theirs = other.fitness();
		return mine.compareTo(theirs);
	}
}
