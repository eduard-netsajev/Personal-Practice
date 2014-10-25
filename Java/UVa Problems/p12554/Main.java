package p12554;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static final String[] song = new String[]{"Happy", "birthday", "to", "you",
            "Happy", "birthday", "to", "you", "Happy", "birthday", "to", "Rujia",
            "Happy", "birthday", "to", "you"};

    static final int len = 16;

    public static void main(String[] args) throws IOException {
        Reader.init(System.in);

        int t = Integer.parseInt(Reader.reader.readLine());
        String[] names = new String[t];

        for (int i = 0; i < t; i++) {
            names[i] = Reader.reader.readLine();
        }

        int sang = 0;
        int part = 0;
        while(sang < t || part != 0) {
            System.out.printf("%s: %s\n", names[sang % t], song[part]);
            sang++;
            part = sang % len;
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
