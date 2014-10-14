import java.io.IOException;
import java.util.Arrays;

class CutTheSticksSolution {

    public static void main(String[] args) {
        Reader.init(System.in);

        try {

        int n = Integer.parseInt(Reader.next());

        int[] nums = new int[n];

            for (int i = 0; i < n; i++) {
                nums[i] = Integer.parseInt(Reader.next());
            }

        Arrays.sort(nums);
        System.out.println(n);
        for(int i = 1; i < n; i++) {
            if (nums[i] > nums[i-1]) {
                System.out.println(n-i);
            }
        }
        } catch (IOException e) {
            // nope
        }
    }
}