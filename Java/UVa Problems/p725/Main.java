package p725;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int N;
        boolean notFirst = false;
        while((N = Reader.nextInt()) > 0) {
            boolean found = false;

            if (notFirst) {
                Reader.out.println();
            } else {
                notFirst = true;
            }
            int k = 98765 / N + 1;
            for(int fghij = 1234; fghij < k; fghij++) {
                int abcde = fghij * N;
                int tmp;
                int used = (fghij < 10000) ? 1 : 0;

                tmp = abcde; while(tmp > 0) {
                    used |= 1 << (tmp % 10);
                    tmp /= 10;
                }
                tmp = fghij; while(tmp > 0) {
                    used |= 1 << (tmp % 10);
                    tmp /= 10;
                }
                if (used == (1 << 10) - 1) {
                    found = true;
                    Reader.out.printf("%05d / %05d = %d\n", abcde, fghij, N);
                }
            }
            if (!found) {
                Reader.out.printf("There are no solutions for %d.\n", N);
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
