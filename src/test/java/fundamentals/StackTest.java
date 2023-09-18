package fundamentals;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

// BEGIN STRIP
import java.util.EmptyStackException;
import java.util.stream.Stream;
// END STRIP
@Grade
public class StackTest {

    // BEGIN STRIP
    private static Stream<Stack<Integer>> stackProvider() {
        return Stream.of(
            new ArrayStack<>(),
            new LinkedStack<>()
        );
    }
    // END STRIP

    @Grade
    @Test
    public void testSimpleStack() {

        Stack<Integer> [] stacks = new Stack[2];
        stacks[0] = new ArrayStack<>();
        stacks[1] = new LinkedStack<>();
        for (Stack<Integer> stack : stacks) {
            stack.push(1);
            assertEquals(1, stack.peek());
            stack.push(2);
            stack.push(3);
            assertFalse(stack.empty());
            assertEquals(3, (int) stack.pop());
            assertEquals(2, (int) stack.pop());
            assertEquals(1, (int) stack.pop());
            assertTrue(stack.empty());

        }
    }

    // BEGIN STRIP
    @ParameterizedTest
    @Grade
    @MethodSource("stackProvider")
    public void testEmpty(Stack<Integer> stack) {
        stack.push(0);
        stack.pop();
        assertTrue(stack.empty());
    }

    @ParameterizedTest
    @Grade
    @MethodSource("stackProvider")
    public void testNotEmpty(Stack<Integer> stack) {
        stack.push(0);
        assertFalse(stack.empty());
    }

    @ParameterizedTest
    @Grade
    @MethodSource("stackProvider")
    public void testPeek(Stack<Integer> stack) {
        stack.push(10);
        assertEquals(10, stack.peek());
    }

    @ParameterizedTest
    @Grade
    @MethodSource("stackProvider")
    public void testMultiplePush(Stack<Integer> stack) {
        for (int i = 0; i <= 100; i++) {
            stack.push(i);
        }

        for (int i = 100; i >= 0; i--) {
            assertEquals(i, (int) stack.pop());
        }

        assertTrue(stack.empty());
    }

    @ParameterizedTest
    @Grade
    @MethodSource("stackProvider")
    public void testPopException(Stack<Integer> stack) {
        assertThrows(EmptyStackException.class, () -> { stack.pop(); });
    }

    @ParameterizedTest
    @Grade
    @MethodSource("stackProvider")
    public void testPeekException(Stack<Integer> stack) {
        assertThrows(EmptyStackException.class, () -> { stack.peek(); });
    }
    // END STRIP
}

