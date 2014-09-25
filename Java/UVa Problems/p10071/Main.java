import java.io.*;
import java.util.StringTokenizer;

class Main {
    // UVa Online Judge problem nr. 10071 Run time: 0.596
    public static void main(String[] args) {
        Reader.init(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        while (true){
            try{
                int speed = Reader.nextInt();
                int time = Reader.nextInt();
                out.println(2 * speed * time);
            } catch (Exception e){
                //e.printStackTrace(out);
                break;
            }
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
