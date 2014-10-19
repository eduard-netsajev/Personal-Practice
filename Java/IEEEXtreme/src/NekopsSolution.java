import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NekopsSolution {

    static int max_len = 0;

    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int k = 0;
        ArrayList[] nums = new ArrayList[0];
        try {
            String in = reader.readLine();
            max_len = in.length() + ( k < 10 ? 2 : 3);
            String[] tokens = in.split(" ");
            k = Integer.parseInt(tokens[0]);

            if (k == 0) {
                System.out.println(in.substring(2));
                System.out.println(tokens.length-1);
                System.exit(0);
            }

            nums = new ArrayList[k+1];
            for (int h = 0; h < k+1; h++) {
                nums[h] = new ArrayList<Long>();
            }

            for (int h = 1; h < tokens.length; h++) {
                nums[0].add(Long.parseLong(tokens[h]));
            }

            max_len = lineLen(nums[0]);

        } catch (Exception e) {
        }

        k++;
        for(int i = 1; i < k; i++) {
            nums[i] = nekops(nums[i-1]);
            max_len = Math.max(max_len, lineLen(nums[i]));
        }

        for(int i = 0; i < k; i++) {
            printNekops(nums[i]);
        }
        System.out.println(nums[--k].size());
    }


    static StringBuilder str;
    static void printNekops(ArrayList<Long> seq) {

        int t = max_len - lineLen(seq);

        str = new StringBuilder(max_len);

        if (t % 2 != 0) {
         str.append('.');
        }
        t /= 2;
        for (int i = 0; i < t; i++){
            str.append('.');
        }
        int k = seq.size();
        int z = 0;
        while(true) {
            str.append(seq.get(z));
            z++;
            if (z < k) {
                str.append(' ');
            } else {
                break;
            }
        }
        for (int i = 0; i < t; i++){
            str.append('.');
        }
        System.out.println(str.toString());
    }

    static ArrayList<Long> nekops(ArrayList<Long> seq) {

        ArrayList<Long> nekops = new ArrayList<>(100); // todo check in capacity

        long n = -1;
        long c = 0;
        boolean start = true;

        for (Long i: seq) {
            if(!start){
                if (i == n) {
                    c++;
                } else {
                    nekops.add(c);
                    nekops.add(n);
                    c = 1;
                    n = i;
                }
            }
            else {
                n = i;
                c = 1;
                start = false;
            }
        }

        nekops.add(c);
        nekops.add(n);
        return nekops;
    }

    static int lineLen(ArrayList<Long> seq) {

        int len = -1;

        for (Long i: seq) {
            len += (i < 0) ? stringSize(-i) + 2 : stringSize(i) + 1;
        }

        return len;

    }

    static int stringSize(long x) {
        long p = 10;
        for (int i=1; i<19; i++) {
            if (x < p)
                return i;
            p = 10*p;
        }
        return 19;
    }
}
