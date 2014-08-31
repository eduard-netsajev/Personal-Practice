import java.util.Scanner;

public class Etc {
    public static void main (String[] args) {

        int i = 1;
        int sum = 0;
        while (sum < 10000) {
            System.out.println(sum);
            sum = sum + i;
            i++;
        }

        //same as above
        for (int n = 1, total = 0; total < 10000; total += n++);

        Scanner input = new Scanner(System.in);


        // while variant
        System.out.print("Enter an integer " +
                "(the input ends if it is 0): ");
        int number1 = input.nextInt();
        int sum1 = number1;
        while (number1 != 0) {
            System.out.print("Enter an integer " +
                    "(the input ends if it is 0): ");
            number1 = input.nextInt();
            sum1 += number1;
        }
        System.out.println("The sum is " + sum1);


        //do-while variant
        int sum2 = 0, number2;
        do {
            System.out.print("Enter an integer " +
                    "(the input ends if it is 0): ");
            number2 = input.nextInt();
            sum2 += number2;
        } while (number2 != 0);
        System.out.println("The sum is " + sum2);


        //for variant
        int sum3 = 0;
        for (int number3 = -1; number3 != 0; sum3 += number3){
            System.out.print("Enter an integer " +
                    "(the input ends if it is 0): ");
            number3 = input.nextInt();
        }
        System.out.println("The sum is " + sum3);


        //bad variant with empty body
        for (int number4 = -1, sum4 = 0;  // initial action
             number4 != 0;  //loop continuation condition
             System.out.print("Enter an integer (the input ends if it is 0): "),
             number4 = input.nextInt() , sum4 += number4){}
        System.out.println("The sum is " + sum3);

    }
}
