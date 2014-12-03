import java.util.*;


public class EduStrongStrategy implements ComputerStrategy {

    private boolean virgin = true;

    private Cell[] cells;
    private int iAM;

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

    private long evaluation(int player) {
        return gradeColumns(player) + gradeRows(player)
                + gradeDiagsDown(player) + gradeDiagsUp(player);
    }

    private long gradeDiagsDown(int player) {

        long score = 0;

        Cell[] cells = new Cell[7];
        int[] cellindexes = new int[7];

        Tuple t;
        Cell m;
        int rd, rc;
        boolean next, prev;

        for (int rowi = 0; rowi < SIZE - 4; rowi++) {
            rd = rowi * 100;
            for (int coli = 0; coli < SIZE - 4; coli++) {

                rc = rd + coli;

                cells[0] = this.cells[rc];
                cells[1] = this.cells[rc+101];
                cells[2] = this.cells[rc+202];
                cells[3] = this.cells[rc+303];
                cells[4] = this.cells[rc+404];

                cellindexes[0] = rc;
                cellindexes[1] = rc+101;
                cellindexes[2] = rc+202;
                cellindexes[3] = rc+303;
                cellindexes[4] = rc+404;

                next = false;
                if (rowi + 5 < SIZE && coli + 5 < SIZE) {
                    next = true;
                    cellindexes[6] = rc+505;
                    cells[6] = this.cells[rc+505];
                }

                prev = false;
                if (rowi > 0 && coli > 0) {
                    prev = true;
                    cellindexes[5] = rc - 101;
                    cells[5] = this.cells[rc - 101];
                }

                t = new Tuple(cells, cellindexes);

                t.hasNext = next;
                t.hasPrevious = prev;

                score = gradeTuple(t, player);
                if (score > 0) {

                    m = this.cells[rc];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 101];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 202];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 303];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 404];
                    m.tuples.add(t);
                    m.score += score;
                }
            }
        }
        return score;
    }

    private long gradeDiagsUp(int player) {

        long score = 0;

        Cell[] cells = new Cell[7];
        int[] cellindexes = new int[7];

        Tuple t;
        Cell m;

        int rc;
        boolean next, prev;
        for (int coli = 4; coli < SIZE; coli++) {
            for (int rowi = 0; rowi + 4 < SIZE; rowi++) {
                rc = rowi * 100 + coli;


                cells[0] = this.cells[rc];
                cells[1] = this.cells[rc + 100 - 1];
                cells[2] = this.cells[rc + 200 - 2];
                cells[3] = this.cells[rc + 300 - 3];
                cells[4] = this.cells[rc + 400 - 4];

                cellindexes[0] = rc;
                cellindexes[1] = rc + 100 - 1;
                cellindexes[2] = rc + 200 - 2;
                cellindexes[3] = rc + 300 - 3;
                cellindexes[4] = rc + 400 - 4;

                next = false;
                if (rowi + 5 < SIZE && coli - 4 > 0) {
                    next = true;
                    cellindexes[6] = rc+500-5;
                    cells[6] = this.cells[rc+500-5];
                }

                prev = false;
                if (rowi > 0 && coli + 1 < SIZE) {
                    prev = true;
                    cellindexes[5] = rc - 100 + 1;
                    cells[5] = this.cells[rc - 100 + 1];
                }

                t = new Tuple(cells, cellindexes);

                t.hasNext = next;
                t.hasPrevious = prev;

                score = gradeTuple(t, player);

                if (score > 0) {

                    m = this.cells[rc];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 100 - 1];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 200 - 2];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 300 - 3];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 400 - 4];
                    m.tuples.add(t);
                    m.score += score;
                }
            }
        }
        return score;
    }

    /**
     * Check every row.
     */
    private long gradeRows(int player) {

        long score = 0;
        Cell[] cells = new Cell[7];
        int[] cellindexes = new int[7];

        Tuple t;
        Cell m;
        int rd, rc;
        boolean next, prev;
        for (int rowi = 0; rowi < SIZE; rowi++) {
            rd = rowi * 100;

            for (int coli = 0; coli < SIZE - 4; coli++) {

                rc = rd + coli;

                cells[0] = this.cells[rc];
                cells[1] = this.cells[rc + 1];
                cells[2] = this.cells[rc + 2];
                cells[3] = this.cells[rc + 3];
                cells[4] = this.cells[rc + 4];

                cellindexes[0] = rc;
                cellindexes[1] = rc+1;
                cellindexes[2] = rc+2;
                cellindexes[3] = rc+3;
                cellindexes[4] = rc+4;

                next = false;
                if (coli + 5 < SIZE) {
                    next = true;
                    cellindexes[6] = rc+5;
                    cells[6] = this.cells[rc+5];
                }

                prev = false;
                if (coli > 0) {
                    prev = true;
                    cellindexes[5] = rc - 1;
                    cells[5] = this.cells[rc - 1];
                }

                t = new Tuple(cells, cellindexes);

                t.hasNext = next;
                t.hasPrevious = prev;

                score = gradeTuple(t, player);
                if (score > 0) {

                    m = this.cells[rc];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 1];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 2];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 3];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 4];
                    m.tuples.add(t);
                    m.score += score;
                }
            }
        }
        return score;
    }

    /**
     * Check every column.
     */
    private long gradeColumns(int player) {

        long score = 0;
        Cell[] cells = new Cell[7];
        int[] cellindexes = new int[7];

        Tuple t;
        Cell m;

        int rc;
        boolean next, prev;
        for (int coli = 0; coli < SIZE; coli++) {
            for (int rowi = 0; rowi < SIZE-4; rowi++) {

                if (coli == 5 && rowi == 3) {
                    System.out.println();
                }

                rc = rowi * 100 + coli;
                //cells = new Cell[7];
                cells[0] = this.cells[rc];
                cells[1] = this.cells[rc+100];
                cells[2] = this.cells[rc+200];
                cells[3] = this.cells[rc+300];
                cells[4] = this.cells[rc+400];

                cellindexes[0] = rc;
                cellindexes[1] = rc+100;
                cellindexes[2] = rc+200;
                cellindexes[3] = rc+300;
                cellindexes[4] = rc+400;

                next = false;
                if (rowi + 5 < SIZE) {
                    next = true;
                    cellindexes[6] = rc+500;
                    cells[6] = this.cells[rc+500];
                }

                prev = false;
                if (rowi > 0) {
                    prev = true;
                    cellindexes[5] = rc - 100;
                    cells[5] = this.cells[rc - 100];
                }

                t = new Tuple(cells, cellindexes);

                t.hasNext = next;
                t.hasPrevious = prev;

                score = gradeTuple(t, player);
                if (score > 0) {

                    m = this.cells[rc];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 100];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 200];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 300];
                    m.tuples.add(t);
                    m.score += score;

                    m = this.cells[rc + 400];
                    m.tuples.add(t);
                    m.score += score;

                }
            }
        }

        return score;
    }

    private static long gradeTuple (Tuple t, int player) {

        Cell[] cells = t.cells;
        int current = 0;
        int count = 0;

        for(int i = 0; i < 5; i++) {
            if(cells[i].var != 0) {
                if (current != 0) {
                    if (cells[i].var != current) return t.score = 0;
                    count++;
                } else {
                    current = cells[i].var;
                    count++;
                }
            }
        }

        t.player = current;
        t.enemy = current * -1;
        t.count = count;

        if (count > 0) {
            if (current == player) {
                switch (count) {
                    case 1:
                        return t.score = 35;
                    case 2:
                        return t.score = 800;
                    case 3:
                        return t.score = 15000;
                    case 4:
                        return t.score = 800000;
                    default:
                        return t.score = 8000000;
                }
            } else {
                switch (count) {
                    case 1:
                        return t.score = 15;
                    case 2:
                        return t.score = 400;
                    case 3:
                        return t.score = 1800; // play with this, original 1800 // 8000 is fine for general game
                    case 4:
                        return t.score = 100000;
                    default:
                        return t.score = 1000000;
                }
            }
        }
        return 7;
    }

    private static void countThreats(Cell cell, int player) {
        ArrayList<Tuple> list = cell.tuples;
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

            if (t.count > 3) {
                threat4++;
                if(!cell.FantasticFour) {
                    cell.FantasticFour = t.player == player;
                }
                cell.score += 10000;
                continue;
            }

            boolean threat = false;
            boolean closedthreat = false;

            int[] cellIndexes = t.cellindexes;
            Cell[] cells = t.cells;
            if (cells[0].var == EMPTY && cells[1].var == EMPTY && cellIndexes[1] == cell.hashcode) {

                if (t.hasNext && cells[6].var != t.enemy) {
                    threat = true;
                }

            } else if (cells[3].var == EMPTY && cells[4].var == EMPTY && cellIndexes[3] == cell.hashcode) {

                if (t.hasPrevious && cells[5].var != t.enemy) {
                    threat = true;
                }
            } else if (cells[1].var == EMPTY && cells[4].var == EMPTY) { // 0-00-

                int k = cell.hashcode;

                if(k == cellIndexes[1]) {

                    if (t.hasPrevious && cells[5].var != t.enemy) {
                        threat = true;
                    } else if (t.hasNext && cells[6].var != t.enemy) {
                        closedthreat = true;
                    }
                } else if (k == cellIndexes[4]) {
                    if (t.hasPrevious && cells[5].var != t.enemy) {
                        closedthreat = true;
                    } else if (t.hasNext && cells[6].var != t.enemy) {
                        closedthreat = true;
                    }
                }
            } else if (cells[0].var == EMPTY && cells[2].var == EMPTY) { // -0-00

                int k = cell.hashcode;

                if(k == cellIndexes[0]) {
                    if (t.hasNext && cells[6].var != t.enemy) {
                        closedthreat = true;
                    } else if (t.hasPrevious && cells[5].var != t.enemy) {
                        closedthreat = true;
                    }
                } else if (k == cellIndexes[2]) {
                    if (t.hasNext && cells[6].var != t.enemy) {
                        threat = true;
                    } else if (t.hasPrevious && cells[5].var != t.enemy) {
                        closedthreat = true;
                    }
                }
            } else if (cells[0].var == EMPTY && cells[3].var == EMPTY) { // -00-0

                int k = cell.hashcode;

                if(k == cellIndexes[0]) {
                    if (t.hasNext && cells[6].var != t.enemy) {
                        closedthreat = true;
                    } else if (t.hasPrevious && cells[5].var != t.enemy) {
                        closedthreat = true;
                    }
                } else if (k == cellIndexes[3]) {
                    if (t.hasNext && cells[6].var != t.enemy) {
                        threat = true;
                    } else if (t.hasPrevious && cells[5].var != t.enemy) {
                        closedthreat = true;
                    }
                }
            } else if (cells[2].var == EMPTY && cells[4].var == EMPTY) { // 00-0-

                int k = cell.hashcode;

                if(k == cellIndexes[2]) {
                    if (t.hasPrevious && cells[5].var != t.enemy) {
                        threat = true;
                    } else if (t.hasNext && cells[6].var != t.enemy) {
                        closedthreat = true;
                    }
                } else if (k == cellIndexes[4]) {
                    if (t.hasPrevious && cells[5].var != t.enemy) {
                        closedthreat = true;
                    } else if (t.hasNext && cells[6].var != t.enemy) {
                        closedthreat = true;
                    }
                }
            }
            if(threat) {
                threatOpen3 += 1;
                cell.FantasticFour = player == t.player;
                cell.score += 1000;
            }
            if(closedthreat) {
                threatClosed3++;
                cell.score +=100;
            }

        }

        for(int i = 0; i < c; i++) {
            // first tuple here

            Tuple one = tuples[i];
            if (one.count < 2) continue;

            int[] oneCellIndexes = one.cellindexes;
            Cell[] oneCells = one.cells;

            if (one.count == 2) {
                if (!one.hasNext || !one.hasPrevious || oneCells[5].var == one.enemy || oneCells[6].var == one.enemy) {
                    continue;
                }
            }

            loop:
            for(int j = i; j < c; j++) { // j = i so we have only unique pairs, only 0-2 and no 2-0 indexes
                if(i != j) {

                    Tuple two = tuples[j];

                    if (two.count < 2 || (one.player != two.player) ) {
                        continue;
                    }

                    ArrayList<Integer> firstCellsIndexes = new ArrayList<>(5);
                    ArrayList<Integer> secondCellsIndexes = new ArrayList<>(5);

                    int[] twoCellIndexes = two.cellindexes;
                    Cell[] twoCells = two.cells;

                    if (two.count == 2) {

                        if (!two.hasNext || !two.hasPrevious || twoCells[5].var == two.enemy || twoCells[6].var == two.enemy) {
                            continue;
                        }
                    }

                    for(int z = 0; z < 5; z++) {
                        if(oneCells[z].var != EMPTY) firstCellsIndexes.add(oneCellIndexes[z]);
                        if(twoCells[z].var != EMPTY) secondCellsIndexes.add(twoCellIndexes[z]);
                    }

                    for(int f : firstCellsIndexes) {
                        for (int s : secondCellsIndexes) {
                            if (f == s) {
                                continue loop;
                            }
                        }
                    }

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
                            cell.score += 2000;
                            break;
                        case 23:
                            threat2x3++;
                            cell.score += 4500;
                            break;
                        case 33:
                            threat3x3++;
                            cell.score += 8000;
                            break;
                        case 24:
                            threat2x4++;
                            cell.score += 20000;
                            break;
                        case 34:
                            threat3x4++;
                            cell.score += 60000;
                            break;
                        case 44:
                            threat4x4++;
                            cell.score += 777777;
                    }
                }
            }
        }

        cell.threat4x4 = threat4x4;
        cell.threat3x4 = threat3x4;
        cell.threat2x4 = threat2x4;
        cell.threat4 = threat4;
        cell.threat3x3 = threat3x3;
        cell.threat2x3 = threat2x3;
        cell.threatOpen3 = threatOpen3;
        cell.threatClosed3 = threatClosed3;
        cell.threat2x2 = threat2x2;

        if (threat2x4 > 0 || threat4 > 0 || threat3x4 > 0 || threat4x4 > 0) {
            cell.magnitude = 4;
        } else if (threat2x3 > 0 || threat3x3 > 0 || threatOpen3 > 0 || threatClosed3 > 0) {
            cell.magnitude = 3;
        } else if (threat2x2 > 0) {
            cell.magnitude = 2;
        }

        cell.threats = threat2x2 + threatClosed3 + threatOpen3 + threat2x3 + threat3x3 + threat4 + threat2x4 + threat3x4 + threat4x4;

    }

    private static class Tuple {
        Cell[] cells;
        int[] cellindexes;

        long score;
        int player;
        int enemy;
        int count;

        Tuple(Cell[] cells, int[] cellindexes) {  // 6 prev, 7 next TODO do i need indexes separated
            this.cells = cells.clone();
            this.cellindexes = cellindexes.clone();
        }

        boolean hasNext = false;
        boolean hasPrevious = false;
    }

    private class Cell implements Comparable<Cell> {

        boolean FantasticFour = false;

        ArrayList<Tuple> tuples = new ArrayList<>(25);

        int var = 0;

        int x, y;
        long score;


        /**
         * Connected cells are only 32 cells that lay on 8 sides, each side up to 4 cells
         * In corner for example, it's only 12 cells
         */
        ArrayList<Cell> getAffected() {
            ArrayList<Cell> affectedCells = new ArrayList<>(33);

            // columns down

            {
                int startrow = max(y-4, 0);
                int endrow = min(SIZE, y+5);
                while (startrow < endrow) {
                    if (startrow != y && cells[startrow*100+x].var == EMPTY) {
                        affectedCells.add(cells[startrow * 100 + x]);
                    }
                    startrow++;
                }
            }

            // rows right

            {
                int rc = y * 100;
                int startcol = max(x-4, 0);
                int endcol = min(SIZE, x+5);
                while(startcol < endcol) {
                    if (startcol != x && cells[rc+startcol].var == EMPTY) {
                        affectedCells.add(cells[rc + startcol]);
                    }
                    startcol++;
                }
            }

            // diags down

            {
                int startrow = y-4;
                int startcol = x-4;
                if (startrow < 0) {
                    startcol -= startrow;
                    startrow = 0;
                }
                if (startcol < 0) {
                    startrow -= startcol;
                    startcol = 0;
                }

                int endrow = y + 5;
                int endcol = x + 5;
                if (endrow > SIZE) {
                    endcol -= (endrow - SIZE);
                    endrow = SIZE;
                }
                if (endcol > SIZE) {
                    endrow -= (endcol - SIZE);
                }

                while(startrow < endrow) {

                    if(startrow != y && cells[startrow * 100 + startcol].var == EMPTY) {
                        affectedCells.add(cells[startrow*100 + startcol]);
                    }

                    startrow++;
                    startcol++;
                }

            }

            // diags up

            {
                int startrow = y+4;
                int startcol = x-4;
                if (startrow >= SIZE) {
                    startcol -= (SIZE - startrow - 1);
                    startrow = SIZE - 1;
                }
                if (startcol < 0) {
                    startrow += startcol;
                    startcol = 0;
                }

                int endrow = y - 5;
                int endcol = x + 5;
                if (endrow < 0) {
                    endcol += endrow + 1;
                    endrow = - 1;
                }
                if (endcol > SIZE) {
                    endrow += (endcol - SIZE - 1);
                    endcol = SIZE;
                }

                while(startcol < endcol) {
                    if(startrow != y && cells[startrow*100 + startcol].var == EMPTY) {
                        affectedCells.add(cells[startrow*100 + startcol]);
                    }
                    startrow--;
                    startcol++;
                }

            }
            return affectedCells;
        }

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

        int magnitude = 0;
        int hashcode;

        Cell(int var, int y, int x) {
            this.var = var;
            this.x = x;
            this.y = y;
            hashcode = y * 100 + x;
        }

        @Override
        public boolean equals(Object otherObject) {
            if (otherObject instanceof Cell) {
                Cell otherCell = (Cell) otherObject;
                if (otherCell.x == this.x && otherCell.y == this.y) {
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
        public int compareTo(Cell otherCell) {
            if (score > otherCell.score) {
                return 1;
            } else if (score < otherCell.score) {
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

    @Override
    public Location getMove(SimpleBoard board, int player) {

        iAM = player;

        long t1 = System.nanoTime();

        int[][] badBoard = board.getBoard();

        /*for (int[] row : badBoard) {
            for (int i : row) {
                if (i == WHITE) System.out.print("O ");
                if (i == BLACK) System.out.print("X ");
                if (i == EMPTY) System.out.print("- ");
            }
            System.out.println();
        }*/

        SIZE = badBoard.length;

        ArrayList<Cell> moveList = new ArrayList<>(SIZE * SIZE + 1);

        if (virgin) {

            cells = new Cell[SIZE * 100 * SIZE];

            for (int rowi = 0; rowi < SIZE; rowi++) {
                int rd = rowi * 100;
                for (int coli = 0; coli < SIZE; coli++) {
                    Cell m;
                    cells[rd + coli] = m = new Cell(badBoard[rowi][coli], rowi, coli);
                    if (m.var == EMPTY) {
                        moveList.add(m);
                    }
                }
            }

            evaluation(player);

//            virgin = false; todo
        } else {
            for (int rowi = 0; rowi < SIZE; rowi++) {
                int rd = rowi * 100;
                for (int coli = 0; coli < SIZE; coli++) {
                    if (cells[rd + coli].var != badBoard[rowi][coli]) {
//TODO                        HashSet<Move> affectedMoves = moves[rd + coli].makeMove(enemyOf(player));

                    }
                }
            }
        }

        int n = moveList.size();

        if (n > 98) {
            Collections.sort(moveList);
            Collections.reverse(moveList);
            System.out.println("HOHOHOHOHO, LET'S PLAY A GAME");
            return new Location(moveList.get(0).y, moveList.get(0).x);
        }

        int s = n;
        Cell[] bestCells = new Cell[n];
        for (Cell m : moveList) {
            bestCells[--n] = m;
        }

        for (int i = 0; i < s; i++) {
            countThreats(bestCells[i], player);
        }

        Arrays.sort(bestCells, new ThreatComparator().reversed());

        int bestX = bestCells[0].x;
        int bestY = bestCells[0].y;

//        for (int k = 0; k < min(bestCells.length, 3); k++) {
//            System.out.println(bestCells[k].toString());
//        }
//        System.out.println();

        long t2 = System.nanoTime();

//        System.out.println("Me NanoSeconds to decide: " + (t2-t1));

        //bestMoves[0].makeMove(player);

        return new Location(bestY, bestX);
    }

    @Override
    public String getName() {
        return "Eduard The Great";
    }

    class ThreatComparator implements Comparator<Cell> {
        @Override
        public int compare(Cell a, Cell b) {

            if (a.threats == 0 && b.threats == 0) {
                return a.compareTo(b);
            } else if (b.threats == 0) {
                return 1;
            } else if (a.threats == 0) {
                return -1;
            }

            // TODO careful here
            if (a.magnitude == 4 && b.magnitude == 4) {
                if (a.FantasticFour) return 1;
                if (b.FantasticFour) return -1;
            }

            if (a.magnitude == 3 && b.magnitude == 3) {
                if (a.FantasticFour) return 1;
                if (b.FantasticFour) return -1;
            }

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
        EduStrongStrategy ai = new EduStrongStrategy();
        ai.getMove(simpleBoard, WHITE);
        System.out.println();
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }
    int min(int a, int b) {
        return (a < b) ? a : b;
    }

}