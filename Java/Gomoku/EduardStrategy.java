import java.util.*;

public class EduardStrategy implements ComputerStrategy {

	static int SIZE = 10;
	static final int WHITE = -1;
	static final int BLACK = 1;
	static final int EMPTY = 0;
	static final int TIME = 950;
	static final long INFINITY = Long.MAX_VALUE;
	static final long MINUS_INFINITY = Long.MIN_VALUE;

	private static int enemyOf(int player) {
		if(player != WHITE) {
			return WHITE;
		} else {
			return BLACK;
		}
	}


	private static class Position {

		int[][] board;
		int turnOfPlayer;

		Position(int[][] board, int player) {
			this.board = board;
			turnOfPlayer = player;
		}
	}

	private static class Move {
		int x, y;
		Move(int x, int y) {
			this.x = x;
			this.y = y;
		}

		int getRow(){
			return y;
		}
		int getCol(){
			return x;
		}
	}

	private int makeMoveList(Position p, Move[] moves) {
		int[][] board = p.board;

		int counter = 0;

		for (int rowi = 0; rowi < board.length; rowi++) {
			int[] row = board[rowi];
			for (int i = 0; i < row.length; i++) {
				if(board[rowi][i] == EMPTY) moves[counter++] = new Move(i, rowi);
			}
		}

//		System.out.println("Made move list for " + p.turnOfPlayer);

		return counter;
	}

	private void doMove(Move m, Position p) {
		p.board[m.y][m.x] = p.turnOfPlayer;
	}

	private void undoMove(Move m, Position p) {
		p.board[m.y][m.x] = EMPTY;
	}

	private long evaluation(Position p) {

//		System.out.println("Evaluate position of " + p.turnOfPlayer);

		int[][] board = p.board;
		int player = p.turnOfPlayer;

		long score = gradeDiagsDown(board, player) + gradeDiagsUp(board, player)
				+ gradeColumns(board, player) + gradeRows(board, player);

		return score;
	}

	private static long gradeDiagsDown(int[][] board, int player) {

		long score = 0;

		int[] cells = new int[5];

		for (int rowi = 0; rowi < SIZE - 4; rowi++) {
			for (int t = 0; rowi + t < SIZE - 4; t++) {


				cells[0] = board[rowi+t][t];
				cells[1] = board[rowi+t+1][t+1];
				cells[2] = board[rowi+t+2][t+2];
				cells[3] = board[rowi+t+3][t+3];
				cells[4] = board[rowi+t+4][t+4];

				score += gradeTuple(new Tuple(cells), player);
			}
		}
		return score;
	}

	private static long gradeDiagsUp(int[][] board, int player) {

		long score = 0;

		int[] cells = new int[5];

		for (int coli = 0; coli < SIZE - 4; coli++) {
			for (int t = 0; coli - t >= 4; t++) {

				cells[0] = board[t][coli-t];
				cells[1] = board[t+1][coli-t-1];
				cells[2] = board[t+2][coli-t-2];
				cells[3] = board[t+3][coli-t-3];
				cells[4] = board[t+4][coli-t-4];

				score += gradeTuple(new Tuple(cells), player);
			}
		}
		return score;
	}

	/**
	 * Check every row.
	 */
	private static long gradeRows(int[][] board, int player) {

		long score = 0;
		int[] cells = new int[5];

		for(int[] row : board) {
			for (int i = 0; i < SIZE - 4; i++) {
				cells[0] = row[i];
				cells[1] = row[i+1];
				cells[2] = row[i+2];
				cells[3] = row[i+3];
				cells[4] = row[i+4];

				score += gradeTuple(new Tuple(cells), player);
			}
		}
		return score;
	}

	/**
	 * Check every column.
	 */
	private static long gradeColumns(int[][] board, int player) {

		long score = 0;
		int[] cells = new int[5];

		for (int coli = 0; coli < SIZE; coli++) {
			for (int rowi = 0; rowi < SIZE-4; rowi++) {

				cells[0] = board[coli][rowi];
				cells[1] = board[coli][rowi+1];
				cells[2] = board[coli][rowi+2];
				cells[3] = board[coli][rowi+3];
				cells[4] = board[coli][rowi+4];

				score += gradeTuple(new Tuple(cells), player);
			}
		}

		return score;
	}

	private static long gradeTuple (Tuple t, int player) {

		int[] cells = t.cells;
		int current = 0;
		int count = 0;

		for(int i = 0; i < cells.length; i++) {
			if(cells[i] != 0) {
				if (current != 0) {
					if (cells[i] != current) return 0;
					count++;
				} else {
					current = cells[i];
					count++;
				}
			}
		}

		if (count > 0) {
			if (true) {
				switch (count) {
					case 1:
						return 35;
					case 2:
						return 800;
					case 3:
						return 15000;
					case 4:
						return 800000;
					default:
						return 8000000;
				}
			} else {
				switch (count) {
					case 1:
						return 15;
					case 2:
						return 400;
					case 3:
						return 1800;
					case 4:
						return 100000;
					default:
						return 1000000;
				}
			}
		}
		return 7;
	}

	private static class Tuple {
		int[] cells;

		Tuple(int[] cells) {
			this.cells = cells;
		}
	}

	private boolean checkwin (Position p) {

		int[][] board = p.board;

		int X = 1;
//		int O = -1;

		int max = max(max(maxDiagsDown(board, X), maxDiagsUp(board, X)), max(maxColumns(board, X), maxRows(board, X)));

//		int en_max = max(max(maxDiagsDown(board, O), maxDiagsUp(board, O)), max(maxColumns(board, O), maxRows(board, O)));

		return max == 5;
	}

	private static int maxDiagsDown(int[][] board, int p) {
		int max = 0;
		int counter = 0;

		for (int rowi = 0; rowi < SIZE; rowi++) {
			for (int t = 0; rowi + t < SIZE; t++) {

				int c = board[rowi+t][t];
				if (c == p) {
					counter++;
				} else {
					max = max(counter, max);
					counter = 0;
				}
			}
		}
		return max;
	}

	private static int maxDiagsUp(int[][] board, int p) {
		int max = 0;
		int counter = 0;

		for (int coli = 0; coli < SIZE; coli++) {
			for (int t = 0; coli - t >= 0; t++) {

				int c = board[t][coli-t];
				if (c == p) {
					counter++;
				} else {
					max = max(counter, max);
					counter = 0;
				}
			}
		}
		return max;
	}

	/**
	 * Check every row.
	 */
	private static int maxRows(int[][] board, int p) {
		int max = 0;
		int counter = 0;

		for(int[] row : board) {
			for (int i = 0; i < SIZE; i++) {
				int c = row[i];
				if (c == p) {
					counter++;
				} else {
					boolean check = false;
					if (counter == 5) {
						check = true;
					} else if (counter == 4) {
						if (i > 4) {
							if (row[i-5] == 0) check = true;
						}
						if (i < 9) {
							if (row[i] == 0) check = true;
						}
					} else if (counter == 3) {
						if (i > 4 && i < 9) {
							if (row[i-5] == 0 && row[i] == 0) check = true;
						}
					}

					if(check) max = max(counter, max);
					counter = 0;
				}
			}
		}
		return max;
	}

	/**
	 * Check every column.
	 */
	private static int maxColumns(int[][] board, int p) {
		int max = 0;
		int counter = 0;

		for (int coli = 0; coli < SIZE; coli++) {
			for (int rowi = 0; rowi < SIZE; rowi++) {
				int c = board[rowi][coli];
				if (c == p) {
					counter++;
				} else {
					boolean check = false;
					if (counter == 5) {
						check = true;
					} else if (counter == 4) {
						if (rowi > 4) {
							if (board[rowi-5][coli] == 0) check = true;
						}                        if (rowi < 9) {
							if (board[rowi][coli] == 0) check = true;
						}
					} else if (counter == 3) {
						if (rowi > 4 && rowi < 9) {
							if (board[rowi][coli] == 0 && board[rowi-5][coli] == 0) check = true;
						}
					}

					if(check) max = max(counter, max);
					counter = 0;
				}
			}
		}
		return max;
	}

	private long negamax(Position p, int depth) {

		Move[] list = new Move[SIZE*SIZE];
		int i,n;
		long value;
		long bestvalue = MINUS_INFINITY;

		if(checkwin(p))
			return -INFINITY;

		p.turnOfPlayer = enemyOf(p.turnOfPlayer);

		if(depth == 0) {
			return evaluation(p);
		}

		n = makeMoveList(p, list);
		for(i=0; i<n; i++) {
			doMove(list[i], p);
			value = -negamax(p,depth-1);
			undoMove(list[i],p);

			if (value > bestvalue) {
				bestvalue = value;
				if (depth == startDepth) {
					best.y = list[i].y;
					best.x = list[i].x;
				}
			}
			bestvalue = max(value,bestvalue);
		}
		return bestvalue;
	}

	static Move best = new Move(-1, -1);
	static int startDepth;

	@Override
	public Location getMove(SimpleBoard board, int player) {
		SIZE = board.getHeight();
		startDepth = 2;
		negamax(new Position(board.getBoard(), player*-1), startDepth);

//		System.out.println("Move at " + best.y + " and column " + best.x);

		return new Location(best.y, best.x);
	}

	@Override
	public String getName() {
		return "Eduard The Great";
	}


	static long max(long a, long b) {
		return (a > b) ? a : b;
	}

	static int max(int a, int b) {
		return (a > b) ? a : b;
	}

}

