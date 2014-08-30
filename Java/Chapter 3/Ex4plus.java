import java.util.Scanner;

public class Ex4plus {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Prompt the user to enter weight in kilograms
        System.out.print("Enter weight in kilograms: ");
        double weight = input.nextDouble();

        // Prompt the user to enter height in meters
        System.out.print("Enter height in meters: ");
        double height = input.nextDouble();

        // Compute BMI
        double bmi = weight / (height * height);

        // Display result
        System.out.println("BMI is " + bmi);
        if (bmi < 18.5)
            System.out.println("Underweight");
        else if (bmi < 25)
            System.out.println("Normal");
        else if (bmi < 30)
            System.out.println("Overweight");
        else
        System.out.println("Obese");

        System.out.println((bmi > 25) ? "Go running!" : "You are good");
        System.out.println((Math.random() < 0.5) ? -1 : 1);
    }
}
