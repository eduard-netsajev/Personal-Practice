package Chapter_13;

import com.sun.istack.internal.NotNull;

import java.util.*;

public class Etc {
    public static void main(String[] args) {
        Number[] numberArray = new Number[2];
        numberArray[0] = new Double(1.5);
        numberArray[1] = new Integer(3);

        Number x = 3;
        System.out.println(x.intValue());
        System.out.println(x.doubleValue());

        Calendar septemberFirst = new GregorianCalendar(2008, 8, 1);
        Calendar today = new GregorianCalendar();
        System.out.println(today.get(Calendar.WEEK_OF_YEAR));
        System.out.println(septemberFirst.get (Calendar.WEEK_OF_YEAR));

        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();

        Person[] people = new Person[]{p1, p2, p3};
        List<Person> samePeople = new ArrayList<>(Arrays.asList(people));

        Arrays.sort(people);
        Collections.sort(samePeople, new PersonComparator());
        Collections.sort(samePeople, Collections.reverseOrder(new PersonComparator()));
        // samePeople.sort(new PersonComparator()); - redirects to above
    }
}

class Person implements Comparable<Person>, Scream {
    String name;
    @Override
    public int compareTo(@NotNull Person person) {
        return 0; // Socialism
    }

    @Override
    public void calmDown(Person p){
        System.out.printf("Hey, %s, calm down please\n", p.name);
    }

    @Override
    public void greet(Person p) {
        System.out.printf("%s greets %s!\n", this.name, p.name);
    }
}

class PersonComparator implements Comparator<Person> {
    @Override
    public int compare(Person one, Person two) {
        return 0; // Socialism
    }
}

interface Scream {
    static void shout(){
        System.out.println("AAAAAaaaaaaaaaaaaaaaaa!");
    }

    void calmDown(Person o);

    void greet(Person o);
}