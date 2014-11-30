import java.util.ArrayList;
import java.util.List;

import javafx.beans.Observable;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

/**
 * @author Ago
 *
 * Holds the state of the game.
 */
public class Game {
	
	public static final int WIN_COUNT = 5;
	
	private Player playerWhite;
	private Player playerBlack;
	
	private ReadOnlyObjectWrapper<Player> currentPlayer;
	private ReadOnlyObjectWrapper<GameStatus> gameStatus;
	
	
	// Board related stuff
	//private final Board board;
	private final int width;
	private final int height;
	private final List<List<ReadOnlyObjectWrapper<SquareState>>> board;
	
	
	/**
	 * Initialization with the default players:
	 * human vs student strategy
	 * @param height
	 * @param width
	 */
	public Game(int height, int width) {
		this(new Player("human"), new ComputerPlayer(new StudentStrategy()), height, width);
	}

	public Game(Player playerWhite, Player playerBlack, int height, int width) {
		this.playerWhite = playerWhite;
		this.playerBlack = playerBlack;
		// set square states for the players
		playerWhite.setSquareState(SquareState.WHITE);
		playerBlack.setSquareState(SquareState.BLACK);
		
		currentPlayer = new ReadOnlyObjectWrapper<Player>(this, "currentPlayer", null);
		
		
		// init the board
		this.width = width;
		this.height = height;
		List<Observable> allSquares = new ArrayList<Observable>();
		board = new ArrayList<List<ReadOnlyObjectWrapper<SquareState>>>(height);
		for (int i = 0; i < height; i++) {
			List<ReadOnlyObjectWrapper<SquareState>> row = new ArrayList<ReadOnlyObjectWrapper<SquareState>>(this.width);
			for (int j = 0; j < width; j++) {
				ReadOnlyObjectWrapper<SquareState> square = new ReadOnlyObjectWrapper<SquareState>(SquareState.EMPTY);
				row.add(square);
				allSquares.add(square);
			}
			board.add(row);
		}
		//System.out.println(board.squareStateProperty(new Location(0, 0)));
		gameStatus = new ReadOnlyObjectWrapper<GameStatus>(this, "gameStatus", GameStatus.OPEN);
		
		// binding for game status
		ObjectBinding<GameStatus> gameStatusBinding = new ObjectBinding<GameStatus>() {

			{
				super.bind(allSquares.toArray(new Observable[allSquares.size()]));
			}
			
			@Override
			protected GameStatus computeValue() {
				return getGameStatus();
			}
			
		};
		gameStatus.bind(gameStatusBinding);
		
	}
	
	public void start(Player startingPlayer) {
		currentPlayer.set(startingPlayer);
	}
	
	public void start() {
		// player with O starts
		start(playerWhite);
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public SimpleBoard getSimpleBoard() {
		Location lastMove = null;
		int[][] simpleBoard = new int[height][width];
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				SquareState state = squareStateProperty(row, col).get();
				simpleBoard[row][col] = state == SquareState.EMPTY ? SimpleBoard.EMPTY :
					(state == SquareState.WHITE ? SimpleBoard.PLAYER_WHITE : SimpleBoard.PLAYER_BLACK);
			}
		}
		SimpleBoard board = new SimpleBoard(simpleBoard);
		return board;
	}
	
	public void makeMove(Location location) {
		if (!(currentPlayer.get() instanceof ComputerPlayer)) {
			// only human player can use this shortcut
			makeMove(currentPlayer.get(), location);
		} else {
			throw new IllegalArgumentException("It's computer's turn, pass the player to makeMove()");
		}
	}
	
	public void makeMove(Player player, int row, int column) {
		makeMove(player, new Location(row, column));
	}
	
	public void makeMove(Player player, Location location) {
		//System.out.println("make move " + player + "@" + location);
		if (player != currentPlayer.get()) {
			// TODO: specific exceptions?
			throw new IllegalArgumentException("It is not " + player + "'s turn!");
		}
		if (location == null) {
			throw new IllegalArgumentException("Location is not set");
		}
		final ReadOnlyObjectWrapper<SquareState> squareState = board.get(location.getRow()).get(location.getColumn());
		
		if (squareState.get() != SquareState.EMPTY) {
			throw new IllegalArgumentException(location + " is already occupied by " + squareState.get());
		}
		if (player == playerWhite) {
			squareState.set(SquareState.WHITE);
		} else {
			squareState.set(SquareState.BLACK);
		}
		if (gameStatus.get() == GameStatus.OPEN) {
			currentPlayer.set(player == playerBlack ? playerWhite : playerBlack);
		}
	}
	
	SimpleBoard simpleBoard;
	int[][] simpleBoardBoard;
	private GameStatus getGameStatus() {
		simpleBoard = getSimpleBoard();
		simpleBoardBoard = simpleBoard.getBoard();
		int rows = simpleBoard.getHeight();
		int cols = simpleBoard.getWidth();
		int winningPlayer = 0;
		boolean hasEmpty = false;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				int player = simpleBoardBoard[row][col];
				if (player != SimpleBoard.EMPTY) {
					if (row <= rows - WIN_COUNT)  {// down
						if (countFive(row, col, 1, 0)) { // just down
							return GameStatus.getWinnerFromSimpleBoardValue(player);
						}
						if (col > 3 && countFive(row, col, 1, -1)) { // diag down and left
							return GameStatus.getWinnerFromSimpleBoardValue(player);
						}
					}
					if (col <= cols - WIN_COUNT) { // right
						if (countFive(row, col, 0, 1)) { // just right
							return GameStatus.getWinnerFromSimpleBoardValue(player);
						}
						if (row <= rows - WIN_COUNT && countFive(row, col, 1, 1)) { // right and down
							return GameStatus.getWinnerFromSimpleBoardValue(player);
						}
					}
				} else {
					hasEmpty = true;
				}
			}
		}
		if (hasEmpty) return GameStatus.OPEN;
		return GameStatus.DRAW;
	}
	
	private boolean countFive(int row, int column, int deltaRow, int deltaColumn) {
		int player = simpleBoardBoard[row][column];
		for (int i = 1; i < WIN_COUNT; i++) {
			if (simpleBoardBoard[row + i * deltaRow][column + i * deltaColumn] != player) return false;
		}
		return true;
	}
	
	public ReadOnlyObjectProperty<GameStatus> gameStatusProperty() {
		return gameStatus.getReadOnlyProperty();
	}
	
	public ReadOnlyObjectProperty<Player> currentPlayerProperty() {
		return currentPlayer.getReadOnlyProperty();
	}
	
	public ReadOnlyObjectProperty<SquareState> squareStateProperty(int row, int column) {
		return squareStateProperty(new Location(row, column));
	}
	
	public ReadOnlyObjectProperty<SquareState> squareStateProperty(Location location) {
		return board.get(location.getRow()).get(location.getColumn()).getReadOnlyProperty();
	}
}
