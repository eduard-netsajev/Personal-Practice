package p11292;

import java.io.*;
import java.util.Arrays;

public class Main {

    static OutputWriter out	= new OutputWriter(System.out);

    static int n, m, gold, d, k;

    static int[] heads;
    static int[] knights;

    public static void main(String[] args) throws IOException {
        IntStreamReader inp = new IntStreamReader(System.in);

        while(true) {
            n = inp.getNextInt();
            m = inp.getNextInt();
            if(n == 0 && m == 0) break;
            if (n > m) {
                out.print("Loowater is doomed!\n");
                inp.skip(n+m);
                continue;
            }
            heads = new int[n];
            for(int i = 0; i < n; i++) {
                heads[i] = inp.getNextInt();
            }

            knights = new int[m];
            for(int i = 0; i < m; i++) {
                knights[i] = inp.getNextInt();
            }
            Arrays.sort(heads);
            Arrays.sort(knights);

            gold = d = k = 0;
            while(d < n && k < m) {
                while (k < m && heads[d] > knights[k]) k++;
                if (k == m) break;
                gold += knights[k];
                d++;
                k++;
            }

            if(d == n) out.printLine(gold);
            else out.print("Loowater is doomed!\n");
        }

        out.close();
    }

    static class IntStreamReader {

        private BufferedInputStream inp = null;
        private int offset = 0;
        private int size = 1024;
        private byte[] buff = new byte[size];

        public IntStreamReader(InputStream in) throws IOException {
            inp = new BufferedInputStream(in);
            inp.read(buff, 0, size);
        }

        public void skip(int n) throws IOException {
            for(int i = 0; i < n; i++) {
                getNextInt();
            }
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