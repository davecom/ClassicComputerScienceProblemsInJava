// TTTMinimaxTests.java
// From Classic Computer Science Problems in Java Chapter 8
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

package chapter8;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

// Annotation for unit tests
@Retention(RetentionPolicy.RUNTIME)
@interface UnitTest {
	String name() default "";
}

public class TTTMinimaxTests {

	// Check if two values are equal and report back
	public static <T> void assertEquality(T actual, T expected) {
		if (actual.equals(expected)) {
			System.out.println("Passed!");
		} else {
			System.out.println("Failed!");
			System.out.println("Actual: " + actual.toString());
			System.out.println("Expected: " + expected.toString());
		}
	}

	@UnitTest(name = "Easy Position")
	public void easyPosition() {
		TTTPiece[] toWinEasyPosition = new TTTPiece[] {
				TTTPiece.X, TTTPiece.O, TTTPiece.X,
				TTTPiece.X, TTTPiece.E, TTTPiece.O,
				TTTPiece.E, TTTPiece.E, TTTPiece.O };
		TTTBoard testBoard1 = new TTTBoard(toWinEasyPosition, TTTPiece.X);
		Integer answer1 = Minimax.findBestMove(testBoard1, 8);
		assertEquality(answer1, 6);
	}

	@UnitTest(name = "Block Position")
	public void blockPosition() {
		TTTPiece[] toBlockPosition = new TTTPiece[] {
				TTTPiece.X, TTTPiece.E, TTTPiece.E,
				TTTPiece.E, TTTPiece.E, TTTPiece.O,
				TTTPiece.E, TTTPiece.X, TTTPiece.O };
		TTTBoard testBoard2 = new TTTBoard(toBlockPosition, TTTPiece.X);
		Integer answer2 = Minimax.findBestMove(testBoard2, 8);
		assertEquality(answer2, 2);
	}

	@UnitTest(name = "Hard Position")
	public void hardPosition() {
		TTTPiece[] toWinHardPosition = new TTTPiece[] {
				TTTPiece.X, TTTPiece.E, TTTPiece.E,
				TTTPiece.E, TTTPiece.E, TTTPiece.O,
				TTTPiece.O, TTTPiece.X, TTTPiece.E };
		TTTBoard testBoard3 = new TTTBoard(toWinHardPosition, TTTPiece.X);
		Integer answer3 = Minimax.findBestMove(testBoard3, 8);
		assertEquality(answer3, 1);
	}

	// Run all methods marked with the UnitTest annotation
	public void runAllTests() {
		for (Method method : this.getClass().getMethods()) {
			for (UnitTest annotation : method.getAnnotationsByType(UnitTest.class)) {
				System.out.println("Running Test " + annotation.name());
				try {
					method.invoke(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("____________________");
			}
		}
	}

	public static void main(String[] args) {
		new TTTMinimaxTests().runAllTests();
	}
}
