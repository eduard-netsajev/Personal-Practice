package p100;
import java.io.*;
import java.util.StringTokenizer;

class Main {

    public static PrintWriter out;

    public static void main (String[] args) {
        // UVa Online Judge problem nr. 100
        // Run time: 0.169
        out = new PrintWriter(new BufferedOutputStream(System.out));
        Reader.init(System.in);
        int ARRAY_SIZE = 1000000;
        int[] stats = new int[ARRAY_SIZE];
        int i, j;
        while (true) {
            try {
                i = Reader.nextInt();
                j = Reader.nextInt();
            } catch (Exception e){
                break;
            }
            out.print(i);
            out.print(" ");
            out.print(j);
            out.print(" ");

            if (j < i) {
                int temp = i;
                i = j;
                j = temp;
            }
            j++;
            int l = 0;
            while (i < j) {
                int k = 1;
                if (stats[i] > 0) {
                    k = stats[i];
                } else {
                    int n = i;
                    while (n > 1) {
                        if (n % 2 > 0){
                            n = n * 3 + 1;
                        } else {
                            n /= 2;
                        }
                        if (n < ARRAY_SIZE) {
                            if (stats[n] > 0) {
                                k += stats[n];
                                break;
                            }
                        }
                        k++;
                    }
                    stats[i] = k;
                }
                l = (k > l) ? k : l;
                i++;
            }
            out.println(l);
        }
    out.close();
    }
}

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