package fundamentals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayQueue<Item> implements Iterable<Item> {

    public Item[] q;
    private int head;
    private int tail;
    private int size;
    // BEGIN STRIP
    private long nOp = 0;
    // END STRIP

    @SuppressWarnings("unchecked")
    public ResizingArrayQueue(int capacity) {
        q = (Item[]) new Object[capacity];
        // BEGIN STRIP
        head = 0;
        tail = 0;
        size = 0;
        nOp = 0;
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

    public Item dequeue() {
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
