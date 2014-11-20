package Chapter_23;

import java.util.Random;

/** Merge Sort with two temporary arrays for both halves */
public class MergeSort {

    /** Main method for sorting numbers using Merge Sort */
    public static void mergeSort(int[] list) {
        if (list.length > 1) {
            // Merge Sort the first half
            int[] firstHalf = new int[list.length / 2];
            System.arraycopy(list, 0, firstHalf, 0, list.length/2);
            mergeSort(firstHalf);

            // Merge sort the second half
            int secondHalfLength = list.length - list.length / 2;
            int[] secondHalf = new int[secondHalfLength];
            System.arraycopy(list, list.length / 2, secondHalf, 0, secondHalfLength);
            mergeSort(secondHalf);

            // Merge firstHalf with secondHalf into list
            merge(firstHalf, secondHalf, list);
        }
    }

    /** Merge two sorted lists */
    public static void merge(int[] firstList, int[] secondList, int[] resultList) {

        // Current position index in the list
        int posFirst = 0;
        int posSecond = 0;
        int posResult = 0;

        while (posFirst < firstList.length && posSecond < secondList.length) {
            if (firstList[posFirst] < secondList[posSecond]) {
                resultList[posResult++] = firstList[posFirst++];
            } else {
                resultList[posResult++] = secondList[posSecond++];
            }
        }

        while (posFirst < firstList.length) {
            resultList[posResult++] = firstList[posFirst++];
        }

        while (posSecond < secondList.length) {
            resultList[posResult++] = secondList[posSecond++];
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
        mergeSort(list);
        long t2 = System.nanoTime();

        //System.out.println(Arrays.toString(list));
        System.out.println(t2-t1);
    }
}