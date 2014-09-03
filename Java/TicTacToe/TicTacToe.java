import java.util.Scanner;

public class TicTacToe {

    private static final int LINE_SIZE = 3;
    private static final int CELL_COUNT = 9;

    public static void main(String[] args) {
        int[] board = {0, 1, 0, -1, 0, 1, 0, -1, 0};
        while (checkWin(board) == 0) {
            printBoard(board);
            nextMove(board, 1);
            nextMove(board, -1);
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
            board[makeMove(board)] = -1;
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

        return (int) (Math.random() * board.length);
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
        return LinesHandler.getWinner(board);
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
     * Static class for handling the board for Tic-Tac-Toe game.
     */
    public static class LinesHandler {

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




    }

}
