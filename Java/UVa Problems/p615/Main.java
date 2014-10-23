package p615;
import java.util.*;
import java.io.*;

class Main {
    // UVa Online Judge problem nr. 615
    // Ranking: 1821    Run time:  0.129

    public static void main(String[] args) {
        Reader.init(System.in);
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
            try {
                int n1 = Reader.nextInt();
                int n2 = Reader.nextInt();
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
                        System.out.println("Case " + testCase + " is a tree.");
                    } else {
                        System.out.println("Case " + testCase +
                                " is not a tree.");
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
            } catch (IOException e) {
                break;
            }
        }
    }
}


// Class for buffered reading int and double values

class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

// call this method to initialize reader for InputStream

    static void init(InputStream input) {
        reader = new BufferedReader(
                new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

// get next word

    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                    reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
}
