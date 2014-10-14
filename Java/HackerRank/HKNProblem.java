import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class HKNProblemSolution {

    public static void main(String[] args) {
        Reader.init(System.in);

        try {
            long a = Long.parseLong(Reader.next());
            long b = Long.parseLong(Reader.next()) + 1;

            int count = 0;
            for (; a < b; a++) {
                if (isPalindrome(a)) {
                    count++;
                }
            }
            System.out.println(count);
        } catch (IOException e) {
            // nothing
        }
    }

    private static boolean isPalindrome(long a) {
        if (Long.lowestOneBit(a) != 1 ) {
            return false;
        }
        char[] chars = Long.toBinaryString(a).toCharArray();

        for (int i = 0, j = chars.length -1; i < j; i++, j--) {
            if (chars[i] != chars[j]) {
                return false;
            }
        }



        return true;

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
        tokenizer = new StringTokenizer("", ",");
        currentLine = "";
    }

    /**
     * Clear both tokenizer and current line.
     */
    static void flushTokenizer() {
        currentLine = "";
        tokenizer = new StringTokenizer(currentLine, ",");
    }

    /**
     * Get next token (word).
     * @return next token
     * @throws java.io.IOException if any problems happen
     */
    static String next() throws IOException {
        while (!tokenizer.hasMoreTokens()) {
            currentLine = reader.readLine();
            tokenizer = new StringTokenizer(currentLine, ",");
        }
        return tokenizer.nextToken();
    }

    /**
     * Grab whole next line. Put it into the tokenizer.
     * @return next line
     */
    static String nextLine() {
        // probably not even needed here
        flushTokenizer();
        try {
            currentLine = reader.readLine();
            tokenizer = new StringTokenizer(currentLine);
            return currentLine;
        } catch (IOException|NullPointerException e) {
            return null;
        }
    }
}