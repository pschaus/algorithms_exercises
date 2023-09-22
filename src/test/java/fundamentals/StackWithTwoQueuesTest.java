package fundamentals;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

// BEGIN STRIP
import java.util.EmptyStackException;
// END STRIP

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class StackWithTwoQueuesTest {

    @Test
    @Grade(value=1)
    @Order(0)
    public void simpleTest(){
        StackWithTwoQueues<Integer> stack = new StackWithTwoQueues<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        assertFalse(stack.empty());
        for (int i = 9; i >= 0; i--) {
            assertEquals(i, (int) stack.pop());
        }
        assertTrue(stack.empty());
    }

    // BEGIN STRIP
    @Test
    @Grade(value=1)
    @Order(1)
    public void testEmpty() {
        StackWithTwoQueues<String> stack = new StackWithTwoQueues<>();
        stack.push("test");
        stack.pop();
        assertTrue(stack.empty());
    }

    @Test
    @Grade(value=1)
    @Order(2)
    public void testNotEmpty() {
        StackWithTwoQueues<String> stack = new StackWithTwoQueues<>();
        stack.push("test");
        assertFalse(stack.empty());
    }

    @Test
    @Grade(value=1)
    @Order(3)
    public void testPeek() {
        StackWithTwoQueues<String> stack = new StackWithTwoQueues<>();
        stack.push("elem");
        assertEquals("elem", stack.peek());
        assertFalse(stack.empty());
    }

    @Test
    @Grade
    @Order(4)
    public void testMultiplePush() {
        StackWithTwoQueues<Integer> stack = new StackWithTwoQueues<>();

        for (int i = 0; i <= 100; i++) {
            stack.push(i);
        }

        for (int i = 100; i >= 0; i--) {
            assertEquals(i, (int) stack.pop());
        }
        assertTrue(stack.empty());
    }

    @Test
    @Grade
    @Order(5)
    public void testPopException() {
        StackWithTwoQueues<Integer> stack = new StackWithTwoQueues<>();
        assertThrows(EmptyStackException.class, () -> { stack.pop(); });
    }

    @Test
    @Grade
    @Order(6)
    public void testPeekException() {
        StackWithTwoQueues<Integer> stack = new StackWithTwoQueues<>();
        assertThrows(EmptyStackException.class, () -> { stack.pop(); });
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @Order(7)
    @Grade(cpuTimeout=10)
    public void testComplexity() {
        StackWithTwoQueues<Integer> stack = new StackWithTwoQueues<>();
        for (int i = 0; i < 30_000; i++) {
            stack.push(i);
        }
    }
    // END STRIP
}
