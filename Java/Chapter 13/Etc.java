import java.util.Arrays;
import java.util.Calendar;
import org.jetbrains.annotations.NotNull;
import java.util.GregorianCalendar;

public class Etc {
    public static void main(String[] args) {
        Number[] numberArray = new Number[2];
        numberArray[0] = new Double(1.5);
        numberArray[1] = new Integer(3);

        Number x = 3;
        System.out.println(x.intValue());
        System.out.println(x.doubleValue());

        Calendar calendar = new GregorianCalendar(2008, 8, 1);
        Calendar today = new GregorianCalendar();
        System.out.println(today.get(Calendar.WEEK_OF_YEAR) +" "+ calendar.get
                (Calendar
                .WEEK_OF_YEAR));

        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();

        Person[] people = new Person[]{p1, p2, p3};
        Arrays.sort(people);
    }
}

class Person implements Comparable<Person> {
    @Override
    public int compareTo(@NotNull Person person) {
        return 0; // Socialism
    }
}
