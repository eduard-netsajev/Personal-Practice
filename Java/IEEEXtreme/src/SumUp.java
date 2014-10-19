import java.io.IOException;

public class SumUp {
    public static void main(String[] args) throws IOException {

        Reader.init(System.in);

        int n = Reader.nextInt();
        long[] longs = new long[n];
        long[] londub;

        for (int i = 0; i < n; i++) {
         longs[i] = Reader.nextLong();
        }

        londub = longs.clone();

        int q = Reader.nextInt();
        int op;
        int t;
        while(q-- > 0) {
            op = Reader.nextInt();
            for (int i = 0; i < n; i++) {
                t = (i - op) % n;
                if (t < 0) {
                    t += n;
                }
                londub[i] += longs[t];
                System.out.print(londub[i] + " ");
            }
            System.out.println();
            longs = londub.clone();
        }
        long sum = 0;
        for (int i = 0; i < n; i++){
            sum += longs[i];
        }
        sum = sum % 10_000_000_007l;
        System.out.println(sum);
    }
}
