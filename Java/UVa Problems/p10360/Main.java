package p10360;

import java.io.*;
import java.util.StringTokenizer;

public class Main {

    static int[][] city;

    static int d;

    static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    static int min(int a, int b) {
        return (a < b) ? a : b;
    }

    static void fill(int x, int y, int rats) {
        int xe = min(x + d + 1, 1024);
        int ye = min(y + d + 1, 1024);

        int sy = max(0, y - d);
        for (int cx = max(0, x - d); cx < xe; cx++) {
            for (int cy = sy; cy < ye; cy++ ) {
                city[cx][cy] += rats;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Reader.init(System.in);

        int T = Reader.nextInt();

        while(T-- > 0) {

            d = Reader.nextInt();
            city = new int[1024][1024];

            int n = Reader.nextInt();

            while (n-- > 0) {
                fill(Reader.nextInt(), Reader.nextInt(), Reader.nextInt());
            }

            int mx = 0, my =0, max = 0;

            for(int x = 0; x < 1024; x++) {
                for(int y = 0; y < 1024; y++) {
                    if (city[x][y] > max) {
                        max = city[x][y];
                        mx = x; my = y;
                    }
                }
            }

            Reader.out.printf("%d %d %d\n", mx, my, max);

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
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
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

