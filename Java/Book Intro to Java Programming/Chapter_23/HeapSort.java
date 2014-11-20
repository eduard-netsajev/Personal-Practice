package Chapter_23;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class HeapSort {

    /** Heap sort method */
    public static <E extends Comparable<E>> void heapSort(E[] list) {
        // Create a Heap of integers
        BinaryHeap<E> heap = new BinaryHeap<>();

        // Add elements to the heap
        for (int i = 0; i < list.length; i++)
            heap.add(list[i]);

        // Remove elements from the heap
        for (int i = list.length - 1; i >= 0; i--)
            list[i] = heap.remove();
    }

    /** Test */
    public static void main(String[] args) {

        Random rand = new Random(131654);

        Integer[] list = new Integer[100000];

        for (int i = 0; i < list.length; i++) {
            list[i] = rand.nextInt(10000);
        }

        System.out.println(Arrays.toString(list));

        long t1 = System.nanoTime();
        heapSort(list);
        long t2 = System.nanoTime();

        System.out.println(Arrays.toString(list));
        System.out.println(t2-t1);
    }

}