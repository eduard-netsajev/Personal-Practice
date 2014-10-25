package Chapter_2;

public class Ex17plus {
    public static void main(String[] args) {
        int m, r;
        m = r = 2;

        System.out.println(m * Math.pow(r, 2));
        System.out.println(9223372036854775807L);
        System.out.println(1_2.3e-4);
        System.out.println(123_232_456_451L == 123232456451F);
        System.out.println(-2147483648 - 2147483643);

        int secondsTotal = (int) (System.currentTimeMillis() / 1000);
        int secondsNow = secondsTotal % 60;
        int minutesNow = secondsTotal / 60 % 60;
        int hoursNow = secondsTotal / 3600 % 24;

        System.out.printf("\nTime now: %d:%d:%02d GMT\n", hoursNow, minutesNow,
                secondsNow);

        int remainedAmount = 17;
        int nickels;
        remainedAmount -= 5* (nickels = remainedAmount / 5);
        System.out.println(nickels);
        System.out.println(remainedAmount);
        System.out.println(10.03 * 100);
    }
}
