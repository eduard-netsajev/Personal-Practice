import java.io.*;
import java.util.StringTokenizer;

class Main {
    // UVa Online Judge problem nr. 11636 Run Time: 0.249
    public static void main(String[] args) {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        Reader.init(System.in);

        int i;
        int count = 1;
        try {
            while (true) {
                i = Reader.nextInt();
                if (i > 0) {
                    out.print("Case ");
                    out.print(count);
                    out.print(": ");

                    if (i < 2) {
                        out.println(0);
                    } else if (i < 3) {
                        out.println(1);
                    } else if (i < 5) {
                        out.println(2);
                    } else if (i < 9) {
                        out.println(3);
                    } else if (i < 17) {
                        out.println(4);
                    } else if (i < 33) {
                        out.println(5);
                    } else if (i < 65) {
                        out.println(6);
                    } else if (i < 129) {
                        out.println(7);
                    } else if (i < 257) {
                        out.println(8);
                    } else if (i < 513) {
                        out.println(9);
                    } else if (i < 1025) {
                        out.println(10);
                    } else if (i < 2049) {
                        out.println(11);
                    } else if (i < 4097) {
                        out.println(12);
                    } else if (i < 8193) {
                        out.println(13);
                    } else {
                        out.println(14);
                    }
                    count++;
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
