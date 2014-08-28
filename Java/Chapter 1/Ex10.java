public class Ex10 {
    public static void main(String[] args){
        double hours = (45 + 30/60) / 60.0;
        double miles = 14 / 1.6;
        double mph = miles / hours;
        System.out.println("A runner who runs 14 kilometers in 45 minutes and" +
                " 30 seconds is running with average speed " + mph + " miles " +
                "per hour");
    }
}
