package fundamentals;

import java.util.EmptyStackException;

/**
 * Author: Pierre Schaus
 *
 * You have to implement the interface using
 * - a simple linkedList as internal structure
 * - a growing array as internal structure
 */
public interface Stack<E> {

    public boolean empty();

    public E peek() throws EmptyStackException;

    public E pop() throws EmptyStackException;

    public void push(E item);

}

/**
 * Implement the Stack interface above using a simple linked list.
 * You should have at least one constructor withtout argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class LinkedStack<E> implements Stack<E> {

    private Node<E> top;        // the node on the top of the stack
    private int size;        // size of the stack

    // helper linked list class
    private class Node<E> {
        private E item;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }

    /**
     * Tests if this stack is empty
     */
    @Override
    public boolean empty() {
        // TODO Implement empty method
        // STUDENT return false;
        // BEGIN STRIP
        return false;
        // END STRIP
    }

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    @Override
    public E peek() throws EmptyStackException {
        // TODO Implement peek method
        // STUDENT return null;
        // BEGIN STRIP
        return null;
        // END STRIP
    }

    /**
     * Removes the object at the top of this stack
     * and returns that object as the value of this function
     */
    @Override
    public E pop() throws EmptyStackException {
        // TODO Implement pop method
        // STUDENT return null;
        // BEGIN STRIP
        return null;
        // END STRIP
    }

    /**
     * Pushes an item onto the top of this stack
     * @param item the item to append
     */
    @Override
    public void push(E item) {
        // TODO STUDENT: Implement push method
        // BEGIN STRIP
        // END STRIP
    }
}


/**
 * Implement the Stack interface above using an array as internal representation
 * The capacity of the array should double when the number of elements exceed its length.
 * You should have at least one constructor withtout argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class ArrayStack<E> implements Stack<E> {

    private E[] array;        // array storing the elements on the stack
    private int size;        // size of the stack

    /**
     * Tests if this stack is empty
     */
    @Override
    public boolean empty() {
        // TODO Implement empty method
        // STUDENT return false;
        // BEGIN STRIP
        return false;
        // END STRIP
    }

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    @Override
    public E peek() throws EmptyStackException {
        // TODO Implement peek method
        // STUDENT return null;
        // BEGIN STRIP
        return null;
        // END STRIP
    }

    /**
     * Removes the object at the top of this stack
     * and returns that object as the value of this function
     */
    @Override
    public E pop() throws EmptyStackException {
        // TODO Implement pop method
        // STUDENT return null;
        // BEGIN STRIP
        return null;
        // END STRIP
    }

    /**
     * Pushes an item onto the top of this stack
     * @param item the item to append
     */
    @Override
    public void push(E item) {
        // TODO STUDENT: Implement push method
        // BEGIN STRIP
        // END STRIP
    }
}

