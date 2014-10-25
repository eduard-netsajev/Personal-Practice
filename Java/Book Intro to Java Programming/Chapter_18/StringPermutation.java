package Chapter_18;

import java.util.Scanner;

public class StringPermutation {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the string: ");
        String str =  in.next();
        System.out.println("The string permutations are: ");
        displayPermutation(str);
        System.out.println("The number of permutations is " +
                numberOfPermutations);
    }

    static int numberOfPermutations = 0;

    public static void displayPermutation(String s){
        displayPermutation("", s);
    }
    public static void displayPermutation(String s1, String s2){
        int len = s2.length();
        if (len == 0) {
            System.out.println(s1);
            numberOfPermutations++;
        } else {
            for (int i = 0; i < len; i++) {
                String sNew = s2.substring(0, i) + s2.substring(i + 1);
                displayPermutation(s1 + s2.charAt(i), sNew);
            }
        }
    }
}
