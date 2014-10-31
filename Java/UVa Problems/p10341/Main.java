package p10341;

import java.io.*;
import java.util.StringTokenizer;

public class Main {

    static int p, q, r, s, t, u;

    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        try {
            while (true) {
                p = Reader.nextInt();
                q = Reader.nextInt();
                r = Reader.nextInt();
                s = Reader.nextInt();
                t = Reader.nextInt();
                u = Reader.nextInt();

                double x = calc(0);
                double y = calc(1);

                if(x==0) {
                    Reader.out.println("0.0000");
                    continue;
                } else if (y == 0) {
                    Reader.out.println("1.0000");
                    continue;
                }

                boolean c0neg = (x < 0);
                boolean c1neg = (y < 0);
                if (c0neg ^ c1neg) {
                    double lo = 0.0, hi = 1.0, mid = 0.0, ans = 0.0;
                    for (int i = 0; i < 25; i++) {
                        mid = (lo + hi) / 2.0;
                        ans = calc(mid);
                        if (ans > 0) {
                            lo = mid;
                        } else if (ans < 0) {
                            hi = mid;
                        }
                    }
                    Reader.out.printf("%.4f\n", mid);
                } else {
                    Reader.out.println("No solution");
                }
            }
        } catch (Exception e) {
            Reader.out.close();
        }
    }

    static double calc (double x) {
        return t*x*x + s*Math.tan(x) + q*Math.sin(x) + p*(Math.expm1(-x)+1) + r*Math.cos(x) + u;
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
