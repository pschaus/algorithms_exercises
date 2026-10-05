package sorting;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * You are asked to implement a merge sort on a singly linked list of integers.
 *
 * The list is made of inner Node objects, each holding a value and a reference to the next node.
 * The method sort() must sort the list in non-decreasing order, in place, by relinking the nodes
 * (no new node must be created and no array or java.util collection may be used).
 *
 * The sort must be a merge sort running in O(n log n) time and must be stable:
 * equal elements keep their relative order.
 * The recursion depth must remain O(log n).
 *
 * You must implement:
 *  - sort(): sorts the list using merge sort
 *  - mergeSort(Node head): sorts the chain starting at head and returns the new head
 *  - merge(Node a, Node b): merges two sorted chains and returns the head of the merged chain
 */
public class SortLinkedList implements Iterable<Integer> {

    static class Node {
        int value;
        Node next;

        Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    protected Node head;
    protected int size;

    /**
     * Adds a value at the beginning of the list in O(1).
     */
    public void addFirst(int value) {
        head = new Node(value, head);
        size++;
    }

    public int size() {
        return size;
    }

    /**
     * Sorts this list in non-decreasing order with a stable merge sort.
     */
    public void sort() {
        // TODO
        // BEGIN STRIP
        head = mergeSort(head);
        // END STRIP
    }

    /**
     * Sorts the chain of nodes starting at head.
     *
     * @param head the first node of the chain (possibly null)
     * @return the first node of the sorted chain
     */
    protected Node mergeSort(Node head) {
        // TODO
        // BEGIN STRIP
        if (head == null || head.next == null) {
            return head;
        }
        // find the end of the first half
        Node slow = head;
        Node fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node second = slow.next;
        slow.next = null;
        return merge(mergeSort(head), mergeSort(second));
        // END STRIP
    }

    /**
     * Merges two sorted chains into one sorted chain, stable: on ties, nodes of a come first.
     *
     * @param a the first node of a sorted chain (possibly null)
     * @param b the first node of a sorted chain (possibly null)
     * @return the first node of the merged chain
     */
    protected Node merge(Node a, Node b) {
        // TODO
        // BEGIN STRIP
        Node dummy = new Node(0, null);
        Node tail = dummy;
        while (a != null && b != null) {
            if (a.value <= b.value) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
        // END STRIP
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Integer next() {
                if (current == null) throw new NoSuchElementException();
                int v = current.value;
                current = current.next;
                return v;
            }
        };
    }
}
