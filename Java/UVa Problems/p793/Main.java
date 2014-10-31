package p793;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Reader.init(System.in);
        try {
            int t = Reader.nextInt();

            while (true) {

                DisjointSets DS = new DisjointSets(Reader.nextInt());

                int succss = 0;
                int fail = 0;

                try {
                    String cmd;
                    String ch;
                    while (true) {
                        ch = Reader.nextLine();
                        if (ch.length() < 3) {
                            break;
                        }
                        cmd = Reader.next();
                        int a = Reader.nextInt() - 1;
                        int b = Reader.nextInt() - 1;
                        if (cmd.equals("q")) {
                            if (DS.isSameSet(a, b)) {
                                succss++;
                            } else {
                                fail++;
                            }
                        } else {
                            DS.unionSet(a, b);
                        }
                    }
                } catch (Exception e) {
//            e.printStackTrace();
                } finally {
                    System.out.print(succss);
                    System.out.print(',');
                    System.out.println(fail);
                }
                if (--t > 0) {
                    System.out.println();
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            //nope
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

class DisjointSets {
    private int[] parent, rank, setSize;
    private int numSets;

    public DisjointSets(int N) {
        parent = new int[N];
        rank = new int[N];
        setSize = new int[N];

        numSets = N;

        for(int i = 0; i < N; i++) {
            parent[i] = i;
            rank[i] = 0;
            setSize[i] = 1;
        }
    }

    public int findSet(int c) {

        while(parent[c] != c) {
            parent[c] = parent[parent[c]];
            c = parent[c];
        }
        return c;
    }

    public boolean isSameSet(int i, int j) {
        return findSet(i) == findSet(j);
    }

    public void unionSet(int i, int j) {
        if (!isSameSet(i, j)) {
            numSets--;
            int x = findSet(i);
            int y = findSet(j);

            if (rank[x] > rank[y]) {
                connectToSet(x, y);
            } else if (rank[y] > rank[x]) {
                connectToSet(y, x);
            } else {
                connectToSet(x, y);
                rank[x]++;
            }
        }
    }

    private void connectToSet(int larger, int smaller) {
        parent[smaller] = larger;
        setSize[larger] += setSize[smaller];
    }

    public int numDisjointSets() {
        return numSets;
    }

    public int sizeOfSet(int i) {
        return setSize[findSet(i)];
    }

}
