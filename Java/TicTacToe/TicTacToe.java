import java.util.Scanner;

public class TicTacToe {

    private static final int BOARD_SIDE = 3;
    private static final int CELL_COUNT = 9;

    public static void main(String[] args) {
        int[] board = {0, 1, 0, -1, 0, 1, 0, -1, 0};
        printBoard(board);
        checkWin(board);

        int move = readInput();
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

        return (int) (Math.random() * CELL_COUNT);
    }

    /**
     * Reads a number from the standard input and returns it.
     * Beware: this method needs some improvements!
     * @return Number read from the input
     */
    public static int readInput() {
        final String errorMessage = "Invalid input. Please try again. Only "
                + "digits 1-9 are allowed.\n\n";

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input the cell number > ");
            if (scanner.hasNextInt()) {
                int cellIndex = scanner.nextInt();
                if (cellIndex > 0 && cellIndex <= CELL_COUNT) {
                    scanner.close();
                    return cellIndex;
                }
            }
            System.out.print(errorMessage);
            scanner.nextLine(); // Skip fault line, go to next
        }
    }

    /**
     * Checks the board and returns which player
     * has winning combination.
     * @param board The current state of the board
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
        for (int i = 0; i < BOARD_SIDE; i++) {
            System.out.print("|");
            for (int j = 0; j < BOARD_SIDE; j++) {
                System.out.printf(" %c |", boardMarks[i * BOARD_SIDE + j]);
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

                if (sum == BOARD_SIDE) {
                    return 1;
                } else if (sum == -BOARD_SIDE) {
                    return -1;
                }
            }

            return 0;
        }




    }

}
