/**
 * @author Ago
 * The status of the game.
 * Its either open (game goes on),
 * one of the players has won,
 * or a draw (board is full).
 */
public enum GameStatus {
    WHITE_WON {
        @Override
        public String toString() {
            return "White won";
        }
    },
    BLACK_WON {
        @Override
        public String toString() {
            return "Black won";
        }
    },
    DRAW {
        @Override
        public String toString() {
            return "Game drawn";
        }
    },
    OPEN {
        @Override
        public String toString() {
            return "Game on";
        }
    };

    /**
     * Used to translate player on the simple board
     * into winning state.
     * @param value SimpleBoard.board cell value
     * @return If the given argument indicates a player,
     * corresponding player's winning state is returned,
     * OPEN otherwise.
     */
    public static GameStatus getWinnerFromSimpleBoardValue(int value) {
        if (value == SimpleBoard.PLAYER_WHITE) return WHITE_WON;
        if (value == SimpleBoard.PLAYER_BLACK) return BLACK_WON;
        return OPEN;
    }
}