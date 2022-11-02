package searching;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.github.guillaumederval.javagrading.Grade;
import org.junit.runners.Parameterized;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.*;
import java.io.IOException;


@RunWith(Enclosed.class)
public class BinarySearchTreeHigherTest {
    public static class TestNotParameterized {
        public BinarySearchTreeHigher<Integer, Integer> getExample() {

            BinarySearchTreeHigher<Integer, Integer> bst = new BinarySearchTreeHigher<Integer, Integer>();

            bst.put(12,12);
            bst.put(8,8);
            bst.put(3,3);
            bst.put(11,11);
            bst.put(9,9);
            bst.put(18,18);
            bst.put(14,14);
            bst.put(15,15);
            bst.put(20,20);

            return bst;
        }


        @Test
        @Grade(value=5)
        public void testMinKeyExample() {
            BinarySearchTreeHigher<Integer, Integer> bst = getExample();
            assertEquals(Integer.valueOf(3), bst.minKey());

        }

        @Test
        @Grade(value=10)
        public void testHigherExample() {
            BinarySearchTreeHigher<Integer, Integer> bst = getExample();
            assertEquals(Integer.valueOf(3), bst.higherKey(0));
            assertEquals(Integer.valueOf(9), bst.higherKey(8));
            assertEquals(Integer.valueOf(12), bst.higherKey(11));
            assertEquals(Integer.valueOf(18), bst.higherKey(16));
            assertEquals(null, bst.higherKey(20));

        }

    }

    @RunWith(Parameterized.class)
    public  static class BinarySearchTreeHigherTestComplexity {


        private BinarySearchTreeHigher<Integer,Integer> student;
        private TreeSet<Integer> correct;


        public BinarySearchTreeHigherTestComplexity(BinarySearchTreeHigher<Integer,Integer> student, TreeSet<Integer> correct) {
            this.student = student;
            this.correct = correct;
        }


        @Test(timeout=100)
        @Grade(value=1)
        public void testMinKey() {
            assertEquals(correct.first(), student.minKey());
        }

        @Test(timeout=100)
        @Grade(value=3)
        public void testHigher() {

            int min = correct.first();
            int max = correct.last();

            assertEquals(correct.higher(min), student.higherKey(min));
            assertEquals(correct.higher(max-1), student.higherKey(max-1));
            assertEquals(correct.higher(min+(max-min)/2), student.higherKey(min+(max-min)/2));

        }





        @Parameterized.Parameters
        public static List<Object[]> data() throws IOException {
            Random r = new Random();
            LinkedList tests = new LinkedList<>();
            for (int i = 0; i < 5; i++) {
                BinarySearchTreeHigher<Integer,Integer> a = new BinarySearchTreeHigher<>();
                Set<Integer> b = new TreeSet<>();
                for (int k = 0; k < 100000; k++) {
                    int v = r.nextInt();
                    a.put(v,v);
                    b.add(v);
                }

                tests.add(new Object[]{a,b});
            }
            return tests;
        }
    }

    @RunWith(Parameterized.class)
    public static class BinarySearchTreeHigherTestRandom {

        private BinarySearchTreeHigher<Integer,Integer> student;
        private TreeSet<Integer> correct;


        public BinarySearchTreeHigherTestRandom(BinarySearchTreeHigher<Integer,Integer> student, TreeSet<Integer> correct) {
            this.student = student;
            this.correct = correct;
        }


        @Test
        @Grade(value=0.3)
        public void testMinKey() {
            assertEquals(correct.first(), student.minKey());
        }


        @Test
        @Grade(value=0.4)
        public void testHigher() {
            Random r = new Random();
            int min = correct.first();
            int max = correct.last();
            for (int i = 0; i < 5; i++) {
                int v = r.nextInt((max - min) + 1) + min;
                assertEquals(correct.higher(v), student.higherKey(v));
            }
        }



        @Parameterized.Parameters
        public static List<Object[]> data() throws IOException {
            Random r = new Random();
            int min=1, max=26;
            LinkedList tests = new LinkedList<>();
            for (int i = 0; i < 50; i++) {
                BinarySearchTreeHigher<Integer,Integer> a = new BinarySearchTreeHigher<>();
                Set<Integer> b = new TreeSet<>();
                for (int k = 0; k < 15; k++) {
                    int v = r.nextInt((max - min) + 1) + min;
                    a.put(v,v);
                    b.add(v);
                }

                tests.add(new Object[]{a,b});
            }
            return tests;
        }
    }
}
