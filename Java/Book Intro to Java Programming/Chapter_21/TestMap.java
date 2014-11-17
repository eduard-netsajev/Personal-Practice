package Chapter_21;

import java.util.HashMap;
import java.util.Map.Entry;

public class TestMap {
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(5, 15);
        map.put(3, 9);
        map.put(4, 12);
        map.put(2, 6);

        for(Entry<Integer, Integer> e : map.entrySet()) {
            e.setValue(e.getKey() * 4);
        }

        System.out.println(map);
    }
}
