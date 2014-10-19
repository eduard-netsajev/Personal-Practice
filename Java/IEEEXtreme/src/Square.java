import java.io.IOException;

public class Square {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);

        int n;
        int turns;
        double[] squares;
        double turn;
        double turnsTotal;

        while(true)
        {
            n = Reader.nextInt();
            if (n == 0) {
                break;
            }

            turnsTotal = 1.0;
            turn = 1.0;

            n--;

            squares = new double[n];

            for (int i = 0; i < n; i++) {
                squares[i] = Reader.nextDouble();

            }


            while(n-- > 0) {
                turn /= squares[n];
                turnsTotal += turn;
            }

            turnsTotal += 0.5;
            turns = (int) turnsTotal;
            System.out.println(turns);
        }



    }
}
