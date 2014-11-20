package Chapter_23;

import java.util.Random;

/**
 * Quick Sort with the first number in the list chosen as a pivot.
 */
public class QuickSort {

    /** Main sort method */
    public static void quickSort(int[] list) {
        quickSort(list, 0, list.length -1);
    }

    /** Helper method */
    public static void quickSort(int[] list, int first, int last) {
        if (last > first) {
            int pivotIndex = partition(list, first, last);
            quickSort(list, first, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, last);
        }
    }

    /** Partition the array part from [first] to [last] */
    public static int partition(int[] list, int first, int last) {
        int pivot = list[first];
        int low = first + 1; // Index for forward search
        int high = last; // Index for backward search

        while(high > low) {
            // Search forward from left
            while(low <= high && list[low] <= pivot) {
                low++;
            }

            // Search backward from right
            while(low <= high && list[high] > pivot) {
                high--;
            }

            // Swap two elements in the list
            if (high > low) {
                int temp = list[low];
                list[low] = list[high];
                list[high] = temp;
            }
        }

        while(high > first && list[high] >= pivot) {
            high--;
        }

        // Swap pivot with list[high]
        if(pivot > list[high]) {
            list[first] = list[high];
            list[high] = pivot;
            return high;
        } else {
            return first;
        }
    }

    /** Test */
    public static void main(String[] args) {

        Random rand = new Random(131654);

        int[] list = new int[10000000];

        for (int i = 0; i < list.length; i++) {
            list[i] = rand.nextInt(10000);
        }

        //System.out.println(Arrays.toString(list));

        long t1 = System.nanoTime();
        quickSort(list);
        long t2 = System.nanoTime();

        //System.out.println(Arrays.toString(list));
        System.out.println(t2-t1);
    }
}