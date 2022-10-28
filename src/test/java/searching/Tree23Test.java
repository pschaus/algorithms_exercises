package searching;

import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;

public class Tree23Test
{
    @Test
    public void testSimple()
    {

        Tree23 t = new Tree23();
        for (int i = 0; i < 10; i++) {
            t.put(i, (double) i);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals((double) t.search(i), (double) i, 0.0001);
        }

        assertEquals(t.search(11) , null);
    }

    @Test
    public void testSimple2()
    {

        Tree23 t = new Tree23();
        for (int i = 10; i >= 0; i--) {
            t.put(i, (double) i);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals((double) t.search(i), (double) i, 0.0001);
        }

        assertEquals(t.search(11) , null);
    }

    @Test
    public void isBalanced(){
        Tree23 t = new Tree23();
        for (int i = 0; i < 1000; i++) {
            t.put(i, (double) i);
        }
        assertEquals(t.isBalanced(), true);
    }

    @Test
    public void isBst(){
        Tree23 t = new Tree23();
        for (int i = 0; i < 1000; i++) {
            t.put(i, (double) i);
        }
        assertEquals(t.isBst(), true);
    }

    @Test
    public void randomTest(){
        Tree23 t = new Tree23();
        HashMap<Integer, Double> correct = new HashMap<>();
        Random rng = new Random();
        for (int i = 0; i < 1000; i++) {
            int rand = rng.nextInt(5000);
            if (correct.containsKey(rand)) continue;
            t.put(i, (double)i);
            correct.put(i, (double) i);
        }

        for (Integer i : correct.keySet()){
            assertEquals((double) t.search(i), (double) i, 0.001); // check if all keys are present
        }

        for (int i = 0; i < 1000; i++) {
            int rand = rng.nextInt(5000);
            assertEquals(t.search(rand), correct.get(rand));
        }
    }
}
