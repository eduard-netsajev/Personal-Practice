package p661;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int tc = 1;

        int n;
        long m, c;

        while(true) {

            boolean success = true;

            n = Reader.nextInt();
            m = Reader.nextInt();
            c = Reader.nextInt();

            if (n + m + c == 0) {
                break;
            }

            boolean[] states = new boolean[n];
            long[] consumptions = new long[n];
            //todo maybe turn off manually at start

            for (int i = 0; i < n; i++) {
                consumptions[i] = Reader.nextInt();
            }

            long currentVoltage = 0;
            long maxVoltage = 0;

            while(m-- > 0) {

                int device = Reader.nextInt() - 1;
                if (states[device]) {
                    states[device] = false;
                    currentVoltage -= consumptions[device];
                } else {
                    states[device] = true;
                    currentVoltage += consumptions[device];
                    if (currentVoltage > maxVoltage) {
                        maxVoltage = currentVoltage;
                    }
                    if (currentVoltage > c) {
                        success = false;
                    }
                }

            }

            System.out.printf("Sequence %d\n", tc++);
            if (success) {
                System.out.printf("Fuse was not blown.\nMaximal power consumption was %d amperes.\n\n", maxVoltage);
            } else {
                System.out.println("Fuse was blown.\n");
            }

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

