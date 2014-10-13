import java.util.Scanner;

class ServiceLaneSolution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int t = in.nextInt();

        int[] results = new int[n];

        for (int i = 0; i < n; i++) {
            results[i] = in.nextInt();
        }

        for (; t > 0; --t) {
            int max = 3;
            int i = in.nextInt();
            int j = in.nextInt() + 1;

            for (; i < j; i++) {
                if (results[i] < max) {
                    if (results[i] > 1) {
                        max = 2;
                    } else {
                        max = 1;
                        break;
                    }
                }
            }
            System.out.println(max);
        }


    }
}