package p750;

import java.io.*;
import java.util.*;

class Main { /* 8 Queens Chess Problem */
    static int[] row = new int[9];
    static int TC, a, b; // it is ok to use global variables in competitive programming

    static int counter = 0;
    static int[][] table = new int[100][9];

    static boolean place(int col, int tryrow) {
        for (int prev = 1; prev < col; prev++) // check previously placed queens
            if (row[prev] == tryrow || (Math.abs(row[prev] - tryrow) == Math.abs(prev - col)))
                return false; // an infeasible solution if share same row or same diagonal
        return true;
    }

    static void backtrack(int col) {
        for (int tryrow = 1; tryrow < 9; tryrow++) // try all possible row
            if (place(col, tryrow)) { // if can place a queen at this col and row...
                row[col] = tryrow; // put this queen in this col and row
                if (col == 8 ) { //&& row[b] == a) { // a candidate solution & (a, b) has 1 queen
                    table[counter++] = Arrays.copyOf(row, 9);
                }
                else
                    backtrack(col + 1); // recursively try next column
            }   }

    public static void main(String[] args) throws IOException {
        Reader.init(System.in);

        TC = Reader.nextInt();
        backtrack(1);
        while (true) {
            a = Reader.nextInt();
            b = Reader.nextInt();
            int lineCounter = 0;
            Reader.out.println("SOLN       COLUMN");
            Reader.out.println(" #      1 2 3 4 5 6 7 8\n");

            for(int i = 0; i < 92; i++) {
                if (table[i][b] == a) {
                    Reader.out.printf("%2d      %d %d %d %d %d %d %d %d\n",
                            ++lineCounter, table[i][1], table[i][2], table[i][3],
                            table[i][4], table[i][5], table[i][6],
                            table[i][7], table[i][8]);
                }
            }
            if (--TC > 0) Reader.out.println();
            else {
                break;
            }
        }
        Reader.out.close();
    }
}


/**
 * Reader class for general input reading.
 */
class Reader {
    /**
     * BufferedReader instance.
     */
    static BufferedReader reader;

    /**
     * Fast output.
     */
    static PrintWriter out;

    /**
     * StringTokenizer instance.
     */
    static StringTokenizer tokenizer;

    /**
     * Currently being used line.
     */
    static String currentLine;

    /**
     * Call this method to initialize reader for InputStream.
     *
     * @param input InputStream instance
     */
    static void init(InputStream input) {
        out = new PrintWriter(new BufferedOutputStream(System.out));
        reader = new BufferedReader(new InputStreamReader(input));
        tokenizer = new StringTokenizer("");
        currentLine = "";
    }

    /**
     * Clear both tokenizer and current line.
     */
    static void flushTokenizer() {
        currentLine = "";
        tokenizer = new StringTokenizer(currentLine);
    }

    /**
     * Get next token (word).
     * @return next token
     * @throws IOException if any problems happen
     */
    static String next() throws IOException {
        while (!tokenizer.hasMoreTokens()) {
            currentLine = reader.readLine();
            tokenizer = new StringTokenizer(currentLine);
        }
        return tokenizer.nextToken();
    }

    /**
     * Grab whole next line. Put it into the tokenizer.
     * @return next line
     */
    static String nextLine() {
        try {
            currentLine = reader.readLine();
            tokenizer = new StringTokenizer(currentLine);
            return currentLine;
        } catch (IOException|NullPointerException e) {
            return null;
        }
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
    static long nextLong() throws IOException {
        return Long.parseLong( next() );
    }
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}