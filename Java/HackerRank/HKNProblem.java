import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class HKNProblemSolution {

    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
             StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), ",");

            long a = Long.parseLong(tokenizer.nextToken());
            long b = Long.parseLong(tokenizer.nextToken());

            int count = 0;
            for (; a <= b; a++) {
                if (is_palbin(a)) {
                    count++;
                }
            }
            System.out.println(count);
        } catch (IOException e) {}
    }

    private static boolean is_palbin(long a) {
        int len = Long.SIZE - Long.numberOfLeadingZeros(a);
        // 24
        long x;
        long y;

        if (len == 0){
            return false;
        }

        for (int i = 0; i < len; i++) {
            x = (a & ((1 << len) - 1)) >> --len;
            y = (a >> i) & 1;

            if (x != y) {
                return false;
            }
        }
        return true;
    }
}