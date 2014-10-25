package Chapter_3;

public class RandomMonth {
    public static void main(String[] args) {
        String[] monthNames = {"January", "February", "March", "April",
                "May", "June", "July", "August", "September", "November",
                "December"};
        System.out.print(monthNames[(int) (Math.random() * 12)]);
    }
}
