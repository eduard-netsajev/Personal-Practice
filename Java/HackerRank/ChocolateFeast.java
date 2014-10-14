class ChocolateFeastSolution {

    public static void main(String[] args) {

        Reader.init(System.in);

        try {

            for (int t = Reader.nextInt(); t > 0; t--) {
                int n = Reader.nextInt();
                int c = Reader.nextInt();
                int m = Reader.nextInt();

                System.out.println(task(n/c, m));

            }


        } catch (Exception e) {
            // nothing
        }
    }

    static int task (int current_bottles, int bottles_rate) {
        int drinked_total = 0;
        int empty_bottles = 0;

        while(current_bottles > 0 || empty_bottles >= bottles_rate) {

            empty_bottles += current_bottles;
            drinked_total += current_bottles;

            current_bottles = empty_bottles / bottles_rate;
            empty_bottles %= bottles_rate;
        }
        return drinked_total;
    }
}
