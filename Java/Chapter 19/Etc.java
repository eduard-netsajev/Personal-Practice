import java.util.ArrayList;

public class Etc {
    public static void main(String[] args ) {
        Integer[] integers = {1, 2, 3, 4, 5};
        String[] strings = {"London", "Paris", "New York", "Austin"};

        print(integers);
        Etc.<String>print(strings);

        ArrayList[] list = new ArrayList[10];
        list[0] = new ArrayList<String>();
    }

    public static <E> void print(E[] list) {
        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i] + " ");
        }
        System.out.println();
    }
}