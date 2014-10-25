package p11942;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int t = Reader.nextInt();
        System.out.println("Lumberjacks:");

        while(t-- > 0) {

            int[] nums = new int[10];

            nums[0] = Reader.nextInt();
            nums[1] = Reader.nextInt();

            boolean orderASC = false;
            if (nums[1] > nums[0]) {
                orderASC = true;
            }

            boolean ordered = true;

            int i = 2;
            for ( ; i < 10; i++){

                nums[i] = Reader.nextInt();
                if (nums[i] < nums[i-1] && orderASC) {
                    ordered = false;
                    i++;
                    break;
                } else if (nums[i] > nums[i-1] && !orderASC) {
                    ordered = false;
                    i++;
                    break;
                }
            }
            for ( ; i < 10; i++){
                Reader.next();
            }

            if (ordered) {
                System.out.println("Ordered");
            } else {
                System.out.println("Unordered");
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
