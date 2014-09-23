/*import java.io.*;
import java.util.*;

class Main {

    //-----------PrintWriter for faster output---------------------------------
    public static PrintWriter out;

    //-----------MyScanner class for faster input----------
    public static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        public MyScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine(){
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                //e.printStackTrace();
            }
            return str;
        }
    }

    public static void main (String[] args) {
        // UVa Online Judge problem nr. 10146

        MyScanner in = new MyScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        int i = in.nextInt();
        in.nextLine();
        while (i > 0) {
            String line;
            String prefix = "";
            int plen = 0;
            int slen = 0;
            do {
                line = in.nextLine();
                if (line == null) {
                    i = 1;
                    break;
                }
                slen = line.length();
                if (slen == 0) {
                    if (i > 1){
                        out.println();
                    }
                    break;
                }
                if (line.startsWith(prefix)) {
                    if (!(slen < plen)) {
                        if (slen > plen) {
                            prefix = prefix + line.charAt(plen);
                        }
                        plen++;
                    }
                    for (int j = 1; j < plen; j++) {
                        line = ' ' + line;
                    }
                } else {
                    while (!line.startsWith(prefix)) {
                        prefix = prefix.substring(0, plen - 1);
                        plen--;
                    }
                    if (!(slen < plen)) {
                        if (slen > plen) {
                            prefix = prefix + line.charAt(plen);
                        }
                        plen++;
                    }
                    for (int j = 1; j < plen; j++) {
                        line = ' ' + line;
                    }
                }
                out.println(line);
            } while (true);
            i--;
        }

        out.close();
    }
}*/
/*
import java.util.Scanner;

class Main {
    public static void main (String[] args) {
        // UVa Online Judge problem nr. 10146
        Scanner in = new Scanner(System.in);
        int i = in.nextInt();
        in.nextLine();
        while(i>0) {
            String line;
            in.nextLine();
            char[] alphas = new char[1000];
            int slen;
            do {
                line = in.nextLine();
                char[] chars = line.toCharArray();
                slen = chars.length;
                if (!(slen > 0)) {
                    break;
                }
                int j = 0;
                while (j < slen) {
                    if (chars[j] == alphas[j]) {
                        line = '.' + line;
                        if (slen > j + 1) {j++;}
                        else { break;}
                    } else {
                        break;
                    }
                }
                alphas[j] = chars[j];
                System.out.println(line);
            }while (in.hasNextLine());
            i--;
        }
    }
}*/
/*
import java.util.Scanner;

class Main {
    public static void main (String[] args) {
        // UVa Online Judge problem nr. 10146
        Scanner in = new Scanner(System.in);
        int i = in.nextInt();
        if (i > 0) {
            in.nextLine();
            in.nextLine();
            while(true) {
                String line;
                char[] alphas = new char[10];
                int slen;
                int n =0;
                while (in.hasNextLine()) {
                    line = in.nextLine();
                    slen = line.length();
                    if (slen == 0){
                        break;
                    }
                    loop: {
                        while (n < slen && line.charAt(n) == alphas[n]) {
                            n++;
                            if (slen < n + 1) {
                                alphas[n] = '-';
                                break loop;
                            }
                        }
                        alphas[n] = line.charAt(n);
                    }
                    for (; n > 0; n--) {
                        line = '.' + line;
                    }
                    System.out.println(line);
                }
                i--;
                if (i > 0 && in.hasNextLine()) {
                    System.out.println();
                } else {
                    break;
                }
            }
        }
    }
}*/
/*
import java.util.Scanner;

class Main {
    public static void main (String[] args) {
        try {
            // UVa Online Judge problem nr. 10146
            Scanner in = new Scanner(System.in);
            int i = Integer.parseInt(in.nextLine());
            if (i > 0 && in.hasNextLine()) {
                in.nextLine();
                while (in.hasNextLine()) {
                    String line;
                    char[] alphas = new char[15];
                    int slen;
                    int n = 0;
                    while (in.hasNextLine()) {
                        line = in.nextLine();
                        slen = line.length();
                        if (slen == 0) {
                            break;
                        }
                        loop:
                        {
                            while (n < slen && line.charAt(n) == alphas[n]) {
                                n++;
                                if (slen < n + 1) {
                                    alphas[n] = '-';
                                    break loop;
                                }
                            }
                            alphas[n] = line.charAt(n);
                        }
                        for (; n > 0; n--) {
                            line = '.' + line;
                        }
                        System.out.println(line);
                    }
                    i--;
                    if (i > 0 && in.hasNextLine()) {
                        System.out.println();
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {}
    }
}*/

import java.io.*;
import java.util.*;

class Main {

    //-----------PrintWriter for faster output---------------------------------
    public static PrintWriter out;

    //-----------MyScanner class for faster input----------
    public static class MyScanner {
        BufferedReader br;
        StringTokenizer st;
        public MyScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    return "";
                    // e.printStackTrace(out);
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        String nextLine(){
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                return "";
                //  e.printStackTrace(out);
            }
            return str;
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
        boolean wasEmpty = true;

        while (i > 0) {
            char[] alphas = new char[15];
            while (true) {
                int n = 0;
                line = in.nextLine();
                if (line == null) {
                    out.close();
                    break;
                }
                slen = line.length();
                if (slen == 0) {
                    if(!wasEmpty) {
                        wasEmpty = true;
                        out.println();
                    } else {
                        i++;
                    }
                        break;
                }
                wasEmpty = false;
                loop:
                {
                    while (n < slen && line.charAt(n) == alphas[n]) {
                        out.print(' ');
                        n++;
                        if (slen < n + 1) {
                            for (int k = n; k < 15; k++){
                                alphas[k] = '-';
                            }
                            break loop;
                        }
                    }
                    alphas[n] = line.charAt(n);
                    while(++n < 15){
                        alphas[n] = '-';
                    }
                }
                out.println(line);
            }
            i--;
            if(i == 0) {
                break;
            }
        }
        out.close();
    }
}