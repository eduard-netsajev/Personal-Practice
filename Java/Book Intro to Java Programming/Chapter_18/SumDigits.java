package Chapter_18;

import java.util.Scanner;

public class SumDigits {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the numbers: ");
        int n = in.nextInt();
        System.out.println("The sum of its digits is: " + sumDigits(n));
    }

    public static int sumDigits(int n) {
        if (n < 10) return n;
        return n % 10 + sumDigits(n / 10);
    }
}
