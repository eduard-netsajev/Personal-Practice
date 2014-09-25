import java.io.*;
import java.util.StringTokenizer;

class Main {
    // UVa Online Judge problem nr. 102 Run Time: 0.692
    public static void main(String[] args) {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        final int[][] cases = new int[][] {
                {0, 5, 7}, {0, 4, 8}, {2, 3, 7}, {2, 4, 6}, {1, 3, 8}, {1, 5, 6}
        };
        final int array_size = 9;
        Reader.init(System.in);

        int[] bottles = new int[array_size];
        int[] max_case = new int[3];
        int total;
        int max;
        int current;

        while (true){
            try{
                max = 0;
                total = 0;
                for (int i = 0; i < array_size; i++) {
                    bottles[i] = Reader.nextInt();
                    total += bottles[i];
                }
                for (int i = 0; i < 6; i++) {
                    current = 0;
                    current += bottles[cases[i][0]];
                    current += bottles[cases[i][1]];
                    current += bottles[cases[i][2]];
                    if (current > max) {
                        max = current;
                        max_case = cases[i];
                    }
                }
                if (max_case[0] == 0){
                    out.print('B');
                } else if (max_case[0] == 1) {
                    out.print('G');
                } else {
                    out.print('C');
                }
                if (max_case[1] == 3){
                    out.print('B');
                } else if (max_case[1] == 4) {
                    out.print('G');
                } else {
                    out.print('C');
                }
                if (max_case[2] == 6){
                    out.print("B ");
                } else if (max_case[2] == 7) {
                    out.print("G ");
                } else {
                    out.print("C ");
                }

                out.println(total - max);
            } catch (Exception e){
                //e.printStackTrace(out);
                break;
            }
        }
        out.close();
    }
}

class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

// call this method to initialize reader for InputStream

    static void init(InputStream input) {
        reader = new BufferedReader(
                new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

// get next word

    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                    reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
}