import java.util.Scanner;

/**
 * Tic-Tac-Toe game.
 *
 * The one-dimensional board is indexed as follows:
 *
 * +---+---+---+
 * | 0 | 1 | 2 |
 * +---+---+---+
 * | 3 | 4 | 5 |
 * +---+---+---+
 * | 6 | 7 | 8 |
 * +---+---+---+
 *
 * So, the following state:
 *
 * +---+---+---+
 * | X |   |   |
 * +---+---+---+
 * |   | O | X |
 * +---+---+---+
 * |   |   |   |
 * +---+---+---+
 *
 * is given as an array:
 * {1, 0, 0, 0, -1, 1, 0, 0, 0}
 * where 1 indicates a player stone ("X") and
 * -1 indicates a computer stone ("O")
 */
public class TicTacToe {

    /**
     * Length of one line.
     * Or one side of the board.
     * Or whatever it is when we need to use number 3
     * but dumb CheckStyle plugin doesn't let us.
     */
    private static final int LINE_SIZE = 3;

    /**
     * Number of cells in the board.
     *
     *@see TicTacToe#LINE_SIZE
     */
    private static final int CELL_COUNT = 9;

    /**
     * Game starting point.
     *
     * @param args console arguments.
     */
    public static void main(String[] args) {
        int[] board = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        boolean playingGame = true;

        int playerTurn = (int) Math.round(Math.random());
        if (playerTurn == 0) {
            playerTurn--;
        }

        printBoard(board, 1);

        while (playingGame) {
            switch (checkWin(board)) {
                case -1:
                    System.out.println("Lost to a dumb machine, smart kid");
                    playingGame = false;
                    break;
                case 1:
                    System.out.println("Won a dumb machine, "
                           + "what an accomplishment /s");
                    playingGame = false;
                    break;
                default:
                    if (boardFilled(board)) {
                        System.out.println("Draw. Can't win this, huh?");
                        playingGame = false;
                    } else {
                        if (playerTurn == 1) {
                            nextMove(board, 1);
                        } else {
                            nextMove(board, -1);
                        }
                        playerTurn = -playerTurn;
                        printBoard(board);
                    }
            }
        }
    }

    /**
     * This function is for both sides to make their moves.
     * For the player side, it checks whether the chosen cell
     * is empty. If it is not, display an error message and ask
     * again. Checking the AI move is not needed, it is smart.
     *
     * @param board Current state of the board
     * @param player Who is making the move. 1 for the player
     */
    public static void nextMove(int[] board, int player) {
        if (player == 1) {
            while (true) {
                int move = readInput() - 1;
                if (board[move] != 0) {
                    System.out.println("Sorry, this cell is already marked.");
                } else {
                    board[move] = 1;
                    return;
                }
            }
        } else {
            int move = makeMove(board);
            board[move] = -1;
            System.out.println("Machine: " + ++move);
        }
    }

    /**
     * Takes the current state of the board
     * and returns the cell index where computer
     * makes its move.
     *
     * @param board Current state of the board.
     * @return Cell index where the computer makes its move.
     */
    public static int makeMove(int[] board) {

        int winningMove = findWinningMove(board);
        if (winningMove > -1) {
            System.out.println("Machine: you dun goofed.");
            return winningMove;
        }

        int survivingMove = findSurvivingMove(board);
        if (survivingMove > -1) {
            System.out.println("Machine: you won't win this");
            return survivingMove;
        }

        if (board[CENTER_POINT] == 0) {
            return CENTER_POINT;
        }

        int cornerPoint = findCornerPoint(board);
        if (cornerPoint > -1) {
            return cornerPoint;
        }

        int randomEmptyCell;
        do {
            randomEmptyCell = (int) (Math.random() * board.length);
        } while (board[randomEmptyCell] != 0);

        return randomEmptyCell;
    }

    /**
     * Reads a number from the standard input and returns it.
     * If the input is not appropriate to our desires then inform the user
     * about it. Ask again and again until we get what we want.
     *
     * @return Number read from the input
     */
    public static int readInput() {
        final String errorMessage = "Invalid input. Please try again. Only "
                + "digits 1-9 are allowed.\n\n";
        final String inputMessage = "Input the cell number > ";

        Scanner scanner = new Scanner(System.in);

        int cellNumber;
        System.out.print(inputMessage);

        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.print(errorMessage + inputMessage);
                scanner.nextLine();
            }
            cellNumber = scanner.nextInt();
            if (cellNumber < 1 || cellNumber > CELL_COUNT) {
                System.out.print(errorMessage + inputMessage);
            } else {
                return cellNumber;
            }
        }
    }

    /**
     * Checks the board and returns which player
     * has winning combination.
     * @param board Current state of the board
     * @return The indicator of the player who has winning combination.
     * If there are no winning combinations, 0 is returned.
     */
    public static int checkWin(int[] board) {
        return getWinner(board);
    }

    /**
     * Prints the board into console.
     *
     * @param board Current state of the board
     */
    public static void printBoard(int[] board) {
        char[] boardMarks = new char[CELL_COUNT];

        for (int i = 0; i < CELL_COUNT; i++) {
            switch(board[i]) {
                case 1:
                    boardMarks[i] = 'X';
                    break;
                case -1:
                    boardMarks[i] = 'O';
                    break;
                default:
                    boardMarks[i] = ' ';
            }
        }
        printBoard(boardMarks);
    }

    /**
     * Prints out the board.
     *
     * @param board Current state of the board
     * @param showNumbers if set, show the cell numbers inside them
     */
    public static void printBoard(int[] board, int showNumbers) {
        if (showNumbers == 1) {
            char[] boardMarks = new char[CELL_COUNT];
            for (int i = 0; i < CELL_COUNT;) {
                boardMarks[i] = (char) ('0' + ++i);
            }
            printBoard(boardMarks);
        } else {
            printBoard(board);
        }
    }

    /**
     * Prints out the board in a user-friendly format.
     *
     * @param boardMarks Current content of the board
     */
    public static void printBoard(char[] boardMarks) {
        System.out.println("+---+---+---+");

        for (int i = 0; i < LINE_SIZE; i++) {
            System.out.print("|");
            for (int j = 0; j < LINE_SIZE; j++) {
                System.out.printf(" %c |", boardMarks[i * LINE_SIZE + j]);
            }
            System.out.print("\n+---+---+---+\n");
        }
    }

    /**
     * Container of all possible lines.
     * 3 horizontal, 3 vertical, 2 diagonal lines
     */
    private static final int[][] LINES = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };

    /**
     * Corner points of the board.
     */
    private static final int[] CORNER_POINTS = {0, 2, 6, 8 };

    /**
     * Center point on the board.
     */
    private static final int CENTER_POINT = 4;

    /**
     * Function checks every line whether
     * it's sum is either 3 or -3.
     *
     * @param board Current state of the board
     * @return -1 if AI won, 1 if Player won, 0 if no winner yet
     */
    public static int getWinner(int[] board) {
        for (int[] line: LINES) {
            int sum = lineSum(board, line);

            if (sum == LINE_SIZE) {
                return 1;
            } else if (sum == -LINE_SIZE) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * Returns a game winning move, given the opportunity.
     *
     * @param board Current state of the board
     * @return Game winning move or -1 if it doesn't exist
     */
    public static int findWinningMove(int[] board) {
        for (int[] line: LINES) {
            int sum = lineSum(board, line);

            if (sum == 1 - LINE_SIZE) {
                int emptyCell = getLineEmptyCell(board, line);
                if (emptyCell != -1) {
                    return emptyCell;
                }
            }
        }
        return -1;
    }

    /**
     * Returns a defensive move, if AI life is in danger.
     *
     * @param board Current state of the board
     * @return Move that will not let enemy win
     */
    public static int findSurvivingMove(int[] board) {
        for (int[] line: LINES) {
            int sum = lineSum(board, line);

            if (sum == LINE_SIZE - 1) {
                int emptyCell = getLineEmptyCell(board, line);
                if (emptyCell != -1) {
                    return emptyCell;
                }
            }
        }
        return -1;
    }

    /**
     * Return sum of statuses given
     * particular line and board status.
     *
     * @param board Current state of the board
     * @param line One of 8 lines in the array
     * @return Sum of statuses from -3 to 3
     */
    public static int lineSum(int[] board, int[] line) {
        int sum = 0;
        for (int cellIndex : line) {
            sum += board[cellIndex];
        }
        return sum;
    }

    /**
     * Given a line of cells, returns the first empty one.
     *
     * @param board Current state of the board
     * @param line Array of line cells' indexes
     * @return Index of first empty cell in a line
     */
    private static int getLineEmptyCell(int[] board, int[] line) {
        for (int cellIndex : line) {
            if (board[cellIndex] == 0) {
                return cellIndex;
            }
        }
        return -1;
    }

    /**
     * Function used by game AI. Checks all
     * corners given by array
     * CORNER_POINTS. The array is shuffled
     * in random order beforehand.
     *
     * @param board Current state of the board
     * @return the most important free cell
     */
    public static int findCornerPoint(int[] board) {
        int[] shuffledCorners = CORNER_POINTS.clone();
        shuffleArray(shuffledCorners);

        for (int point: shuffledCorners) {
            if (board[point] == 0) {
                return point;
            }
        }
        return -1;
    }

    /**
     * Shuffles given array.
     * @param numbers arrays of integers to shuffle
     */
    public static void shuffleArray(int[] numbers) {
        int len = numbers.length;
        for (int i = 0; i < len; i++) {
            int j = (int) (Math.random() * len);
            int temp = numbers[i];
            numbers[i] = numbers[j];
            numbers[j] = temp;
        }
    }

    /**
     * Function for checking whether there
     * is any free cell on the game board.
     * @param board Current state of the board
     * @return true if there is no free cells
     */
    public static boolean boardFilled(int[] board) {
        for (int cellStatus: board) {
            if (cellStatus == 0) {
                return false;
            }
        }
        return true;
    }
}