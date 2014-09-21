import java.util.Scanner;

class Main {
    // UVa Online Judge problem nr. 10370   Run time: 0.559
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        for (int tests = in.nextInt(); tests > 0; tests--){
            int count = in.nextInt();
            int total = 0;
            int[] students = new int[count];
            for (int i = count; i > 0;) {
                students[--i] = in.nextInt();
                total += students[i];
            }
            float average = total / count;

            int aboveAverage = 0;
            for(int grade: students){
                if (grade > average) {
                    aboveAverage++;
                }
            }
            System.out.printf("%.3f%%\n", (float)aboveAverage /count*100);
        }
    }
}