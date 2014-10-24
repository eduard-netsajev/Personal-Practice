package p12577;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
        int c = 1;
        String s;
        while((s = rdr.readLine()).length() > 1) {
            if (s.charAt(0) == 'H') {
                System.out.printf("Case %d: Hajj-e-Akbar\n", c++);
            } else {
                System.out.printf("Case %d: Hajj-e-Asghar\n", c++);
            }
        }
    }
}
