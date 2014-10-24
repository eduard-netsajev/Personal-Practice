package p10114;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);

        int months;
        double payment;
        double cost;
        int periodsCount;
        loop:
        while((months = Reader.nextInt()) >= 0) {

            payment = Reader.nextDouble();
            cost = Reader.nextDouble();
            periodsCount = Reader.nextInt();

            int[] mon = new int[periodsCount];
            double[] rate = new double[periodsCount];

            for (int i = 0; i < periodsCount; i++) {
                mon[i] = Reader.nextInt();
                rate[i] = Reader.nextDouble();
            }

            double carCost = (cost + payment) * (1 - rate[0]);
            payment = cost / months;

            if (carCost > cost) {
                System.out.println("0 months");
                continue;
            }

            int rateNum = 0;
            months++;
            for(int i = 1; i < months; i++) {
                cost -= payment;
//                System.out.printf("Car costs %f after %d months. Rate was %f To pay: %f\n", carCost, i, rate[rateNum], cost);

                if (rateNum+1 < periodsCount && i == mon[rateNum + 1]) {
                    rateNum++;
                }
                carCost = carCost * (1.0 - rate[rateNum]);
                if (cost < carCost) {
                    if (i > 1) {
                        System.out.println(i + " months");
                    } else {
                        System.out.println(i + " month");
                    }
                    continue loop;
                }
            }
            System.out.println(1 + " month");
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
