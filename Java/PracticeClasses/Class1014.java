import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static wiki.WikiAPI.getWikiInformation;

/**
 * @author Eduard Netšajev on 14/10/2014.
 */
public class Class1014 {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {

        for (String arg : args) {
            System.out.println(arg);
            getTouched(getWikiInformation(arg));
            System.out.println();
        }
    }

    public static void getTouched(String str) {

        String a = str.substring(str.indexOf("touched=\"")+9);
        a = a.substring(0, a.indexOf("\""));
        try {

            Calendar clndr = Calendar.getInstance();
            clndr.setTime(dateFormat.parse(a));
            System.out.println(clndr.getTime().toString());

            clndr.add(Calendar.DATE, 44);
            Date dnew = clndr.getTime();

            System.out.println("Add 44 days: " + dnew.toString());

        } catch (Exception e) {
//            return null;
        }
    }
}
