public class Class0909 {
    public static void main(String[] args) {
        fill(8, 1, 5, 2);
        System.out.println();
        fill(7, 1, 5, 2);

    }

    public static boolean fill(int amount, int dec, int fives, int ones) {
        // 10 5 1
        int mDecimals = 0;
        int mFives = 0;
        int mOnes = 0;

        do {
            if(amount > 10 && dec > 0) {
                amount -= 10;
                dec--;
                mDecimals++;
            } else if (amount > 5 && fives > 0) {
                amount -= 5;
                fives--;
                mFives++;
            } else if (amount > 0 && ones > 0) {
                amount--;
                ones--;
                mOnes++;
            } else {
                break;
            }
        } while(true);

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
}
