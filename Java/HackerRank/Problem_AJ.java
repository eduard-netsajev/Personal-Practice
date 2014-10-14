import java.util.Scanner;

class Problem_AJSolution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = Integer.parseInt(in.nextLine());

        int speed = 0;

        for (int i = 0; i < n; i++) {

            speed += in.nextInt();
            if (speed < 0){
                speed = 0;
            }

        }
        System.out.println(speed);
    }
}