public class ThePipelineSolution {

    public static final long INF = Long.MAX_VALUE;

    public static void main(String[] args) {

        try {
            Reader.init(System.in);

            int n;
            int[][] field;
            long[][] cost;


            n = Reader.nextInt();
            field = new int[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    field[i][j] = Reader.nextInt();
                }
            }
            if (n > 2) {
                cost = new long[n][n];
                int f = 0;

//                Queue<IntegerPair> Q = new LinkedList<>();
//                Stack<IntegerPair> B = new Stack<>();

                for (int i = 0; i < n; i++) {
                    cost[i][f] = field[i][f];
//                    Q.add(new IntegerPair(i, f+1));
                }
                f++;
//                int y, x;
//                IntegerPair c;

                for (; f < n; f++){

                    for (int i = 0; i < n; i++) {
                        cost[i][f] = field[i][f] + cost[i][f-1];

                        if (i > 0) {
                            cost[i][f] = Math.min(field[i][f] + cost[i-1][f], cost[i][f]);
                        }

                    }
                    for (int i = n-2; i >= 0; i--) {
                        cost[i][f] = Math.min(field[i][f] + cost[i+1][f], cost[i][f]);

//                        cost[i+1][f] = Math.min(field[i+1][f] + cost[i][f], cost[i+1][f]); // todo del
                    }
                }


                /*while(!Q.isEmpty() && f < n) {
                    c = Q.poll();
                    y = c.y;
                    x = c.x;

                    if (x < n - 1) {

                        Q.add(new IntegerPair(y, x + 1));

                        if (y > 0) {
                            if (x > 0) {
//                                System.out.println(c);
//                                if (x == 1 && y == 5) {
//                                    c = c;
//                                }
                                if (cost[y - 1][x] > cost[y][x - 1]) {
                                    cost[y][x] = field[y][x] + cost[y][x - 1];
                                    if (cost[y][x] + field[y-1][x] < cost[y-1][x]) {
                                        B.add(new IntegerPair(y-1, x));
                                        cost[y-1][x] = cost[y][x] + field[y-1][x];
                                    }
                                } else {
                                    cost[y][x] = field[y][x] + cost[y - 1][x];

//                                    if (cost[y][x] + field[y][x-1] < cost[y][x-1]) {
//                                        B.add(new IntegerPair(y, x-1));
//                                        cost[y][x-1] = cost[y][x] + field[y][x-1];
//                                    }
                                }
                            }
                        } else {
                            cost[y][x] = cost[y][x-1] + field[y][x];
                            Q.add(new IntegerPair(y, x + 1));
                        }
                    }

                    while(!B.isEmpty()){
                        c = B.pop();
                        y = c.y;
                        x = c.x;
//                        System.out.println(c);

                        if (x < n){   //(x > 1 && x < n - 1)
                            if (y > 0){
                                if (cost[y][x] + field[y][x+1] < cost[y][x+1]) { // not needed TODO maybe
                                    cost[y][x+1] = cost[y][x] + field[y][x+1];
                                    B.add(new IntegerPair(y, x+1));
                                }
                                if (cost[y][x] + field[y-1][x] < cost[y-1][x]) {
                                    cost[y-1][x] = cost[y][x] + field[y-1][x];
                                    B.add(new IntegerPair(y-1, x));
                                }
                            }
//                            if (cost[y][x] + field[y][x-1] < cost[y][x-1]) {
//                                cost[y][x-1] = cost[y][x] + field[y][x-1];
//                                B.add(new IntegerPair(y, x-1));
//                            }
                            if (y+1 < n && (cost[y][x] + field[y+1][x] < cost[y+1][x])) {
                                cost[y+1][x] = cost[y][x] + field[y+1][x];
                                B.add(new IntegerPair(y+1, x));
                            }*/
//                        }
//                    }
//                }
//



//                for (int i = 0; i < n; i++) {
//                    for (int j = 0; j < n; j++) {
//                        System.out.print(" " + cost[i][j]);
//                    }
//                    System.out.println();
//                }

                long min = INF;
                long z;
                f = n - 2;
                for (int i = 0; i < n; i++) {
                    z = cost[i][f] + field[i][n-1];
                    min = Math.min(min, z);
//                    System.out.println(cost[i][f]);
                }
                System.out.println(min);
                System.exit(0);

            } else if (n == 2) {
                System.out.println(Math.min(field[0][0] + field[0][1], field[1][0] + field[1][1]));
                System.exit(0);
            } else {
                System.out.println(field[0][0]);
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }
}

class IntegerPair {
    int y, x;

    IntegerPair(int row, int column) {
        y = row;
        x = column;
    }

    @Override
    public String toString() {
        return y + " " + x;
    }
}
