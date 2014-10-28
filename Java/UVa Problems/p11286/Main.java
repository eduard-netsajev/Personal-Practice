package p11286;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);

        int n;
        String[] courses;
        HashMap<String, Integer> map;
        StringBuilder sb;
        while(true) {
            n = Reader.nextInt();
            if (n < 1) {
                break;
            }

            map = new HashMap<>(n+500);

            while(n-- > 0) {
                courses = new String[5];
                for(int i = 0; i < 5; i++) {
                    courses[i] = Reader.next();
                }
                Arrays.sort(courses);
                sb = new StringBuilder(30);
                for(int i = 0; i < 5; i++) {
                    sb.append(courses[i]);
                }
                String res = sb.toString();
                Integer f = map.put(res, 1);
                if (f != null) {
                    map.put(res, f + 1);
                }
            }

            int max = 0;
            for (Integer i : map.values()){
                if (i > max) {
                    max = i;
                }
            }

            int count = 0;
            for (Integer i : map.values()){
                if (i == max) {
                    count += i;
                }
            }
            System.out.println(count);
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

