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

	private long evaluation(int[] board, int player) {

		return gradeDiagsUp(board, player) + gradeDiagsDown(board, player)
				+ gradeRows(board, player) + gradeColumns(board, player);
	}

	private static long gradeDiagsDown(int[] board, int player) {

		long score = 0;

		int[] cells = new int[5];
		int[] cellindexes = new int[5];

		Tuple t;
		Move m;
		int rd, rc;

		for (int rowi = 0; rowi < SIZE - 4; rowi++) {
			rd = rowi * 100;
			for (int coli = 0; coli < SIZE - 4; coli++) {

				rc = rd + coli;

				cells[0] = board[rc];
				cells[1] = board[rc+101];
				cells[2] = board[rc+202];
				cells[3] = board[rc+303];
				cells[4] = board[rc+404];

				cellindexes[0] = rc;
				cellindexes[1] = rc+101;
				cellindexes[2] = rc+202;
				cellindexes[3] = rc+303;
				cellindexes[4] = rc+404;

				t = new Tuple(cells.clone(), cellindexes);

				score = gradeTuple(t, player);
				if (score == 0) continue;

				m = moves[rc];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc + 101];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc + 202];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc + 303];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc + 404];
				m.tuples.add(t);
				m.score += score;
			}
		}
		return score;
	}

	private static long gradeDiagsUp(int[] board, int player) {

		long score = 0;

		int[] cells = new int[5];
		int[] cellindexes = new int[5];

		Tuple t;
		Move m;

		int rc;
		for (int coli = 4; coli < SIZE; coli++) {
			for (int rowi = 0; rowi + 4 < SIZE; rowi++) {
				rc = rowi * 100 + coli;


				cells[0] = board[rc];
				cells[1] = board[rc + 100 - 1];
				cells[2] = board[rc + 200 - 2];
				cells[3] = board[rc + 300 - 3];
				cells[4] = board[rc + 400 - 4];

				cellindexes[0] = rc;
				cellindexes[1] = rc + 100 - 1;
				cellindexes[2] = rc + 200 - 2;
				cellindexes[3] = rc + 300 - 3;
				cellindexes[4] = rc + 400 - 4;

				t = new Tuple(cells.clone(), cellindexes);

				score = gradeTuple(t, player);
				if (score == 0) continue;

				m = moves[rc];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc + 100 - 1];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc + 200 - 2];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc + 300 - 3];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc + 400 - 4];
				m.tuples.add(t);
				m.score += score;
			}
		}
		return score;
	}

	/**
	 * Check every row.
	 */
	private static long gradeRows(int[] board, int player) {

		long score = 0;
		int[] cells = new int[5];
		int[] cellindexes = new int[5];

		Tuple t;
		Move m;
		int rd, rc;
		for (int rowi = 0; rowi < SIZE; rowi++) {
			rd = rowi * 100;

			for (int coli = 0; coli < SIZE - 4; coli++) {

				rc = rd + coli;

				cells[0] = board[rc];
				cells[1] = board[rc + 1];
				cells[2] = board[rc + 2];
				cells[3] = board[rc + 3];
				cells[4] = board[rc + 4];

				cellindexes[0] = rc;
				cellindexes[1] = rc+1;
				cellindexes[2] = rc+2;
				cellindexes[3] = rc+3;
				cellindexes[4] = rc+4;

				t = new Tuple(cells.clone(), cellindexes);

				score = gradeTuple(t, player);
				if (score < 400) continue;

				m = moves[rc];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc+1];
				m.tuples.add(t);;
				m.score += score;

				m = moves[rc+2];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc+3];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc+4];
				m.tuples.add(t);
				m.score += score;
			}
		}
		return score;
	}

	/**
	 * Check every column.
	 */
	private static long gradeColumns(int[] board, int player) {

		long score = 0;
		int[] cells = new int[5];
		int[] cellindexes = new int[5];

		Tuple t;
		Move m;

		int rc;
		for (int coli = 0; coli < SIZE; coli++) {
			for (int rowi = 0; rowi < SIZE-4; rowi++) {

				rc = rowi * 100 + coli;

				cells[0] = board[rc];
				cells[1] = board[rc+100];
				cells[2] = board[rc+200];
				cells[3] = board[rc+300];
				cells[4] = board[rc+400];

				cellindexes[0] = rc;
				cellindexes[1] = rc+100;
				cellindexes[2] = rc+200;
				cellindexes[3] = rc+300;
				cellindexes[4] = rc+400;

				t = new Tuple(cells.clone(), cellindexes);

				score = gradeTuple(t, player);
				if (score < 400) continue;

				m = moves[rc];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc+100];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc+200];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc+300];
				m.tuples.add(t);
				m.score += score;

				m = moves[rc+400];
				m.tuples.add(t);
				m.score += score;


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

	private static void countThreats(Move move, int[] board, int player) {
		ArrayList<Tuple> list = move.tuples;
		Tuple[] tuples = new Tuple[list.size()];

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

			if (t.count < 3) continue;

			if (t.count > 4) {
				threat4++;
				if(!move.FantasticFour) {
					move.FantasticFour = t.player == player;
				}
				move.score += 10000;
				continue;
			}

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

				if (board[ay*100 + ax] != t.player * -1) {
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

				if (board[py*100 + px] != t.player * -1) {
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
					} else if (board[py*100 + px] != t.player * -1) {
						threat = true;
					} else {

						int ly = cellIndexes[4] / 100;
						int lx = cellIndexes[4] % 100;

						int ay = ly + dy;
						int ax = lx + dx;

						if (ay < 0 || ax < 0 || ay >= SIZE || ax >= SIZE)
							continue;

						if (board[ay*100 + ax] != t.player * -1) {
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
					} else if (board[py*100 + px] != t.player * -1) {
						closedthreat = true;
					} else {

						int ly = cellIndexes[4] / 100;
						int lx = cellIndexes[4] % 100;

						int ay = ly + dy;
						int ax = lx + dx;

						if (ay < 0 || ax < 0 || ay >= SIZE || ax >= SIZE)
							continue;

						if (board[ay*100 + ax] != t.player * -1) {
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
					} else if (board[ay*100 + ax] != t.player * -1) {
						closedthreat = true;
					} else {
						int fy = cellIndexes[0] / 100;
						int fx = cellIndexes[0] % 100;

						int py = fy - dy;
						int px = fx - dx;

						if (py < 0 || px < 0 || py >= SIZE || px >= SIZE) continue;

						if (board[py*100 + px] != t.player * -1) {
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
					} else if (board[ay*100 + ax] != t.player * -1) {
						threat = true;
					} else {
						int fy = cellIndexes[0] / 100;
						int fx = cellIndexes[0] % 100;

						int py = fy - dy;
						int px = fx - dx;

						if (py < 0 || px < 0 || py >= SIZE || px >= SIZE) continue;

						if (board[py*100 +px] != t.player * -1) {
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
					} else if (board[ay*100 + ax] != t.player * -1) {
						closedthreat = true;
					} else {
						int fy = cellIndexes[0] / 100;
						int fx = cellIndexes[0] % 100;

						int py = fy - dy;
						int px = fx - dx;

						if (py < 0 || px < 0 || py >= SIZE || px >= SIZE) continue;

						if (board[py*100 + px] != t.player * -1) {
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
					} else if (board[ay * 100 + ax] != t.player * -1) {
						threat = true;
					} else {
						int fy = cellIndexes[0] / 100;
						int fx = cellIndexes[0] % 100;

						int py = fy - dy;
						int px = fx - dx;

						if (py < 0 || px < 0 || py >= SIZE || px >= SIZE) continue;

						if (board[py*100 + px] != t.player * -1) {
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
					} else if (board[py*100 + px] != t.player * -1) {
						threat = true;
					} else {

						int ly = cellIndexes[4] / 100;
						int lx = cellIndexes[4] % 100;

						int ay = ly + dy;
						int ax = lx + dx;

						if (ay < 0 || ax < 0 || ay >= SIZE || ax >= SIZE)
							continue;

						if (board[ay*100 + ax] != t.player * -1) {
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
					} else if (board[py*100 + px] != t.player * -1) {
						closedthreat = true;
					} else {

						int ly = cellIndexes[4] / 100;
						int lx = cellIndexes[4] % 100;

						int ay = ly + dy;
						int ax = lx + dx;

						if (ay < 0 || ax < 0 || ay >= SIZE || ax >= SIZE)
							continue;

						if (board[ay*100 + ax] != t.player * -1) {
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
			// first tuple here

			Tuple one = tuples[i];
			if (one.count < 2) continue;

			int[] oneCellIndexes = one.cellindexes;
			int[] oneCells = one.cells;

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
				if (board[py * 100 + px] == (one.player * -1) || board[ay*100 + ax] == (one.player * -1)) continue;
			}

			for(int j = i; j < c; j++) { // j = i so we have only unique pairs, only 0-2 and no 2-0 indexes
				if(i != j) {

					Tuple two = tuples[j];

					if (two.count < 2 || (one.player != two.player) ) {
						continue;
					}

					ArrayList<Integer> firstCellsIndexes = new ArrayList<>(5);
					ArrayList<Integer> secondCellsIndexes = new ArrayList<>(5);

					int[] twoCellIndexes = two.cellindexes;

					int[] twoCells = two.cells;

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
						if (board[py * 100 + px] == (two.player * -1) || board[ay * 100 + ax] == (two.player * -1)) continue;
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

		ArrayList<Tuple> tuples = new ArrayList<>(25);

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
		public int compareTo(Move otherMove) {
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

	private static Move[] moves;
	private static int iAM;

	@Override
	public Location getMove(SimpleBoard board, int player) {

		iAM = player;

		long t1 = System.nanoTime();

		int[][] badBoard = board.getBoard();

		SIZE = badBoard.length;

		moves = new Move[SIZE*100*SIZE];
		int[] disBoard = new int[SIZE*100*SIZE];

		for(int rowi = 0; rowi < SIZE; rowi++) {
			int rd = rowi * 100;
			for(int coli = 0; coli < SIZE; coli++) {
				Move m = new Move(coli, rowi);
				moves[rd + coli] = m;
				disBoard[rd + coli] = badBoard[rowi][coli];
			}
		}

		evaluation(disBoard, player);

		ArrayList<Move> moveList = new ArrayList<>(SIZE*SIZE+1);

		for(int rowi = 0; rowi < SIZE; rowi++) {
			int rd = rowi * 100;
			for(int coli = 0; coli < SIZE; coli++) {
				Move m = moves[rd + coli];
				if(disBoard[rd+coli] == EMPTY) {
					moveList.add(m);
				}
			}
		}

		int n = moveList.size();
		Move[] bestMoves = new Move[n];
		for(Move m : moveList) {
			bestMoves[--n] = m;
		}

		for (int i = 0; i < n; i++) {
			countThreats(bestMoves[i], disBoard, player);
		}

		Arrays.sort(bestMoves, new ThreatComparator().reversed());

		int bestX = bestMoves[0].x;
		int bestY = bestMoves[0].y;

//		for (int k = 0; k < min(bestMoves.length, 3); k++) {
//			System.out.println(bestMoves[k].toString());
//		}
//		System.out.println();

		long t2 = System.nanoTime();

		//System.out.println("Me NanoSeconds to decide: " + (t2-t1));


		return new Location(bestY, bestX);
	}

	@Override
	public String getName() {
		return "Eduard The Great";
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

			// todo maybe should be last
			if (a.threat2x2 > b.threat2x2) {
				return 1;
			} else if (a.threat2x2 < b.threat2x2) {
				return -1;
			}

			if (a.threatClosed3 > b.threatClosed3) {
				return 1;
			} else if (a.threatClosed3 < b.threatClosed3) {
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