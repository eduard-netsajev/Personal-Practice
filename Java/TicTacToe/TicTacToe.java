import java.util.Scanner;

public class TicTacToe {

    private static final int LINE_SIZE = 3;
    private static final int CELL_COUNT = 9;

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
                        System.out.println("Draw. Can't win this?");
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
     *
     * @param board Current state of the board.
     * See the description of the method for more information.
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

        int importantPoint = findImportantPoint(board);
        if (importantPoint > -1) {
            return importantPoint;
        }

        int randomEmptyCell;
        do {
            randomEmptyCell = (int) (Math.random() * board.length);
        } while (board[randomEmptyCell] != 0);

        return randomEmptyCell;
    }

    /**
     * Reads a number from the standard input and returns it.
     * Beware: this method needs some improvements!
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

    private static final int[] IMPORTANT_POINTS = {4, 0, 2, 6, 8 };

    /**
     * Function checks every line whether
     * it's sum is either 3 or -3.
     *
     * @param board Current state of the board
     * @return -1 if AI won, 1 if Player won, 0 if no winner yet
     */
    public static int getWinner(int[] board) {
        for (int[] line: LINES) {
            int sum = 0;
            for (int cellIndex : line) {
                sum += board[cellIndex];
            }

            if (sum == LINE_SIZE) {
                return 1;
            } else if (sum == -LINE_SIZE) {
                return -1;
            }
        }
        return 0;
    }

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

    private static int getLineEmptyCell(int[] board, int[] line) {
        for (int cellIndex : line) {
            if (board[cellIndex] == 0) {
                return cellIndex;
            }
        }
        return -1;
    }


    public static int findImportantPoint(int[] board) {
        for (int point: IMPORTANT_POINTS) {
            if (board[point] == 0) {
                return point;
            }
        }
        return -1;
    }

    public static boolean boardFilled(int[] board) {
        for (int cellStatus: board) {
            if (cellStatus == 0) {
                return false;
            }
        }
        return true;
    }

}
