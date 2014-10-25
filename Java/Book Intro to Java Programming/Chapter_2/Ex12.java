package Chapter_2;

public class Ex12 {
    public static void main(String[] args){
        // If today is Tuesday, what will be the day in 100 days
        int day = 2;
        day += 100;
        day %= 7;
        System.out.println(day);
        // day = 4, which means Thursday
    }
}