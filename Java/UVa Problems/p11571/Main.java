package p11571;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        Reader.init(System.in);
        int t = Reader.nextInt();
        while (t-- > 0) {
            long A = Reader.nextLong(), B = Reader.nextLong(), C = Reader.nextLong();

            double Cp = (A * A - C) / 2.0, a = -A, b = Cp, c = -B;
            double Q = (a * a - 3 * b) / 9.0, R = (2 * a * a * a - 9 * a * b + 27 * c) / 54;

            double M = (R * R - Q * Q * Q);

            if (M > 0) {
                Reader.out.println("No solution.");
                continue;
            }

                double theta = Math.acos(R / (Math.sqrt(Q) * Math.sqrt(Q) * Math.sqrt(Q)));
                double x = (-1 * Math.cos(theta / 3.0) * 2 * Math.sqrt(Q)) - a / 3.0,
                        y = (-1 * Math.cos((theta + 2 * Math.PI) / 3.0) * 2 * Math.sqrt(Q)) - a / 3.0,
                        z = (-1 * Math.cos((theta - 2 * Math.PI) / 3.0) * 2 * Math.sqrt(Q)) - a / 3.0;

//            Reader.out.printf("%f %f %f\n", x, y, z);
                long[] res = new long[]{(long)(x), (long)(y), (long)(z)};

                Arrays.sort(res);

                long xb = res[0]+2;
                long yb = res[1]+2;
                long zb = res[2]+2;

                boolean solved = false;
                loop:
                for(long i = res[0]-2; i < xb; i++) {
                    for(long j = res[1]-2; j < yb; j++) {
                        if (j == i) continue;
                        for(long k = res[2]-2; k < zb; k++) {
                            if (k == j || k == i) continue;
                            if (i + j + k == A) {
                                if (i * j * k == B) {
                                    if (j*j + i*i + k*k == C) {
                                        Reader.out.printf("%d %d %d\n", i, j, k);
                                        solved = true;
                                        break loop;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!solved) {
                    Reader.out.println("No solution.");
                }
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
