package p11565;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int N = Reader.nextInt();
        loop:
        while(N-- > 0) {

            int a = Reader.nextInt();
            int b = Reader.nextInt();
            int c = Reader.nextInt();

            int csqrt = (int) Math.sqrt(c);
            int bcube = (int) Math.abs(Math.cbrt(b));

            int brd0 = Math.min(bcube, csqrt) + 1;

            calc:
            for(int x = -22; x <= 22; x++) {
                if (x == 0) continue;
                int xx = x*x;

                int cxx = (int) Math.sqrt(c-xx);
                int bmul = Math.abs(b / x);

                int brdr1 = Math.min(cxx, bmul) + 1;

                for(int y = -100; y <= 100; y++) {
                    if(y == 0) {
                        continue;
                    }
                    int xxyy = y*y + xx;
                    int mul = x * y;
                    int sum = x + y;

                    int z = a - sum;

                    if (z != x && z!= y && x!=y && mul * z == b && xxyy + z*z == c && sum + z == a) {
                        Reader.out.printf("%d %d %d\n", x, y, z);
                        continue loop;
                    }
                }
            }
            Reader.out.println("No solution.");
        }
        Reader.out.close();

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
     * Fast output.
     */
    static PrintWriter out;

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
        out = new PrintWriter(new BufferedOutputStream(System.out));
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
     * @throws IOException if any problems happen
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