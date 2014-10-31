package p11935;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static ArrayList<Integer> mile;
    static ArrayList<Integer> events;

    static int eventCount;

    static boolean can (double cap) {

        double fuelMax = cap;
        int leaks = 0;
        int lastP = 0;
        int lastUsage = events.get(0);
        for (int i = 1; i < eventCount; i++) {
            int P = mile.get(i);
            int km = P - lastP;
            cap -= (km * lastUsage) / 100.0;
            if (cap < 0) {
                return false;
            }
            lastP = P;
            int e = events.get(i);
            if (e > 0) {
                lastUsage = e + leaks * 100;
            } else if (e == -1) {
                leaks++;
                lastUsage += 100;
            } else if (e == -2) {
                cap = fuelMax;
            } else if (e == -3) {
                lastUsage -= leaks * 100;
                leaks = 0;
            } else {
                return true;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        while(true) {

            eventCount = 0;
            mile = new ArrayList<Integer>(51);
            events = new ArrayList<Integer>(51);

            Reader.next();
            Reader.next();
            Reader.next();
            {
                int f = Reader.nextInt();
                if (f == 0) break;
                mile.add(0);
                events.add(f);
                eventCount++;
            }
            reading:
            while(true) {
                mile.add(Reader.nextInt());
                eventCount++;
                switch (Reader.next()) {
                    case "Fuel":
                        Reader.next();
                        events.add(Reader.nextInt());
                        break;
                    case "Leak":
                        events.add(-1);   // - 1 for leak
                        break;
                    case "Gas":
                        Reader.next();
                        events.add(-2);  // -2 for gas station
                        break;
                    case "Mechanic":
                        events.add(-3); // -3 for mechanic
                        break;
                    case "Goal":
                        events.add(-7); // -7 for finish
                        break reading;
                }
            }

            double lo = 0.0, hi = 10000.0, mid, ans = 0.0;
            for(int i = 0; i < 27; i++) {
                mid = (lo+hi)/2.0;
                if (can(mid)) {
                    ans = mid;
                    hi = mid;
                } else {
                    lo = mid;
                }
            }
            System.out.printf("%.3f\n", ans);
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
     * Fast output.
     */
    static PrintWriter out;

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
        out = new PrintWriter(new BufferedOutputStream(System.out));
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

