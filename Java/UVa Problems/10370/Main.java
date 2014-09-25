import java.io.*;
import java.util.StringTokenizer;

class Main {
    // UVa Online Judge problem nr. 10370   Run time: 0.296

    public static PrintWriter out;

    public static void main(String[] args) {
        out = new PrintWriter(new BufferedOutputStream(System.out));
        Reader.init(System.in);
        try {
            for (int tests = Reader.nextInt(); tests > 0; tests--) {
                int count = Reader.nextInt();
                if (count == 0) {
                    continue;
                }
                int total = 0;
                int[] students = new int[count];
                for (int i = count; i > 0; ) {
                    students[--i] = Reader.nextInt();
                    total += students[i];
                }
                float average = total / count;

                int aboveAverage = 0;
                for (int grade : students) {
                    if (grade > average) {
                        aboveAverage++;
                    }
                }
                out.printf("%.3f", 100.0 * aboveAverage / count);
                out.println("%");
            }
        } catch (IOException e) {}
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
