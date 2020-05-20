// C4Board.java
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

public class C4Board implements Board<Integer> {
	public static final int NUM_COLUMNS = 7;
	public static final int NUM_ROWS = 6;
	public static final int SEGMENT_LENGTH = 4;
	public static final ArrayList<C4Location[]> SEGMENTS = generateSegments();

	// generate all of the segments for a given board
	// this static method is only run once
	private static ArrayList<C4Location[]> generateSegments() {
		ArrayList<C4Location[]> segments = new ArrayList<>();
		// vertical
		for (int c = 0; c < NUM_COLUMNS; c++) {
			for (int r = 0; r <= NUM_ROWS - SEGMENT_LENGTH; r++) {
				C4Location[] bl = new C4Location[SEGMENT_LENGTH];
				for (int i = 0; i < SEGMENT_LENGTH; i++) {
					bl[i] = new C4Location(c, r + i);
				}
				segments.add(bl);
			}
		}
		// horizontal
		for (int c = 0; c <= NUM_COLUMNS - SEGMENT_LENGTH; c++) {
			for (int r = 0; r < NUM_ROWS; r++) {
				C4Location[] bl = new C4Location[SEGMENT_LENGTH];
				for (int i = 0; i < SEGMENT_LENGTH; i++) {
					bl[i] = new C4Location(c + i, r);
				}
				segments.add(bl);
			}
		}
		// diagonal from bottom left to top right
		for (int c = 0; c <= NUM_COLUMNS - SEGMENT_LENGTH; c++) {
			for (int r = 0; r <= NUM_ROWS - SEGMENT_LENGTH; r++) {
				C4Location[] bl = new C4Location[SEGMENT_LENGTH];
				for (int i = 0; i < SEGMENT_LENGTH; i++) {
					bl[i] = new C4Location(c + i, r + i);
				}
				segments.add(bl);
			}
		}
		// diagonal from bottom right to top left
		for (int c = NUM_COLUMNS - SEGMENT_LENGTH; c >= 0; c--) {
			for (int r = SEGMENT_LENGTH - 1; r < NUM_ROWS; r++) {
				C4Location[] bl = new C4Location[SEGMENT_LENGTH];
				for (int i = 0; i < SEGMENT_LENGTH; i++) {
					bl[i] = new C4Location(c + i, r - i);
				}
				segments.add(bl);
			}
		}

		return segments;
	}

	private C4Piece[][] position; // column first, then row
	private int[] columnCount; // number of pieces in each column
	private C4Piece turn;

	public C4Board() {
		// note that we're doing columns first
		position = new C4Piece[NUM_COLUMNS][NUM_ROWS];
		for (C4Piece[] col : position) {
			Arrays.fill(col, C4Piece.E);
		}
		// ints by default are initialized to 0
		columnCount = new int[NUM_COLUMNS];
		turn = C4Piece.B; // black goes first
	}

	public C4Board(C4Piece[][] position, C4Piece turn) {
		this.position = position;
		columnCount = new int[NUM_COLUMNS];
		for (int c = 0; c < NUM_COLUMNS; c++) {
			int piecesInColumn = 0;
			for (int r = 0; r < NUM_ROWS; r++) {
				if (position[c][r] != C4Piece.E) {
					piecesInColumn++;
				}
			}
			columnCount[c] = piecesInColumn;
		}

		this.turn = turn;
	}

	@Override
	public Piece getTurn() {
		return turn;
	}

	@Override
	public C4Board move(Integer location) {
		C4Piece[][] tempPosition = Arrays.copyOf(position, position.length);
		for (int col = 0; col < NUM_COLUMNS; col++) {
			tempPosition[col] = Arrays.copyOf(position[col], position[col].length);
		}
		tempPosition[location][columnCount[location]] = turn;
		return new C4Board(tempPosition, turn.opposite());
	}

	@Override
	public List<Integer> getLegalMoves() {
		List<Integer> legalMoves = new ArrayList<>();
		for (int i = 0; i < NUM_COLUMNS; i++) {
			if (columnCount[i] < NUM_ROWS) {
				legalMoves.add(i);
			}
		}
		return legalMoves;
	}

	private int countSegment(C4Location[] segment, C4Piece color) {
		int count = 0;
		for (C4Location location : segment) {
			if (position[location.column][location.row] == color) {
				count++;
			}
		}
		return count;
	}

	@Override
	public boolean isWin() {
		for (C4Location[] segment : SEGMENTS) {
			int blackCount = countSegment(segment, C4Piece.B);
			int redCount = countSegment(segment, C4Piece.R);
			if (blackCount == SEGMENT_LENGTH || redCount == SEGMENT_LENGTH) {
				return true;
			}
		}
		return false;
	}

	private double evaluateSegment(C4Location[] segment, Piece player) {
		int blackCount = countSegment(segment, C4Piece.B);
		int redCount = countSegment(segment, C4Piece.R);
		if (redCount > 0 && blackCount > 0) {
			return 0.0; // mixed segments are neutral
		}
		int count = Math.max(blackCount, redCount);
		double score = 0.0;
		if (count == 2) {
			score = 1.0;
		} else if (count == 3) {
			score = 100.0;
		} else if (count == 4) {
			score = 1000000.0;
		}
		C4Piece color = (redCount > blackCount) ? C4Piece.R : C4Piece.B;
		if (color != player) {
			return -score;
		}
		return score;
	}

	@Override
	public double evaluate(Piece player) {
		double total = 0.0;
		for (C4Location[] segment : SEGMENTS) {
			total += evaluateSegment(segment, player);
		}
		return total;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int r = NUM_ROWS - 1; r >= 0; r--) {
			sb.append("|");
			for (int c = 0; c < NUM_COLUMNS; c++) {
				sb.append(position[c][r].toString());
				sb.append("|");
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

}
