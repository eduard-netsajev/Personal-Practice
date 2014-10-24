package p11559;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Reader.init(System.in);

        try {
            while (true) {
                int N = Reader.nextInt();
                int B = Reader.nextInt();
                int H = Reader.nextInt();
                int W = Reader.nextInt();

                int min = B + 1;

                while (H--  > 0) {
                    int cost = N * Reader.nextInt();
                    if ((min < B && min <= cost) || B < cost) {
                        for (int i = 0; i < W; i++) {
                            Reader.next();
                        }
                    } else {
                        int i = 0;
                        for ( ; i < W; i++) {
                            if (N <= Reader.nextInt()) {
                                min = Math.min(min, cost);
                            }
                        }
                        for ( ; i < W; i++) {
                            Reader.next();
                        }
                    }
                }
                if (min > B) {
                    System.out.println("stay home");
                } else {
                    System.out.println(min);
                }
            }
        } catch (Exception e) {
            //nope
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
