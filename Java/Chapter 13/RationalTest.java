import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class RationalTest {
    public static void main (String[] args) {
        // Create and initialize two rational numbers r1 and r2
        Rational r1 = new Rational(4, 2);
        Rational r2 = new Rational(2, 3);

        // Display results
        System.out.println(r1 + " + " + r2 + " = " + r1.add(r2)); // 8/3
        System.out.println(r1 + " - " + r2 + " = " + r1.subtract(r2)); // 4/3
        System.out.println(r1 + " * " + r2 + " = " + r1.multiply(r2)); // 4/3
        System.out.println(r1 + " : " + r2 + " = " + r1.divide(r2));  // 3
        System.out.println(r2 + " is " + r2.doubleValue()); // 0.66666...
        System.out.println();

        Rational r3 = new Rational(-2, 7);
        Rational r4 = new Rational(5, 2);

        Rational[] rationals = new Rational[]{r1, r2, r3, r4};
        Arrays.sort(rationals);
        for (Rational num: rationals) {
            System.out.println(num.toString());
        }
        Rational r5 = new Rational(1, 2);
        Rational r6 = new Rational(1, -2);
        System.out.println(r5.add(r6));
    }
}

class Rational extends Number implements Comparable<Rational>, Cloneable {

    private long numerator = 0;
    private long denominator = 1;

    // Default constructor
    public Rational() {
        this(0, 1);
    }

    // Construct a specific rational number
    public Rational(long numerator, long denominator) {
        long gcd = gcd(numerator, denominator);
        this.numerator = ((denominator > 0) ? 1 : -1) * numerator / gcd;
        this.denominator =  Math.abs(denominator) / gcd;
    }

    // Find the greatest common denominator of two numbers
    private static long gcd(long n, long d) {
        long n1 = Math.abs(n);
        long n2 = Math.abs(d);
        for (long gcd = Math.min(n1, n2); gcd > 0; gcd--) {
            if (n1 % gcd == 0 && n2 % gcd == 0) {
                return gcd;
            }
        }
        return Math.max(n1, n2);
    }

    // Add a rational number to this rational number
    public Rational add(Rational otherRational) {
        long n = numerator * otherRational.getDenominator()
                 + denominator * otherRational.getNumerator();
        long d = denominator * otherRational.getDenominator();
        return new Rational(n, d);
    }

    // Subtract a rational number from this rational number
    public Rational subtract(Rational otherRational) {
        long n = numerator * otherRational.getDenominator()
                 - denominator * otherRational.getNumerator();
        long d = denominator * otherRational.getDenominator();
        return new Rational(n, d);
    }

    // Multiply a rational number by this rational number
    public Rational multiply(Rational otherRational) {
        long n = numerator * otherRational.getNumerator();
        long d = denominator * otherRational.getDenominator();
        return new Rational(n, d);
    }

    // Divide a rational number by this rational number
    public Rational divide(Rational otherRational) {
        long n = numerator * otherRational.getDenominator();
        long d = denominator * otherRational.getNumerator();
        return new Rational(n, d);
    }

    @Override
    public int compareTo(@NotNull Rational o) {
        long dif = this.subtract(o).getNumerator();

        if (dif > 0) {
            return 1;
        } else if (dif < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // Shouldn't ever happen
            System.out.println("Rational.clone() failed. " + e);
            return null;
        }
    }

    @Override
    public int intValue() {
        return (int)doubleValue();
    }

    @Override
    public long longValue() {
        return (long)doubleValue();
    }

    @Override
    public float floatValue() {
        return (float)doubleValue();
    }

    @Override
    public double doubleValue() {
        return numerator * 1.0 / denominator;
    }

    @Override
    public String toString() {
        if (denominator == 1){
            return numerator + "";
        } else {
            return numerator + "/" + denominator;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Rational && (this.subtract((Rational) (other))).getNumerator() == 0;
    }

    public long getNumerator() {
        return numerator;
    }

    public long getDenominator() {
        return denominator;
    }
}