package p11059;
import java.io.*;
import java.util.*;

class Main {

    //-----------PrintWriter for faster output---------------------------------
    public static PrintWriter out;

    public static void main(String[] args) {
        // UVa Online Judge problem nr. 11059 Run time: 0.135
        Reader.init(System.in);
        out = new PrintWriter(new BufferedOutputStream(System.out));

        int k = 1;
        long max = 0;
        long current;
        long firstPositive;
        long withoutLastNegative;
        boolean negative;
        boolean noNegative;
        boolean noPositive;
        while(true) {
            try {
                int size = Reader.nextInt();
                out.print("Case #");
                out.print(k);
                noPositive = true;

                if (size > 0) {
                    // Variables -> %d*Product
                    max = 1;
                    current = 1;
                    firstPositive = 1;
                    withoutLastNegative = 1;
                    noNegative = true;
                    negative = false;

                    for (int i; size > 0; size--) {
                        i = Reader.nextInt();
                        if (i > 0) { // i > 0 i < 0 i == 0
                            current *= i;
                            noPositive = false;
                        } else if (i < 0) {
                            if (negative){
                                current *= i;
                                noPositive = false;
                            } else {
                                if (noNegative) {
                                    withoutLastNegative = current;
                                    current *= i;
                                    firstPositive = current;
                                    noNegative = false;
                                } else {
                                    withoutLastNegative = current;
                                    current *= i;
                                }
                            }
                            negative = !negative;
                        } else {
                            if(negative) {
                                long withoutFirstNegatives = current/firstPositive;

                                if (withoutFirstNegatives > withoutLastNegative) {
                                    current = withoutFirstNegatives;
                                } else {
                                    current = withoutLastNegative;
                                }
                            }
                            if (current > max) {
                                max = current;
                            }
                            current = 1;
                            firstPositive = 1;
                            withoutLastNegative = 1;
                            negative = false;
                            noNegative = true;
                        }
                    }

                    if(negative) {
                        long withoutFirstNegatives = current/firstPositive;

                        if (withoutFirstNegatives > withoutLastNegative) {
                            current = withoutFirstNegatives;
                        } else {
                            current = withoutLastNegative;
                        }
                    }
                    if (current > max) {
                        max = current;
                    }
                }
                out.print(": The maximum product is ");
                if(noPositive){
                    out.print(0);
                }
                else {
                    out.print(max);
                }
                out.println('.');
                k++;
                out.println();
            } catch (NullPointerException e) {
                break;
            } catch (IOException e) {
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