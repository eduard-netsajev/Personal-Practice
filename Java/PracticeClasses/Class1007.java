import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.StringTokenizer;

public class Class1007 {

    public static void main(String[] args) {

        try {

            URL url = new URL("http://courses.cs.ttu" +
                    ".ee/w/images/7/76/Praktikum-06-input.txt");

            Reader.init(new DataInputStream(url.openStream()));
            String s;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            ArrayList<Activity> activities = new ArrayList<>();

            while(true) {

                s = Reader.nextLine();
                if (s == null) {
                    break;
                }
                Date date = dateFormat.parse(Reader.next());
                String type = Reader.next();

                double distance = Double.parseDouble(Reader.next());
                double duration = Double.parseDouble(Reader.next());

                switch (type) {
                    case "R": {
                        int steps = Integer.parseInt(Reader.next());
                        int maxspeed = Integer.parseInt(Reader.next());

                        Running nRun = new Running();
                        nRun.date = date;
                        nRun.distance = distance;
                        nRun.duration = duration;
                        nRun.steps = steps;
                        nRun.maxSpeed = maxspeed;
                        activities.add(nRun);
                        break;
                    }
                    case "C": {
                        int maxspeed = Integer.parseInt(Reader.next());

                        Cycling nCycle = new Cycling();

                        nCycle.date = date;
                        nCycle.distance = distance;
                        nCycle.duration = duration;
                        nCycle.maxSpeed = maxspeed;
                        activities.add(nCycle);
                        break;
                    }
                    case "W":
                        int restduration = Integer.parseInt(Reader.next());

                        Walking nWalk = new Walking();

                        nWalk.date = date;
                        nWalk.distance = distance;
                        nWalk.duration = duration;
                        nWalk.restingDuration = restduration;
                        activities.add(nWalk);
                        break;
                }

                Reader.flushTokenizer();
            }

            Collections.sort(activities);

            for (Activity a: activities) {
                if (a instanceof Running) {
                    System.out.println("running " + a.toString());
                }
                if (a instanceof Cycling) {
                    System.out.println("cycling " + a.toString());
                }
                if (a instanceof Walking) {
                    System.out.println("walking " + a.toString());
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Activity implements Comparable{
    Date date;
    double distance;
    double duration;

    @Override
    public int compareTo(Object o) {
        Activity other = (Activity) o;
        return this.date.compareTo(other.date);
    }
}

class Running extends Activity {
    int steps;
    int maxSpeed;

    public String toString() {
        String s = date.toString() + " " + distance + " " + duration + " " +
                steps + " " + maxSpeed;
        return s;
    }
}

class Cycling extends Activity {
    int maxSpeed;
    public String toString() {
        String s = date.toString() + " " + distance + " " + duration + " " +
                maxSpeed;
        return s;
    }
}

class Walking extends Activity {
    int restingDuration;
    public String toString() {
        String s = date.toString() + " " + distance + " " + duration + " " +
                restingDuration;
        return s;
    }
}

class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;
    static String currentLine;

    // call this method to initialize reader for InputStream

    static void init(InputStream input) {
        reader = new BufferedReader(
                new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
        currentLine = "";
    }

    // get next word
    static void flushTokenizer() {
        currentLine = "";
        tokenizer = new StringTokenizer(
                currentLine );
    }

    static String next() throws IOException {

        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            currentLine = reader.readLine();
            tokenizer = new StringTokenizer(
                    currentLine );
        }
        return tokenizer.nextToken();
    }

    static String nextLine(){
        flushTokenizer();
        try {
            currentLine = reader.readLine();
            tokenizer = new StringTokenizer(currentLine);
            return currentLine;
        } catch (IOException|NullPointerException e) {
            return null;
        }
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
}