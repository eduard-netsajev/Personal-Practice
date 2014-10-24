package p11764;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int t = Reader.nextInt();
        int tc = 1;
        while(t-- > 0) {

            int n = Reader.nextInt();
            int up = 0;
            int down = 0;

            int last = Reader.nextInt();
            int c;
            while(--n > 0) {
                c = Reader.nextInt();
                if (c > last) {
                    up++;
                } else if (c < last) {
                    down++;
                }
                last = c;
            }

            System.out.printf("Case %d: %d %d\n", tc++, up, down);
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
