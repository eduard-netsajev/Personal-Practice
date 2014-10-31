package p410;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Reader.init(System.in);
        int set = 1;
        try {
            while(true) {
                int C = Reader.nextInt();
                int S = Reader.nextInt();

                int max = 2*C;

                int[] W = new int[max];

                int sum = 0;
                for (int i = 0; i < S; i++) {
                    W[i] = Reader.nextInt();
                    sum += W[i];
                }

                for (int i = S; i < max; i++) {
                    W[i] = 0;
                }
                Arrays.sort(W);

                double AVG = sum * 1.0 / C;

                double imbalance = 0.0;
                Reader.out.printf("Set #%d\n", set++);
                for (int i = C; i < max; i++) {
                    Reader.out.printf(" %d:", i-C);
                    if (W[i] > 0 ) {
                        Reader.out.printf(" %d", W[i]);
                        if (W[max-i-1] > 0) {
                            Reader.out.printf(" %d", W[max-i-1]);
                        }
                    }
                    Reader.out.println();
                    imbalance += Math.abs(W[max-i-1] + W[i] - AVG);
                }
                Reader.out.printf("IMBALANCE = %.5f\n\n", imbalance);
            }
        } catch (Exception e) {
            Reader.out.close();
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
