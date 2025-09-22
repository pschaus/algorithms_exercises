package fundamentals;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Proposed at the exam of August 2025
 * You are asked to implement a specialized linked chain data structure
 * optimized for high-performance insertions.
 *
 * This chain consists of n elements numbered from 0 to n-1.
 * Elements 0 and n-1 are always present in the chain and cannot be removed or relocated.
 * No elements may be inserted before 0 or after n-1.
 * All other elements start unlinked and can be inserted exactly once.
 *
 * To ensure constant-time operations, the chain is represented internally using two arrays:
 * 	successor[i] and predecessor[i] represent the immediate next and
 * 	previous elements of element i in the chain.
 * 	If the element i is not yet part of the chain, we have successor[i] = predecessor[i] = i.
 *  By convention, predecessor[0] == 0 and successor[n - 1] == n - 1.
 *
 * When the data structure is initialized with n elements,
 * the chain contains exactly two linked elements: 0 <-> n-1.
 *
 * You must implement the following methods:
 * 	-	insertAfter(a, b): Insert element b immediately after element a in the chain.
 * 	-	insertBefore(a, b): Insert element b immediately before element a in the chain.
 * 	-   iterator() that enables to iterate over the elements of the chain in their current order,
 * 	    from 0 to n-1.
 */
public class LinkedChain implements Iterable<Integer> {

    protected int[] successor;
    protected int[] predecessor;

    /**
     * Constructs a linked chain with n elements.
     * The chain is initialized with two linked elements: 0 <-> n-1.
     * The other elements (1 to n-2) are not yet in the chain.
     * @param n > 1 the capacity of the chain
     */
    public LinkedChain(int n) {
        // TODO
        // BEGIN STRIP
        if (n <= 1) {
            throw new IllegalArgumentException("The number must be greater than 1.");
        }
        successor = new int[n];
        predecessor = new int[n];

        // Initialize the arrays
        for (int i = 0; i < n; i++) {
            successor[i] = i; // Not in the list
            predecessor[i] = i; // Not in the list
        }

        // Add initial nodes 0 and n-1
        successor[0] = n - 1;
        predecessor[n - 1] = 0;
        // END STRIP
    }


    /**
     * Inserts an element just after another one in the chain
     * @param a the element after which b will be inserted,
     *        "a" must be in the chain and must be different from n-1 (last element)
     * @param b the element to insert, it must not be in the chain
     * @throws IllegalArgumentException if "a" is not in the chain,
     *         or if "b" is already in the chain or if "a" is the last
     *         or if "a" or "b" are not in the range [0, n-1]
     */
    public void insertAfter(int a, int b) {
        // TODO
        // BEGIN STRIP
        if (a < 0 || a >= successor.length || b < 0 || b >= successor.length) {
            throw new IllegalArgumentException("Elements must be in the range [0, n-1]");
        }
        if (successor[a] == a) {
            throw new IllegalArgumentException(a + " is not in the chain!");
        }
        if (successor[b] != b || predecessor[b] != b) {
            throw new IllegalArgumentException(b + " is already in the chain!");
        }
        if (a == successor.length - 1) {
            throw new IllegalArgumentException(a + " cannot be n-1!");
        }
        int c = successor[a];
        // a <-> b <-> c
        successor[a] = b;
        predecessor[b] = a;
        successor[b] = c;
        predecessor[c] = b;
        // END STRIP
    }


    /**
     * Inserts element "b" just before element "a".
     * @param a the element before which "b" should be inserted,
     *        "a" must be in the chain and must be different from 0 (first element)
     * @param b the element to insert, it must not be in the chain
     * @throws IllegalArgumentException if "a" is not in the chain,
     *        or if "b" is already in the chain or if "a" is the first
     *        or if "a" or "b" are not in the range [0, n-1]
     */
    public void insertBefore(int a, int b) {
        // TODO
        // BEGIN STRIP
        if (a < 0 || a >= successor.length || b < 0 || b >= successor.length) {
            throw new IllegalArgumentException("Elements must be in the range [0, n-1]");
        }
        if (predecessor[a] == a) {
            throw new IllegalArgumentException(a + " is not in the chain!");
        }
        if (successor[b] != b || predecessor[b] != b) {
            throw new IllegalArgumentException(b + " is already in the chain!");
        }
        if (a == 0) {
            throw new IllegalArgumentException(a + " cannot be 0!");
        }
        int c = predecessor[a];
        // c <-> b <-> a
        successor[c] = b;
        predecessor[b] = c;
        successor[b] = a;
        predecessor[a] = b;
        // END STRIP
    }



    /**
     * Iterator over the chain from 0 to n-1 in the order they are connected.
     * @return an iterator over the chain, in the order they are connected,
     *         starting from the first i.e. 0 and ending with the last i.e. n-1
     */
    @Override
    public Iterator<Integer> iterator() {
        // TODO
        // STUDENT return null;
        // BEGIN STRIP
        return new ChainIterator();
        // END STRIP
    }

    // BEGIN STRIP
    private class ChainIterator implements Iterator<Integer> {
        private int current = 0; // Start at the first
        private final int end = successor.length - 1; // End at N-1
        boolean endReached = false;

        @Override
        public boolean hasNext() {
            return !endReached;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the chain.");
            }
            int value = current;
            if (current == end) {
                endReached = true;
            }
            current = successor[current]; // Move to the next
            return value;
        }
    }
    // END STRIP
}
