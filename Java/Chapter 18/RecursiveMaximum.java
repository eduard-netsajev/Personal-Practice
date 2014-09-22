import java.util.Scanner;

public class RecursiveMaximum {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int size = 8;
        int[] numArray = new int[size];
        System.out.printf("Enter %d numbers: ", size);
        for (int i = 0; i < size; i++) {
            numArray[i] = in.nextInt();
        }
        System.out.println("The largest number is: " + arrayMax(numArray));
    }

    public static int arrayMax(int[] numArray) {
        return arrayMax(numArray, 0);
    }
    public static int arrayMax(int[] numArray, int start) {
        if (numArray.length - 1 == start) return numArray[start];
        int nextMax = arrayMax(numArray, start + 1);
        return (numArray[start] > nextMax) ? numArray[start] : nextMax;
    }
}
