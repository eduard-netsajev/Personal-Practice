package p615;
import java.util.*;
import java.io.*;

class Main {
    // UVa Online Judge problem nr. 615
    // Ranking: 1821    Run time:  0.129


    //initialize
    static InputReader in = new InputReader(System.in);
    static OutputWriter out	= new OutputWriter(System.out);

    public static void main(String[] args) {

        // ID - ParentID pair
        int NODES_RANGE = 51000;
        int[] nodes = new int[NODES_RANGE];
        // Number of every test case
        int testCase = 1;

        // Set of all nodes
        int ALL_SIZE = 1000;
        int[] allNodes = new int[ALL_SIZE];
        int k = 0; //node count (real number is up to 2 times smaller)

        boolean tree = true;

        while(true) {
                int n1 = in.readInt();
                int n2 = in.readInt();
                if (n1 == 0 && n2 == 0) {
                    if (k > 0) {
                        // Variable for holding the root key
                        int root = -1;
                        if (tree) {
                            // Check roots count (node without a parent)
                            int roots = 0;
                            for(int i = 0; allNodes[i] != 0; i++ ){
                                if (nodes[allNodes[i]] == 0 && root
                                        != allNodes[i]) {
                                    roots++;
                                    root = allNodes[i];
                                }
                            }
                            if (roots != 1) {
                                tree = false;
                            }
                        }
                        // Check if you can go to root from each node
                        if (tree) {
                            int travels;
                            loop:
                            for(int i = 0; allNodes[i] != 0; i++ ){
                                travels = 0;
                                int node = allNodes[i];
                                while (node != root) {
                                    node = nodes[node];
                                    travels++;
                                    if (travels > 15) {
                                        tree = false;
                                        break loop;
                                    }
                                }
                            }
                        }
                    }
                    // Verdict
                    if(tree){
                        out.print("Case ");
                        out.print(testCase);
                        out.print(" is a tree.\n");
                    } else {
                        out.print("Case ");
                        out.print(testCase);
                        out.print(" is not a tree.\n");
                    }
                    // Start with new test case
                    testCase++;
                    tree = true;
                    // maybe replace with nodes[k--] = 0
                    k=0;
                    nodes = new int[NODES_RANGE];
                    allNodes = new int[ALL_SIZE];

                } else if (n1 == -1 && n2 == -1) {
                    break;
                } else if (tree) {
                    if (nodes[n2] != 0){
                        tree = false;
                    } else {
                        allNodes[k] = n1;
                        k++;
                        allNodes[k] = n2;
                        k++;
                        nodes[n2] = n1;
                    }
                }
        }

//flush output
        out.flush();

//remember to close the
//outputstream, at the end
        out.close();
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