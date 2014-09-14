public class Class0909 {
    // Completed the assigned task first in the class
    // The function returns how you need to use your paper bills in order to
    // have exact sum of money. Given bills are 10, 5 and 1.
    public static void main(String[] args) {
        fill(8, 1, 5, 2);
        System.out.println();
        fill(7, 1, 5, 2);
        System.out.println();
        fill(37, 2, 2, 8);
    }

    public static boolean fill(int amount, int dec, int fives, int ones) {
        int mDecimals = min(amount / 10, dec);
        amount -= mDecimals * 10;
        int mFives = min(amount / 5, fives);
        amount -= mFives * 5;
        int mOnes = min(amount, ones);
        amount -= mOnes;

        if(amount == 0) {
            System.out.println("You have exact cash.");
            System.out.printf("You will need %dx10, %dx5, %dx1\n", mDecimals,
                    mFives,
                    mOnes);
            return true;
        } else {
            System.out.println("You don't have exact cash.");
            return false;
        }
    }

    public static int min(int a, int b){
        return (a < b) ? a : b;
    }
}
