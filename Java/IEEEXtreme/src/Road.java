import java.io.IOException;
import java.util.Arrays;

public class Road {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int cases = Reader.nextInt();

        int INF = Integer.MAX_VALUE;

        cases:
        while(cases-- > 0) {
            int n = Reader.nextInt();
            int max_fuel = Reader.nextInt();
            int cur_fuel = Reader.nextInt();
            int dist = Reader.nextInt();
            long cost = 0;
            Pair[] gas = new Pair[n];
            for (int i = 0; i < n; i++) {
                gas[i] = new Pair(Reader.nextInt(), Reader.nextInt());
            }

            Arrays.sort(gas);

            int cur_distance = 0;
            int min_price;
            int target = 0;
            int loc = -1;
            int last_dis = -1;

            boolean heh = false;
            while (true) {




//                System.out.println("Cur dis: " + cur_distance);
//                System.out.println("Cur loc: " + loc);
//                System.out.println("Cur fuel " + cur_fuel);
                if (!(dist - cur_distance > cur_fuel)){
                    System.out.println(cost);
                    continue cases;
                }

                min_price = INF;

                for (int i = loc + 1; i < n; i++) {

                    if (gas[i].x - cur_distance > cur_fuel) {
                        break;
                    }
                    if (gas[i].cost < min_price) {
                        min_price = gas[i].cost;
                        target = i;
                    }
                }
                if (min_price == INF  && loc == -1 && gas[loc+1].x > max_fuel) {
                    System.out.println(-1);
                    continue cases;
                }

//                System.out.println("Cur fuel " + cur_fuel);
//                System.out.println("Min price" + min_price);
                if (loc > -1){
                    if (gas[loc].cost <= min_price) {
                        for (int i = loc + 1; i < n + 1; i++) {
                            if (i == n) {
                                if (target == loc) {
                                int need = dist - cur_distance - cur_fuel;
//                                    System.out.println("Cur fuel " + cur_fuel);
                                if (need > 0) {
                                    cost += need * gas[loc].cost;
//                                    System.out.println("Bought " + need);
//                                    System.out.println(cost);
                                }
                                System.out.println(cost);
                                continue cases;
                            } else {
                                break;
                            }
                            }
                            if (gas[i].x - cur_distance > max_fuel) {
                                break;
                            }
                            if (gas[i].cost < min_price) {
//                                System.out.println("NEW MIN!");
                                min_price = gas[i].cost;
                                target = i;
                                if (gas[i].cost <= gas[loc].cost) { // todo check
                                    break;
                                }
                            }
                        }
                    }

//                    System.out.println("New min price" + Math.min(min_price, gas[loc].cost));

                    long need = gas[target].x - cur_distance;

                    if (gas[loc].cost < min_price) {

                        need = Math.min(dist-cur_distance, max_fuel);
                    }
                    need -= cur_fuel;
//                    System.out.println(cur_fuel + " fuel now we need " + need);

                    if (need > 0) {
                        cost += need * gas[loc].cost;
//                        System.out.println("Bought " + need);
//                        System.out.println(cost);
                        cur_fuel += need;
                    }

                }
//                System.out.println("Cur fuel " + cur_fuel);
                cur_fuel -= (gas[target].x - cur_distance);
                if (cur_fuel < 0) {
                    System.out.println(-1);
                    continue cases;
                }
//                System.out.println("Now fuel " + cur_fuel);

                cur_distance = gas[target].x;

                if (cur_distance == last_dis && loc == target) {
                    if (heh) {
                        System.out.println(-1);
                        continue cases;
                    } else {
                        heh = true;
                    }
                } else {
                    last_dis = cur_distance;
                }

                loc = target;


            }
        }
    }
}

class Pair implements Comparable {
    int cost, x;

    Pair(int x, int cost) {
        this.cost = cost;
        this.x = x;
    }

    @Override
    public String toString() {
        return cost + " " + x;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Pair){
            if (x < ((Pair) o).x){
                return -1;
            }
            if (x > ((Pair) o).x){
                return 1;
            }
            return 0;
        }
        return 0;
    }
}