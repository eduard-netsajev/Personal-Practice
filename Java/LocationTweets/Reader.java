import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;
    static String currentLine;

    // call this method to initialize reader for InputStream

    static void init(InputStream input) {
        reader = new BufferedReader(
                new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
        currentLine = "";
    }

    static void flushTokenizer() {
        currentLine = "";
        tokenizer = new StringTokenizer(
                currentLine );
    }

    // get next word
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            currentLine = reader.readLine();
            tokenizer = new StringTokenizer(
                    currentLine );
        }
        return tokenizer.nextToken();
    }

    static String nextLine(){
        flushTokenizer();
        try {
            currentLine = reader.readLine();
            tokenizer = new StringTokenizer(currentLine);
            return currentLine;
        } catch (IOException|NullPointerException e) {
            return null;
        }
    }
}