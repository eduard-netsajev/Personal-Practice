/**
 * Created by Netšajev on 28/08/2014.
 */
public class Ex7 {
    public static void main(String[] args) {
        double pi_quater = 0;
        for(int i = 1, y = 1; i < 1000000; i = i+2) {
            if (y > 0) {
                pi_quater += 1.0/i;
            }
            else {
                pi_quater -= 1.0/i;
            }
            y *= -1;
        }
        System.out.println(4 * pi_quater);
    }
}
