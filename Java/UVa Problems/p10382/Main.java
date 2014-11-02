package p10382;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

class Interval implements Comparable<Interval>{
    double start;
    double end;

    @Override
    public int compareTo(Interval other) {
        if (start < other.start) {
            return -1;
        } else if (start > other.start) {
            return 1;
        } else {
            if (end > other.end) {
                return -1;
            } else if (end < other.end) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}

public class Main {

    public static void addIntv(int x, int r) {
        double dx2 = (r - w/2.0) * (r + w/2.0);
        if (dx2 > 0) {
            double dx = Math.sqrt(dx2);
            Interval inv = new Interval();
            inv.start = x - dx;
            inv.end = x + dx;
            intervals[counter++] = inv;
        }
    }

    static int counter;

    static int n, w, l;

    static Interval[] intervals;

    static Interval[] intervalsSmall;

    public static void main(String[] args) {
        Reader.init(System.in);
        try{
            loop:
            while(true) {
                n = Reader.nextInt();
                l = Reader.nextInt();
                w = Reader.nextInt();

                counter = 0;
                intervals = new Interval[n];
                for(int i = 0; i < n; i++) {
                    addIntv(Reader.nextInt(), Reader.nextInt());
                }

                intervalsSmall = new Interval[counter];
                for (int i = 0; i < counter; i++) {
                    intervalsSmall[i] = intervals[i];
                }
                intervals = intervalsSmall;
                Arrays.sort(intervals);

                double pos = 0;
                int counter = 0;
                for(int i = 0; i < Main.counter; i++) {
                    Interval inv = intervals[i];
                    if (inv.end <= pos) {
                        continue;
                    }
                    if (inv.start > pos) {
                        Reader.out.println(-1);
                        continue loop;
                    }

                    for(int j = i+1; j < Main.counter; j++) {

                        if (intervals[j].start > pos) {
                            break;
                        }
                        if (intervals[j].end > inv.end) {
                            inv = intervals[j];
                            i = j;
                        }
                    }
                    counter++;
                    pos = inv.end;
                    if (pos >= l) {
                        break;
                    }
                }
                if (pos < l) {
                    Reader.out.println(-1);
                } else
                Reader.out.println(counter);
            }
        } catch (Exception e) {
            Reader.out.close();
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

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
}
