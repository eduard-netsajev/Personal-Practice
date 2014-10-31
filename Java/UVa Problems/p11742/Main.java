package p11742;

import java.io.*;
import java.util.*;

public class Main {

    static int[] a;
    static int[] b;
    static int[] c;

    static int[] ppl;

    static int distance(int a, int b) {

        int p1 = 0;
        int p2 = 0;

        for(int i = 0; i < n; i++) {
        if (ppl[i] == a) {
            p1 = i;
            break;
        }
        }
        for(int i = 0; i < n; i++) {
            if (ppl[i] == b) {
                p2 = i;
                break;
            }
        }
        return abs(p1-p2);
    }

    static int abs(int a) {
        return (a < 0) ? -a : a;
    }

    static int n;

    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int m;
        while((n = Reader.nextInt()) + (m = Reader.nextInt()) > 0) {

            int[] people = new int[n];
            for(int i = 0; i < n; i++) {
                people[i] = i;
            }

            a = new int[m];
            b = new int[m];
            c = new int[m];

            for(int i = 0; i < m; i++) {
                a[i] = Reader.nextInt();
                b[i] = Reader.nextInt();
                c[i] = Reader.nextInt();
            }



            Permutations perm = new Permutations(people);
            int count = 0;
            loop:
            while(perm.hasNext()){
                ppl = perm.next();
                for(int i = 0; i < m; i++) {

                    int d = distance(a[i], b[i]);

                    if (c[i] < 0) {
                        if (d < abs(c[i])) continue loop;
                    } else {
                        if (d > abs(c[i])) continue loop;
                    }
                }
                count++;
            }
            System.out.println(count);


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
     * Fast output.
     */
    static PrintWriter out;

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
        out = new PrintWriter(new BufferedOutputStream(System.out));
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
     * @throws IOException if any problems happen
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


/**
 * Not mine. Found on StackOverflow.
 */
class Permutations {

    private int[] arr;
    private int[] ind;
    private boolean has_next;

    public int[] output;//next() returns this array, make it public

    Permutations(int[] arr){
        this.arr = arr.clone();
        ind = new int[arr.length];
        //convert an array of any elements into array of integers - first occurrence is used to enumerate
        Map<Integer, Integer> hm = new HashMap<Integer, Integer>();
        for(int i = 0; i < arr.length; i++){
            Integer n = hm.get(arr[i]);
            if (n == null){
                hm.put(arr[i], i);
                n = i;
            }
            ind[i] = n;
        }
        Arrays.sort(ind);//start with ascending sequence of integers


        //output = new E[arr.length]; <-- cannot do in Java with generics, so use reflection
        output = new int[arr.length];
        has_next = true;
    }

    public boolean hasNext() {
        return has_next;
    }

    /**
     * Computes next permutations. Same array instance is returned every time!
     * @return next permutation array
     */
    public int[] next() {
        if (!has_next)
            throw new NoSuchElementException();

        for(int i = 0; i < ind.length; i++){
            output[i] = arr[ind[i]];
        }


        //get next permutation
        has_next = false;
        for(int tail = ind.length - 1;tail > 0;tail--){
            if (ind[tail - 1] < ind[tail]){//still increasing

                //find last element which does not exceed ind[tail-1]
                int s = ind.length - 1;
                while(ind[tail-1] >= ind[s])
                    s--;

                swap(ind, tail-1, s);

                //reverse order of elements in the tail
                for(int i = tail, j = ind.length - 1; i < j; i++, j--){
                    swap(ind, i, j);
                }
                has_next = true;
                break;
            }

        }
        return output;
    }

    private void swap(int[] arr, int i, int j){
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

}
