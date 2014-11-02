package p11450;

import java.io.*;

class MyMain { /* UVa 11450 - Wedding Shopping - Bottom Up */

    static IntStreamReader in;
    static OutputWriter out = new OutputWriter(System.out);
    static int i, j, l, TC, M, C, K;
    static int[][] price = new int[25][25]; // price[g (<= 20)][model (<= 20)]
    static boolean[][] reachable = new boolean[210][25]; // reachable table[money (<= 200)][g (<= 20)]

    public static void main(String[] args) throws IOException {
        in = new IntStreamReader(System.in);

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
                    reachable[i][j] = false; // clear everything

            for (i = 1; i <= price[0][0]; i++) // initial values
                if (M - price[0][i] >= 0)
                    reachable[M - price[0][i]][0] = true; // if only using first garment g = 0

            for (j = 1; j < C; j++) // for each remaining garment (note: this is written in column major)
                for (i = 0; i < M; i++) if (reachable[i][j - 1]) // if can reach this state
                    for (l = 1; l <= price[j][0]; l++) if (i - price[j][l] >= 0) // flag the rest
                        reachable[i - price[j][l]][j] = true; // as long as it is feasible

            M++;
            for (i = 0; i < M && !reachable[i][C - 1]; i++); // the answer is in the last column

            if (i != M) {
                out.printLine(M - 1 - i);
            } else {
                out.printLine("no solution"); // nothing in this last column has its bit turned on
            }

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