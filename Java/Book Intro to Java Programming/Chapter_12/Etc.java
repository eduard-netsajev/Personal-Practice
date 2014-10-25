package Chapter_12;

import java.util.Scanner;

public class Etc {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int intValue = input.nextInt();
        double doubleValue = input.nextDouble();
        String line = input.nextLine();
        System.out.println(intValue);
        System.out.println(doubleValue);
        System.out.println(line);
    }
}
