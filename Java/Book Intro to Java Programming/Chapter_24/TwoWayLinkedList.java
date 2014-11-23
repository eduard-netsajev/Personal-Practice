package Chapter_24;

import java.util.AbstractSequentialList;
import java.util.ListIterator;

public class TwoWayLinkedList<E> extends AbstractSequentialList<E> {
    private Node<E> head, tail;
    int size = 0;

    /** Create a default list */
    public TwoWayLinkedList() {
    }

    /** Return the number of elements in this list */
    public int size() {
        return size;
    }

    /** Create a list from an array of objects */
    public TwoWayLinkedList(E[] objects) {
        for (E object : objects) {
            addLast(object);
        }
    }

    /** Return the head element in the list */
    public E getFirst() {
        if (size == 0) {
            return null;
        }
        else {
            return head.element;
        }
    }

    /** Return the last element in the list */
    public E getLast() {
        if (size == 0) {
            return null;
        }
        else {
            return tail.element;
        }
    }

    /** Add an element to the beginning of the list */
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e); // Create a new node
        newNode.next = head; // link the new node with the head
        head.previous = newNode; // link the head with the new node
        head = newNode; // head points to the new node
        size++; // Increase list size

        if (tail == null) // The new node is the only node in list
            tail = head;
    }

    /** Add an element to the end of the list */
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e); // Create a new node for e

        if (tail == null) {
            head = tail = newNode; // The only node in list
        }
        else {
            tail.next = newNode; // Link the last node with the new node
            newNode.previous = tail; // Link the new node with the last node
            tail = newNode; // tail now points to the last node
        }

        size++; // Increase size
    }

    /** Add a new element at the specified index
     * in this list. The index of the head element is 0 */
    @Override
    public void add(int index, E e) {
        if (index == 0) addFirst(e); // Insert first
        else if (index >= size) addLast(e); // Insert last
        else { // Insert in the middle
            Node<E> current = head;
            for (int i = 1; i < index; i++)
                current = current.next;

            Node<E> newNode = new Node<>(e);
            Node<E> temp = current.next;

            current.next = newNode;
            newNode.previous = current;

            newNode.next = temp;
            temp.previous = newNode;
            size++;
        }
    }

    /** Remove the head node and
     * return the object that is contained in the removed node. */
    public E removeFirst() {
        if (size == 0) return null; // Nothing to delete
        else {
            Node<E> temp = head; // Keep the first node temporarily
            head = head.next; // Move head to point to next node
            size--; // Reduce size by 1
            if (head == null) tail = null; // List becomes empty
            else head.previous = null;
            return temp.element; // Return the deleted element
        }
    }


    /** Remove the last node and
     * return the object that is contained in the removed node. */
    public E removeLast() {
        if (size == 0) return null; // Nothing to remove
        else if (size == 1) { // Only one element in the list
            Node<E> temp = head;
            head = tail = null; // list becomes empty
            size = 0;
            return temp.element;
        }
        else {
            Node<E> current = tail.previous;
            current.next = null;
            Node<E> temp = tail;
            tail = current;
            size--;
            return temp.element;
        }
    }

    /** Remove the element at the specified position in this
     * list. Return the element that was removed from the list. */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) return null; // Out of range
        else if (index == 0) return removeFirst(); // Remove first
        else if (index == size - 1) return removeLast(); // Remove last
        else {
            Node<E> current = head;

            for (int i = 1; i < index; i++) {
                current = current.next;
            }

            Node<E> foundNode = current.next;
            current.next = foundNode.next;
            (foundNode.next).previous = current;
            size--;
            return foundNode.element;
        }
    }

    @Override
    public String toString() {

        if (head == null) return "[]";

        StringBuilder result = new StringBuilder("[");
        Node<E> current = head;
        while(true) {
            result.append(current.element);
            if (current.hasNext()) {
                result.append(", ");
            } else {
                break;
            }
        }
        result.append("]"); // Insert the closing ] in the string

        return result.toString();
    }

    @Override /** Clear the list */
    public void clear() {
        size = 0;
        head = tail = null;
    }

    /** Return true if this list contains the element e */
    @Override
    public boolean contains(Object e) {
        Node<E> current = head;

        while(current != null) {
            if (current.element == e) return true;
            current = current.next;
        }
        return false;
    }

    @Override /** Return the element at the specified index */
    public E get(int index) {
        if (index < 0 || index >= size) return null; // Out of range
        else if (index == 0) return head.element; // Get first
        else if (index == size - 1) return tail.element; // Get last
        else {
            Node<E> current = head;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.element;
        }
    }

    /** Return the index of the head matching element
     * in this list. Return -1 if no match. */
    @Override
    public int indexOf(Object e) {

        Node<E> current = head;
        int index = 0;

        while(current != null) {
            if (current.element == e) return index;
            index++;
            current = current.next;
        }
        return -1;
    }

    /** Return the index of the last matching element
     * in this list. Return -1 if no match. */
    @Override
    public int lastIndexOf(Object e) {
        Node<E> current = tail;
        int index = size - 1;

        while(current != null) {
            if (current.element == e) return index;
            index--;
            current = current.previous;
        }

        return -1;
    }

    /** Replace the element at the specified position
     * in this list with the specified element. */
    @Override
    public E set(int index, E e) {
        if (index < 0 || index >= size) return null; // Out of range
        else {
            Node<E> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            E temp = current.element;
            current.element = e;
            return temp;
        }
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new TwoWayLinkedListIterator();
    }

    private class TwoWayLinkedListIterator implements ListIterator<E> {  // TODO implement ListIterator
        private Node<E> current = head; // Current index
        private int index = 0;

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public E next() {
            E e = current.element;
            current = current.next;
            return e;
        }

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException
                    ("This operation is not yet supported");
        }

        @Override
        public E previous() {
            throw new UnsupportedOperationException
                    ("This operation is not yet supported");
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException
                    ("This operation is not yet supported");
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException
                    ("This operation is not yet supported");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException
                    ("This operation is not yet supported");
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException
                    ("This operation is not yet supported");
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException
                    ("This operation is not yet supported");
        }
    }

    // This class is only used in DoublyLinkedList, so it is private.
    // This class does not need to access any
    // instance members of DoublyLinkedList, so it is defined static.
    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        public Node(E element) {
            this.element = element;
        }

        public boolean hasNext() {
            return next != null;
        }

        public boolean hasPrevious() {
            return previous != null;
        }
    }
}