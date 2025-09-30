package fundamentals;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
import java.util.Queue;

/**
 * Author: Pierre Schaus and Auguste Burlats
 * Implement the abstract data type stack using two queues
 * You are not allowed to modify or add the instance variables,
 * only the body of the methods
 */
public class StackWithTwoQueues<E> {

    Queue<E> queue1;
    Queue<E> queue2;

    public StackWithTwoQueues() {
        queue1 = new ArrayDeque();
        queue2 = new ArrayDeque();
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue contains no elements, false otherwise
     */
    public boolean empty() {
        // STUDENT return false;
        // BEGIN STRIP
        return queue1.isEmpty();
        // END STRIP
    }

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException {
        // STUDENT return null;
        // BEGIN STRIP
        if (empty()) throw new EmptyStackException();
        return queue1.peek();
        // END STRIP
    }

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException {
        // STUDENT return null;
        // BEGIN STRIP
        if (empty()) throw new EmptyStackException();
        return queue1.remove();
        // END STRIP
    }

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item) {
        // BEGIN STRIP
        queue2.add(item);
        while (!queue1.isEmpty()) {
            queue2.add(queue1.remove());
        }
        Queue<E> buffer = queue1;
        queue1 = queue2;
        queue2 = buffer;
        // END STRIP
    }

}
