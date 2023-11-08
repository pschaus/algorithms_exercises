package strings;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

@Grade
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WordCounterTest {


    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    @Order(0)
    @GradeFeedback(message = "Sorry, something is wrong with your get-count, debug on small example", on = TestResultStatus.FAIL)
    public void testWordCount() {
        WordCounter wc = new WordCounter();
        wc.addWord("cat");
        wc.addWord("dog");
        wc.addWord("cat");
        wc.addWord("bird");
        wc.addWord("cat");


        assertEquals(3, wc.getCount("cat"));
        assertEquals(1, wc.getCount("dog"));
        assertEquals(1, wc.getCount("bird"));
        assertEquals(0, wc.getCount("fish"));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    @Order(0)
    @GradeFeedback(message = "Sorry, something is wrong with your iterator, debug on small example", on = TestResultStatus.FAIL)
    public void testIterator() {
        WordCounter wc = new WordCounter();
        wc.addWord("cat");
        wc.addWord("dog");
        wc.addWord("cat");
        wc.addWord("bird");
        wc.addWord("cat");

        String[] expected = { "bird","cat","dog"};
        int i = 0;
        for (String word : wc) {
            assertEquals(expected[i++], word);
        }
    }



    // BEGIN STRIP
    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    @Order(1)
    @GradeFeedback(message = "Too slow, failed for time-complexity", on = TestResultStatus.FAIL)
    public void testTimeComplexityIterator() {
        WordCounter wc = new WordCounter();
        int n = 100000;
        for (int i = 0; i < n; i++) {
            wc.addWord("word");
        }
        assertEquals(n,wc.getCount("word"));
        for (String word : wc) {
            // do nothing
        }
    }
    // END STRIP
}

