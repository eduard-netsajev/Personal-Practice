public class Ex4 {
    public static void main(String[] args){
        System.out.println("a     a^2    a^3");
        for(int a = 1; a < 5; a++){

            System.out.printf("%-7d", a);
            System.out.printf("%-7d", a*a);
            System.out.printf("%d\n", a*a*a);
        }
    }
}
