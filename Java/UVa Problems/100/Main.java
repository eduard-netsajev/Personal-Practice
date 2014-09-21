import java.util.Scanner;

class Main {
    public static void main (String[] args) {
        // UVa Online Judge problem nr. 100
        Scanner in = new Scanner(System.in);

        boolean first = false;
        while (in.hasNext()) {
            int i = 0;
            int j = 0;
            if (in.hasNextInt()) {
                i = in.nextInt();
            } else {
                in.next();
                continue;
            }
            if (in.hasNextInt()) {
                j = in.nextInt();
            } else {
                in.next();
                continue;
            }
            int a1 = i;
            int a2 = j;
            if (j < i) {
                int temp = i;
                i = j;
                j = temp;
            }
            j++;
            int l = 0;
            while (i < j) {
                int n = i;
                int k = 1;
                while (n > 1) {
                    n = (n % 2 == 0) ? (n / 2) : (n * 3 + 1);
                    k++;
                }
                l = (k > l) ? k : l;
                i++;
            }
            System.out.printf("%d %d %d\n", a1, a2, l);
        }
    }
}
