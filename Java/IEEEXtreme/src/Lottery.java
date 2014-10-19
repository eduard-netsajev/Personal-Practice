import java.io.IOException;

public class Lottery {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);

        int min = Reader.nextInt();
        int max = Reader.nextInt() + 1;
        int index = Reader.nextInt();
        int n = Reader.nextInt();


//        int[] numstr = new int[n];
        String[] nums = new String[n];

        for (int j = 0; j < n; j++) {
//            numstr[j] = Reader.nextInt();
            nums[j] = Reader.next();
        }


        int c = 0;
        int i = 0;
        n--;
        while(min < max){

            for (String num: nums) {
                if (String.valueOf(min).contains(num)){
                    i++;
                    c++;
                    if (c == index) {
                        System.out.println(min);
                        System.exit(0);
                    } else if (i > n){
                        i = 0;
                    }
                    break;
                }
            }
            min++;
        }

//
//        HashSet<Integer> uniqs = new HashSet<>();
//
//        boolean uniq = true;
//        for (int j = 0; j < n; j++) {
//            String th = nums[j];
//            for (int k = 0; k < n; k++) {
//                if (th.contains(nums[k]) && k != j) {
//                    uniq = false;
//                    break;
//                }
//            }
//            if (uniq) {
//                uniqs.add(numstr[j]);
//            }
//        }
//
//        int s = uniqs.size();
//
//        numstr = new int[s];
//        nums = new String[s];
//
//        int c = 0;
//        for (Integer i: uniqs){
//        numstr[c++] = i;
//        }
//        Arrays.sort(numstr);
//
//        ArrayList<String> unstrings = new ArrayList<>(s);
//        for (c = 0; c < s; c++) {
//            unstrings.add(String.valueOf(numstr[c]));
//        }
//
//        for(int i = 0; i < s; i++){
//            nums[i] = String.valueOf(numstr[i]);
//        }
//
//        c = 0;
//        int i = 0;
//        s--;
//        while(min < max){
//
//            for (String num: unstrings) {
//                if (String.valueOf(min).contains(num)){
//                    i++;
//                    c++;
//                    if (c == index) {
//                        System.out.println(min);
//                        System.exit(0);
//                    } else if (i > s){
//                        i = 0;
//                    }
//                }
//            }
//            min++;
//        }
        System.out.println("DOES NOT EXIST");
        System.exit(0);
    }
}
