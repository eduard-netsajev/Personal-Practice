package p11995;

import java.io.*;
import java.util.*;

public class Main {

    static BufferedWriter out = new BufferedWriter(new PrintWriter(System.out));

    public static void main(String[] args) {
        Reader.init(System.in);
        try {
            while(true) {
                int n = Reader.nextInt();

                boolean mbStack = true;
                boolean mbQueue = true;
                boolean mbPriorityQueue = true;

                Stack<Integer> stack = new Stack<>();
                Queue<Integer> queue = new LinkedList<>();
                PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(n, Collections.reverseOrder());

                boolean possible = true;
                int in = 0;

                while(n-- > 0) {
                    int c = Reader.nextInt();
                    int a = Reader.nextInt();

                    if (c > 1) {
                        if (in == 0) {
                            possible = false;
                        }
                        in--;
                        if (possible) {
                            if (mbStack) {
                                mbStack = a == stack.pop();
                            }
                            if (mbQueue) {
                                mbQueue = a == queue.poll();
                            }
                            if (mbPriorityQueue) {
                                mbPriorityQueue = a == priorityQueue.poll();
                            }
                        }

                    } else {
                        in++;
                        if (mbStack) {
                            stack.push(a);
                        }
                        if (mbQueue) {
                            queue.offer(a);
                        }
                        if (mbPriorityQueue) {
                            priorityQueue.offer(a);
                        }
                    }
                }

                if (!possible) {
                    out.write("impossible\n");
                } else if ((mbStack && mbQueue) || (mbQueue && mbPriorityQueue) || (mbPriorityQueue && mbStack)) {
                    out.write("not sure\n");
                } else if (mbStack) {
                    out.write("stack\n");
                } else if (mbQueue) {
                    out.write("queue\n");
                } else if (mbPriorityQueue) {
                    out.write("priority queue\n");
                } else {
                    out.write("impossible\n");
                }
            }
        } catch (Exception e) {
            //
        }
        try {
            out.close();
        } catch (IOException e) {
            //nope
        }
    }
}

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
     * @throws java.io.IOException if any problems happen
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
