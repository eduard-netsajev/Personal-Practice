package p10911;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {

    private static int target, N;
    private static double[][] dist;
    private static double[] memo;

    private static double matching(int bitmask) {

        // we initialize `memo' with -1 in the main function
        if (memo[bitmask] > -0.5)          // this state has been computed before
            return memo[bitmask];                   // simply lookup the memo table
        if (bitmask == target)                // all students are already matched
            return memo[bitmask] = 0;                              // the cost is 0

        double ans = 20000.0;               // initialize with a large value
        int p1, p2;
        for (p1 = 0; p1 < 2 * N; p1++)
            if ((bitmask & (1 << p1)) == 0)
                break;                              // find the first bit that is off
        for (p2 = p1 + 1; p2 < 2 * N; p2++) {              // then, try to match p1
            if ((bitmask & (1 << p2)) == 0) { // with another bit p2 that is also off
                ans = Math.min(ans, dist[p1][p2] + matching(bitmask | (1 << p1) | (1 << p2)));
            }
        }
        memo[bitmask] = ans;
        return memo[bitmask] = ans;    // store result in a memo table and return

    }

    public static void main(String[] args) throws IOException {
        Reader.init(System.in);

        int n;
        int tc = 1;

        while ((n = Reader.nextInt() * 2) != 0) {
            int[] x = new int[n];
            int[] y = new int[n];
            N = n / 2;
            for (int i = 0; i < n; i++) {
                Reader.next();
                x[i] = Reader.nextInt();
                y[i] = Reader.nextInt();
            }

            dist = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = StrictMath.hypot(x[i] - x[j], y[i] - y[j]);
                }
            }
            int ms = (1 << (2 * N));
            target = ms - 1;
            memo = new double[ms];
            for (int i = 0; i < ms; i++)
                memo[i] = -1;
            double dist = matching(0);
            System.out.printf("Case %d: %.2f\n", tc++, dist);
        }
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
     * @throws java.io.IOException if any problems happen
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
