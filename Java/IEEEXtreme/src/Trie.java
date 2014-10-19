import java.io.IOException;
import java.util.HashMap;

public class Trie {

    static HashMap<String, Long> map;

    public static void main(String[] args) throws IOException {

        Reader.init(System.in);
        int n = Reader.nextInt();

        map = new HashMap<>();

        long alphas;
        long len;
        long count;
        long cur = 0;
        boolean limit;
        long new_level;

        while(n-- > 0) {
            alphas = Reader.nextLong();
            len = Reader.nextLong() + 1;
            count = Reader.nextLong();
            cur = 0;
            new_level = 0;
            limit = false;


            for (int i = 0; i < len; i++) {
                if (limit) {
                    cur += count;
//                    System.out.println(count);
                } else {
                new_level = pow(alphas, i);


                    if (new_level < count) {
                        cur += new_level;
    //                        System.out.println(new_level);
                    } else {
                    limit = true;
//                        System.out.println(count);
                    cur += count;
                }
                }
//                System.out.println(cur);
            }
//            System.out.println(cur);
            cur *= alphas;
            cur *= 4;
            System.out.println(cur);
        }

    }

    static StringBuilder sb;

    static long pow (long a, int i) {

        sb = new StringBuilder(100);
        sb.append(a);
        sb.append(',');
        sb.append(i);
        i++;
        String th = sb.toString();
        if(map.containsKey(th)) {
            return map.get(th);
        }

        long foo = 1;
        for(int j = 1; j < i; j++) {
            foo *= a;
            sb = new StringBuilder(100);
            sb.append(a);
            sb.append(',');
            sb.append(j);
            map.put(sb.toString(), foo);
        }
        return foo;
    }
}