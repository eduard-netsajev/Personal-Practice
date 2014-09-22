import java.util.Scanner;

public class RecursiveGCD {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter two numbers: ");
        int n = in.nextInt();
        int m = in.nextInt();
        System.out.println("The greatest common denominator is: " + gcd(n, m));
    }

    public static int gcd(int n, int m) {
        int x = n % m;
        return (x == 0) ? m : gcd(m, x);
    }
}