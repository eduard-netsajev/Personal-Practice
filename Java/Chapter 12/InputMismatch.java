import java.util.InputMismatchException;
import java.util.Scanner;

public class InputMismatch {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int x = readFirstInt(in);
        int y = readSecondInt(in);

        System.out.println("\nThe sum of these numbers is " + (x+y));
    }

    private static int readSecondInt(Scanner input) {
        while(true) {
            try {
                System.out.print("Input second integer > ");
                return input.nextInt();
            } catch (InputMismatchException ex) {
                input.next();
                System.out.println("Input is incorrect. Try again");
            }
        }
    }

    public static int readFirstInt(Scanner input) {
        int x;
        try {
            System.out.print("Input first integer > ");
            x = input.nextInt();
        } catch (InputMismatchException ex) {
            input.next();
            System.out.println("Input is incorrect. Try again");
            x = readFirstInt(input);
        }
        return x;
    }
}