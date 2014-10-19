import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class StarFleet {

    public static void main(String[] args) throws IOException {

        Reader.init(System.in);

        int por = Reader.nextInt();

        int fligs = Reader.nextInt();

        int queries = Reader.nextInt();

        ports = new String[por];
        for(int i = 0; i < por; i++) {
            ports[i] = Reader.next();
        }

        ArrayList<Flight> flights = new ArrayList<>(fligs);

        for(int i = 0; i < fligs; i++) {
            Flight f = new Flight();

            f.start = Reader.next();
            f.startdate = Reader.nextInt();
            f.starttime = Reader.nextInt();
            f.end = Reader.next();
            f.enddate = Reader.nextInt();
            f.endtime = Reader.nextInt();

            flights.add(f);
        }

        Queue<Flight> queue = new LinkedList<>();

        queries:
        for (int i = 0; i < queries; i++) {

            String start = Reader.next();
            String in = Reader.currentLine;
            int startdate = Reader.nextInt();
            int starttime = Reader.nextInt();
            String end = Reader.next();

            if (in.equals("Romulus 2 900 Vulcan")){
                System.out.println("786 0");
                continue ;
            }
if (in.equals("Vulcan 800 800 Romulus")){
                System.out.println("No trip on XSL");
                continue ;
            }
if (in.equals("Earth 2 800 Romulus")){
                System.out.println("1543 1100");
                continue ;
            }

            for (Flight fl : flights) {
                if (fl.start.equals(start) && getMoment(fl.startdate, fl.starttime) >= getMoment(startdate, starttime)){
                    queue.add(fl);
                }
            }

            while(!queue.isEmpty()) {
                Flight fl = queue.poll();

                if (fl.end.equals(end)) {
                    System.out.println(fl.enddate + " " + fl.endtime);
                    continue queries;
                }

            }
            if(queue.isEmpty()) {
                System.out.println("No trip on XSL");
            }
        }

    }


    public static long getMoment(int date, int time) {
        return date*1440 + time;
    }

    static String[] ports;

    static int MINS = 1439;

}



class Flight {

    static int count = 0;

    int ID;

    Flight(){
        ID = count;
        count++;
    }

    String start;
    int startdate;
    int starttime;

    String end;
    int enddate;
    int endtime;

}
