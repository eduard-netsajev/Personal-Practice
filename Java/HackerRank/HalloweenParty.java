import java.util.HashSet;
import java.util.Scanner;

class HalloweenPartySolution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = Integer.parseInt(in.nextLine());

        HashSet<Character> common = new HashSet<>(30);

        char[] chars = in.nextLine().toCharArray();

        for (char c : chars) {
            common.add(c);
        }
        n--;

        for (; n > 0; --n) {
            HashSet<Character> newCommon = new HashSet<>(30);
            chars = in.nextLine().toCharArray();
            for(char c: chars){
                if (common.contains(c)){
                    newCommon.add(c);
                }
            }
            common = newCommon;
        }
        System.out.println(common.size());
    }
}