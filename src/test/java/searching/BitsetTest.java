package searching;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

// BEGIN STRIP
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
// END STRIP

@Grade
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BitsetTest {

    // BEGIN STRIP
    Random random = new Random(546543);
    // END STRIP

    @Test
    @Grade(value = 1)
    @GradeFeedback(message = "Your implementation fails to effectively represents the given data. Test on a small example", on = TestResultStatus.FAIL)
    @Order(1)
    public void testSimple() {

        Bitset bitset = new Bitset(30);

        // Bitset Initial Values
        assertEquals(1, bitset.getNbWords());
        assertEquals(0, bitset.count());

        // Set First bit
        bitset.set(0);
        assertTrue(bitset.contains(0));
        // Set the last bit
        bitset.set(29);
        assertTrue(bitset.contains(29));

        // All the others bits are clear
        for (int i = 1; i < 29; i++) {
            assertFalse(bitset.contains(i));
        }

        // Compute the count of bits set
        assertEquals(2, bitset.count());

        // Clear the first bit
        bitset.clear(0);
        assertEquals(1, bitset.count());

        // Clear the last bit
        bitset.clear(29);
        assertEquals(0, bitset.count());
    }


    // BEGIN STRIP
    @Test
    @Grade(value = 1)
    @GradeFeedback(message = "Your implementation fails to effectively represents the given data. Test on a small example", on = TestResultStatus.FAIL)
    @Order(2)
    public void moreThanOneWordTest() {

        int size = 120;
        Bitset bitset = new Bitset(size);
        assertEquals(2, bitset.getNbWords());


        // Set all first word bits
        for (int i = 0; i < 64; i++) {
            bitset.set(i);
        }
        assertEquals(64, bitset.count());
        for (int i = 64; i < 120; i++) {
            assertFalse(bitset.contains(i));
        }
    }

    @Test
    @Grade(value = 2)
    @GradeFeedback(message = "Your implementation fails to effectively represents the given data. Test on a small example", on = TestResultStatus.FAIL)
    @Order(1)
    public void perfectSizedBitsetTest() {
        int size = 192;
        int nbBits = 33;

        Bitset bitset = new Bitset(size);
        assertEquals(size / 64, bitset.getNbWords());
        assertEquals(0, bitset.count());

        List<Integer> indices = getRandomBits(size, nbBits);

        for (int bit : indices) {
            bitset.set(bit);
            assertTrue(bitset.contains(bit));
        }
        assertEquals(nbBits, bitset.count());


    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message = "Your implementation fails to effectively represents the given data. Test on a small example", on = TestResultStatus.FAIL)
    @Order(2)
    public void allSetAndRandomClearTest() {
        int size = 1000;
        int to_remove = 530;

        List<Integer> indices = getRandomBits(size, to_remove);


        Bitset bitset = new Bitset(size);
        assertEquals(16, bitset.getNbWords());
        assertEquals(0, bitset.count());

        for (int i = 0; i < size; i++) {
            bitset.set(i);
        }
        assertEquals(size, bitset.count());


        for (int bit : indices) {
            bitset.clear(bit);
            assertFalse(bitset.contains(bit));
        }

        assertEquals(size - to_remove, bitset.count());
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000, unit = TimeUnit.MILLISECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message = "Your implementation does not finish in the allocated time.", on = TestResultStatus.FAIL)
    @Order(3)
    public void largeBitsetTest() {
        int iterations = 100;
        for (int i = 0; i < iterations; i++) {
            int size = 10000;
            int toSet = 2000 + random.nextInt(size - 2000);
            int toRemove = 100 + random.nextInt(toSet - 100);

            List<Integer> indicesToSet = getRandomBits(size, toSet);
            List<Integer> indicesToRemove = getRandomBits(size, toRemove);

            int nbCommonElements = indicesToSet.stream()
                    .distinct()
                    .filter(indicesToRemove::contains)
                    .collect(Collectors.toSet()).size();


            Bitset bitset = new Bitset(size);
            assertEquals(157, bitset.getNbWords());
            assertEquals(0, bitset.count());

            for (int bit : indicesToSet) {
                bitset.set(bit);
                assertTrue(bitset.contains(bit));
            }

            assertEquals(toSet, bitset.count());
            for (int bit : indicesToRemove) {
                bitset.clear(bit);
                assertFalse(bitset.contains(bit));
            }
            assertEquals(toSet - nbCommonElements, bitset.count());
        }

    }

    private List<Integer> getRandomBits(int bitsetSize, int nbElements) {
        List<Integer> indices = IntStream.range(0, bitsetSize).boxed().collect(Collectors.toList());
        Collections.shuffle(indices);
        indices = indices.subList(0, nbElements);
        return indices;
    }
    // END STRIP

}
