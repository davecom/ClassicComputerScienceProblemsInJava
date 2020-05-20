// ConnectFour.java
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

import java.util.Scanner;

public class ConnectFour {

	private C4Board board = new C4Board();
	private Scanner scanner = new Scanner(System.in);

	private Integer getPlayerMove() {
		Integer playerMove = -1;
		while (!board.getLegalMoves().contains(playerMove)) {
			System.out.println("Enter a legal column (0-6):");
			Integer play = scanner.nextInt();
			playerMove = play;
		}
		return playerMove;
	}

	private void runGame() {
		// main game loop
		while (true) {
			Integer humanMove = getPlayerMove();
			board = board.move(humanMove);
			if (board.isWin()) {
				System.out.println("Human wins!");
				break;
			} else if (board.isDraw()) {
				System.out.println("Draw!");
				break;
			}
			Integer computerMove = Minimax.findBestMove(board, 7);
			System.out.println("Computer move is " + computerMove);
			board = board.move(computerMove);
			System.out.println(board);
			if (board.isWin()) {
				System.out.println("Computer wins!");
				break;
			} else if (board.isDraw()) {
				System.out.println("Draw!");
				break;
			}
		}
	}

	public static void main(String[] args) {
		new ConnectFour().runGame();
	}

}
