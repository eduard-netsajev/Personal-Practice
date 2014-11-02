package p11450;

import java.io.*;

class Main { /* UVa 11450 - Wedding Shopping - Top Down */
    private static int M, C, K;
    private static int[][] price = new int[25][25]; // price[g (<= 20)][model (<= 20)]
    private static int[][] memo = new int[210][25]; // dp table memo[money (<= 200)][g (<= 20)]

    private static int shop(int money, int g) {
        if (money < 0)            return -1000000000; // fail, return a large negative number (1B)
        if (g == C)               return M - money; // we have bought last garment, done
        if (memo[money][g] != -1) return memo[money][g]; // this state has been visited before
        int ans = -1000000000;
        for (int model = 1; model <= price[g][0]; model++) // try all possible models
            ans = Math.max(ans, shop(money - price[g][model], g + 1));
        return memo[money][g] = ans; // assign ans to dp table + return it!
    }

    static IntStreamReader in;
    static OutputWriter out	= new OutputWriter(System.out);

    public static void main(String[] args) throws IOException { // easy to code if you are already familiar with it
        in = new IntStreamReader(System.in);
        int i, j, TC, score;

        TC = in.getNextInt();
        while (TC-- > 0) {
            M = in.getNextInt(); C = in.getNextInt();
            for (i = 0; i < C; i++) {
                K = in.getNextInt();
                price[i][0] = K; // to simplify coding, we store K in price[i][0]
                for (j = 1; j <= K; j++)
                    price[i][j] = in.getNextInt();
            }

            for (i = 0; i < 210; i++)
                for (j = 0; j < 25; j++)
                    memo[i][j] = -1; // initialize DP memo table

            score = shop(M, 0); // start the top-down DP
            if (score < 0) out.print("no solution\n");
            else           out.printLine(score);
        }
        out.close();
    }

    static class IntStreamReader {

        private BufferedInputStream inp = null;
        private int offset = 0;
        private int size = 51200;
        private byte[] buff = new byte[size];

        public IntStreamReader(InputStream in) throws IOException {
            inp = new BufferedInputStream(in);
            inp.read(buff, 0, size);
        }

        public int getNextInt() throws IOException {
            int parsedInt = 0;
            int i = offset;
            // skip any non digits
            while (i < buff.length && (buff[i] < '0' || buff[i] > '9')) {
                i++;
            }
            // read digits and parse number
            while (i < buff.length && buff[i] >= '0' && buff[i] <= '9') {
                parsedInt *= 10;
                parsedInt += buff[i] - '0';
                i++;
            }
            // check if we reached end of buffer
            if (i == buff.length) {
                // copy leftovers to buffer start
                int j = 0;
                for (; offset < buff.length; j++, offset++) {
                    buff[j] = buff[offset];
                }
                // and now fil the buffer
                inp.read(buff, j, size - j);
                // and attempt to parse int again
                offset = 0;
                parsedInt = getNextInt();
            } else {
                offset = i;
            }
            return parsedInt;
        }
    }
}


class OutputWriter {
    private final PrintWriter writer;

    public OutputWriter(OutputStream outputStream) {
        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
    }

    public OutputWriter(Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    public void print(Object...objects) {
        for (int i = 0; i < objects.length; i++) {
            if (i != 0)
                writer.print(' ');
            writer.print(objects[i]);
        }
    }

    public void printLine(Object...objects) {
        print(objects);
        writer.println();
    }

    public void close() {
        writer.close();
    }

    public void flush() {
        writer.flush();
    }

}