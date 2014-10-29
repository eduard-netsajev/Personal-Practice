package Library;

import java.util.ArrayList;

public class DisjointSets {
    private ArrayList<Integer> parent, rank, setSize;
    private int numSets;

    public DisjointSets(int N) {
        parent = new ArrayList<>(N);
        rank = new ArrayList<>(N);
        setSize = new ArrayList<>(N);

        numSets = N;

        for(int i = 0; i < N; i++) {
            parent.add(i);
            rank.add(0);
            setSize.add(1);
        }
    }

    public int findSet(int c) {

        while(parent.get(c) != c) {
            parent.set(c, parent.get(parent.get(c)));
            c = parent.get(c);
        }
        return c;
    }

    public boolean isSameSet(int i, int j) {
        return findSet(i) == findSet(j);
    }

    public void unionSet(int i, int j) {

        int x = findSet(i);
        int y = findSet(j);

        if (x != y) {
            numSets--;

            if (rank.get(x) > rank.get(y)) {
                connectToSet(x, y);
            } else if (rank.get(y) > rank.get(x)) {
                connectToSet(y, x);
            } else {
                connectToSet(x, y);
                rank.set(x, rank.get(x) + 1);
            }
        }
    }

    private void connectToSet(int larger, int smaller) {
        parent.set(smaller, larger);
        setSize.set(larger, setSize.get(larger) + setSize.get(smaller));
    }

    public int numDisjointSets() {
        return numSets;
    }

    public int sizeOfSet(int i) {
        return setSize.get(findSet(i));
    }

    public static void main(String[] args) {
        //test

        System.out.printf("Assume that there are 5 disjoint sets initially\n");
        DisjointSets UF = new DisjointSets(5); // create 5 disjoint sets
        System.out.printf("%d\n", UF.numDisjointSets()); // 5
        UF.unionSet(0, 1);
        System.out.printf("%d\n", UF.numDisjointSets()); // 4
        UF.unionSet(2, 3);
        System.out.printf("%d\n", UF.numDisjointSets()); // 3
        UF.unionSet(4, 3);
        System.out.printf("%d\n", UF.numDisjointSets()); // 2
        System.out.printf("isSameSet(0, 3) = %b\n", UF.isSameSet(0, 3)); // will return false
        System.out.printf("isSameSet(4, 3) = %b\n", UF.isSameSet(4, 3)); // will return true
        for (int i = 0; i < 5; i++) // findSet will return 1 for {0, 1} and 3 for {2, 3, 4}
            System.out.printf("findSet(%d) = %d, sizeOfSet(%d) = %d\n", i, UF.findSet(i), i, UF.sizeOfSet(i));
        UF.unionSet(0, 3);
        System.out.printf("%d\n", UF.numDisjointSets()); // 1
        for (int i = 0; i < 5; i++) // findSet will return 3 for {0, 1, 2, 3, 4}
            System.out.printf("findSet(%d) = %d, sizeOfSet(%d) = %d\n", i, UF.findSet(i), i, UF.sizeOfSet(i));

    }
}
