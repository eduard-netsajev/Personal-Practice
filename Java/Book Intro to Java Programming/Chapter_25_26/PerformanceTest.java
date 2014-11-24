package Chapter_25_26;

import java.util.Arrays;
import java.util.Random;

/** Speed test for both trees */
public class PerformanceTest {
    public static void main(String[] args) {

        Random rand = new Random(131654);
        final int SIZE = 500000;

        Integer[] list = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            list[i] = rand.nextInt();
        }

        long t1 = System.nanoTime();
        BinarySearchTree<Integer> BSTree = new BinarySearchTree<>(list);
        long t2 = System.nanoTime();

        System.out.println("BST insert: " + (t2-t1));

        t1 = System.nanoTime();
        BinarySearchTree<Integer> AVLTree = new AVLTree<>(list);
        t2 = System.nanoTime();

        System.out.println("AVL insert: " + (t2-t1));

        ShuffleArray(list);

        t1 = System.nanoTime();
        for (int i = 0; i < SIZE; i++) {
            BSTree.search(list[i]);
        }
        t2 = System.nanoTime();

        System.out.println("\nBST search: " + (t2 - t1));

        t1 = System.nanoTime();
        for (int i = 0; i < SIZE; i++) {
            AVLTree.search(list[i]);
        }
        t2 = System.nanoTime();

        System.out.println("AVL search: " + (t2-t1));

        t1 = System.nanoTime();
        for (int i = 0; i < SIZE; i++) {
            BSTree.delete(list[i]);
        }
        t2 = System.nanoTime();

        ShuffleArray(list);

        System.out.println("\nBST delete: " + (t2-t1));

        t1 = System.nanoTime();
        for (int i = 0; i < SIZE; i++) {
            AVLTree.delete(list[i]);
        }
        t2 = System.nanoTime();

        System.out.println("AVL delete: " + (t2-t1));



    }

    private static void ShuffleArray(Integer[] array) {
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
