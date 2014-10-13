import java.util.Scanner;

class UtopianTreeSolution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] results = new int[62];
        results[0] = 1;
        results[1] = 2;
        results[2] = 3;
        results[3] = 6;
        results[4] = 7;
        int got = 4;

        for (int cases = in.nextInt(); cases > 0; cases--) {

            int n = in.nextInt();

            while(results[n] == 0) {
                results[got + 1] = (got % 2 == 0) ? results[got] * 2 :
                        results[got] + 1;
                got++;
            }
            System.out.println(results[n]);
        }
    }
}