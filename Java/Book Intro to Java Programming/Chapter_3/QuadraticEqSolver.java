package Chapter_3;

import java.util.Scanner;

public class QuadraticEqSolver {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double a, b, c;

        System.out.print("Enter a, b, c: ");
        a = input.nextDouble();
        b = input.nextDouble();
        c = input.nextDouble();

        // Compute discriminant

        double d = b * b - 4 * a * c;

        if (d < 0)
            System.out.println("The equations has no real roots");
        else {
        double x1 = (-b + Math.sqrt(d)) / (2 * a);
            if (d > 0) {
                double x2 = (-b - Math.sqrt(d)) / (2 * a);
                System.out.printf("The equation has two roots %.3f and %.3f", x1, x2);
            }
            else
                System.out.println("The equation has one root " + x1);
        }
    }
}
