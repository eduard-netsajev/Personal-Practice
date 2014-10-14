class ManasaAndStonesSolution {

    public static void main(String[] args) {

        Reader.init(System.in);
        try {
            for (int t = Reader.nextInt(); t > 0; t--) {
                int n = Reader.nextInt() - 1;
                int a = Reader.nextInt();
                int b = Reader.nextInt();

                if (a == b) {
                    System.out.println(n*b);
                    continue;
                }

                int d = a - b;
                if (d < 0) {
                    d = -d;
                    b = a;
                }
                int min = n * b;
                System.out.print(min);
                for (int i = 0; i < n; i++) {
                    min += d;
                    System.out.print(" " + min);
                }
                System.out.println();
            }
        } catch (Exception e) {
            // nothing
        }
    }
}
