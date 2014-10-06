import java.io.*;
import java.util.*;

class Main {

    //-----------PrintWriter for faster output---------------------------------
    public static PrintWriter out;

    public static void main(String[] args) {

        out = new PrintWriter(new BufferedOutputStream(System.out));

        Reader.init(System.in);

        int cases;

        // c for candidate
        int cNumber;
        HashSet<Integer> cValid;
        String[] cNames;
        int[][] votes;
        int j = 0;
        int maxVotes;
        int minVotes;
        int[] voteBins;

        try {
            cases = Reader.nextInt();
            Reader.nextLine();

            while (cases > 0) {
                cValid = new HashSet<Integer>();
                cNumber = Reader.nextInt();
                if (cNumber == 0){
                    cases--;
                    continue;
                }
                cNames = new String[cNumber];

                for (int c = 0; c < cNumber; c++) {
                    cNames[c] = Reader.nextLine();
                    cValid.add(c);
                }

                votes = new int[1000][cNumber];

                try {
                    for (j = 0; j < 1000; j++) {
                        Reader.nextLine();
                        if (Reader.tokenizer.countTokens() == 0) {
                            break;
                        }
                        for (int i = 0; i < cNumber; i++) {
                            votes[j][i] = Reader.nextInt();
                        }
                    }
                } catch (Exception e){}

                int fifty = j / 2; // vote count must be > fifty

                outer:
                while (true) {
                    maxVotes = 0;
                    minVotes = 1000;
                    voteBins = new int[cNumber];

                    for (int k = 0; k < j; k++){
                        int i = 0;
                        while(!(cValid.contains(votes[k][i]-1))) {
                            i++;
                        }
                        voteBins[votes[k][i]-1]++;
                    }

                    for(int k = 0; k < cNumber; k++) {
                        if (cValid.contains(k)) {
                            if (voteBins[k] > fifty) {
                                out.println(cNames[k]);
                                break outer;
                            }
                            if (voteBins[k] > maxVotes){
                                maxVotes = voteBins[k];
                            }
                            if (voteBins[k] < minVotes) {
                                minVotes = voteBins[k];
                            }
                        }
                    }

                    if (minVotes == maxVotes) {
                        for (int cNum: cValid) {
                            out.println(cNames[cNum]);
                        }
                        break;
                    }
                    for(int k = 0; k < cNumber; k++) {
                        if (voteBins[k] == minVotes){
                            cValid.remove(k);
                        }
                    }
                }
                cases--;
                if(cases > 0) {
                    out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(out);
        }
        out.close();
    }
}


class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;
    static String currentLine;

// call this method to initialize reader for InputStream

    static void init(InputStream input) {
        reader = new BufferedReader(
                new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
        currentLine = "";
    }

// get next word

    static void flushTokenizer() {
        currentLine = "";
        tokenizer = new StringTokenizer(
                currentLine );
    }

    static String next() throws IOException {

        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            currentLine = reader.readLine();
            tokenizer = new StringTokenizer(
                    currentLine );
        }
        return tokenizer.nextToken();
    }

    static String nextLine(){
        flushTokenizer();
        try {
            currentLine = reader.readLine();
            tokenizer = new StringTokenizer(
                    currentLine );

            return currentLine;
        } catch (IOException e) {
            return "";
        }
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
}
