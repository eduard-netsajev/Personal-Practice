import java.util.*;

public class StudentStrategy implements ComputerStrategy {

	// Class for holding a result of some move (score, x, y)
	public static class MoveScore {
		long points;
		int x;
		int y;

		// Create a data point of score and move data
		MoveScore(long points, int x, int y) {
			this.points = points;
			this.x = x;
			this.y = y;
		}

		//No move -> -1 -1 i.e. move somewhere as far as possible TODO it's just an assumption
		MoveScore(long points) {
			this(points, -1, -1);
		}

		MoveScore(MoveScore move) {
			this.points = move.points;
			this.x = move.x;
			this.y = move.y;
		}

		public static class Compare implements Comparator<MoveScore> {

			public int compare(MoveScore thisMove, MoveScore otherMove) {
				long thisPoints = thisMove.points;
				long otherPoints = otherMove.points;

				if(otherPoints > thisPoints) {
					return 1;
				} else { //TODO something doing with their delta. Seems like some bug can be found here
					long delta;
					int var10000 = (delta = otherPoints - thisPoints) == 0L ? 0 : (delta < 0L ? -1 : 1);
					return var10000 < 0?-1:0;

				}
			}
		}
	}

	public static class GameLogic {
		static boolean fastSearch = false;

		static int[] bestMove(int[][] board, int player) {
			int boardRows;
			int[] bestMove;
			boardRows = board.length;

			int boardColumns = board[0].length;
			Constants.ROWS = boardRows;
			Constants.COLUMNS = boardColumns;

			//TODO determine what kind of board it is
			int[][] invertedBoard = new int[boardRows][boardColumns];

			int piecesCount = 0;
			int coli = 0;

			int rowi = 0;
			while(coli < boardColumns) {
				rowi = 0;

				while(rowi < boardRows) {

					invertedBoard[coli][rowi] = board[rowi][coli];
					if(board[coli][rowi] != 0) {
						++piecesCount;
					}

					++rowi;
				}
				coli++;
			}

			if(piecesCount <= 2) {
				fastSearch = false;
			}

			int depth = 6; // TODO DEPPTH

			int searchRadius;

			if(boardRows * boardColumns > 150) {
				searchRadius = 2;
			} else {
				searchRadius = 5;
			}

			if(fastSearch) {
				--depth;
				++searchRadius;
			}

			AI ai = new AI(depth, depth - 2, searchRadius);
			bestMove = ai.bestMove(invertedBoard, player);
			if(ai.searchCount < 15 && ai.stop) {
				fastSearch = true;
			}
			return bestMove;
		}
	}

	public static class Constants {
		static int COLUMNS = 10;
		static int ROWS = 10;
		static final int WHITE = -1;
		static final int BLACK = 1;
		static final int EMPTY = 0;
		static final int TIME = 9500;
		static final long INFINITY = Long.MAX_VALUE;
		static final long MINUS_INFINITY = Long.MIN_VALUE;

	}

	// Board Evaluation function
	public static class GameStatus {
		int[][] board;
		int player;
		int opponent;

		long getGameStatus(int[][] board, int player) {
			this.board = board;
			this.player = player;

			this.opponent = AI.getOpponent(player);
			long points = 0L;

			for(int coli = AI.lBound; coli < AI.rBound; ++coli) {
				for(int rowi = AI.uBound; rowi < AI.dBound; ++rowi) {
					int nearStones = this.countNearBy(coli, rowi, 5);

					points += countToPoints(nearStones);
				}
			}

			return points;
		}

		private int countNearBy(int var1, int var2, int var3) {
			int var4;
			int var5;
			var4 = 0;
			var5 = -1;

			while(var5 <= 1) {
				for(int var6 = -1; var6 <= 1; ++var6) {


					if(var5 != 0 || var6 != 0) {
						int var7 = this.countDirection(var1, var2, var3, var5, var6, this.player, this.opponent);
						if(var7 > var4) {
							var4 = var7;
						}
					}
				}

				++var5;
			}

			return var4;
		}

		// TODO scrutinize this
		private int countDirection(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
			int var8 = var1 + var3 * var4;

			int var9 = var2 + var3 * var5;

			if(var8 <= Constants.COLUMNS && var8 >= -1 && var9 <= Constants.COLUMNS && var9 >= -1) {
				var9 = 0;

				for(var8 = 0; var8 < var3; ++var8) {
					int var10 = this.board[var1 + var8 * var4][var2 + var8 * var5];
					if(var10 != 0) {

						if(var10 == var6) {
							++var9;
						} else if(var10 == var7) {
							return 0;
						}
					}
				}

				return var9;
			} else {
				return 0;
			}
		}

		// Seems like this is the evaluation function (points = 100 ^ (stones in a row + 1)
		static long countToPoints(int stones) {
			long var1 = 100L;

			for(int var3 = 0; var3 < stones; ++var3) {
				var1 *= 100L;
			}

			return var1;
		}
	}

	public static class AI {
		boolean stop;
		MoveScore result;
		int turnPlayer;
		int maxDepth;
		int sortDepth;

		// Search parameters
		static int lBound; // Left border
		static int uBound; // Upper border
		static int rBound; // Right border
		static int dBound; // Lower border
		static int searchRadius;
		int searchCount;

		AI() {
			this.stop = false;
			this.searchCount = 0;
			int var10000 = lBound = Constants.COLUMNS / 2;
			int var1 = Constants.ROWS / 2;
			uBound = var1;
			rBound = var10000;
			dBound = var1;
			this.maxDepth = 6;
			this.sortDepth = 2;
			searchRadius = 3;
		}

		AI(int maxDepth, int sortDepth) {
			this();
			this.maxDepth = maxDepth;
			this.sortDepth = sortDepth;
		}

		AI(int maxDepth, int sortDepth, int searchRadius) {
			this();
			this.maxDepth = maxDepth;
			this.sortDepth = sortDepth;
			AI.searchRadius = searchRadius;
		}

		int[] bestMove(int[][] board, int player) {
			this.turnPlayer = player;
			this.stop = false;

			this.timer();
			int boardRows = Constants.ROWS;
			int[] coordinats;

			int var8 = Constants.COLUMNS;
			if(board[boardRows / 2][var8 / 2] == 0) {
				int[] var6 = new int[]{boardRows / 2, var8 / 2};
				return var6;
			} else {
				setAllBounds(board);
				this.result = this.alphabeta(board, this.maxDepth, new MoveScore(Constants.MINUS_INFINITY),
						new MoveScore(Constants.INFINITY), true, player);
				if(this.result == null || this.result.x == -1) {
					this.result = (MoveScore)this.getList(board, player, true).get(0);
				}

				coordinats = new int[]{this.result.x, this.result.y};
				return coordinats;
			}

		}

		private MoveScore alphabeta(int[][] board, int var2, MoveScore var3, MoveScore var4, boolean var5, int enemyPlayer) {
			if(this.stop) {
				return new MoveScore(Constants.MINUS_INFINITY);
			}

			if(!var5) {
				enemyPlayer = getOpponent(enemyPlayer);
			}

			if(var2 == 0) {
				return new MoveScore(this.gameStatus(board, this.turnPlayer));
			} else {
				List var7;
				if(var2 > this.sortDepth) {
					var7 = this.getList(board, enemyPlayer, true);
				} else {
					var7 = this.getList(board, enemyPlayer, false);
				}

				if(var7.size() == 0) {
					return new MoveScore(this.gameStatus(board, this.turnPlayer));
				} else {
					MoveScore enemyMoveScore;
					if(var5) {
						Iterator var12 = var7.iterator();

						do {
							boolean var13 = var12.hasNext();

							if(!var13) {
								break;
							}

							enemyMoveScore = (MoveScore)var12.next();
							if(var2 == this.maxDepth) {
								this.searchCount++;
							}

							board[enemyMoveScore.x][enemyMoveScore.y] = enemyPlayer;
							MoveScore var14 = this.alphabeta(board, var2 - 1, var3, var4, false, enemyPlayer);
							if(var14.points > var3.points) {
								var3 = new MoveScore(var14.points, enemyMoveScore.x, enemyMoveScore.y);
							}

							board[enemyMoveScore.x][enemyMoveScore.y] = 0;
						} while(var4.points > var3.points && !this.stop);

						return var3;
					} else {
						Iterator var8 = var7.iterator();

						while(var8.hasNext()) {
							MoveScore var11 = (MoveScore)var8.next();
							board[var11.x][var11.y] = enemyPlayer;
							enemyMoveScore = this.alphabeta(board, var2 - 1, var3, var4, true, enemyPlayer);
							if(enemyMoveScore.points < var4.points) {
								var4 = enemyMoveScore;
							}

							board[var11.x][var11.y] = 0;
							if(var4.points <= var3.points) {
								break;
							}
						}

						return var4;
					}
				}
			}
		}

		List<MoveScore> getList(int[][] board, int player, boolean sortDepthExceeded) { // TODO sortDepthExceeded is wrong
			GameStatus gameStatus = new GameStatus();

			int opponent = getOpponent(player);
			ArrayList var5 = new ArrayList();

			for(int x = lBound; x < rBound; ++x) {
				for(int y = uBound; y < dBound; ++y) {
					if(board[x][y] == 0) {
						long points;
						if(sortDepthExceeded) {
							board[x][y] = player;
							long var10000 = gameStatus.getGameStatus(board, player);
							board[x][y] = opponent;
							points = Math.max(var10000, gameStatus.getGameStatus(board, opponent));
							int[] var12 = board[x];

							var12[y] = 0;
						} else {
							points = 0L;
						}

						var5.add(new MoveScore(points, x, y));
					}
				}
			}

			if(sortDepthExceeded) {
				Collections.sort(var5, new MoveScore.Compare());
			}

			return var5;
		}

		private long gameStatus(int[][] board, int player) {
			GameStatus gameStatus = new GameStatus();

			int enemy = getOpponent(player);
			return gameStatus.getGameStatus(board, player) - gameStatus.getGameStatus(board, enemy);
		}

		static int getOpponent(int player) {
			return player == Constants.WHITE ? Constants.BLACK : Constants.WHITE;
		}

		private void timer() {
			(new Timer()).schedule(new TimerTask() {
				final AI thisAI = AI.this;

				public void run() {
					this.thisAI.stop = true;
					System.out.println("STOP");
				}
			}, Constants.TIME);
		}

		static void setBounds(int var0, int var1) {
			int var2 = searchRadius;
			int var3 = var0 - var2;

			if(var3 < lBound) {
				lBound = var0 - var2;
			}

			if(var1 - var2 < uBound) {
				uBound = var1 - var2;
			}

			if(var0 + var2 > rBound) {
				rBound = var0 + var2;
			}

			if(var1 + var2 > dBound) {
				dBound = var1 + var2;
			}

			if(lBound < 0) {
				lBound = 0;
			}

			if(uBound < 0) {
				uBound = 0;
			}

			var3 = rBound;
			var2 = Constants.COLUMNS;
			if(var3 > var2) {
				rBound = Constants.COLUMNS;
			}

			if(dBound > Constants.ROWS) {
				dBound = Constants.ROWS;
			}

		}

		static void setAllBounds(int[][] var0) {
			int var1 = var0.length;

			int var2 = var0[0].length;

			for(int var3 = 0; var3 < var1; ++var3) {
				for(int var4 = 0; var4 < var2; ++var4) {
					if(var0[var3][var4] != 0) {
						setBounds(var3, var4);
					}
				}
			}
		}
	}


	@Override
	public Location getMove(SimpleBoard board, int player) {

		long t1 = System.nanoTime();

		int[] loc = getMove(board.getBoard(), player);

		System.out.println("\n Enemy move at " + loc[1] + " at row " + loc[0] + "\n");


		long t2 = System.nanoTime();

		System.out.println("Enemy NanoSeconds to decide: " + (t2-t1));


		return new Location(loc[0], loc[1]);
	}

	public int[] getMove(int[][] board, int player) {
		int[] var10000 = GameLogic.bestMove(board, player);
		int[] var4 = new int[2];
		int var3 = var10000[1];
		var4[0] = var3;
		var3 = var10000[0];
		var4[1] = var3;
		return var4;
	}

	@Override
	public String getName() {
		return "Eduard";
	}

}

