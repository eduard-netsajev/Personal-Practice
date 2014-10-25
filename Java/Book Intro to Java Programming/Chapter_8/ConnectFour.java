package Chapter_8;

import java.util.Scanner;

/**
 * Connect four is a two-player board game in which the
 players alternately drop colored disks into a seven-column, six-row vertically
 suspended grid, as shown below.
 */
public class ConnectFour {

    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final int CONNECTED = 4;

    /**
     * The objective of the game is to connect four same-colored disks in a row,
     * a column, or a diagonal before your opponent can do likewise. The
     * program prompts two players to drop a red or yellow disk alternately.
     * In the preceding figure, the red disk is shown in a dark color and the
     * yellow in a light color. Whenever a disk is dropped,
     * the program redisplays the board on the console and determines the
     * status of the game (win, draw, or continue).
     *
     * @param args console params
     */
    public static void main(String[] args){
        int[][] board = new int[ROWS][COLUMNS];

        boolean playingGame = true;

        int playerTurn = (int) Math.round(Math.random());
        if (playerTurn == 0) {
            playerTurn--;
        }

        printBoard(boardToChars(board));

        while (playingGame) {
            switch (checkWin(board)) {
                case -1:
                    System.out.println("Yellow player won!");
                    playingGame = false;
                    break;
                case 1:
                    System.out.println("Red player won!");
                    playingGame = false;
                    break;
                default:
                    if (boardFilled(board)) {
                        System.out.println("Draw. Can't win this, huh?");
                        playingGame = false;
                    } else {
                        if (playerTurn == 1) {
                            askUser(1, board);
                        } else {
                            askUser(-1, board);
                        }
                        playerTurn = -playerTurn;
                        printBoard(boardToChars(board));
                    }
            }
        }
    }

    public static char[][] boardToChars(int[][] board) {
        char[][] charBoard = new char[ROWS][COLUMNS];
        for(int row = 0; row < 6; row++) {
            for(int column = 0; column < 7; column++){
                switch (board[row][column]) {
                    case 1:
                        charBoard[row][column] = 'R';
                        break;
                    case -1:
                        charBoard[row][column] = 'Y';
                        break;
                    default:
                        charBoard[row][column] = ' ';
                        break;
                }
            }
        }
        return charBoard;
    }

    public static int checkWin(int[][] board){

        // check columns
        int columnsCheck = checkColumns(board);
        if (columnsCheck != 0) {
            return  columnsCheck;
        }

        // check rows
        int rowsCheck = checkRows(board);
        if (rowsCheck != 0) {
            return  rowsCheck;
        }

        // check diagonals that rise to right
        for(int row = ROWS - 1; row > 2; row--) {
            int check = checkUpDiagonal(row, 0, board);
            if (check != 0) {
                return check;
            }
        }
        for (int column = 1; column < COLUMNS - 3; column++) {
            int check = checkUpDiagonal(ROWS-1, column, board);
            if (check != 0) {
                return check;
            }
        }

        // check diagonals that rise to left
        for(int row = ROWS - 4; row > 0; row--) {
            int check = checkDownDiagonal(row, 0, board);
            if (check != 0) {
                return check;
            }
        }
        for (int column = 0; column < COLUMNS - 3; column++) {
            int check = checkDownDiagonal(0, column, board);
            if (check != 0) {
                return check;
            }
        }
        return 0;
    }

    public static int checkColumns(int[][] board) {
        int redLine;
        int yellowLine;

        for(int column = 0; column < COLUMNS; column++){
            redLine = 0;
            yellowLine = 0;

            for(int row = 0; row < ROWS; row++){
                switch (board[row][column]){
                    case 1:
                        redLine++;
                        yellowLine = 0;
                        break;
                    case -1:
                        redLine = 0;
                        yellowLine++;
                        break;
                    default:
                        redLine = 0;
                        yellowLine = 0;
                }
                if (redLine >= CONNECTED) {
                    return 1;
                } else if (yellowLine >= CONNECTED) {
                    return -1;
                }
            }
        }
        return 0;
    }

    public static int checkRows(int[][] board) {
        int redLine;
        int yellowLine;

        for(int row = 0; row < ROWS; row++){
            redLine = 0;
            yellowLine = 0;

            for(int column = 0; column < COLUMNS; column++){
                switch (board[row][column]){
                    case 1:
                        redLine++;
                        yellowLine = 0;
                        break;
                    case -1:
                        redLine = 0;
                        yellowLine++;
                        break;
                    default:
                        redLine = 0;
                        yellowLine = 0;
                }
                if (redLine >= CONNECTED) {
                    return 1;
                } else if (yellowLine >= CONNECTED) {
                    return -1;
                }
            }
        }
        return 0;
    }

    public static int checkUpDiagonal(int row, int column, int[][] board){
        // --row ++column YELLOW
        int redLine = 0;
        int yellowLine = 0;

        while(row >= 0 && column < COLUMNS) {
            switch (board[row][column]){
                case 1:
                    redLine++;
                    yellowLine = 0;
                    break;
                case -1:
                    redLine = 0;
                    yellowLine++;
                    break;
                default:
                    redLine = 0;
                    yellowLine = 0;
            }
            if (redLine >= CONNECTED) {
                return 1;
            } else if (yellowLine >= CONNECTED) {
                return -1;
            }
            row--;
            column++;
        }
        return 0;
    }

    public static int checkDownDiagonal(int row, int column, int[][] board){
        // ++row ++column RED
        int redLine = 0;
        int yellowLine = 0;

        while(row < ROWS && column < COLUMNS) {
            switch (board[row][column]){
                case 1:
                    redLine++;
                    yellowLine = 0;
                    break;
                case -1:
                    redLine = 0;
                    yellowLine++;
                    break;
                default:
                    redLine = 0;
                    yellowLine = 0;
            }
            if (redLine >= CONNECTED) {
                return 1;
            } else if (yellowLine >= CONNECTED) {
                return -1;
            }
            row++;
            column++;
        }
        return 0;
    }

    public static boolean boardFilled(int[][] board){
        for(int row = 0; row < ROWS; row++) {
            for(int column = 0; column < COLUMNS; column++) {
                if(board[row][column] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void printBoard(char[][] charBoard){
        for(int row = 0; row < ROWS; row++) {
            for(int column = 0; column < COLUMNS; column++) {
                System.out.print("|" + charBoard[row][column]);
            }
            System.out.print("|\n");
        }
        System.out.println("---------------");
    }

    public static void askUser(int player, int[][] board) {
        final String errorMessage = "Invalid input. Please try again. Only "
                + "digits 1-7 are allowed.\n\n";

        String inputMessage;
        if (player > 0) {
            inputMessage = "Drop a red disk at column > ";
        } else {
            inputMessage = "Drop a yellow disk at column > ";
        }

        final String columnFilled = "Given column is already filled\n";
        int columnNumber;
        System.out.print(inputMessage);

        Scanner scanner = new Scanner(System.in);

        while(true) {
            while (!scanner.hasNextInt()) {
                System.out.print(errorMessage + inputMessage);
                scanner.nextLine();
            }
            columnNumber = scanner.nextInt();
            if (columnNumber < 1 || columnNumber > COLUMNS) {
                System.out.print(errorMessage + inputMessage);
            } else {
                columnNumber--;
                if (putDisk(player, columnNumber, board)) {
                    return;
                } else {
                    System.out.print(columnFilled);
                }
            }
        }
    }

    public static boolean putDisk(int player, int column, int[][] board) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if(board[row][column] == 0) {
                board[row][column] = player;
                return true;
            }
        }
        return false;
    }
}
