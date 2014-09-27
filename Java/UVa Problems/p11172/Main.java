import java.io.*;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        Reader.init(System.in);
        try {
            for (int count = Reader.nextInt(); count > 0; count--){
                int x = Reader.nextInt();
                int y = Reader.nextInt();

                if(x > y) {
                    out.println('>');
                } else if(x < y) {
                    out.println('<');
                } else {
                    out.println('=');
                }
            }
        } catch (Exception e) {}
        out.close();
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