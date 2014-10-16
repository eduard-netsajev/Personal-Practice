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

            int len1 = Long.SIZE - Long.numberOfLeadingZeros(a);
            int len2 = Long.SIZE - Long.numberOfLeadingZeros(b);

            if (len2 > len1) {

                int bound1 = 1 << (len1);
                int bound2 = 1 << (len2 - 1);

                if (Long.bitCount(a) == 1) {
                    len1--;
                } else {
                    for (; a < bound1; a++) {

                        if (is_palbin(a)) {
                            count++;
                        }
                    }
                }

                if (Long.bitCount(b) == len2) {
                    len2++;
                } else {
                    for (; bound2 <= b; bound2++) {
                        if (is_palbin(bound2)) {
                            count++;
                        }
                    }
                }

                int x;
                for (++len1; len1 < len2; len1++) {
                    x = (int) (len1 / 2.0 - 0.5);
                    count += 1 << x;

                }

            } else {
                for (; a <= b; a++) {
                    if (is_palbin(a)) {
                        count++;
                    }
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