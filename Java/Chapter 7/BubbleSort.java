import java.util.Arrays;

/**
 * (Bubble sort) Write a sort method that uses the bubble-sort algorithm. The
 * bubblesort algorithm makes several passes through the array. On each pass,
 * successive neighboring pairs are compared. If a pair is not in order,
 * its values are swapped; otherwise, the values remain unchanged. The
 * technique is called a bubble sort or sinking sort because the smaller
 * values gradually “bubble” their way to the top and the larger values
 * “sink” to the bottom. Write a test program that reads in ten double
 * numbers, invokes the method, and displays the sorted numbers.
 */
public class BubbleSort {
    public static void main(String[] args) {
        double[] list = {1, 9, 4.5, 6.6, 5.7, -4.5};
        System.out.println(Arrays.toString(list));
        Arrays.sort(list);
        System.out.println(Arrays.toString(list));

        System.out.append("\n");

        list = new double[]{1, 9, 4.5, 6.6, 5.7, -4.5};
        System.out.println(Arrays.toString(list));
        bubbleSort(list);
        System.out.println(Arrays.toString(list));

    }

    public static void bubbleSort(double[] list) {
        boolean not_filtered = true;
        while(not_filtered) {
            not_filtered = false;
            for(int i = 0; i < list.length - 1; i++ ) {
                if(list[i+1] < list[i]){
                    double temp = list[i];
                    list[i] = list[i+1];
                    list[i+1] = temp;
                    not_filtered = true;
                }
            }
        }
    }
}
