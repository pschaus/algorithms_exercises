package fundamentals;


import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class CircularLinkedListTest {

    // some random tests
    @RunWith(Parameterized.class)
    public static class CircularLinkedListTestRandom {
        private CircularLinkedList<Integer> student;
        private List<Integer> correct;

        public CircularLinkedListTestRandom(CircularLinkedList<Integer> student, List<Integer> correct) {
            this.student = student;
            this.correct = correct;
        }

        @Test
        @Grade(value=0.5) //0.5 par test x 50 = 25
        public void runAsExpected() {
            Iterator<Integer> aIter = student.iterator();
            Iterator<Integer> bIter = correct.iterator();
            assertEquals(correct.size(),student.size());
            while (bIter.hasNext()) {
                assertTrue(aIter.hasNext());
                assertEquals(bIter.next(),aIter.next());
            }
            assertFalse(bIter.hasNext());
            assertFalse(aIter.hasNext());
        }


        @Parameterized.Parameters
        public static List<Object[]> data() throws IOException {
            Random r = new Random();
            LinkedList tests = new LinkedList<>();
            for (int i = 0; i < 50; i++) {
                CircularLinkedList<Integer> a = new CircularLinkedList<>();
                List<Integer> b = new LinkedList<>();
                for (int k = 0; k < 100; k++) {
                    int v = r.nextInt();
                    a.enqueue(v);
                    b.add(v);
                }
                if (i%2 == 0) {
                    a.remove(10);
                    b.remove(10);
                    a.remove(0);
                    b.remove(0);
                    a.remove(a.size()-1);
                    b.remove(b.size()-1);
                }
                tests.add(new Object[]{a,b});
            }
            return tests;
        }
    }

    // complexity tests
    @RunWith(Parameterized.class)
    @Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
    public static class CircularLinkedListTestComplexity {

        private CircularLinkedList<Integer> student;
        private List<Integer> correct;

        public CircularLinkedListTestComplexity(CircularLinkedList<Integer> student, List<Integer> correct) {
            this.student = student;
            this.correct = correct;
        }

        @Test(timeout=300)
        @Grade(value=10) //10 par test x 5 = 50
        public void runAsExpected() {
            int sz = correct.size();
            for (int i = 0; i < sz/2; i++) {
                student.remove(0);
                correct.remove(0);
            }
            Iterator<Integer> aIter = student.iterator();
            Iterator<Integer> bIter = correct.iterator();

            while (bIter.hasNext()) {
                assertTrue(aIter.hasNext());
                assertEquals(bIter.next(),aIter.next());
            }
            assertFalse(bIter.hasNext());
            assertFalse(aIter.hasNext());
        }

        @Parameterized.Parameters
        public static List<Object[]> data() throws IOException {
            LinkedList tests = new LinkedList<>();
            for (int i = 0; i < 5; i++) {
                CircularLinkedList<Integer> a = new CircularLinkedList<>();
                List<Integer> b = new LinkedList<>();
                for (int k = 0; k < 1000000; k++) {
                    a.enqueue(k);
                    b.add(k);
                }
                tests.add(new Object[]{a,b});
            }
            return tests;
        }
    }

    // more extreme tests
    public static class TestExtreme {

        @Test
        @Grade(value=15)
        public void testIteratorList() {
            for (int i = 0; i < 20; i++) {
                CircularLinkedList<Integer> a = new CircularLinkedList<>();
                assertEquals(0,a.size());
                a.enqueue(i);
                assertEquals(1,a.size());
                Iterator itera = a.iterator();
                assertTrue(itera.hasNext());
                assertEquals(i,itera.next());

                CircularLinkedList<Integer> b = new CircularLinkedList<>();
                b.enqueue(i);
                b.remove(0);
                Iterator iterb = b.iterator();
                assertFalse(iterb.hasNext());

            }
        }

        @Test(expected = IndexOutOfBoundsException.class)
        @Grade(value=5)
        public void testOutOfBound() {
            CircularLinkedList<Integer> a = new CircularLinkedList<>();
            a.enqueue(3);
            a.remove(1);
        }

        @Test(expected = ConcurrentModificationException.class)
        @Grade(value=5)
        public void testConcurrentModificationNext() {
            CircularLinkedList<Integer> a = new CircularLinkedList<>();
            Iterator iter = a.iterator();
            a.enqueue(3);
            iter.next();
        }
    }
}

