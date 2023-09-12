package sorting;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;

@Grade
public class MinPQLinkedTest {

    public static Comparator<Integer> comparator = Comparator.naturalOrder();

    private static boolean greater(MinPQLinked<Integer>.Node i, MinPQLinked<Integer>.Node j) {
        return comparator.compare(i.value,j.value) > 0;
    }

    public static boolean isMinHeapOrdered(MinPQLinked<Integer>.Node n) {
            if (n == null || (n.left == null && n.right == null)) return true;
            if (n.left != null && greater(n, n.left))  return false;
            if (n.right != null && greater(n, n.right)) return false;
            return isMinHeapOrdered(n.left) && isMinHeapOrdered(n.right);
    }


    public static boolean isEssentiallyComplete(MinPQLinked<Integer>.Node n) {
        boolean [] visited = new boolean[n.size+1];
        collect(n,visited,1);
        for (int i = 1; i <= n.size ; i++) {
            if (!visited[i]) return false;
        }
        return true;
    }

    private static void collect(MinPQLinked<Integer>.Node n, boolean [] visited, int index) {
       visited[index] = true;
       if (n.left  != null) collect(n.left, visited, index*2);
       if (n.right != null) collect(n.right, visited, index*2+1);
    }




    @Test
    @Grade(value=1, cpuTimeout=1000)
    public void testDeleteMin0() {
        MinPQLinked<Integer> pq = new MinPQLinked<Integer>(Comparator.naturalOrder());
        pq.insert(-20);
        pq.insert(-15);
        pq.insert(-18);
        pq.insert(-14);
        pq.insert(-11);
        pq.insert(-10);
        pq.insert(-9);
        pq.insert(-8);
        pq.insert(-13);
        pq.insert(-5);
        pq.insert(-2);
        pq.insert(-5);
        pq.insert(-8);

        assertEquals(13, (int) pq.size());
        assertTrue(isMinHeapOrdered(pq.root));
        assertTrue(isEssentiallyComplete(pq.root));

        assertEquals(-20, (int) pq.delMin());

        assertEquals(-18, (int) pq.delMin());

        assertTrue(isMinHeapOrdered(pq.root));
        assertTrue(isEssentiallyComplete(pq.root));

        assertEquals(11, (int) pq.size());

        assertEquals(-15, (int) pq.delMin());
        assertEquals(-14, (int) pq.delMin());

        assertEquals(9, (int) pq.size());

        assertEquals(-13, (int) pq.delMin());

        assertEquals(-11, (int) pq.delMin());
        assertEquals(-10, (int) pq.delMin());

        assertTrue(isMinHeapOrdered(pq.root));
        assertTrue(isEssentiallyComplete(pq.root));

        assertEquals(-9, (int) pq.delMin());
        assertEquals(-8, (int) pq.delMin());
        assertEquals(-8, (int) pq.delMin());
        assertEquals(-5, (int) pq.delMin());
        assertEquals(-5, (int) pq.delMin());
        assertEquals(-2, (int) pq.delMin());
    }

    @Test
    @Grade(value=1, cpuTimeout=1000)
    public void testDeleteMin1() {
        MinPQLinked<Integer> pq = new MinPQLinked<Integer>(Comparator.naturalOrder());
        pq.insert(2);
        pq.insert(3);
        pq.insert(1);
        pq.insert(4);
        pq.insert(0);
        pq.insert(6);
        pq.insert(7);
        pq.insert(-1);

        assertEquals(8,(int) pq.size());

        assertEquals(-1,(int) pq.delMin());
        assertEquals(0,(int) pq.delMin());

        assertEquals(6,(int) pq.size());

        assertEquals(1,(int) pq.delMin());
        assertEquals(2,(int) pq.delMin());
        assertEquals(3,(int) pq.delMin());

        assertEquals(3,(int) pq.size());
        assertTrue(isMinHeapOrdered(pq.root));
        assertTrue(isEssentiallyComplete(pq.root));

        assertEquals(4,(int) pq.delMin());
        assertEquals(6,(int) pq.delMin());
        assertEquals(7,(int) pq.delMin());

        assertEquals(0,(int) pq.size());


    }

    @Test
    @Grade(value=1, cpuTimeout=1000)
    public void testDeleteMin2() {
        MinPQLinked<Integer> pq = new MinPQLinked<Integer>(Comparator.naturalOrder());
        pq.insert(2);
        pq.insert(3);
        pq.insert(1);
        pq.insert(4);
        pq.insert(0);
        pq.insert(6);
        pq.insert(7);
        pq.insert(-1);

        assertEquals(8,(int) pq.size());

        assertEquals(-1,(int) pq.delMin());
        assertEquals(0,(int) pq.delMin());
        assertTrue(isMinHeapOrdered(pq.root));
        assertEquals(6,(int) pq.size());

        assertEquals(1,(int) pq.delMin());
        assertEquals(2,(int) pq.delMin());
        assertTrue(isMinHeapOrdered(pq.root));
        assertTrue(isEssentiallyComplete(pq.root));
        assertEquals(3,(int) pq.delMin());

        assertEquals(3,(int) pq.size());
        assertTrue(isMinHeapOrdered(pq.root));
        assertTrue(isEssentiallyComplete(pq.root));
        assertEquals(4,(int) pq.delMin());
        assertEquals(6,(int) pq.delMin());
        assertEquals(7,(int) pq.delMin());

        assertEquals(0,(int) pq.size());

    }


    // BEGIN STRIP
    @Test
    @Grade(value=1, cpuTimeout=1000)
    public void testDeleteMin3() {
        MinPQLinked<Integer> pq = new MinPQLinked<Integer>(Comparator.naturalOrder());
        pq.insert(1);
        assertTrue(isMinHeapOrdered(pq.root));
        assertEquals(1,(int) pq.size());

        assertEquals(1,(int) pq.delMin());

        assertEquals(0,(int) pq.size());

    }

    @Test
    @Grade(value=1, cpuTimeout=1000)
    public void testDeleteMin4() {
        MinPQLinked<Integer> pq = new MinPQLinked<Integer>(Comparator.naturalOrder());
        pq.insert(1);
        pq.insert(2);
        assertTrue(isMinHeapOrdered(pq.root));
        assertTrue(isEssentiallyComplete(pq.root));
        assertEquals(2,(int) pq.size());

        assertEquals(1,(int) pq.delMin());
        assertEquals(2,(int) pq.delMin());

        assertEquals(0,(int) pq.size());

    }

    @Test
    @Grade(value=1, cpuTimeout=1000)
    public void testDeleteMin5() {
        MinPQLinked<Integer> pq = new MinPQLinked<Integer>(Comparator.naturalOrder());
        pq.insert(1);
        pq.insert(5);
        pq.insert(7);
        assertTrue(isMinHeapOrdered(pq.root));
        assertTrue(isEssentiallyComplete(pq.root));
        assertEquals(3,(int) pq.size());

        assertEquals(1,(int) pq.delMin());
        assertEquals(2,(int) pq.size());
        assertTrue(isMinHeapOrdered(pq.root));
        assertTrue(isEssentiallyComplete(pq.root));
        assertEquals(5,(int) pq.delMin());
        assertEquals(7,(int) pq.delMin());
        assertEquals(0,(int) pq.size());
    }

    @Test
    @Grade(value=1, cpuTimeout=1000)
    public void testDeleteMin6() {
        MinPQLinked<Integer> pq = new MinPQLinked<Integer>(Comparator.naturalOrder());
        pq.insert(7);
        pq.insert(5);
        pq.insert(1);

        assertTrue(isMinHeapOrdered(pq.root));
        assertEquals(3,(int) pq.size());

        assertEquals(1,(int) pq.delMin());
        assertEquals(2,(int) pq.size());
        assertTrue(isMinHeapOrdered(pq.root));
        assertEquals(5,(int) pq.delMin());
        assertEquals(7,(int) pq.delMin());
        assertEquals(0,(int) pq.size());
    }

    @Test
    @Grade(value=1, cpuTimeout=1000)
    public void testDeleteMin7() {
        MinPQLinked<Integer> pq = new MinPQLinked<Integer>(Comparator.naturalOrder());
        pq.insert(-1);
        pq.insert(1);
        pq.insert(2);
        pq.insert(3);
        pq.insert(4);
        pq.insert(5);
        pq.insert(6);
        pq.insert(7);

        assertEquals(8,(int) pq.size());

        assertEquals(-1,(int) pq.delMin());
        assertTrue(isMinHeapOrdered(pq.root));
        assertEquals(1,(int) pq.delMin());
        assertTrue(isMinHeapOrdered(pq.root));
        assertEquals(6,(int) pq.size());

        assertEquals(2,(int) pq.delMin());
        assertEquals(3,(int) pq.delMin());
        assertTrue(isMinHeapOrdered(pq.root));
        assertEquals(4,(int) pq.delMin());

        assertEquals(3,(int) pq.size());
        assertTrue(isMinHeapOrdered(pq.root));
        assertEquals(5,(int) pq.delMin());
        assertEquals(6,(int) pq.delMin());
        assertEquals(7,(int) pq.delMin());

        assertEquals(0,(int) pq.size());
    }

    @Test
    @Grade(value=1, cpuTimeout=1000)
    public void testDeleteMin8() {
        MinPQLinked<Integer> pq = new MinPQLinked<Integer>(Comparator.naturalOrder());
        pq.insert(2);
        pq.insert(3);
        pq.insert(1);
        pq.insert(4);
        pq.insert(0);
        pq.insert(6);
        pq.insert(8);
        pq.insert(-11);

        assertEquals(8,(int) pq.size());

        assertEquals(-11,(int) pq.delMin());
        assertEquals(0,(int) pq.delMin());
        assertTrue(isMinHeapOrdered(pq.root));
        assertEquals(6,(int) pq.size());

        assertEquals(1,(int) pq.delMin());
        assertEquals(2,(int) pq.delMin());
        assertTrue(isMinHeapOrdered(pq.root));
        assertEquals(3,(int) pq.delMin());

        assertEquals(3,(int) pq.size());
        assertTrue(isMinHeapOrdered(pq.root));
        assertEquals(4,(int) pq.delMin());
        assertEquals(6,(int) pq.delMin());
        assertEquals(8,(int) pq.delMin());

        assertEquals(0,(int) pq.size());
    }

    // END STRIP

}

