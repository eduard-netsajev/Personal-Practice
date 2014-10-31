package p11235;

import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static int[] array;
    static int[] tree;

    static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

    public static void initialize(int i, int j, int node) {
        if (i == j) {
            tree[node] = i;
            return;
        }
        int mid = (i + j) / 2;
        initialize(i, mid, 2 * node);
        initialize(mid + 1, j, 2 * node + 1);
        if (array[tree[2 * node]] >= array[tree[2 * node + 1]])
            tree[node] = tree[2 * node];
        else
            tree[node] = tree[2 * node + 1];
    }

    public static int query(int i, int j, int x, int y, int node) {
        if (i >= x && j <= y) {
            return tree[node];
        } else if (i > y || j < x)
            return -1;
        int mid = (i + j) / 2;
        int left = query(i, mid, x, y, 2 * node);
        int right = query(mid + 1, j, x, y, 2 * node + 1);
        if (left == -1)
            return right;
        if (right == -1)
            return left;
        if (array[left] >= array[right])
            return left;
        else
            return right;
    }

    static class boundary {

        int start;
        int end;
        int freq;

        public boundary(int st, int e) {
            start = st;
            end = e;
            freq = e - st + 1;
        }
    }

    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        while (true) {

            int n = Reader.nextInt();
            if (n == 0)
                break;
            int q = Reader.nextInt();
            HashMap<Integer, boundary> map2 = new HashMap<Integer, boundary>();

            int prev = Reader.nextInt();
            int last = 0;
            int count = 0;
            int[] con = new int[n];
            for (int i = 1; i < n; i++) {
                int now = Reader.nextInt();
                if (now > prev) {
                    map2.put(count++, new boundary(last, i-1));
                    last = i;
                }
                prev = now;
                con[i] = count;
            }
            map2.put(count, new boundary(last, n-1));

            array = new int[n];
            int h = (int) (Math.log(n) / Math.log(2));
            h += 2;
            tree = new int[1 << h];
            for (int i = 0; i < n; i++)
                array[i] = map2.get(con[i]).freq;
            initialize(0, n - 1, 1);
            for (int i = 0; i < q; i++) {
                int x = Reader.nextInt() - 1;
                int y = Reader.nextInt() - 1;
                boundary b1 = map2.get(con[x]);
                boundary b2 = map2.get(con[y]);
                if (con[x] == con[y]) {
                    out.println(y - x + 1);
                } else {
                    int cnt1 = b1.end - x + 1;
                    int cnt2 = y - b2.start + 1;
                    int xx = b1.end + 1;
                    int yy = b2.start - 1;
                    if (xx > yy) {
                        if (cnt1 > cnt2)
                            out.println(cnt1);
                        else
                            out.println(cnt2);
                    } else {
                        int z = query(0, n - 1, xx, yy, 1);
                        if (array[z] > cnt1 && array[z] > cnt2)
                            out.println(array[z]);
                        else if (cnt1 > cnt2)
                            out.println(cnt1);
                        else
                            out.println(cnt2);
                    }
                }
            }
        }
        out.close();
    }

    static class Reader {
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
            } catch (Exception e) {
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
}