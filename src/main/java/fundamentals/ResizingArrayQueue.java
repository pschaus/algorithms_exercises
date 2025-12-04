package fundamentals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * We are interested in the implementation of a Queue using an array.
 * The array size needs to be updated when it is full or close to empty
 * i.e. when the array is full, the size of the array should double
 *  and when the array is less than 1/4 full, the size of the array should halve.
 *
 * You are asked to implement this Queue by completing the class see (TODO's)
 * Most important methods are:
 *  - the enqueue method to add an element
 *  - the remove method [The NoSuchElementException is thrown when the queue is empty]
 *  - the iterator used to browse the queue in FIFO
 *
 * Hint : when the head of the stack reaches the end of the array, go back at its beginning.
 *
 * @param <Item>
 */
public class ResizingArrayQueue<Item> implements Iterable<Item> {

    public Item[] q;
    private long nOp = 0;
    // BEGIN STRIP
    private int head;
    private int tail;
    private int size;
    // END STRIP

    @SuppressWarnings("unchecked")
    public ResizingArrayQueue() {
        q = (Item[]) new Object[2];
        nOp = 0;
        // BEGIN STRIP
        head = 0;
        tail = 0;
        size = 0;
        // END STRIP
    }

    public boolean isEmpty() {
        // TODO
        // Student return false;
        // BEGIN STRIP
        return size == 0;
        // END STRIP
    }

    public int size() {
        // TODO
        // Student return 0;
        // BEGIN STRIP
        return size;
        // END STRIP
    }

    private long getnOp() {
        return nOp;
    }
    // BEGIN STRIP
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        nOp++;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = q[(head + i) % q.length];
        }
        q = temp;
        head = 0;
        tail = size;
    }
    // END STRIP

    /**
     * Add an item at the tail of the queue.
     * Resize the array if needed.
     * @param item the item to add.
     */
    public void enqueue(Item item) {
        // TODO
        // BEGIN STRIP
        nOp++;
        if (size == q.length) {
            resize(2 * q.length);
        }
        q[tail] = item;
        tail = (tail + 1) % q.length;
        size++;
        // END STRIP
    }

    /**
     * Removes and return the element at the head of the queue.
     * Resize the array if needed.
     * @return The item freshly removed.
     * @throws NoSuchElementException when the queue is empty.
     */
    public Item dequeue() throws NoSuchElementException{
        // TODO
        // STUDENT return null;
        // BEGIN STRIP
        nOp++;
        if (isEmpty()) throw new NoSuchElementException();

        Item val = q[head];
        q[head] = null;
        head = (head + 1) % q.length;
        size--;

        if (size > 0 && size == q.length / 4) {
            resize(q.length / 2);
        }

        return val;
        // END STRIP
    }

    /**
     * Returns an iterator that iterates through the items in FIFO order.
     * @return an iterator that iterates through the items in FIFO order.
     */
    @Override
    public Iterator<Item> iterator() {
        // TODO
        // STUDENT return null;
        // BEGIN STRIP
        return new QueueIterator();
        // END STRIP
    }

    // BEGIN STRIP
    private class QueueIterator implements Iterator<Item> {
        private final long nOp = getnOp();
        private int currentHead = head;

        @Override
        public boolean hasNext() {
            return currentHead != tail;
        }

        @Override
        public Item next() {
            if (nOp != getnOp()) throw new ConcurrentModificationException();
            Item current = q[currentHead];
            currentHead = (currentHead + 1) % q.length;
            return current;
        }
    }
    // END STRIP
}
