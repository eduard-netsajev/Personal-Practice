/**
 * My implementation of a generic stack data structure
 * using array[] as a data-holder, unlike java.util.Stack that
 * is a vector subtype. Tested, it works.
 */
public class GenericStack<E> {

    private Object[] array;
    protected int elementCount;

    public GenericStack() {
        this(10);
    }

    public GenericStack(int initialCapacity) {
        array = new Object[initialCapacity];
    }

    public int getSize() {
        return elementCount;
    }

    public E peek() {
        return (E) array[elementCount - 1];
    }

    public void push(E o) {
        int arrayLength = array.length;
        if (elementCount < arrayLength) {
            array[elementCount] = o;
        } else {
            Object[] newArray = new Object[arrayLength * 2];
            System.arraycopy(array, 0, newArray, 0, arrayLength);
            newArray[elementCount] = o;
            System.out.println("NEW ARRAY");
            array = newArray;
        }
        elementCount++;
    }

    public E pop() {
        if (elementCount > 0) {
            elementCount--;
            return (E) array[elementCount - 1];
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return elementCount == 0;
    }

    @Override
    public String toString() {
        String result = "stack:";
        for (Object o: array) {
            if (o == null) {
                break;
            }
            result += " " + o.toString();
        }
        return result;
    }
}