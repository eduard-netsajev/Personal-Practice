package p978;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int t = Reader.nextInt();

        int B, SG, SB;

        while(true) {
            B = Reader.nextInt();
            SG = Reader.nextInt();
            SB = Reader.nextInt();

            PriorityQueue<Integer> GreenArmy = new PriorityQueue<Integer>(SG, Collections.reverseOrder());
            PriorityQueue<Integer> BlueArmy = new PriorityQueue<Integer>(SB, Collections.reverseOrder());

            for(int i = 0; i < SG; i++) {
                GreenArmy.offer(Reader.nextInt());
            }
            for(int i = 0; i < SB; i++) {
                BlueArmy.offer(Reader.nextInt());
            }

            while(!GreenArmy.isEmpty() && !BlueArmy.isEmpty()) {

                int gs = GreenArmy.size();
                int bs = BlueArmy.size();

                int f = Math.min(Math.min(gs, bs), B);

                int[] blueWinners = new int[f];
                int[] greenWinners = new int[f];
                int greenWin = 0;
                int blueWin = 0;

                for(int i = 0; i < f; i++) {
                    int b = BlueArmy.poll();
                    int g = GreenArmy.poll();

                    // System.out.printf("Fight: blue %d versus green %d\n", b, g);

                    if (b > g) {
                        blueWinners[blueWin] = b-g;
                        blueWin++;
                    } else if (b < g) {
                        greenWinners[greenWin] = g-b;
                        greenWin++;
                    }
                }
                for (int i = 0; i < greenWin; i++) {
                    GreenArmy.offer(greenWinners[i]);
                }
                for (int i = 0; i < blueWin; i++) {
                    BlueArmy.offer(blueWinners[i]);
                }
            }

            if (!GreenArmy.isEmpty()) {
                System.out.println("green wins");
                while(!GreenArmy.isEmpty()) {
                    System.out.println(GreenArmy.poll());
                }
            } else if (!BlueArmy.isEmpty()) {
                System.out.println("blue wins");
                while(!BlueArmy.isEmpty()) {
                    System.out.println(BlueArmy.poll());
                }
            } else {
                System.out.println("green and blue died");
            }
            if(--t > 0) {
                System.out.println();
            } else {
                break;
            }
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
