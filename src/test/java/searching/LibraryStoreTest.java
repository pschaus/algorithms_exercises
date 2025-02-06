package searching;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Grade
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibraryStoreTest {

    Random random = new Random(15623);

    @Test
    @Grade(value = 1)
    @GradeFeedback(message = "Your algorithm does not return the correct answer on the given example. Debug it locally", on = TestResultStatus.FAIL)
    @Order(1)
    public void testSimpleBookDelete() {

        LibraryStore store = new LibraryStore(3, 1.0);
        assertEquals(0, store.getSize());
        assertEquals(3, store.getCapacity());

        store.add(1, 1900);
        store.add(2, 1901);
        store.add(3, 1902);

        assertEquals(1902, store.get(3));
        assertEquals(3, store.getCapacity());
        assertEquals(3, store.getSize());

        store.delete(3);
        assertNull(store.get(3));
        assertEquals(3, store.getCapacity());
        assertEquals(2, store.getSize());
    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message = "Your algorithm does not return the correct answer on the given example. Debug it locally", on = TestResultStatus.FAIL)
    @Order(1)
    public void testSimpleStoreResize() {

        LibraryStore store = new LibraryStore(3, 1.0);
        assertEquals(0, store.getSize());
        assertEquals(3, store.getCapacity());

        store.add(1, 1900);
        store.add(2, 1901);
        store.add(3, 1902);
        store.add(4, 1902);

        assertEquals(1902, store.get(3));
        assertEquals(6, store.getCapacity());
        assertEquals(4, store.getSize());
    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message = "Your algorithm does not work when trying to remove an existing book. Debug it locally", on = TestResultStatus.FAIL)
    @Order(2)
    public void testDeleteExistingBook() {
        LibraryStore store = new LibraryStore(10);
        for (int i = 0; i < 5; i++) {
            store.add(1600 + i, 2000 + i);
        }

        assertEquals(10, store.getCapacity());
        assertEquals(5, store.getSize());
        assertEquals(2004, store.get(1604));

        store.delete(1604);
        assertNull(store.get(1604));
        assertEquals(10, store.getCapacity());
        assertEquals(4, store.getSize());

    }


    @Test
    @GradeFeedback(message = "Your algorithm does not work when trying to remove multiple books. Debug it locally", on = TestResultStatus.FAIL)
    @Order(2)
    @Grade(value = 1)
    public void testDeleteMultipleBooks() {
        LibraryStore store = new LibraryStore(6, 1.0);

        assertEquals(6, store.getCapacity());

        // Triggers resize three times
        for (int i = 0; i < 30; i++) {
            store.add(1600 + i, 2000 + i);
        }

        assertEquals(48, store.getCapacity());
        assertEquals(30, store.getSize());


        for (int i = 15; i < 30; i++) {
            store.delete(1600 + i);
        }

        assertEquals(48, store.getCapacity());
        assertEquals(15, store.getSize());
        assertNull(store.get(1620));
        assertEquals(2005, store.get(1605));
    }

    @Test
    @Grade(value = 1)
    @Order(2)
    @GradeFeedback(message = "Your algorithm does not work when trying to resize. Debug it locally", on = TestResultStatus.FAIL)
    public void testTriggerResizing() {
        int baseIsbn = 85696;
        int baseYear = 1985;
        int nEntries = 13;
        LibraryStore store = new LibraryStore(nEntries);
        for (int i = 0; i < nEntries; i++) {
            store.add(baseIsbn + i, baseYear + i);
        }
        assertEquals(nEntries, store.getSize());
        assertEquals(nEntries * 2, store.getCapacity());

        for (int i = 0; i < nEntries; i++) {
            assertEquals(baseYear + i, store.get(baseIsbn + i));
        }
    }


    @Test
    @Grade(value = 1)
    @Order(2)
    @GradeFeedback(message = "Your algorithm does not work when trying different load factor. Debug it locally", on = TestResultStatus.FAIL)
    public void testDifferentLoadFactor() {
        int baseIsbn = 85696;
        int baseYear = 1985;
        int nEntries = 50;
        LibraryStore store = new LibraryStore(nEntries, 1.0);
        for (int i = 0; i < nEntries * 3 + 10; i++) {
            store.add(baseIsbn + i, baseYear + i);
        }
        assertEquals(nEntries * 3 + 10, store.getSize());
        assertEquals(nEntries * 4, store.getCapacity());

        for (int i = 0; i < nEntries; i++) {
            assertEquals(baseYear + i, store.get(baseIsbn + i));
        }
    }

    @Test
    @Grade(value = 1)
    @Order(2)
    @GradeFeedback(message = "Your algorithm does not work when trying different load factor and triggering resize. Debug it locally", on = TestResultStatus.FAIL)
    public void testTriggerResizeDifferentLoadFactor() {
        int baseIsbn = 85696;
        int baseYear = 1985;
        int nEntries = 50;
        LibraryStore store = new LibraryStore(nEntries, 1.0);
        for (int i = 0; i < nEntries + 10; i++) {
            store.add(baseIsbn + i, baseYear + i);
        }
        assertEquals(nEntries + 10, store.getSize());
        assertEquals(nEntries * 2, store.getCapacity());

        for (int i = 0; i < nEntries; i++) {
            assertEquals(baseYear + i, store.get(baseIsbn + i));
        }
    }

    // Checks if add is still working
    @Test
    @Grade(value = 1)
    @Order(2)
    @GradeFeedback(message = "Your algorithm does not work when trying to update an existing book. Debug it locally", on = TestResultStatus.FAIL)
    public void testUpdateExistingBook() {
        int initSize = 30;
        int baseIsbn = 0;
        int baseYear = 1985;
        LibraryStore store = new LibraryStore(initSize, 1.0);
        for (int i = 0; i < initSize; i++) {
            store.add(baseIsbn + i, baseYear + i);
        }

        assertEquals(initSize, store.getSize());
        assertEquals(initSize, store.getCapacity());

        int randomBookId = random.nextInt(initSize);
        assertEquals(baseYear + randomBookId, store.get(baseIsbn + randomBookId));
        store.add(baseIsbn + randomBookId, 2025);
        assertEquals(2025, store.get(baseIsbn + randomBookId));
    }


    @Test
    @Grade(value = 1)
    @Order(3)
    @GradeFeedback(message = "Your algorithm does not work when trying different load factor and triggering resize. Debug it locally", on = TestResultStatus.FAIL)
    public void testMultipleResizeTrigger() {
        int[] initialCapacities = new int[]{3, 20, 23, 65};
        double[] loadFactors = new double[]{0.2, 0.4, 0.6, 1.0};
        for (int initialCapacity : initialCapacities) {
            for (double loadFactor : loadFactors) {
                int capacity = initialCapacity;
                LibraryStore store = new LibraryStore(capacity, loadFactor);
                for (int i = 0; i < initialCapacity; i++) {
                    store.add(100 + i, 200 + i);
                    assertTrue(((double) store.getSize() - 1) / store.getCapacity() <= loadFactor);
                    assertEquals(i + 1, store.getSize());
                }
            }
        }
    }

    @Test
    @Order(3)
    @Grade(value = 3, cpuTimeout = 10   , threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message = "Your algorithm does not have the right complexity.", on = TestResultStatus.FAIL)
    public void testAddToLargeStore() {
        int initialCapacity = 1000;
        int multiplier = 500;
        LibraryStore store = new LibraryStore(initialCapacity, 0.75);
        for (int i = 0; i < initialCapacity * multiplier; i++) {
            store.add(i, i);
        }
        for (int i = 0; i < initialCapacity * multiplier; i++) {
            assertEquals(i, store.get(i));
        }

        for (int i = 0; i < initialCapacity * multiplier; i++) {
            store.delete(i);
        }

        assertEquals(0, store.getSize());
    }

}
