package searching;
// BEGIN STRIP
import java.util.HashMap;
import java.util.LinkedList;
// END STRIP

/**
 * We are interested in the implementation of an LRU cache,
 * i.e. a (hash)-map of limited capacity where the addition of
 * a new entry might induce the suppression of the Least Recently Used (LRU)
 * entry if the maximum capacity is exceeded.
 *
 * Your LRU cache implements the same two methods as a MAP :
 * - put(key, elem) inserts the given element in the cache,
 *      this element becomes the most recently used element
 *      and if needed (the cache is full and the key not yet present),
 *      the least recently used element is removed.
 * - get(key) returns the entry with the given key from the cache,
 *      this element becomes the most recently used element
 *
 * These methods need to be implemented with an expected time complexity of O(1).
 * You are free to choose the type of data structure that you consider
 * to best support this cache. You can also use data-structures from Java.
 *
 * Hint for your implementation:
 *       Use a doubly linked list to store the elements from the least
 *       recently used (head) to the most recently used (tail).
 *       If needed the element to suppress is the head of the list.
 *
 *       Use java.util.HashMap with the <key,node> where node is a reference to the node
 *       in the doubly linked list.
 *
 *       Of course, at every put/get the list will need to be updated so that
 *       the "accessed node" is placed at the end of the list.
 *
 *       Feel free to use existing java classes.
 *
 *  Example of usage of an LRU cache with capacity of 3:
 *  // step 0:
 *  put(A,7)  // map{(A,7)}  A is the LRU
 *  // step 1:
 *  put(B,10) // map{(A,7),(B,10)}  A is the LRU
 *  // step 2:
 *  put(C,5)  // map{(A,7),(B,10),(C,5)}  A is the LRU
 *  // step 3:
 *  put(D,8)  // map{(B,10),(C,5),(D,8)}  A is suppressed, B is the LRU
 *  // step 4:
 *  get(B)    // C is the LRU
 *  // step 5
 *  put(E,9)  // map{(B,10),(D,8),(E,9)} D is the LRU
 *  // step 6
 *  put(D,3)  // map{(B,10),(D,3),(E,9)} B is the LRU
 *  // step 7
 *  get(B)    // map{(B,10),(D,3),(E,9)} E is the LRU
 *  // step 8
 *  put(A,2)  // map{(B,10),(D,3),(A,2)} D is the LRU
 *
 *  Feel free to use existing java classes from Java
 */
public class LRUCache<K,V> {

    private int capacity;

    // BEGIN STRIP
    private class Node {
        K key;
        V value;
        Node prev;
        Node next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


    private HashMap<K, Node> map = new HashMap<>();
    private Node head = null; // the MRU (most recently used)
    private Node tail = null; // the LRU (least recently used)

    // END STRIP

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public V get(K key) {
        // STUDENT return null;
        // BEGIN STRIP
        if (!map.containsKey(key)) {
            return null;
        }

        // remove the node from the linked list
        Node node = map.get(key);
        remove(node);

        // add the node to the front of the linked list
        addToFront(node);

        return node.value;
        // END STRIP
    }

    public void put(K key, V value) {
        // BEGIN STRIP
        if (map.containsKey(key)) {
            // update the value of the existing node
            Node node = map.get(key);
            node.value = value;

            // move the node to the front of the linked list
            remove(node);
            addToFront(node);
        } else {
            // create a new node
            Node node = new Node(key, value);

            // add the node to the front of the linked list
            addToFront(node);

            // add the node to the map
            map.put(key, node);

            // if the capacity is reached, remove the least recently used element
            if (map.size() > capacity) {
                removeLRU();
            }
        }
        // END STRIP
    }

    // BEGIN STRIP
    private void remove(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    private void addToFront(Node node) {
        node.next = head;
        node.prev = null;

        if (head != null) {
            head.prev = node;
        }

        head = node;

        if (tail == null) {
            tail = head;
        }
    }

    private void removeLRU() {
        map.remove(tail.key);
        remove(tail);
    }

    // END STRIP
}
