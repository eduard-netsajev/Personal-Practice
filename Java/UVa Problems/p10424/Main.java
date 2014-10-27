package p10424;

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
                char[] a = Reader.nextLine().toCharArray();
                char[] b = Reader.nextLine().toCharArray();

                int ap = 0;
                int bp = 0;

                int al = a.length;
                int bl = b.length;

                int c;
                for(int i = 0; i < al; i++){
                    c = 0;
                    if (a[i] > 96) {
                        c = a[i] - 96;
                    } else if (a[i] > 64) {
                        c = a[i] - 64;
                    }
                    if (c < 27) {
                        ap += c;
                    }
                }

                for(int i = 0; i < bl; i++){
                    c = 0;
                    if (b[i] > 96) {
                        c = b[i] - 96;
                    } else if (b[i] > 64) {
                        c = b[i] - 64;
                    }
                    if (c < 27) {
                        bp += c;
                    }
                }

                ap = num(ap);
                bp = num(bp);

                int min = Math.min(ap, bp);
                int max = Math.max(ap, bp);

                double result = 100.0 * min / max;

                if (max == 0) {
                    System.out.println();
                } else {
                    System.out.printf("%.2f %%\n", result);
                }



            }
        } catch (Exception e) {
            //nope
        }

    }

    static int num (int n) {
        int sum;
        do {
            sum = 0;
            while (n > 9) {
                sum += n % 10;
                n /= 10;
            }
            sum += n;
            n = sum;
        } while (sum > 9);
        return sum;
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
