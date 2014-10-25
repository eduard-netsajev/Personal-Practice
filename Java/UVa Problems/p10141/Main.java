package p10141;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);

        int tc = 1;
        boolean afterFrist = false;

        while(true) {

            int n = Reader.nextInt();
            int p = Reader.nextInt();
            if (n + p == 0) {
                break;
            }

            if (afterFrist) {
                System.out.println();
            } else {
                afterFrist = true;
            }

            for (int i = 0; i < n; i++) {
                Reader.reader.readLine();
            }

            String winner = "";
            int maxN = 0;
            double minP = 0;

            while(p-- > 0) {
                String c = Reader.reader.readLine();
                double price = Reader.nextDouble();
                int cn = Reader.nextInt();
                if (cn > maxN) {
                    winner = c;
                    maxN = cn;
                    minP = price;
                } else if (cn == maxN && price < minP) {
                    winner = c;
                    minP = price;
                }
                while(cn-- > 0) {
                    Reader.reader.readLine();
                }
            }
            System.out.printf("RFP #%d\n%s\n", tc++, winner);


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