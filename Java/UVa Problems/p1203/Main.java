package p1203;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/**
 * Reader class for general input reading.
 */
class Reader {
    /**
     * BufferedReader instance.
     */
    static BufferedReader reader;

    /**
     * StringTokenizer instance.
     */
    static StringTokenizer tokenizer;

    /**
     * Currently being used line.
     */
    static String currentLine;

    /**
     * Call this method to initialize reader for InputStream.
     *
     * @param input InputStream instance
     */
    static void init(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input));
        tokenizer = new StringTokenizer("");
        currentLine = "";
    }

    /**
     * Clear both tokenizer and current line.
     */
    static void flushTokenizer() {
        currentLine = "";
        tokenizer = new StringTokenizer(currentLine);
    }

    /**
     * Get next token (word).
     * @return next token
     * @throws IOException if any problems happen
     */
    static String next() throws IOException {
        while (!tokenizer.hasMoreTokens()) {
            currentLine = reader.readLine();
            tokenizer = new StringTokenizer(currentLine);
        }
        return tokenizer.nextToken();
    }

    /**
     * Grab whole next line. Put it into the tokenizer.
     * @return next line
     */
    static String nextLine() {
        try {
            currentLine = reader.readLine();
            tokenizer = new StringTokenizer(currentLine);
            return currentLine;
        } catch (IOException|NullPointerException e) {
            return null;
        }
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
    static long nextLong() throws IOException {
        return Long.parseLong( next() );
    }
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);

        PriorityQueue<Query> queries = new PriorityQueue<>(1000);

        while(true) {
            String s = Reader.nextLine();
            if(s.equals("#")) {
                Reader.flushTokenizer();
                break;
            }
            Reader.next();
            int a = Reader.nextInt();
            int b = Reader.nextInt();
            queries.offer(new Query(a, b, b));
        }
        int n = Reader.nextInt();
        Reader.reader.close();

        while(n-- > 0) {
            Query q = queries.poll();
            System.out.println(q.id);
            queries.offer(new Query(q.id, q.time + q.originalTime, q.originalTime));
        }
    }
}

class Query implements Comparable<Query> {

    int id;
    int time;
    int originalTime;

    Query (int a, int b, int c) {
        id = a;
        time = b;
        originalTime = c;
    }

    @Override
    public int compareTo(Query o) {

        if (time > o.time) {
            return 1;
        } else if (time < o.time) {
            return -1;
        } else if (id > o.id) {
            return 1;
        } else if (id < o.id) {
            return -1;
        } else {
            return 0;
        }
    }
}