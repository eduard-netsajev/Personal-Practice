public class TestQueueOfIntegers {
    public static void main(String[] args) {
        QueueOfIntegers queue = new QueueOfIntegers();
        for (int i = 0; i < 10; i++)
            queue.enqueue(i);

        while (!queue.empty())
            System.out.print(queue.dequeue() + " ");
    }
}

class QueueOfIntegers {
    private int[] elements;
    private int size;
    public static final int DEFAULT_CAPACITY = 8;

    /** Construct a queue with the default capacity 8 */
    public QueueOfIntegers() {
        this (DEFAULT_CAPACITY);
    }

    /** Construct a queue with the specified maximum capacity */
    public QueueOfIntegers(int capacity) {
        elements = new int[capacity];
    }


    /** Add a new integer into the queue */
    public void enqueue(int value) {
        if (size >= elements.length) {
            int[] temp = new int[elements.length * 2];
            System.arraycopy(elements, 0, temp, 0, elements.length);
            elements = temp;
        }

        elements[size++] = value;
    }

    /** Return and remove the first element from the queue */
    public int dequeue() {
        int temp = elements[0];
        System.arraycopy(elements, 1, elements, 0, --size);
        return temp;
    }

    /** Return the first element from the queue */
    public int peek() {
        return elements[0];
    }

    /** Test whether the queue is empty */
    public boolean empty() {
        return size == 0;
    }

    /** Return the number of elements in the queue */
    public int getSize() {
        return size;
    }
}