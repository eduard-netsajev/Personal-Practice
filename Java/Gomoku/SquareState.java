public enum SquareState {
    WHITE, BLACK, EMPTY {
        @Override
        public String toString() {
            return "-";
        }
    }
}