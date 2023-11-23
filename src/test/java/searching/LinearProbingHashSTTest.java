package searching;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

// BEGIN STRIP
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.HashMap;
//END STRIP



@Grade
public class LinearProbingHashSTTest {

    // BEGIN STRIP
    private static final Random random = new Random(582);
    //END STRIP

    @Test
    @GradeFeedback(message="Sorry, something is wrong with your algorithm. Debug first on this small example")
    public void testExample() {
        LinearProbingHashST<Integer,String> lp = new LinearProbingHashST<>();

        assertEquals(4,lp.capacity());
        assertEquals(0,lp.size());

        lp.put(5, "five");
        assertEquals("five",lp.get(5));
        assertEquals(1,lp.size());

        lp.put(9,"nine");
        assertEquals("nine",lp.get(9));
        assertEquals(2,lp.size());

        lp.put(9,"neuf");
        assertEquals("neuf",lp.get(9));
        assertEquals(2,lp.size());

        lp.put(8,"huit");
        assertEquals("huit",lp.get(8));
        assertEquals(3,lp.size());
        assertEquals(8,lp.capacity());


        lp.put(0,"zero");
        assertEquals("zero",lp.get(0));
        assertEquals(4,lp.size());
        assertEquals(8,lp.capacity());


        lp.put(16,"sixteen");
        assertEquals("sixteen",lp.get(16));
        assertEquals(5,lp.size());
        assertEquals(16,lp.capacity());


        assertTrue(lp.contains(5));
        assertTrue(lp.contains(9));


        assertFalse(lp.contains(4));
        assertFalse(lp.contains(32));
        assertFalse(lp.contains(64));
    }

    // BEGIN STRIP
    @Test
    @Grade(value=1, cpuTimeout = 1000)
    @GradeFeedback(message="You do not raise a IllegalArgumentException when we try to put an element with a null key")
    @Order(1)
    public void testPutNullKey() {
        LinearProbingHashST<Integer, String> lp = new LinearProbingHashST<>();
        lp.put(1,"test");
        lp.put(2,"test");
        assertThrows(IllegalArgumentException.class, () -> lp.put(null, "test"));
    }

    @Test
    @Grade(value=1, cpuTimeout = 1000)
    @GradeFeedback(message="You do not raise a IllegalArgumentException when we try to get an element with a null key")
    @Order(1)
    public void testGetNullKey() {
        LinearProbingHashST<Integer, String> lp = new LinearProbingHashST<>();
        lp.put(1,"test");
        lp.put(2,"test");
        assertThrows(IllegalArgumentException.class, () -> lp.get(null));
    }

    static Stream<Arguments> dataProvider() {
        return IntStream.range(0, 50).mapToObj(i -> {
            int size = 10;
            LinearProbingHashST<Integer,Integer> lp = new LinearProbingHashST<>(size);
            HashMap<Integer, Integer> map = new HashMap<>(size);
            for (int k = 0; k < size/2; k++) {
                int v = random.nextInt(100);
                lp.put(v,v);
                map.put(v,v);
            }
            return Arguments.of((Object) new LinearProbingHashSTTest.Instance(lp, map, size));
        });
    }

    @Grade(value=1)
    @ParameterizedTest
    @MethodSource("dataProvider")
    @GradeFeedback(message = "Your get method does not return the good value")
    @Order(1)
    public void testRandomGet(Instance instance) {
        for (int i = 0; i < 5; i++) {
            int key = random.nextInt(instance.size/2);
            assertEquals(instance.map.get(key), instance.lp.get(key));
        }
    }

    @Grade(value=1)
    @ParameterizedTest
    @MethodSource("dataProvider")
    @GradeFeedback(message = "Something is wrong with your put method")
    @Order(1)
    public void testRandomPut(Instance instance) {
        for (int i = 0; i < 5; i++) {
            int key = instance.size+i;
            int v = random.nextInt();
            instance.lp.put(key, v);
            instance.map.put(key, v);
        }
        for (int i = 0; i < 5; i++) {
            int key = random.nextInt(instance.size/2 + 5);
            assertEquals(instance.map.get(key), instance.lp.get(key));
        }
    }

    static Stream<Instance> dataProviderComplexity() {
        return IntStream.range(0, 5).mapToObj(i -> {
            int size = 10000;
            LinearProbingHashST<Integer,Integer> lp = new LinearProbingHashST<>(size);
            HashMap<Integer, Integer> map = new HashMap<>(size);
            for (int k = 0; k < size/2; k++) {
                int v = random.nextInt();
                lp.put(v,v);
                map.put(v,v);
            }
            return new Instance(lp, map, size);
        });
    }

    @Grade(value=1, cpuTimeout = 100)
    @ParameterizedTest
    @MethodSource("dataProviderComplexity")
    @GradeFeedback(message = "Check the complexity of your get method")
    @Order(1)
    public void testRandomGetComplexity(Instance instance) {
        for (int i = 0; i < 100; i++) {
            int key = random.nextInt(instance.size/2);
            assertEquals(instance.map.get(key), instance.lp.get(key));
        }
    }

    @Grade(value=1, cpuTimeout = 100)
    @ParameterizedTest
    @MethodSource("dataProviderComplexity")
    @GradeFeedback(message = "Check the complexity of your put method")
    @Order(1)
    public void testRandomPutComplexity(Instance instance) {
        for (int i = 0; i < 100; i++) {
            int key = instance.size+i;
            int v = random.nextInt();
            instance.lp.put(key, v);
        }
    }


    private static class Instance {
        LinearProbingHashST<Integer, Integer> lp;
        HashMap<Integer, Integer> map;
        int size;

        public Instance(LinearProbingHashST<Integer, Integer> lp, HashMap<Integer, Integer> map, int size) {
            this.lp = lp;
            this.map = map;
            this.size = size;
        }

    }
    // END STRIP
    
}
