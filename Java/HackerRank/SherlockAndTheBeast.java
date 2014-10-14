class SherlockAndTheBeastSolution {
    public static void main(String[] args) {

        Reader.init(System.in);

        try {
            int n;
            int c;
            for (int t = Reader.nextInt(); t > 0; t--) {
            n = Reader.nextInt();


                c = 5*(2*n%3);
                if (c > n) {
                   System.out.println(-1);
                } else {
                    for (int d = (n - c) / 3; d > 0; d--) {
                        System.out.print(555);
                    }
                    for (c = c / 5; c > 0; c--) {
                        System.out.print(33333);
                    }
                    System.out.println();
                }

            }
        } catch (Exception e) {
            //
        }
    }
}
