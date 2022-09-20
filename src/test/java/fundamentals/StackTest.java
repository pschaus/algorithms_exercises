package fundamentals;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.EmptyStackException;
import java.util.function.Supplier;

@RunWith(Parameterized.class)
@Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
public class StackTest {

    @Parameterized.Parameters
    public static Supplier<Stack>[] data() {
        return new Supplier[]{
                () -> new LinkedStack<>(),
                () -> new ArrayStack<>(),
        };
    }

    @Parameterized.Parameter
    public Supplier<Stack> stackFactory;

    @Test
    @Grade
    public void testEmpty() {
        String message = "Test of empty;";
        Stack<String> stack = stackFactory.get();
        stack.push("test");
        stack.pop();
        assertTrue(message, stack.empty());
    }

    @Test
    @Grade
    public void testNotEmpty() {
        String message = "Test of empty;";
        Stack<String> stack = stackFactory.get();
        stack.push("test");
        assertFalse(message, stack.empty());
    }


    @Test
    @Grade
    public void testPeek() {
        String message = "Test of peek;";
        Stack<String> stack = stackFactory.get();
        stack.push("elem");
        assertEquals(message, "elem", stack.peek());
        assertFalse(message, stack.empty());
    }

    @Test
    @Grade
    public void testMultiplePush() {
        String message = "Test of push (multiple);";
        Stack<Integer> stack = stackFactory.get();

        for (int i = 0; i <= 100; i++) {
            //assertEquals(message, i, stack.push(i));
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
        Stack<String> stack = stackFactory.get();

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
        Stack<String> stack = stackFactory.get();

        try {
            stack.peek();
            fail(message);
        } catch (EmptyStackException e) {
            // Ok
        }
    }
}

