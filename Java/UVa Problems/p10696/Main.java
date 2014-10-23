package p10696;
import java.io.*;
import java.util.StringTokenizer;

class Main {
    // UVa Online Judge problem nr. 11636 Run Time: 0.249
    public static void main(String[] args) {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        Reader.init(System.in);

        int i;
        try {
            while (true) {
                i = Reader.nextInt();
                if (i > 0) {
                    out.print("f91(");
                    out.print(i);
                    out.print(") = ");
                    if (i < 102) {
                        out.println(91);
                    } else {
                        out.println(i - 10);
                    }
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            //e.printStackTrace(out);
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
