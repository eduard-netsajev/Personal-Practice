package p119;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        Reader.init(System.in);
        boolean afterFirst = false;
        try {
            while (true) {

                int pc = Reader.nextInt();

                HashMap<String, Integer> balances = new HashMap<>(pc*2);

                String[] names = new String[pc];

                for(int i = 0; i < pc; i++) {
                    names[i] = Reader.next();
                    balances.put(names[i], 0);
                }

                for(int i = 0; i < pc; i++) {
                    String name = Reader.next();
                    int budget = Reader.nextInt();

                    int rc = Reader.nextInt();
                    if (rc > 0) {
                        int gift = budget / rc;
                        int cost = gift * rc;
                        balances.put(name, balances.get(name) - cost);
                        while (rc-- > 0) {
                            String n = Reader.next();
                            balances.put(n, balances.get(n) + gift);
                        }
                    }
                }

                if (afterFirst) {
                    System.out.println();
                } else {
                    afterFirst = true;
                }

                for(int i = 0; i < pc; i++) {
                    System.out.printf("%s %d\n", names[i], balances.get(names[i]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
