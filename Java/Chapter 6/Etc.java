public class Etc {
    public static void main(String[] args) {
        System.out.println("Sum from 1 to 10 is " + sum(1, 10));
        System.out.println("Sum from 20 to 37 is " + sum(20, 37));
        System.out.println("Sum from 35 to 49 is " + sum(35, 49));

        System.out.println("The first 50 prime numbers are \n");
        printPrimeNumbers(50);

        double z = m(4, 5);
        double a = m(4, 5.4);
        double b = m(4.5, 5.4);

    }

    public static double m(double x, double y){
        System.out.println("DD");
        return x + y;
    }
    public static double m(int x, double y) {
        System.out.println("IND");
        return x + y;
    }

    public static int sum(int i1, int i2) {
        int result = 0;
        for  (int i = i1; i <= i2; i++)
            result += i;

        return result;
    }

    public static void printPrimeNumbers(int numberOfPrimes) {
        final int NUMBER_OF_PRIMES_PER_LINE = 10; // Display 10 per line
        int count = 0; // Count the number of prime numbers
        int number = 2; // A number to be tested for primeness

        // Repeatedly find prime numbers
        while (count < numberOfPrimes) {
            // Print the prime number and increase the count
            if (isPrime(number)) {
                count++; // Increase the count

                if (count % NUMBER_OF_PRIMES_PER_LINE == 0) {
                    // Print the number and advance to the new line
                    System.out.printf("%-5s\n", number);
                }
                else
                    System.out.printf("%-5s", number);
            }

            // Check whether the next number is prime
            number++;
        }
    }

    /** Check whether number is prime */
    public static boolean isPrime(int number) {
        for (int divisor = 2; divisor <= number / 2; divisor++) {
            if (number % divisor == 0) { // If true, number is not prime
                return false; // Number is not a prime
            }
        }

        return true; // Number is prime
    }
}
