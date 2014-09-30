public class Parenting {

    public static void main(String[] args) {

        A foo = new A("Parent");

        System.out.println(foo.name);
        System.out.println(foo.child.name);
        System.out.println(foo.child.parent.name);
        System.out.println(foo.child.parent.child.name);

    }
}


class A {
    String name;
    B child;

    A(String name) {
        this.name = name;
        this.child = new B("Child", this);
    }
}

class B {
    String name;
    A parent;

    B(String name, A parent) {
        this.name = name;
        this.parent = parent;
    }
}