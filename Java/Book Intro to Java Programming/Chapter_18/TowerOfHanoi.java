package Chapter_18;

import java.util.Scanner;

public class TowerOfHanoi {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter number of disks: ");
        int n = in.nextInt();

        System.out.println("The moves are:");
        // move from A to B using helper C
        moveDisks(n, 'A', 'B', 'C');
    }

    // So powerful yet so simple
    public static void moveDisks(int n, char source, char target, char helper) {
        if (n == 1) { // means we can move it right now
            System.out.printf("Move disk %d from %c to %c\n", n, source,
                    target);
        } else {
            moveDisks(n - 1, source, helper, target);
            System.out.printf("Move disk %d from %c to %c\n", n, source,
                    target);
            moveDisks(n - 1, helper, target, source);
        }
    }
}