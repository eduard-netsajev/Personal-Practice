package p10226;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            int tc = Integer.parseInt(reader.readLine());
            reader.readLine();

            while(true) {

                TreeMap<String, Integer> data = new TreeMap<String, Integer>();

                String s;
                double total = 0;
                while((s = reader.readLine()) != null && s.length() > 0) {
                    total++;
                    Integer c = data.put(s, 1);
                    if (c != null) {
                        data.put(s, c+1);
                    }
                }
                for(Map.Entry<String, Integer> tree: data.entrySet()) {
                    System.out.printf("%s %.4f\n", tree.getKey(), tree.getValue() / total * 100);
                }

                if(--tc > 0) {
                    System.out.println();
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            // nope
        }

    }
}