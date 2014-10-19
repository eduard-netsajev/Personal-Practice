import java.io.IOException;
import java.util.Stack;

public class K {
    public static void main(String[] args) throws IOException {

        Reader.init(System.in);

        int n = Reader.nextInt();

        Stack<Integer> stack = new Stack<>();

        int[] rows = new int[n];

        int[][] ints = new int[n][n];

        int t;
        int r;

        int counter = 0;

        for (int i = 0; i < n; i++){
            r = 0;
            for (int j = 0; j < n; j++){
                t = Reader.nextInt();
                ints[i][j] = t;
                r += t;
            }
            rows[i] = r;
        }

        int diag = 0;
        for (int i = 0; i < n; i++) {
            diag += ints[i][i];
        }

        for (int i = n; i > 0;) {
            i--;
            if (diag != rows[i]) {
                stack.add(i+1);
            }
        }

        int antidiag = 0;
        for (int i = 0, j = n-1; i < n; i++, j--) {
            antidiag += ints[j][i];
        }
        if (antidiag != diag) {
            stack.push(0);
        }


        int col;

        for (int i = 0; i < n; i++) {
            col = 0;
            for (int j = 0; j < n; j++) {
                col += ints[j][i];
            }
            if (col != diag) {
                stack.push(-i-1);
            }
        }

        System.out.println(stack.size());
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }

    }
}
