package p00272;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb;

        boolean opened = false;
        String in;

        try {
            while ((in = reader.readLine()) != null) {
                in = "t" + in + "t";
                String tkns[] = in.split("\"");

                int i = tkns.length;

                if (i == 1) {
                    System.out.println(in.substring(1, in.length()-1));
                    continue;
                }

                sb = new StringBuilder();
                int j = 0;
                for (; j < i-1; j++) {

                    sb.append(tkns[j]);
                    if(opened) {
                        sb.append("''");
                    } else {
                        sb.append("``");
                    }
                    opened = !opened;
                }
                sb.append(tkns[j]);
                System.out.println(sb.toString().substring(1, sb.length() - 1));
            }
        } catch (IOException e){
//            nope
        }
    }
}
