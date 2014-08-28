/**
 * Created by Netšajev on 28/08/2014.
 */
public class Ex4 {
    public static void main(String[] args){
        System.out.println("a      a^2    a^3");
        for(int a = 1; a < 5; a++){
            System.out.print(a + "      " + (a*a));
            if (a < 4){
                System.out.println("      " + (a*a*a));
            }
            else {
                System.out.println("     " + (a*a*a));
            }
        }
    }
}
