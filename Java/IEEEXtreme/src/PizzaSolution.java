import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PizzaSolution {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) throws IOException {

        String in = reader.readLine();
        String tokens[] = in.split(" ");

        String tops[];

        int l = tokens.length;
        int cal = 0;
        int mul;
        int sum;
        int temp;
        for(int i = 1; i < l; i++) {
            mul = Integer.parseInt(tokens[i]);
            cal += 270 * mul;

            i++;

            tops = tokens[i].split(",");
            sum = 0;
            for (String top : tops) {
                temp = 0;
                switch (top) {
                    case "Anchovies":
                        temp = 50;
                        break;
                    case "Artichoke":
                        temp = 60;
                        break;
                    case "Bacon":
                        temp = 92;
                        break;
                    case "Broccoli":
                        temp = 24;
                        break;
                    case "Cheese":
                        temp = 80;
                        break;
                    case "Chicken":
                        temp = 30;
                        break;
                    case "Feta":
                        temp = 99;
                        break;
                    case "Garlic":
                        temp = 8;
                        break;
                    case "Ham":
                        temp = 46;
                        break;
                    case "Jalapeno":
                        temp = 5;
                        break;
                    case "Meatballs":
                        temp = 120;
                        break;
                    case "Mushrooms":
                        temp = 11;
                        break;
                    case "Olives":
                        temp = 25;
                        break;
                    case "Onions":
                        temp = 11;
                        break;
                    case "Pepperoni":
                        temp = 80;
                        break;
                    case "Peppers":
                        temp = 6;
                        break;
                    case "Pineapple":
                        temp = 21;
                        break;
                    case "Ricotta":
                        temp = 108;
                        break;
                    case "Sausage":
                        temp = 115;
                        break;
                    case "Spinach":
                        temp = 18;
                        break;
                    case "Tomatoes":
                        temp = 14;
                        break;
                }
                sum += temp * mul;
            }

            cal += sum;
        }

        System.out.print("The total calorie intake is ");
        System.out.println(cal);
    }
}
