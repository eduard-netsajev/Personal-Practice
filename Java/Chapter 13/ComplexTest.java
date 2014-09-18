public class ComplexTest {
    public static void main(String[] args) {
        // Create and initialize two complex numbers c1 and c2
        Complex c1 = new Complex(3.5, 5.5);
        Complex c2 = new Complex(-3.5, 1);

        // Display results
        System.out.println(c1 + " + " + c2 + " = " + c1.add(c2));
        System.out.println(c1 + " - " + c2 + " = " + c1.subtract(c2));
        System.out.println(c1 + " * " + c2 + " = " + c1.multiply(c2));
        System.out.println(c1 + " : " + c2 + " = " + c1.divide(c2));
        System.out.println("|" + c1 + "| = " + c1.abs());
    }
}

class Complex implements Cloneable {

    private double realPart = 0;
    private double imaginaryPart = 0;

    // Default constructor
    public Complex() {
        this(0, 0);
    }

    // Construct a complex number without imaginary part
    public Complex(double realPart) {
        this(realPart, 0);
    }

    // Construct a specific complex number
    public Complex(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart =  imaginaryPart;
    }

    // Find the absolute value of this complex number
    public double abs() {
        return Math.sqrt(realPart * realPart + imaginaryPart * imaginaryPart);
    }

    // Add a complex number to this complex number
    public Complex add(Complex otherComplex) {
        double c = otherComplex.getRealPart();
        double d = otherComplex.getImaginaryPart();

        double a = realPart + c;
        double b = imaginaryPart + d;
        return new Complex(a, b);
    }

    // Subtract a complex number from this complex number
    public Complex subtract(Complex otherComplex) {
        double c = otherComplex.getRealPart();
        double d = otherComplex.getImaginaryPart();

        double a = realPart - c;
        double b = imaginaryPart - d;
        return new Complex(a, b);
    }

    // Multiply a complex number by this complex number
    public Complex multiply(Complex otherComplex) {
        double c = otherComplex.getRealPart();
        double d = otherComplex.getImaginaryPart();

        double a = realPart * c - imaginaryPart * d;
        double b = imaginaryPart * c + realPart * d;
        return new Complex(a, b);
    }

    // Divide a complex number by this complex number
    public Complex divide(Complex otherComplex) {
        double c = otherComplex.getRealPart();
        double d = otherComplex.getImaginaryPart();
        double denominator = c * c + d * d;

        double a = (realPart * c + imaginaryPart * d) / denominator;
        double b = (imaginaryPart * c - realPart * d) / denominator;
        return new Complex(a, b);
    }

    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // Shouldn't ever happen
            System.out.println("Complex.clone() failed. " + e);
            return null;
        }
    }

    @Override
    public String toString() {
        if (imaginaryPart == 0){
            return realPart + "";
        } else {
            String secondPart = (imaginaryPart > 0) ? " + " : " - ";
            return "(" + realPart + secondPart + Math.abs(imaginaryPart) + "i)";
        }
    }

    @Override
    public boolean equals(Object other) {
        double epsilon = 0.000_001;

        return other instanceof Complex
                && (this.subtract((Complex) other)).abs() < epsilon;
    }

    public double getRealPart() {
        return realPart;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }
}

