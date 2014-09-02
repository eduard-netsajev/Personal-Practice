public class Etc {
    public static void main(String[] args) {
        int[] field = new int[9];
        field[3] = 5;

        int[] secondField;
        secondField = new int[10];

        System.out.println(field[5] + secondField[1]);

        // Anonymous array
        printArray(new int[]{3, 1, 2, 6, 4, 2});

        System.out.println("\n----------------");

        int[] list = {1, 2, 3, 5, 4};
        for (int i = 0, j = list.length - 1; i < j; i++, j--) {
            // Swap list[i] with list[j]
            int temp = list[i];
            list[i] = list[j];
            list[j] = temp;
        }
        printArray(list);
    }

    public static void printArray(int[] array) {
        for (int entry : array) {
            System.out.print(entry + " ");
        }
    }
}
