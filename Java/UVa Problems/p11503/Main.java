package p11503;
/**
 * REALLY FAST!
 */
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int T = Reader.nextInt();

        DisjointSets DS;
        String a1;
        String a2;
        HashMap<String, Integer> map;
        while(T-- > 0) {
            int F = Reader.nextInt();
            map = new HashMap<>(4000);
            DS = new DisjointSets(100000);
            int counter = 0;
            while(F-- > 0) {
                a1 = Reader.next();
                a2 = Reader.next();
                Integer an = map.get(a1);

                if (an == null) {
                    map.put(a1, (an = counter++));
                }
                Integer bn = map.get(a2);
                if (bn == null) {
                    map.put(a2, (bn = counter++));
                }
                DS.unionSet(an, bn);
            }
        }
        DisjointSets.out.close();
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

class DisjointSets {

    final static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

    private int[] parent, rank, setSize;

    public DisjointSets(int N) {
        parent = new int[N];
        rank = new int[N];
        setSize = new int[N];
        Arrays.fill(setSize, 1);

        for(int i = 0; i < N; i++) {
            parent[i] = i;
        }
    }

    public int findSet(int c) {

        while(parent[c] != c) {
            parent[c] = parent[parent[c]];
            c = parent[c];
        }
        return c;
    }

    public void unionSet(int i, int j) {

        int x = findSet(i);
        int y = findSet(j);

        if (x != y) {

            if (rank[x] > rank[y]) {
                connectToSet(x, y);
            } else if (rank[y] > rank[x]) {
                connectToSet(y, x);
            } else {
                connectToSet(x, y);
                rank[x]++;
            }
        } else {
            out.println(setSize[x]);
        }
    }

    private void connectToSet(int larger, int smaller) {
        parent[smaller] = larger;
        out.println(setSize[larger] += setSize[smaller]);
    }

    public int sizeOfSet(int i) {
        return setSize[findSet(i)];
    }
}
