/**
 * DooBeeDoo.
 */
public class DooBee {
    /**
     *
     * @param args console args
     */
    public static void main(String[] args) {
        int x = 1;
        int c = x + x + x;
        while (x < c) {
            System.out.print("Doo");
            System.out.print("Bee");
            x = x + 1;
        }
        if (x == c) {
            System.out.print("Do");
        }
    }
}