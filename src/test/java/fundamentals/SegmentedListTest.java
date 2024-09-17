package fundamentals;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class SegmentedListTest {



    @Grade(value=0.5)
    @Order(0)
    @Test
    public void  testAddSegmentAndSize(){
        SegmentedList<Integer> segmentedList = SegmentedList.create();
        List<Integer> segment1 = Arrays.asList(1, 2, 3);
        List<Integer> segment2 = Arrays.asList(4, 5);
        segmentedList.addSegment(segment1);
        segmentedList.addSegment(segment2);

        assertEquals(5, segmentedList.size(), "Size should be the sum of all segments' sizes.");

    }


    @Grade(value=0.5)
    @Order(0)
    @Test
    public void  testRemoveSegmentAndSize(){
        SegmentedList<Integer> segmentedList = SegmentedList.create();
        List<Integer> segment1 = Arrays.asList(1, 2, 3);
        List<Integer> segment2 = Arrays.asList(4, 5);
        segmentedList.addSegment(segment1);
        segmentedList.addSegment(segment2);

        assertEquals(5, segmentedList.size());
        segmentedList.removeSegment(0);
        assertEquals(2, segmentedList.size(), "After removing one segment, size should update.");
    }


    @Grade(value=0.5)
    @Order(0)
    @Test
    public void  testInvalidIndex(){
        SegmentedList<Integer> segmentedList = SegmentedList.create();
        List<Integer> segment1 = Arrays.asList(1, 2, 3);
        segmentedList.addSegment(segment1);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            segmentedList.removeSegment(10);
        }, "Expected an IndexOutOfBoundsException for invalid index.");
    }


    @Grade(value=0.5)
    @Order(0)
    @Test
    public void  testGetElementByGlobalIndex(){
        SegmentedList<Integer> segmentedList = SegmentedList.create();
        List<Integer> segment1 = Arrays.asList(1, 2, 3);
        List<Integer> segment2 = Arrays.asList(4, 5);
        segmentedList.addSegment(segment1);
        segmentedList.addSegment(segment2);

        assertEquals(1, segmentedList.get(0), "First element should be 1.");
        assertEquals(5, segmentedList.get(4), "Fifth element should be 5.");
        assertThrows(IndexOutOfBoundsException.class, () -> segmentedList.get(10), "Index out of bounds");
    }

    @Grade(value=0.5)
    @Order(0)
    @Test
    public void  testIterator(){
        SegmentedList<Integer> segmentedList = SegmentedList.create();
        List<Integer> segment1 = Arrays.asList(1, 2);
        List<Integer> segment2 = Arrays.asList(3, 4);
        segmentedList.addSegment(segment1);
        segmentedList.addSegment(segment2);

        Iterator<Integer> iterator = segmentedList.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(3, iterator.next());
        assertEquals(4, iterator.next());
        assertFalse(iterator.hasNext(), "Iterator should be exhausted.");
    }

    @Grade(value=0.5)
    @Order(0)
    @Test
    public void  testIteratorNoSuchElement(){
        SegmentedList<Integer> segmentedList = SegmentedList.create();
        List<Integer> segment1 = Arrays.asList(1);
        segmentedList.addSegment(segment1);

        Iterator<Integer> iterator = segmentedList.iterator();
        iterator.next();  // Exhaust the iterator

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Grade(value=0.5)
    @Order(0)
    @Test
    public void  testIteratorConcurrentModification(){
        SegmentedList<Integer> segmentedList = SegmentedList.create();
        List<Integer> segment1 = Arrays.asList(1, 2);
        segmentedList.addSegment(segment1);

        Iterator<Integer> iterator = segmentedList.iterator();

        segmentedList.addSegment(Arrays.asList(3, 4));  // Modify the list after creating the iterator

        assertThrows(ConcurrentModificationException.class, iterator::next);

    }

    public void testIteratorAfterSegmentRemoval() {
        SegmentedList<Integer> segmentedList = SegmentedList.create();

        List<Integer> segment1 = Arrays.asList(1, 2);
        List<Integer> segment2 = Arrays.asList(3, 4);
        segmentedList.addSegment(segment1);
        segmentedList.addSegment(segment2);

        segmentedList.removeSegment(1);  // Remove the second segment
        Iterator<Integer> iterator = segmentedList.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertFalse(iterator.hasNext(), "Iterator should only iterate over the first segment after removal.");
    }

}
