package p11507;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        String s;
        while(true) {
            int L = Reader.nextInt();
            if (L == 0) {
                break;
            }

            int pos = 1;
            /*

            1 = +x
            -1 = -x

            2 = +y
            -2 = -y

            3 = +z
            -3 = -z

             */

            while(--L > 0) {
                s = Reader.next();

                switch (s) {
                    case "+y":
                        switch (pos) {
                            case -1:
                                pos = -2;
                                break;
                            case 1:
                                pos = 2;
                                break;
                            case 2:
                                pos = -1;
                                break;
                            case -2:
                                pos = 1;
                                break;
                        }
                        break;
                    case "-y":
                        switch (pos) {
                            case -1:
                                pos = 2;
                                break;
                            case 1:
                                pos = -2;
                                break;
                            case 2:
                                pos = 1;
                                break;
                            case -2:
                                pos = -1;
                                break;
                        }
                        break;
                    case "+z":
                        switch (pos) {
                            case -1:
                                pos = -3;
                                break;
                            case 1:
                                pos = 3;
                                break;
                            case 3:
                                pos = -1;
                                break;
                            case -3:
                                pos = 1;
                                break;
                        }
                        break;
                    case "-z":
                        switch (pos) {
                            case -1:
                                pos = 3;
                                break;
                            case 1:
                                pos = -3;
                                break;
                            case 3:
                                pos = 1;
                                break;
                            case -3:
                                pos = -1;
                                break;
                        }
                        break;
                }
            }
            switch (pos) {
                case 1:
                    System.out.println("+x");
                    break;
                case -1:
                    System.out.println("-x");
                    break;
                case 2:
                    System.out.println("+y");
                    break;
                case -2:
                    System.out.println("-y");
                    break;
                case 3:
                    System.out.println("+z");
                    break;
                case -3:
                    System.out.println("-z");
                    break;
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
