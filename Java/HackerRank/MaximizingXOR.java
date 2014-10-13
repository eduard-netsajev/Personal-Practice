import java.util.Scanner;

class MaximizingXORSolution {

    /*
 * Complete the function below.
 */

    static int maxXor(int l, int r) {

        int max = 0;
        int rplus = r + 1;
        for (; l < rplus; l++) {
            for (int g = l; g < rplus; g++) {
                int k = l ^ g;
                if (k > max) {
                    max = k;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int res;
        int _l;
        _l = Integer.parseInt(in.nextLine());

        int _r;
        _r = Integer.parseInt(in.nextLine());

        res = maxXor(_l, _r);
        System.out.println(res);

    }

}
