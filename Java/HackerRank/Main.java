import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        try {
            line = br.readLine();
        } catch (Exception e) {
        }
        String[] nums = line.split(" ");
        int n = Integer.parseInt(nums[0]);
        long k = Long.parseLong(nums[1]);
        int[] arr = new int[n];
        int max = n - 1;
        int min = 0;
        int[] lasers = new int[n];

        for (int i = 0; i < n; i++) {
            if (k > 0) {
                if (k > max) {
                    arr[max] = i + 1;
                    lasers[i] = max + 1;
                    k -= max;
                    max--;
                } else {
                    arr[((int) k)] = i + 1;
                    lasers[i] = (int) (k + 1);
                    k = 0;
                }
            } else {
                if (arr[min] == 0) {
                    arr[min] = i + 1;
                    lasers[i] = min + 1;
                } else {
                    arr[++min] = i + 1;
                    lasers[i] = min + 1;
                }
                min++;
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.print(lasers[i]);
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
    }
}