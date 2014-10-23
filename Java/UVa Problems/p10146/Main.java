package p10146;

import java.io.*;
import java.util.*;

class Main {

    //-----------PrintWriter for faster output---------------------------------
    public static PrintWriter out;

    //-----------MyScanner class for faster input----------
    public static class MyScanner {
        BufferedReader br;
        public MyScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
                try {
                    return new StringTokenizer(br.readLine()).nextToken();
                } catch (IOException e) {
                    return "";
                }}

        int nextInt() {
            return Integer.parseInt(next());
        }

        String nextLine(){
            try {
                return br.readLine();
            } catch (IOException e) {
                return "";
            }
        }
    }

    public static void main(String[] args) {
        // UVa Online Judge problem nr. 10146
        MyScanner in = new MyScanner();
            out = new PrintWriter(new BufferedOutputStream(System.out));
        int i = in.nextInt();
        in.nextLine();
        String line;
        int slen;
        int n;
        int max_len = 10;
        while (i > 0) {
            char[] alphas = new char[max_len];
            while (true) {
                n = 0;
                line = in.nextLine();
                if (line == null) {
                    out.close();
                    i = 1;
                    break;
                }
                slen = line.length();
                if (slen == 0) {
                    break;
                }
                loop:
                {
                    while (n < slen && line.charAt(n) == alphas[n]) {
                        out.print(' ');
                        n++;
                        if (slen < n + 1) {
                            for (int k = n; k < max_len; k++){
                                alphas[k] = '-';
                            }
                            break loop;
                        }
                    }
                    alphas[n] = line.charAt(n);
                    n++;
                    while(n < max_len){
                        alphas[n] = '-';
                        n++;
                    }
                }
                out.println(line);
            }
            i--;
            out.println();
        }
        out.close();
    }
}