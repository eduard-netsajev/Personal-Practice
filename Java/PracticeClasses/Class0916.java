import java.util.Arrays;

public class Class0916 {
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5,6,7,8,9};
        if (uniqueLines(nums)) {
            System.out.println("No duplicates found on any row or column");
        } else {
            System.out.println("Found some duplicates");
        }
    }

    public static boolean uniqueLines(int[] numbers) {
        final int ROWS = 3;
        final int COLUMNS = 3;

        //rows
        for (int i = 0; i < ROWS; i++){
            int[] thisRow = new int[COLUMNS];
            System.out.println("New row");
            for (int j = 0; j < COLUMNS; j++){

                for (int k = j; k >= 0; k--) {
                    if (thisRow[k] == numbers[i*COLUMNS + j]) {
                        System.out.println(thisRow[k]);
                        System.out.println(numbers[i*COLUMNS + j]);
                        return false;
                    }
                }
                thisRow[j] = numbers[i*COLUMNS + j];
            }
            System.out.println(Arrays.toString(thisRow));
        }

        //columns
        for (int i = 0; i < COLUMNS; i++){
            int[] thisColumn = new int[ROWS];
            System.out.println("New column");
            for (int j = 0; j < ROWS; j++){
                for (int k = j; k >= 0; k--) {
                    if (thisColumn[k] == numbers[i + j*COLUMNS]) {
                        System.out.println(thisColumn[k]);
                        System.out.println(numbers[i + j*COLUMNS]);
                        return false;
                    }
                }
                thisColumn[j] = numbers[i + j*COLUMNS];
            }
            System.out.println(Arrays.toString(thisColumn));
        }

        return true;
    }

}
