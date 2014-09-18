import java.util.ArrayList;

public class MyStack implements Cloneable{

    public static void main(String[] args) {

        Integer i1 = 1;
        Integer i2 = 2;
        Integer i3 = 3;
        Integer i4 = 4;

        MyStack st1 = new MyStack();
        st1.push(i1);
        st1.push(i2);

        MyStack st2 = (MyStack) st1.clone();

        System.out.println(st1);
        System.out.println(st2);

        st1.pop();
        st1.push(i3);
        st2.push(i4);

        System.out.println(st1);
        System.out.println(st2);
    }

    private ArrayList<Object> list;

    public MyStack() {
        list = new ArrayList<Object>();
    }

    public MyStack(ArrayList<Object> list) {
        this.list = new ArrayList<Object>(list);
    }

    // alternative to clone
    public MyStack(MyStack stack) {
        this(stack.asList());
    }

    public ArrayList<Object> asList() {
        return list;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int getSize() {
        return list.size();
    }

    public Object peek() {
        return list.get(getSize() - 1);
    }

    public Object pop() {
        Object temp = list.get(getSize() - 1);
        list.remove(getSize() - 1);
        return temp;
    }

    public void push(Object obj) {
        list.add(obj);
    }

    @Override
    public String toString() {
        return "Stack: " + list.toString();
    }

    @Override
    public Object clone() {
        try {
            Object newObj = super.clone();
            if (newObj instanceof MyStack) {
                ((MyStack) newObj).list = new ArrayList<Object>(list);
            }
            return newObj;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
