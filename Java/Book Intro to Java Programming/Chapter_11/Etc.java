package Chapter_11;

import java.util.Arrays;

public class Etc {
    public static void main(String[] args) {


        String str = "Eduard";
        String str1 = new String("Eduard");
        String str2 = "Eduard";

        System.out.println(str == str1);
        System.out.println(str == str2);
        System.out.println(str.equals(str1));

        Integer[] array = {3, 5, 95, 4, 15, 34, 3, 6, 5};
        System.out.println(java.util.Collections.max(Arrays.asList(array)));
    }

}
