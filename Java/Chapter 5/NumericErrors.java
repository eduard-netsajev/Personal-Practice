public class NumericErrors {
    public static void main(String[] args) {

        // Initialize sum
        float sum = 0;
        // Add 0.01, 0.02, ..., 0.99, 1 to sum
        for (float i = 0.01f; i <= 1.0f; i += 0.01f) {
            sum += i;
        }
        // Display result
        System.out.println("The sum is " + sum);

        // Initialize sum
        double sum2 = 0;
        // Add 0.01, 0.02, ..., 0.99, 1 to sum
        for (double i = 0.01; i <= 1.0; i += 0.01) {
            sum2 += i;
        }
        // Display result
        System.out.println("The sum is " + sum2);

        // The correct way
        float sum3 = 0;
        float currentValue = 0.01f;
        // Add 0.01, 0.02, ..., 0.99, 1 to sum
        for (int counter = 1; counter <= 100; counter++) {
            sum3 += currentValue;
            currentValue += 0.01f;
        }
        // Display result
        System.out.println("The sum is " + sum3);


        double sum4 = 0;
        double currentValue2 = 0.01;
        // Add 0.01, 0.02, ..., 0.99, 1 to sum
        for (int counter = 1; counter <= 100; counter++) {
            sum4 += currentValue2;
            currentValue2 += 0.01;
        }
        // Display result
        System.out.println("The sum is " + sum4);
    }
}
