import java.util.HashMap;
import java.util.Scanner;

class Main {
    public static void main (String[] args) {
        // UVa Online Judge problem nr. 100
        // Run time: 0.569
        Scanner in = new Scanner(System.in);
        HashMap<Integer, Integer> stats = new HashMap<>();
        while (in.hasNextInt()) {
            int i = in.nextInt();
            int j = in.nextInt();
            System.out.print(i + " " + j + " ");
            if (j < i) {
                int temp = i;
                i = j;
                j = temp;
            }
            j++;
            int l = 0;
            while (i < j) {
                int k = 1;
                if (stats.containsKey(i)) {
                    k = stats.get(i);
                } else {
                    int n = i;
                    while (n > 1) {
                        if (n % 2 > 0){
                            n = n * 3 + 1;
                        } else {
                            n /= 2;
                        }
                        k++;
                    }
                    stats.put(i, k);
                }
                l = (k > l) ? k : l;
                i++;
            }
            System.out.println(l);
        }
    }
}
