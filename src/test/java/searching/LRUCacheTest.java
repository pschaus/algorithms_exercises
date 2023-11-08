package searching;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;



@Grade
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LRUCacheTest {


    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    @Order(0)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm, debug first this small example")
    public void testDebug() {

        LRUCache<Character, Integer> cache = new LRUCache<>(3);
        //  Example of an LRU cache with capacity of 3:
        // step 0:
        cache.put('A',7);  // map{(A,7)}  A is the LRU
        // step 1:
        cache.put('B',10); // map{(A,7),(B,10)}  A is the LRU
        // step 2:
        cache.put('C',5);  // map{(A,7),(B,10),(C,5)}  A is the LRU
        // step 3:
        cache.put('D',8);  // map{(B,10),(C,5),(D,8)}  A is suppressed, B is the LRU
        // step 4:
        assertNull(cache.get('A'));
        assertEquals(Integer.valueOf(10),cache.get('B'));    // C is the LRU
        // step 5
        cache.put('E',9);  // map{(B,10),(D,8),(E,9)} D is the LRU
        assertNull(cache.get('C'));
        // step 6
        cache.put('D',3);  // map{(B,10),(D,3),(E,9)} B is the LRU

        // step 7
        cache.get('B');    // map{(B,10),(D,3),(E,9)} E is the LRU
        // step 8
        cache.put('A',2);  // map{(B,10),(D,3),(A,2)} D is the LRU
        assertNull(cache.get('E'));
        assertNull(cache.get('C'));
        assertEquals(Integer.valueOf(2),cache.get('A'));
        assertEquals(Integer.valueOf(3),cache.get('D'));
        assertEquals(Integer.valueOf(10),cache.get('B'));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    @Order(0)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm, debug first this small example")
    public void secondDebugTest() {
        LRUCache<Integer, String> cache = new LRUCache<>(4);
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        cache.put(4, "d");
        cache.put(5, "e");
        cache.get(2);
        cache.put(6, "f");
        assertNull(cache.get(1));
        assertEquals("b", cache.get(2));
        assertNull(cache.get(3));
        assertEquals("d", cache.get(4));
        assertEquals("e", cache.get(5));
        assertEquals("f", cache.get(6));
    }


    // BEGIN STRIP

    @Test
    @Grade(value = 2, cpuTimeout = 1000)
    @Order(1)
    @GradeFeedback(message = "Testing correctness")
    public void testCorrectness() {
        Random r = new Random(0);
        LRUCache<Integer, String> cache = new LRUCache<>(5);
        LRUCacheRef<Integer,String> cacheRef = new LRUCacheRef<>(5);

        for (int i = 0; i < 100000; i++) {
            int nextKey = r.nextInt(10);
            String nextVal = ""+r.nextInt(10);
            cache.put(nextKey,nextVal);
            cacheRef.put(nextKey,nextVal);
            if (i % 10 == 0) {
                Integer nextKey1 = r.nextInt(10);
                assertEquals(cacheRef.get(nextKey1),cache.get(nextKey1));
            }
        }

    }


    @Test
    @Grade(value = 3, cpuTimeout = 1000)
    @GradeFeedback(message = "Testing time-complexity and correctness")
    @Order(2)
    public void testTimeComplexityAndCorrectness() {

        {
            Random r = new Random(0);
            LRUCache<Integer, String> cache = new LRUCache<>(5);
            LRUCacheRef<Integer,String> cacheRef = new LRUCacheRef<>(5);

            for (int i = 0; i < 100000; i++) {
                int nextKey = r.nextInt(10);
                String nextVal = ""+r.nextInt(10);
                cache.put(nextKey,nextVal);
                cacheRef.put(nextKey,nextVal);
                if (i % 10 == 0) {
                    Integer nextKey1 = r.nextInt(10);
                    assertEquals(cacheRef.get(nextKey1),cache.get(nextKey1));
                }
            }
        }

        int n = 100000;
        ArrayList<Integer> perm = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            perm.add(i);
        }
        Collections.shuffle(perm);

        // add the n elements
        LRUCache<Integer, Integer> cache = new LRUCache<>(n/2);
        for (int i = 0; i < n; i++) {
            cache.put(perm.get(i),i);
        }
        // since the capacity is n/2, the first n/2 should not be present
        for (int i = 0; i < n/2; i++) {
            assertNull(cache.get(perm.get(i)));
        }
        // but the last n/2 should be present
        for (int i = n-1; i >= n/2; i--) {
            assertEquals(Integer.valueOf(i), cache.get(perm.get(i)));
        }
        for (int i = n/2; i <n ; i++) {
            assertEquals(Integer.valueOf(i), cache.get(perm.get(i)));
        }
        for (int i = n/2; i <n ; i++) {
            assertEquals(Integer.valueOf(i), cache.get(perm.get(i)));
        }

    }


    public static class LRUCacheRef<K,V> {

        private int capacity;

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


        public LRUCacheRef(int capacity) {
            this.capacity = capacity;
        }

        public V get(K key) {
            if (!map.containsKey(key)) {
                return null;
            }

            // remove the node from the linked list
            Node node = map.get(key);
            remove(node);

            // add the node to the front of the linked list
            addToFront(node);

            return node.value;
        }

        public void put(K key, V value) {
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
        }

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
    }
    // END STRIP

}
