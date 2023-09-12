package fundamentals;

import org.javagrader.Grade;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EmptyStackException;
import java.util.stream.Stream;

@Grade
public class StackTest {
    
    private static Stream<Stack<Integer>> stackProvider() {
        return Stream.of(
            new ArrayStack<>(),
            new LinkedStack<>()
        );
    }

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
}

