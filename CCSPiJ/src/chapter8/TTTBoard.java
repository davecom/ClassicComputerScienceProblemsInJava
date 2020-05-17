// TTTBoard.java
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TTTBoard implements Board<Integer> {
	private static final int NUM_SQUARES = 9;
	private TTTPiece[] position;
	private TTTPiece turn;

	public TTTBoard(TTTPiece[] position, TTTPiece turn) {
		this.position = position;
		this.turn = turn;
	}

	public TTTBoard() {
		// by default start with blank board
		position = new TTTPiece[NUM_SQUARES];
		Arrays.fill(position, TTTPiece.E);
		// X goes first
		turn = TTTPiece.X;
	}

	@Override
	public Piece getTurn() {
		return turn;
	}

	@Override
	public TTTBoard move(Integer location) {
		TTTPiece[] tempPosition = Arrays.copyOf(position, position.length);
		tempPosition[location] = turn;
		return new TTTBoard(tempPosition, turn.opposite());
	}

	@Override
	public List<Integer> getLegalMoves() {
		ArrayList<Integer> legalMoves = new ArrayList<>();
		for (int i = 0; i < NUM_SQUARES; i++) {
			// empty squares are legal moves
			if (position[i] == TTTPiece.E) {
				legalMoves.add(i);
			}
		}
		return legalMoves;
	}

	@Override
	public boolean isWin() {
		// three row, three column, and then two diagonal checks
		return position[0] == position[1] && position[0] == position[2] && position[0] != TTTPiece.E ||
				position[3] == position[4] && position[3] == position[5] && position[3] != TTTPiece.E ||
				position[6] == position[7] && position[6] == position[8] && position[6] != TTTPiece.E ||
				position[0] == position[3] && position[0] == position[6] && position[0] != TTTPiece.E ||
				position[1] == position[4] && position[1] == position[7] && position[1] != TTTPiece.E ||
				position[2] == position[5] && position[2] == position[8] && position[2] != TTTPiece.E ||
				position[0] == position[4] && position[0] == position[8] && position[0] != TTTPiece.E ||
				position[2] == position[4] && position[2] == position[6] && position[2] != TTTPiece.E;
	}

	@Override
	public double evaluate(Piece player) {
		if (isWin() && turn == player) {
			return -1;
		} else if (isWin() && turn != player) {
			return 1;
		} else {
			return 0.0;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				sb.append(position[row * 3 + col].toString());
				if (col != 2) {
					sb.append("|");
				}
			}
			sb.append(System.lineSeparator());
			if (row != 2) {
				sb.append("-----");
				sb.append(System.lineSeparator());
			}
		}
		return sb.toString();
	}

}
