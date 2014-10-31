package p11991;

import java.io.*;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.StringTokenizer;

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


public class Main {
    static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

    public static void main(String[] args) {
        Reader.init(System.in);
        try {
            while (true) {

                ArrayList<Integer> ar;

                int n = Reader.nextInt() + 1;
                int m = Reader.nextInt();

                HashMap<Integer, ArrayList<Integer>> map = new HashMap<>(n);

                for(int i = 1; i < n; i++) {
                    int a = Reader.nextInt();
                    ar = map.get(a);
                    if (ar != null) {
                        ar.add(i);
                    } else {
                        ar = new ArrayList<>(1000);
                        ar.add(i);
                        map.put(a, ar);
                    }
                }

                while(m-- > 0) {
                    int k = Reader.nextInt();
                    int num = Reader.nextInt();
                    ar = map.get(num);
                    if (k > ar.size())
                        out.println(0);
                    else {
                        num = ar.get(--k);
                        out.println(num);
                    }
                }
            }
        } catch (Exception e) {
            //nope
        }
        out.close();
    }
}
