package fundamentals;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.Supplier;
import java.util.Random;

import java.util.EmptyStackException;

@RunWith(Enclosed.class)
public class StackWithTwoQueuesTest {

    public static class TestNotParameterized {

        @Test
        @Grade(value=1, cpuTimeout = 1000)
        public void testEmpty() {
            String message = "Test of empty;";
            StackWithTwoQueues<String> stack = new StackWithTwoQueues<>();
            stack.push("test");
            stack.pop();
            assertTrue(message, stack.empty());
        }

        @Test
        @Grade(value=1, cpuTimeout = 1000)
        public void testNotEmpty() {
            String message = "Test of empty;";
            StackWithTwoQueues<String> stack = new StackWithTwoQueues<>();
            stack.push("test");
            assertFalse(message, stack.empty());
        }

        @Test
        @Grade(value=1, cpuTimeout = 1000)
        public void testPeek() {
            String message = "Test of peek;";
            StackWithTwoQueues<String> stack = new StackWithTwoQueues<>();
            stack.push("elem");
            assertEquals(message, "elem", stack.peek());
            assertFalse(message, stack.empty());
        }

        @Test
        @Grade
        public void testMultiplePush() {
            String message = "Test of push (multiple);";
            StackWithTwoQueues<Integer> stack = new StackWithTwoQueues<>();

            for (int i = 0; i <= 100; i++) {
                stack.push(i);
            }

            for (int i = 100; i >= 0; i--) {
                assertEquals(message, i, (int) stack.pop());
            }

            assertTrue(message, stack.empty());
        }

        @Test
        @Grade
        public void testPopException() {
            String message = "Test of pop when empty;";
            StackWithTwoQueues<Integer> stack = new StackWithTwoQueues<>();

            try {
                stack.pop();
                fail(message);
            } catch (EmptyStackException e) {
                // Ok
            }
        }

        @Test
        @Grade
        public void testPeekException() {
            String message = "Test of peek when empty;";
            StackWithTwoQueues<Integer> stack = new StackWithTwoQueues<>();

            try {
                stack.peek();
                fail(message);
            } catch (EmptyStackException e) {
                // Ok
            }
        }


    }

    //complexity tests
    @RunWith(Parameterized.class)
    @Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
    public static class TestComplexity {

        private StackWithTwoQueues<Integer> student;
        private Stack<Integer> correct;

        public TestComplexity(StackWithTwoQueues<Integer> student, Stack<Integer> correct) {
            this.student = student;
            this.correct = correct;
        }


        @Test(timeout=300)
        @Grade(value=10)
        public void runAsExpected() {
            Random r = new Random(40195375);
            int a = r.nextInt(1000000);

            assertFalse(student.empty());

            student.push(a);
            correct.push(a);

            assertEquals(student.peek(), correct.peek());
            assertEquals(student.pop(), correct.pop());
            assertEquals(student.peek(), correct.peek());
        }

        @Parameterized.Parameters
        public static List<Object[]> data() throws IOException {
            Random r = new Random(40195385);
            LinkedList tests = new LinkedList<>();
            for(int i = 0; i < 5; i++) {
                StackWithTwoQueues<Integer> a = new StackWithTwoQueues<>();
                Stack<Integer> b = new Stack<>();
                int random;
                for(int k = 0; k < 1000; k++) { // if tests take too much time to be executed, reduce the value of k
                    random = r.nextInt(1000000);
                    a.push(random);
                    b.push(random);
                }
                tests.add(new Object[]{a,b});
            }
        
            return tests;
        }
        

    }
    

    
}
