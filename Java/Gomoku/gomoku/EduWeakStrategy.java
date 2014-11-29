package gomoku;

import com.sun.istack.internal.NotNull;

import java.util.*;

public class EduWeakStrategy implements ComputerStrategy {

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


	private long evaluation(Position p) {

		int[][] board = p.board;
		int player = p.turnOfPlayer;

		long score = gradeDiagsUp(board, player) + gradeDiagsDown(board, player)
				+ gradeRows(board, player) + gradeColumns(board, player);

		return score;
	}

	private static long gradeDiagsDown(int[][] board, int player) {

		long score = 0;

		int[] cells = new int[5];
		int[] cellindexes = new int[5];

		for (int rowi = 0; rowi < SIZE - 4; rowi++) {
			for (int coli = 0; coli < SIZE - 4; coli++) {

				cells[0] = board[rowi][coli];
				cells[1] = board[rowi+1][coli+1];
				cells[2] = board[rowi+2][coli+2];
				cells[3] = board[rowi+3][coli+3];
				cells[4] = board[rowi+4][coli+4];

				cellindexes[0] = rowi*100+coli;
				cellindexes[1] = (rowi+1)*100+coli+1;
				cellindexes[2] = (rowi+2)*100+coli+2;
				cellindexes[3] = (rowi+3)*100+coli+3;
				cellindexes[4] = (rowi+4)*100+coli+4;

				Tuple t = new Tuple(cells.clone(), cellindexes);

				score = gradeTuple(t, player);
				if (score == 0) continue;

				Move m = moves[rowi][coli];
				moveTuples.get(m).addLast(t);
				m.score += score;

				m = moves[rowi+1][coli+1];
				moveTuples.get(m).addLast(t);
				m.score += score;

				m = moves[rowi+2][coli+2];
				moveTuples.get(m).addLast(t);
				m.score += score;

				m = moves[rowi+3][coli+3];
				moveTuples.get(m).addLast(t);
				m.score += score;

				m = moves[rowi+4][coli+4];
				moveTuples.get(m).addLast(t);
				m.score += score;
			}
		}
		return score;
	}

	private static long gradeDiagsUp(int[][] board, int player) {

		long score = 0;

		int[] cells = new int[5];
		int[] cellindexes = new int[5];

		for (int coli = 4; coli < SIZE; coli++) {
			for (int rowi = 0; rowi + 4 < SIZE; rowi++) {

				cells[0] = board[rowi][coli];
				cells[1] = board[rowi+1][coli-1];
				cells[2] = board[rowi+2][coli-2];
				cells[3] = board[rowi+3][coli-3];
				cells[4] = board[rowi+4][coli-4];

				cellindexes[0] = rowi*100+coli;
				cellindexes[1] = (rowi+1)*100+coli-1;
				cellindexes[2] = (rowi+2)*100+coli-2;
				cellindexes[3] = (rowi+3)*100+coli-3;
				cellindexes[4] = (rowi+4)*100+coli-4;

				Tuple t = new Tuple(cells.clone(), cellindexes);

				score = gradeTuple(t, player);
				if (score == 0) continue;

				Move m = moves[rowi][coli];
				moveTuples.get(m).addLast(t);
				m.score += score;

				m = moves[rowi+1][coli-1];
				moveTuples.get(m).addLast(t);
				m.score += score;

				m = moves[rowi+2][coli-2];
				moveTuples.get(m).addLast(t);
				m.score += score;

				m = moves[rowi+3][coli-3];
				moveTuples.get(m).addLast(t);
				m.score += score;

				m = moves[rowi+4][coli-4];
				moveTuples.get(m).addLast(t);
				m.score += score;
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
		int[] cellindexes = new int[5];

		for (int rowi = 0; rowi < board.length; rowi++) {
			int[] row = board[rowi];
			for (int coli = 0; coli < SIZE - 4; coli++) {

				cells[0] = row[coli];
				cells[1] = row[coli + 1];
				cells[2] = row[coli + 2];
				cells[3] = row[coli + 3];
				cells[4] = row[coli + 4];

				cellindexes[0] = rowi*100+coli;
				cellindexes[1] = rowi*100+coli+1;
				cellindexes[2] = rowi*100+coli+2;
				cellindexes[3] = rowi*100+coli+3;
				cellindexes[4] = rowi*100+coli+4;

				Tuple t = new Tuple(cells.clone(), cellindexes);

				score = gradeTuple(t, player);
				if (score == 0) continue;

				Move m = moves[rowi][coli];
				moveTuples.get(m).addLast(t);
				m.score += score;

				m = moves[rowi][coli+1];
				moveTuples.get(m).addLast(t);
				m.score += score;

				m = moves[rowi][coli+2];
				moveTuples.get(m).addLast(t);
				m.score += score;

				m = moves[rowi][coli+3];
				moveTuples.get(m).addLast(t);
				m.score += score;

				m = moves[rowi][coli+4];
				moveTuples.get(m).addLast(t);
				m.score += score;
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
		int[] cellindexes = new int[5];

		for (int coli = 0; coli < SIZE; coli++) {
			for (int rowi = 0; rowi < SIZE-4; rowi++) {

				cells[0] = board[rowi][coli];
				cells[1] = board[rowi+1][coli];
				cells[2] = board[rowi+2][coli];
				cells[3] = board[rowi+3][coli];
				cells[4] = board[rowi+4][coli];

				cellindexes[0] = rowi*100+coli;
				cellindexes[1] = (rowi+1)*100+coli;
				cellindexes[2] = (rowi+2)*100+coli;
				cellindexes[3] = (rowi+3)*100+coli;
				cellindexes[4] = (rowi+4)*100+coli;

				Tuple tuple = new Tuple(cells.clone(), cellindexes);

				score = gradeTuple(tuple, player);
				if (score == 0 ) continue;

				moveTuples.get(moves[rowi][coli]).addLast(tuple);
				moves[rowi][coli].score += score;

				moveTuples.get(moves[rowi+1][coli]).addLast(tuple);
				moves[rowi+1][coli].score += score;

				moveTuples.get(moves[rowi+2][coli]).addLast(tuple);
				moves[rowi+2][coli].score += score;

				moveTuples.get(moves[rowi+3][coli]).addLast(tuple);
				moves[rowi+3][coli].score += score;

				moveTuples.get(moves[rowi+4][coli]).addLast(tuple);
				moves[rowi+4][coli].score += score;


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

		t.player = current;
		t.count = count;

		if (count > 0) {
			if (current == player) {
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
						return 1800; // TODO play with this, original 1800 // 8000 is fine for general game
					case 4:
						return 100000;
					default:
						return 1000000;
				}
			}
		}
		return 7;
	}

	private static void countThreats(Move move, int[][] board, int player) {
		LinkedList<Tuple> list = moveTuples.get(move);
		Tuple[] tuples = new Tuple[list.size()];

		if(move.x == 1 && move.y == 4) {
			System.out.println();
		}

		int c = 0;
		for (Iterator<Tuple> iterator = list.iterator(); iterator.hasNext(); ) {
			tuples[c++] = iterator.next();
		}

		int threat4x4 = 0;
		int threat3x4 = 0;
		int threat2x4 = 0;
		int threat4 = 0;
		int threatOpen3 = 0;
		int threatClosed3 = 0;
		int threat3x3 = 0;
		int threat2x3 = 0;
		int threat2x2 = 0;

		for(Tuple t : tuples) {

			if (t.count == 4) {
				threat4++;
				if(!move.FantasticFour) {
					move.FantasticFour = t.player == player;
				}
				move.score += 10000;
				continue;
			}

			if (t.count != 3) continue;

			boolean threat = false;
			boolean closedthreat = false;

			int[] cellIndexes = t.cellindexes;
			int[] cells = t.cells;
			if (cells[0] == EMPTY && cells[1] == EMPTY && cellIndexes[1] == move.hashCode()) {

				int ly = cellIndexes[4] / 100;
				int lx = cellIndexes[4] % 100;

				int dy = cellIndexes[1] / 100 - cellIndexes[0] / 100;
				int dx = cellIndexes[1] % 100 - cellIndexes[0] % 100;

				int ay = ly + dy;
				int ax = lx + dx;

				if (ay < 0 || ax < 0 || ay >= SIZE || ax >= SIZE) continue;

				if (board[ay][ax] != t.player * -1) {
					threat = true;
				}
			} else if (cells[3] == EMPTY && cells[4] == EMPTY && cellIndexes[3] == move.hashCode()) {

				int fy = cellIndexes[0] / 100;
				int fx = cellIndexes[0] % 100;

				int dy = cellIndexes[1] / 100 - cellIndexes[0] / 100;
				int dx = cellIndexes[1] % 100 - cellIndexes[0] % 100;

				int py = fy - dy;
				int px = fx - dx;

				if (py < 0 || px < 0 || py >= SIZE || px >= SIZE) continue;

				if (board[py][px] != t.player * -1) {
					threat = true;
				}
			} else if (cells[1] == EMPTY && cells[4] == EMPTY) { // 0-00-

				int k = move.hashCode();

				if(k == cellIndexes[1]) {
					int fy = cellIndexes[0] / 100;
					int fx = cellIndexes[0] % 100;

					int dy = cellIndexes[1] / 100 - cellIndexes[0] / 100;
					int dx = cellIndexes[1] % 100 - cellIndexes[0] % 100;

					int py = fy - dy;
					int px = fx - dx;

					if (py < 0 || px < 0 || py >= SIZE || px >= SIZE) {
						threat = false;
					} else if (board[py][px] != t.player * -1) {
						threat = true;
					} else {

						int ly = cellIndexes[4] / 100;
						int lx = cellIndexes[4] % 100;

						int ay = ly + dy;
						int ax = lx + dx;

						if (ay < 0 || ax < 0 || ay >= SIZE || ax >= SIZE)
							continue;

						if (board[ay][ax] != t.player * -1) {
							closedthreat = true;
						}
					}
				} else if (k == cellIndexes[4]) {
					int fy = cellIndexes[0] / 100;
					int fx = cellIndexes[0] % 100;

					int dy = cellIndexes[1] / 100 - cellIndexes[0] / 100;
					int dx = cellIndexes[1] % 100 - cellIndexes[0] % 100;

					int py = fy - dy;
					int px = fx - dx;

					if (py < 0 || px < 0 || py >= SIZE || px >= SIZE) {
						closedthreat = false;
					} else if (board[py][px] != t.player * -1) {
						closedthreat = true;
					} else {

						int ly = cellIndexes[4] / 100;
						int lx = cellIndexes[4] % 100;

						int ay = ly + dy;
						int ax = lx + dx;

						if (ay < 0 || ax < 0 || ay >= SIZE || ax >= SIZE)
							continue;

						if (board[ay][ax] != t.player * -1) {
							closedthreat = true;
						}
					}
				}
			} else if (cells[0] == EMPTY && cells[2] == EMPTY) { // -0-00

				int k = move.hashCode();

				if(k == cellIndexes[0]) {
					int ly = cellIndexes[4] / 100;
					int lx = cellIndexes[4] % 100;

					int dy = cellIndexes[1] / 100 - cellIndexes[0] / 100;
					int dx = cellIndexes[1] % 100 - cellIndexes[0] % 100;

					int ay = ly + dy;
					int ax = lx + dx;

					if (ay < 0 || ax < 0 || ay >= SIZE || ax >= SIZE) {
						closedthreat = false;
					} else if (board[ay][ax] != t.player * -1) {
						closedthreat = true;
					} else {
						int fy = cellIndexes[0] / 100;
						int fx = cellIndexes[0] % 100;

						int py = fy - dy;
						int px = fx - dx;

						if (py < 0 || px < 0 || py >= SIZE || px >= SIZE) continue;

						if (board[py][px] != t.player * -1) {
							closedthreat = true;
						}
					}
				} else if (k == cellIndexes[2]) {
					int ly = cellIndexes[4] / 100;
					int lx = cellIndexes[4] % 100;

					int dy = cellIndexes[1] / 100 - cellIndexes[0] / 100;
					int dx = cellIndexes[1] % 100 - cellIndexes[0] % 100;

					int ay = ly + dy;
					int ax = lx + dx;

					if (ay < 0 || ax < 0 || ay >= SIZE || ax >= SIZE) {
						threat = false;
					} else if (board[ay][ax] != t.player * -1) {
						threat = true;
					} else {
						int fy = cellIndexes[0] / 100;
						int fx = cellIndexes[0] % 100;

						int py = fy - dy;
						int px = fx - dx;

						if (py < 0 || px < 0 || py >= SIZE || px >= SIZE) continue;

						if (board[py][px] != t.player * -1) {
							closedthreat = true;
						}
					}
				}
			} else if (cells[0] == EMPTY && cells[3] == EMPTY) { // -0-00 TODO -00-0 check dis

				int k = move.hashCode();

				if(k == cellIndexes[0]) {
					int ly = cellIndexes[4] / 100;
					int lx = cellIndexes[4] % 100;

					int dy = cellIndexes[1] / 100 - cellIndexes[0] / 100;
					int dx = cellIndexes[1] % 100 - cellIndexes[0] % 100;

					int ay = ly + dy;
					int ax = lx + dx;

					if (ay < 0 || ax < 0 || ay >= SIZE || ax >= SIZE) {
						closedthreat = false;
					} else if (board[ay][ax] != t.player * -1) {
						closedthreat = true;
					} else {
						int fy = cellIndexes[0] / 100;
						int fx = cellIndexes[0] % 100;

						int py = fy - dy;
						int px = fx - dx;

						if (py < 0 || px < 0 || py >= SIZE || px >= SIZE) continue;

						if (board[py][px] != t.player * -1) {
							closedthreat = true;
						}
					}
				} else if (k == cellIndexes[3]) {
					int ly = cellIndexes[4] / 100;
					int lx = cellIndexes[4] % 100;

					int dy = cellIndexes[1] / 100 - cellIndexes[0] / 100;
					int dx = cellIndexes[1] % 100 - cellIndexes[0] % 100;

					int ay = ly + dy;
					int ax = lx + dx;

					if (ay < 0 || ax < 0 || ay >= SIZE || ax >= SIZE) {
						threat = false;
					} else if (board[ay][ax] != t.player * -1) {
						threat = true;
					} else {
						int fy = cellIndexes[0] / 100;
						int fx = cellIndexes[0] % 100;

						int py = fy - dy;
						int px = fx - dx;

						if (py < 0 || px < 0 || py >= SIZE || px >= SIZE) continue;

						if (board[py][px] != t.player * -1) {
							closedthreat = true;
						}
					}
				}
			} else if (cells[2] == EMPTY && cells[4] == EMPTY) { // 0-00- TODO 00-0-

				int k = move.hashCode();

				if(k == cellIndexes[2]) {
					int fy = cellIndexes[0] / 100;
					int fx = cellIndexes[0] % 100;

					int dy = cellIndexes[1] / 100 - cellIndexes[0] / 100;
					int dx = cellIndexes[1] % 100 - cellIndexes[0] % 100;

					int py = fy - dy;
					int px = fx - dx;

					if (py < 0 || px < 0 || py >= SIZE || px >= SIZE) {
						threat = false;
					} else if (board[py][px] != t.player * -1) {
						threat = true;
					} else {

						int ly = cellIndexes[4] / 100;
						int lx = cellIndexes[4] % 100;

						int ay = ly + dy;
						int ax = lx + dx;

						if (ay < 0 || ax < 0 || ay >= SIZE || ax >= SIZE)
							continue;

						if (board[ay][ax] != t.player * -1) {
							closedthreat = true;
						}
					}
				} else if (k == cellIndexes[4]) {
					int fy = cellIndexes[0] / 100;
					int fx = cellIndexes[0] % 100;

					int dy = cellIndexes[1] / 100 - cellIndexes[0] / 100;
					int dx = cellIndexes[1] % 100 - cellIndexes[0] % 100;

					int py = fy - dy;
					int px = fx - dx;

					if (py < 0 || px < 0 || py >= SIZE || px >= SIZE) {
						closedthreat = false;
					} else if (board[py][px] != t.player * -1) {
						closedthreat = true;
					} else {

						int ly = cellIndexes[4] / 100;
						int lx = cellIndexes[4] % 100;

						int ay = ly + dy;
						int ax = lx + dx;

						if (ay < 0 || ax < 0 || ay >= SIZE || ax >= SIZE)
							continue;

						if (board[ay][ax] != t.player * -1) {
							closedthreat = true;
						}
					}
				}
			}
			if(threat) {
				threatOpen3++;
				move.score += 1000;
			}
			if(closedthreat) {
				threatClosed3++;
				move.score +=100;
			}

		}

		for(int i = 0; i < c; i++) {
			// TODO check first tuple here // check for count == 0, move on
			for(int j = i; j < c; j++) { // j = i so we have only unique pairs, only 0-2 and no 2-0 indexes
				if(i != j) {
					Tuple one = tuples[i];
					Tuple two = tuples[j];

					if (one.count < 2 || two.count < 2 || (one.player != two.player) ) {
						continue;
					}

					ArrayList<Integer> firstCellsIndexes = new ArrayList<>(5);
					ArrayList<Integer> secondCellsIndexes = new ArrayList<>(5);

					int[] oneCellIndexes = one.cellindexes;
					int[] twoCellIndexes = two.cellindexes;

					int[] oneCells = one.cells;
					int[] twoCells = two.cells;

					if (one.count == 2) {

						int fy = oneCellIndexes[0] / 100;
						int fx = oneCellIndexes[0] % 100;

						int ly = oneCellIndexes[4] / 100;
						int lx = oneCellIndexes[4] % 100;

						int dy = oneCellIndexes[1] / 100 - oneCellIndexes[0] / 100;
						int dx = oneCellIndexes[1] % 100 - oneCellIndexes[0] % 100;

						int py = fy - dy;
						int px = fx - dx;

						int ay = ly + dy;
						int ax = lx + dx;

						if (py < 0 || ay < 0 || px < 0 || ax < 0 || py >= SIZE || ay >= SIZE || px >= SIZE || ax >= SIZE) continue;
						if (board[py][px] == (one.player * -1) || board[ay][ax] == (one.player * -1)) continue;
					}

					if (two.count == 2) {

						int fy = twoCellIndexes[0] / 100;
						int fx = twoCellIndexes[0] % 100;

						int ly = twoCellIndexes[4] / 100;
						int lx = twoCellIndexes[4] % 100;

						int dy = twoCellIndexes[1] / 100 - twoCellIndexes[0] / 100;
						int dx = twoCellIndexes[1] % 100 - twoCellIndexes[0] % 100;

						int py = fy - dy;
						int px = fx - dx;

						int ay = ly + dy;
						int ax = lx + dx;

						if (py < 0 || ay < 0 || px < 0 || ax < 0 || py >= SIZE || ay >= SIZE || px >= SIZE || ax >= SIZE) continue;
						if (board[py][px] == (two.player * -1) || board[ay][ax] == (two.player * -1)) continue;
					}

					for(int z = 0; z < 5; z++) {
						if(oneCells[z] != EMPTY) firstCellsIndexes.add(oneCellIndexes[z]);
						if(twoCells[z] != EMPTY) secondCellsIndexes.add(twoCellIndexes[z]);
					}

					if(!firstCellsIndexes.removeAll(secondCellsIndexes)) {

						int a = one.count;
						int b = two.count;

						if(a > b) {
							int temp = a;
							a = b;
							b = temp;
						}

						int t = a*10 + b;

						switch (t) {
							case 22:
								threat2x2++;
								move.score += 2000;
								break;
							case 23:
								threat2x3++;
								move.score += 4500;
								break;
							case 33:
								threat3x3++;
								move.score += 8000;
								break;
							case 24:
								threat2x4++;
								move.score += 20000;
								break;
							case 34:
								threat3x4++;
								move.score += 60000;
								break;
							case 44:
								threat4x4++;
								move.score += 777777;
						}

					}
				}
			}
		}

		move.threat4x4 = threat4x4;
		move.threat3x4 = threat3x4;
		move.threat2x4 = threat2x4;
		move.threat4 = threat4;
		move.threat3x3 = threat3x3;
		move.threat2x3 = threat2x3;
		move.threatOpen3 = threatOpen3;
		move.threatClosed3 = threatClosed3;
		move.threat2x2 = threat2x2;

		move.threats = threat2x2 + threatClosed3 + threatOpen3 + threat2x3 + threat3x3 + threat4 + threat2x4 + threat3x4 + threat4x4;

	}

	private static class Tuple {
		int[] cells;
		int[] cellindexes;

		int player;
		int count;

		Tuple(int[] cells, int[] cellindexes) {
			this.cells = cells;
			this.cellindexes = cellindexes.clone();
		}
	}

	private static class Move implements Comparable<Move> {

		boolean FantasticFour = false;

		int x, y;
		long score;

		int threats;

		int threat4x4;
		int threat3x4;
		int threat2x4;
		int threat4;
		int threat3x3;
		int threat2x3;
		int threatOpen3;
		int threatClosed3;
		int threat2x2;


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

		@Override
		public boolean equals(Object otherObject) {
			if (otherObject instanceof Move) {
				Move otherMove = (Move) otherObject;
				if (otherMove.x == this.x && otherMove.y == this.y) {
					return true;
				}
			}
			return false;
		}

		@Override
		public int hashCode() {
			return x + y * 100;
		}

		@Override
		public int compareTo(@NotNull Move otherMove) {
			if (score > otherMove.score) {
				return 1;
			} else if (score < otherMove.score) {
				return -1;
			}
			return 0;
		}

		@Override
		public String toString() {
			return "Move: row " + y + " and col " + x + " with points " + score
					+ " and threats 4x4 " + threat4x4 + " 3x4 " + threat3x4
					+ " 2x4 " + threat2x4 + " Four " + threat4
					+ " 3x3 " + threat3x3 + " 2x3 " + threat2x3
					+ " Open3 " + threatOpen3 + " Closed3 " + threatClosed3
					+ " 2x2 " + threat2x2 + ". Fantastic? " + FantasticFour;
		}
	}

	private static HashMap<Move, LinkedList<Tuple>> moveTuples;
	private static Move[][] moves;
	private static int iAM;

	@Override
	public Location getMove(SimpleBoard board, int player) {

		iAM = player;

		long t1 = System.nanoTime();

		int[][] rilBoard = board.getBoard();

		for(int[] row : rilBoard) {
			for (int i : row) {
				if (i == WHITE) System.out.print("O ");
				if (i == BLACK) System.out.print("X ");
				if (i == EMPTY) System.out.print("- ");
			}
			System.out.println();
		}

		SIZE = rilBoard.length;

		moveTuples = new HashMap<>(SIZE*SIZE);
		moves = new Move[SIZE][SIZE];

		for(int rowi = 0; rowi < SIZE; rowi++) {
			for(int coli = 0; coli < SIZE; coli++) {
				Move m = new Move(coli, rowi);
				moves[rowi][coli] = m;
				moveTuples.put(m, new LinkedList<>());
			}
		}

		evaluation(new Position(rilBoard, player));

		long max = MINUS_INFINITY;
		int bestX = 0;
		int bestY = 0;

		ArrayList<Move> moveList = new ArrayList<>(40);

		for(int rowi = 0; rowi < SIZE; rowi++) {
			for(int coli = 0; coli < SIZE; coli++) {
				Move m = moves[rowi][coli];
				if(rilBoard[rowi][coli] == EMPTY) {
					moveList.add(m);
					if(m.score > max) {
						max = m.score;
						bestY = m.y;
						bestX = m.x;
					}
				}
			}
		}
		Collections.sort(moveList);
		int n = moveList.size();

		Move[] bestMoves = new Move[n];
		for (int i = 0; i < n; i++) {
			bestMoves[i] = moveList.get(i);
		}

		for (int i = 0; i < n; i++) {
			countThreats(bestMoves[i], rilBoard, player);
		}

		System.out.println("This move:");
		System.out.println(moves[bestY][bestX].toString());

		Arrays.sort(bestMoves, new ThreatComparator().reversed());

		//	if (bestMoves[0].score * 1.2 > max) {
		bestX = bestMoves[0].x;
		bestY = bestMoves[0].y;
		//	}
		System.out.println("Alternate move:");
		for (int k = 0; k < min(bestMoves.length, 3); k++) {
			System.out.println(bestMoves[k].toString());
		}
		System.out.println();


		/*for(int rowi = 0; rowi < SIZE; rowi++) {
			for(int coli = 0; coli < SIZE; coli++) {
				Move m = moves[rowi][coli];
				if (rilBoard[rowi][coli] != EMPTY) m.score = 0;
				System.out.printf("%6d", m.score);
			}
			System.out.println();
		}*/

//		System.out.println(bestY + " row and col is " + bestX);


		long t2 = System.nanoTime();

		System.out.println("Me NanoSeconds to decide: " + (t2-t1));


		return new Location(bestY, bestX);
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

	static int min(int a, int b) {
		return (a < b) ? a : b;
	}

	class ThreatComparator implements Comparator<Move> {
		@Override
		public int compare(Move a, Move b) {

			if (a.threats == 0 && b.threats == 0) {
				return a.compareTo(b);
			} else if (b.threats == 0) {
				return 1;
			} else if (a.threats == 0) {
				return -1;
			}

			// TODO careful here
			if (a.FantasticFour) return 1;
			if (b.FantasticFour) return -1;

			if (a.threat4x4 > b.threat4x4) {
				return 1;
			} else if (a.threat4x4 < b.threat4x4) {
				return -1;
			}

			if (a.threat3x4 > b.threat3x4) {
				return 1;
			} else if (a.threat3x4 < b.threat3x4) {
				return -1;
			}

			if (a.threat2x4 > b.threat2x4) {
				return 1;
			} else if (a.threat2x4 < b.threat2x4) {
				return -1;
			}

			if (a.threat4 > b.threat4) {
				return 1;
			} else if (a.threat4 < b.threat4) {
				return -1;
			}

			if (a.threatOpen3 > b.threatOpen3) {
				return 1;
			} else if (a.threatOpen3 < b.threatOpen3) {
				return -1;
			}

			if (a.threat3x3 > b.threat3x3) {
				return 1;
			} else if (a.threat3x3 < b.threat3x3) {
				return -1;
			}

			if (a.threat2x3 > b.threat2x3) {
				return 1;
			} else if (a.threat2x3 < b.threat2x3) {
				return -1;
			}


			if (a.threatClosed3 > b.threatClosed3) {
				return 1;
			} else if (a.threatClosed3 < b.threatClosed3) {
				return -1;
			}

			if (a.threat2x2 > b.threat2x2) {
				return 1;
			} else if (a.threat2x2 < b.threat2x2) {
				return -1;
			}

			return a.compareTo(b);
		}
	}

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		int[][] board = new int[10][10];

		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				String s = in.next();
				switch (s) {
					case "O":
						board[i][j] = WHITE;
						break;
					case "X":
						board[i][j] = BLACK;
						break;
					case "-":
						board[i][j] = EMPTY;
						break;
				}
			}
		}

		SimpleBoard simpleBoard = new SimpleBoard(board);
		EduWeakStrategy ai = new EduWeakStrategy();
		ai.getMove(simpleBoard, WHITE);
		System.out.println();
	}

}

