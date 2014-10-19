import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Heist {


    public static void main(String[] args) throws IOException {

        ArrayList<Loot> loot = new ArrayList<>(10);

        Reader.init(System.in);
        int m = Reader.nextInt();
        long n = Reader.nextLong();
        StringTokenizer tk;

        Reader.flushTokenizer();

        String in;

        while(true) {
            in = Reader.nextLine();
            if (in.equals("END")){
                break;
            }
            tk = new StringTokenizer(in, ",");
            loot.add(new Loot(tk.nextToken(), Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken())));
        }

        Collections.sort(loot);
        Collections.reverse(loot);

        long[] take = new long[loot.size()];

        n *= m;

        long vaba_w = n;

        long c;

        for (int i = 0; i < loot.size(); i++) {
            Loot lot = loot.get(i);

            c = vaba_w / lot.w;
            if (c > 0) {
                vaba_w -= c * lot.w;
            }
            take[i] = c;
        }

        StringBuilder sb;

        long takenW = 0;
        long takenG = 0;

        for (int i = 0; i < take.length; i++) {
            if (take[i] > 0) {
                sb = new StringBuilder(50);
                Loot lot = loot.get(i);
                sb.append(lot.name);
                sb.append(',');
                sb.append(take[i]);
                sb.append(',');
                vaba_w = take[i] * lot.w;
                takenW += vaba_w;
                sb.append(vaba_w);
                sb.append(',');
                vaba_w = take[i] * lot.cost;
                takenG += vaba_w;
                sb.append(vaba_w);
                System.out.println(sb.toString());
            }
        }
        sb = new StringBuilder(30);
        sb.append(takenW);
        sb.append(',');
        sb.append(takenG);
        System.out.println(sb.toString());

        System.out.print("Each robber gets: ");
        System.out.printf("%.2f\n", (takenG * 1.0 / m));


    }
}

class Loot implements Comparable{
    String name;
    int w;
    int cost;
    double ratio;
    Loot(String jewel, int w, int cost){
        name = jewel;
        this.w = w;
        this.cost = cost;
        ratio = 1.0 * w / cost;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Loot){
            double oratio = ((Loot) o).ratio;
            if (oratio < ratio) {
                return -1;
            } else if (ratio < oratio) {
                return 1;
            } else {
                return 0;
            }

        }
        return 0;
    }
}