import java.util.Scanner;

class TheLoveLetterMysterySolution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = Integer.parseInt(in.nextLine());

        for (; n > 0; --n) {
            char[] word = in.nextLine().toCharArray();
            int min = 0;
            for (int i = 0, j = word.length - 1; i < j; i++, j--) {
                min += Math.abs(word[i] - word[j]);
            }
            System.out.println(min);
        }
    }
}