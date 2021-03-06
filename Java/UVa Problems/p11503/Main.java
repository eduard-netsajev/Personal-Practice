package p11503;
/**
 * REALLY FAST!
 */

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;

public class Main {

    static InputReader in = new InputReader(System.in);
    static OutputWriter out	= new OutputWriter(System.out);

    static DisjointSets DS;
    static String a1;
    static String a2;
    static HashMap<String, Integer> map;

    public static void main(String[] args) throws IOException {
        int T = in.readInt();

        while(T-- > 0) {
            int F = in.readInt();
            map = new HashMap<>(4000);
            DS = new DisjointSets(100000);
            int counter = 0;
            while(F-- > 0) {
                a1 = in.readString();
                a2 = in.readString();
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
        out.close();
    }
}

class DisjointSets {

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
            Main.out.printLine(setSize[x]);
        }
    }

    private void connectToSet(int larger, int smaller) {
        parent[smaller] = larger;
        Main.out.printLine(setSize[larger] += setSize[smaller]);
    }

    public int sizeOfSet(int i) {
        return setSize[findSet(i)];
    }
}

class InputReader {

    private InputStream stream;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;
    private SpaceCharFilter filter;

    public InputReader(InputStream stream) {
        this.stream = stream;
    }

    public int read() {
        if (numChars == -1)
            throw new InputMismatchException();
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = stream.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars <= 0)
                return -1;
        }
        return buf[curChar++];
    }

    public int readInt() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        int res = 0;
        do {
            if (c < '0' || c > '9')
                throw new InputMismatchException();
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }

    public String readString() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isSpaceChar(c));
        return res.toString();
    }

    public boolean isSpaceChar(int c) {
        if (filter != null)
            return filter.isSpaceChar(c);
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }

    public String next() {
        return readString();
    }

    public interface SpaceCharFilter {
        public boolean isSpaceChar(int ch);
    }
}

class OutputWriter {
    private final PrintWriter writer;

    public OutputWriter(OutputStream outputStream) {
        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
    }

    public OutputWriter(Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    public void print(Object...objects) {
        for (int i = 0; i < objects.length; i++) {
            if (i != 0)
                writer.print(' ');
            writer.print(objects[i]);
        }
    }

    public void printLine(Object...objects) {
        print(objects);
        writer.println();
    }

    public void close() {
        writer.close();
    }

    public void flush() {
        writer.flush();
    }

}