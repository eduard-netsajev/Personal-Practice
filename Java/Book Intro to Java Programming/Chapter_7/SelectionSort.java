package Chapter_7;

import java.util.Arrays;

public class SelectionSort {
    public static void main(String[] args) {
        double[] list = {1, 9, 4.5, 6.6, 5.7, -4.5};
        System.out.println(Arrays.toString(list));
        Arrays.sort(list);
        System.out.println(Arrays.toString(list));

        System.out.append("\n");

        list = new double[]{1, 9, 4.5, 6.6, 5.7, -4.5};
        System.out.println(Arrays.toString(list));
        selectionSort(list);
        System.out.println(Arrays.toString(list));

    }

    public static void selectionSort(double[] list) {
        int len = list.length;

        for(int i = 0; i < len; i++){
            double currentMin = list[i];
            int currentMinIndex = i;

            for(int j = i; j < len; j++){
                if (list[j] < currentMin) {
                    currentMin = list[j];
                    currentMinIndex = j;
                }
            }

            double temp = list[i];
            list[i] = currentMin;
            list[currentMinIndex] = temp;
        }
    }
}
