package p12250;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
        String s;
        int c = 1;
        String res;
        while(!(s = rdr.readLine()).equals("#")) {

            switch (s) {
                case "HELLO":
                    res = "ENGLISH";
                    break;
                case "HOLA":
                    res = "SPANISH";
                    break;
                case "HALLO":
                    res = "GERMAN";
                    break;
                case "BONJOUR":
                    res = "FRENCH";
                    break;
                case "CIAO":
                    res = "ITALIAN";
                    break;
                case "ZDRAVSTVUJTE":
                    res = "RUSSIAN";
                    break;
                default:
                    res = "UNKNOWN";
                    break;
            }
            System.out.printf("Case %d: %s\n", c++, res);
        }
    }
}