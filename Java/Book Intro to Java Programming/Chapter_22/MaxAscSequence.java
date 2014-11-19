package Chapter_22;

import java.util.Scanner;

/**
 * (Maximum consecutive increasingly ordered substring) Write a program that
 * prompts the user to enter a string and displays the maximum consecutive
 * increasingly ordered substring.
 */
public class MaxAscSequence {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = in.nextLine();

        int start = 0;
        int max = 0;
        int maxStart = 0;

        char[] chars = str.toCharArray();

        for (int i = 1, charsLength = chars.length; i < charsLength; i++) {

            if (chars[i] < chars[i - 1]) {
                int dist = i - start;
                if (dist > max) {
                    maxStart = start;
                    max = dist;
                }
                start = i;
            }
        }
        if (chars.length - start > max) {
            maxStart = start;
            max = chars.length - start;
        }
        while(max-- > 0) {
            System.out.print(chars[maxStart++]);
        }
    }
}
