import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

class GameOfThronesSolution {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            char[] chars = reader.readLine().toCharArray();

            boolean center = false;

            Arrays.sort(chars);

            int count = 1;
            for (int i = 1; i < chars.length; i++) {
                if (chars[i] > chars[i-1]) {
                    if (count % 2 != 0) {
                        if (center) {
                            System.out.println("NO");
                            System.exit(0);
                        } else {
                            center = true;
                        }
                    }
                    count = 0;
                }
                count++;
            }


            if (center && count % 2 != 0) {
                System.out.println("NO");
            } else {
                System.out.println("YES");
            }

        } catch (Exception e) {
            // nope
        }
    }
}
