package Chapter_23;

import java.util.*;

/**
 * Radix sort for efficient sorting of integers.
 */
public class RadixSort {

    /** Main sort method */
    public static void radixSort(int[] list) {
        int length = list.length;

        Queue<Integer>[] buckets = new LinkedList[10];
        for (int i = 0; i < 10; i++) {
            buckets[i] = new LinkedList<Integer>();
        }

        int radix = 1;
        while (true) {

            for (int i = 0; i < length; i++) {
                buckets[list[i] / radix % 10].offer(list[i]);
            }

            if (buckets[0].size() == length) {
                break;
            }

            int counter = 0;
            for (int i = 0; i < 10; i++) {
                Queue<Integer> deque = buckets[i];
                while(!deque.isEmpty()) {
                    list[counter++] = deque.poll();
                }
            }

            radix *= 10;
        }

        for(int i = 0; i < length; i++) {
            Queue<Integer> deque = buckets[0];
            list[i] = deque.poll();
        }

    }

    /** Test */
    public static void main(String[] args) {

        Random rand = new Random(131654);

        int[] list = new int[100000];

        for (int i = 0; i < list.length; i++) {
            list[i] = rand.nextInt(10000);
        }

        //System.out.println(Arrays.toString(list));

        long t1 = System.nanoTime();
        radixSort(list);
        long t2 = System.nanoTime();

        //System.out.println(Arrays.toString(list));
        System.out.println(t2-t1);
    }
}
