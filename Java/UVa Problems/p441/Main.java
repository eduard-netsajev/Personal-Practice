package p441;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int k ;
        int[] N;
        boolean notFirst = false;
        while((k = Reader.nextInt()) > 0) {

            if (notFirst) {
                Reader.out.println();
            } else {
                notFirst = true;
            }

            N = new int[k];
            for(int i = 0; i < k; i++) {
                N[i] = Reader.nextInt();
            }
            int ak = k-5;
            int bk = k-4;
            int ck = k-3;
            int dk = k-2;
            int ek = k-1;
            for(int a = 0; a < ak; a++) {
                for (int b = a+1; b < bk; b++) {
                    for(int c = b+1; c < ck; c++) {
                        for(int d = c+1; d < dk; d++) {
                            for(int e = d+1; e < ek; e++) {
                                for(int f = e+1; f < k; f++) {
                                    Reader.out.printf("%d %d %d %d %d %d\n",
                                            N[a], N[b], N[c], N[d], N[e], N[f]);
                                }
                            }
                        }
                    }
                }
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
