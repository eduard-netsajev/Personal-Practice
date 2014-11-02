package p978;

import java.io.*;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main {

    static IntStreamReader inp;
    static OutputWriter out;
    static PriorityQueue<Integer> GreenArmy;
    static PriorityQueue<Integer> BlueArmy;
    static int[] blueWinners;
    static int[] greenWinners;


    public static void main(String[] args) throws IOException {


//initialize
        inp = new IntStreamReader(System.in);

        out	=	new OutputWriter(System.out);


        int t = inp.getNextInt();

        int B, SG, SB;

        while(true) {
            B = inp.getNextInt();
            SG = inp.getNextInt();
            SB = inp.getNextInt();

            GreenArmy = new PriorityQueue<Integer>(SG, Collections.reverseOrder());
            BlueArmy = new PriorityQueue<Integer>(SB, Collections.reverseOrder());

            for(int i = 0; i < SG; i++) {
                GreenArmy.offer(inp.getNextInt());
            }
            for(int i = 0; i < SB; i++) {
                BlueArmy.offer(inp.getNextInt());
            }

            while(!GreenArmy.isEmpty() && !BlueArmy.isEmpty()) {

                int gs = GreenArmy.size();
                int bs = BlueArmy.size();

                int f = Math.min(Math.min(gs, bs), B);

                blueWinners = new int[f];
                greenWinners = new int[f];
                int greenWin = 0;
                int blueWin = 0;

                for(int i = 0; i < f; i++) {
                    int b = BlueArmy.poll();
                    int g = GreenArmy.poll();

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
                out.printLine("green wins");
                while(!GreenArmy.isEmpty()) {
                    out.printLine(GreenArmy.poll());
                }
            } else if (!BlueArmy.isEmpty()) {
                out.printLine("blue wins");
                while(!BlueArmy.isEmpty()) {
                    out.printLine(BlueArmy.poll());
                }
            } else {
                out.printLine("green and blue died");
            }
            if(--t > 0) {
                out.printLine();
            } else {
                break;
            }
        }


//flush output
        out.flush();

//remember to close the
//outputstream, at the end
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