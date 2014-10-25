package Chapter_6;

/**
 * Approximate the square root.
 */
public class ApproxSqrt {
    public static void main (String[] args) {

        double n = 777;

        System.out.println("Approx. square root of " + n + " is: " + sqrt(n));
    }

    /**
     * Returns approximate square root
     * using a technique known as the
     * Babylonian method.
     *
     * @param n - positive real number
    */
    public static double sqrt(double n){
        for(double lastGuess = 1.0, nextGuess; true; lastGuess = nextGuess){
            nextGuess = (lastGuess + n / lastGuess) / 2;
            if (Math.abs(lastGuess-nextGuess) < 1E-4)
                return lastGuess;
        }
    }
}
