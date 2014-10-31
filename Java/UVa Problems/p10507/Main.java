package p10507;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static boolean[] vertices;
    static boolean[][] edges;
    static int[] visited;
    static int visitedV;
    static boolean[] waken;

    static ArrayList<Integer> nextRound;

    static void bfs(int i) {

        for (int j = 0; j < 26; j++) {
            if (edges[i][j] && !waken[j]) {
                if (visited[j] < 2) {
                    visited[j]++;
                } else {
                    waken[j] = true;
                    nextRound.add(j);
                    visitedV++;
                }
            }
        }
    }

    public static void main(String[] args) {
        Reader.init(System.in);
        char[] ch;

        try {
            while (true) {
                Reader.flushTokenizer();
                int V = Reader.nextInt();
                int edgecount = Reader.nextInt();
                ch = Reader.nextLine().toCharArray();

                vertices = new boolean[26];
                waken = new boolean[26];
                edges = new boolean[26][26];
                visited = new int[26];
                nextRound = new ArrayList<>(40);

                visitedV = 3;

                vertices[ch[0] - 65] = true;
                vertices[ch[1] - 65] = true;
                vertices[ch[2] - 65] = true;

                nextRound.add(ch[0] - 65);
                nextRound.add(ch[1] - 65);
                nextRound.add(ch[2] - 65);

                waken[ch[0] - 65] = true;
                waken[ch[1] - 65] = true;
                waken[ch[2] - 65] = true;

                while (edgecount-- > 0) {
                    String str = Reader.nextLine();
                    if (str == null || str.length() < 2) {
                        break;
                    }

                    ch = str.toCharArray();
                    int a = ch[0] - 65;
                    int b = ch[1] - 65;
                    vertices[a] = vertices[b] = edges[a][b] = edges[b][a] = true;
                }

                int count = 0;
                loop:
                for (int i = 0; i < 26; i++) {
                    if (vertices[i]) {
                        if (nextRound.contains(i)){
                            count++;
                            continue;
                        }
                        for (int j = 0; j < 26; j++) {
                            if (edges[i][j]) {
                                count++;
                                continue loop;
                            }
                        }
                    }
                }
                if (count != V) {
                    System.out.println("THIS BRAIN NEVER WAKES UP");
                } else {
                    int years = 0;
                    while(!nextRound.isEmpty() && visitedV < V) {

                        ArrayList<Integer> now = nextRound;
                        nextRound = new ArrayList<>(40);
                        for(Integer i: now) {
                            bfs(i);
                        }
                        years++;
                    }
                    if (visitedV < V) {
                        System.out.println("THIS BRAIN NEVER WAKES UP");
                    } else {
                        System.out.printf("WAKE UP IN, %d, YEARS\n", years);
                    }
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
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
     * @throws java.io.IOException if any problems happen
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