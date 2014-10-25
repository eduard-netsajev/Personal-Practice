package Chapter_11;

public class PolymorphismDemo {
    public static void main(String[] args) {

        Employee john = new Employee("John");
        Friend bill = new Friend("Bill");

        greetPerson(john);
        greetPerson(bill);
        // throws compile-time error
        Object walter = new Friend("Walter");
        // greetPerson(walter);

        new A();
        new B();
    }
    private static void greetPerson(Person person) {
        System.out.printf("Hey, %s!\n", person.name);
    }
}

class Person {
    String name;
    Person(String name) {
        this.name = name;
    }
}

class Employee extends Person{

    double annual_salary;

    Employee (String name) {
        super(name);
    }

    public void fire(){}
}

class Friend extends Person{
    Friend (String name) {
        super(name);
    }
}

class A {
    int i = 7;
    public A() {
        setI(20);
        System.out.println("i from A is " + i);
    }
    public void setI(int i) {
        this.i = 2 * i;
    }
}
class B extends A {
    public B() {
        System.out.println("i from B is " + i);
    }
    public void setI(int i) {
        this.i = 3 * i;
    }
}
