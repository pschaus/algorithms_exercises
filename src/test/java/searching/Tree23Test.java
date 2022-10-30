package searching;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;
import org.junit.runners.Parameterized;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

@RunWith(Enclosed.class)
public class Tree23Test {

    public static class TestNotParameterized {

        @Test
        @Grade(value = 5, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Debug first on this small example", onFail = true)
        public void testSimple() {

            Tree23 t = new Tree23();
            for (int i = 0; i < 10; i++) {
                t.put(i, (double) i);
            }
            for (int i = 0; i < 10; i++) {
                Double temp = t.search(i);
                assertEquals((double) temp, (double) i, 0.0001);
            }

            assertEquals(t.search(11), null);
        }

        @Test
        @Grade(value = 5, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Debug first on this small example", onFail = true)
        public void testSimple2() {

            Tree23 t = new Tree23();
            for (int i = 10; i >= 0; i--) {
                t.put(i, (double) i);
            }
            for (int i = 0; i < 10; i++) {
                assertEquals((double) t.search(i), (double) i, 0.0001);
            }

            assertEquals(t.search(11), null);
        }

        @Test
        @Grade(value = 30, cpuTimeout = 2000)
        @GradeFeedback(message = "Your tree isn't balanced when inserting keys", onFail = true)
        public void isBalanced() {
            Tree23 t = new Tree23();
            for (int i = 0; i < 1000; i++) {
                t.put(i, (double) i);
            }
            assertEquals(t.isBalanced(), true);
        }

        @Test
        @Grade(value = 30, cpuTimeout = 2000)
        @GradeFeedback(message = "The keys in your tree aren't in the good place", onFail = true)
        public void isBst() {
            Tree23 t = new Tree23();
            for (int i = 0; i < 1000; i++) {
                t.put(i, (double) i);
            }
            assertEquals(t.isBst(), true);
        }

        @Test
        @Grade(value = 30, cpuTimeout = 2000)
        @GradeFeedback(message = "There is an error in your tree when inserting random keys", onFail = true)
        public void randomTest() {
            Tree23 t = new Tree23();
            HashMap<Integer, Double> correct = new HashMap<>();
            Random rng = new Random();
            for (int i = 0; i < 1000; i++) {
                int rand = rng.nextInt(5000);
                if (correct.containsKey(rand)) continue;
                t.put(i, (double) i);
                correct.put(i, (double) i);
            }

            for (Integer i : correct.keySet()) {
                assertEquals((double) t.search(i), (double) i, 0.001); // check if all keys are present
            }

            for (int i = 0; i < 1000; i++) {
                int rand = rng.nextInt(5000);
                assertEquals(t.search(rand), correct.get(rand));
            }
        }
    }
}


