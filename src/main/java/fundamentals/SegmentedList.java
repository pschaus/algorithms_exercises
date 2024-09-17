package fundamentals;

import java.util.*;

/**
 * Author Pierre Schaus
 *
 * The SegmentedList is a linear structure that consists of several segments (or groups) of elements,
 * where each segment can have its own size.
 * It is like a list of lists, but the user interacts with it as a single linear sequence.
 * The iterator will need to traverse each segment in order, element by element,
 * seamlessly presenting the entire structure as a single flat sequence.
 *
 * Be careful with the iterator, it should implement the fail-fast behavior.
 * A fail-fast iterator detects illegal concurrent modification during iteration (see the tests).
 *
 * Example:
 *
 *         SegmentedList<Integer> segmentedList = create();
 *
 *         // Add segments
 *         List<Integer> segment1 = new ArrayList<>();
 *         segment1.add(1); segment1.add(2); segment1.add(3);
 *         segmentedList.addSegment(segment1); // add [1,2,3]
 *
 *         List<Integer> segment2 = new ArrayList<>();
 *         segment2.add(4); segment2.add(5);
 *         segmentedList.addSegment(segment2); // add [4,5]
 *
 *         List<Integer> segment3 = new ArrayList<>();
 *         segment3.add(6); segment3.add(7); segment3.add(8); segment3.add(9);
 *         segmentedList.addSegment(segment3); // add [6,7,8,9]
 *
 *         segmentedList.removeSegment(1); // remove [4,5], the segment in second position
 *
 *         // Iterate through the SegmentedList that is elements 1,2,3,6,7,8,9
 *         for (Integer value : segmentedList) {
 *             System.out.println(value);
 *         }
 *
 *         // Example of using get()
 *         System.out.println("Element at global index 4: " + segmentedList.get(4)); // Should print 5
 *
 * @param <T>
 */
public interface SegmentedList<T> extends Iterable<T> {

    // Add a new segment (list) to the SegmentedList.
    void addSegment(List<T> segment);

    // Remove a segment by its index.
    void removeSegment(int index);

    // Get the total size of the segmented list (across all segments).
    int size();

    // Retrieve an element at a global index (spanning all segments).
    T get(int globalIndex);

    // Static method inside the interface
    static <T> SegmentedList<T> create() {
        return new SegmentedListImpl<>();
    }

}

class SegmentedListImpl<T> implements SegmentedList<T> {

    // TODO: Implement the SegmentedList interface here

    // BEGIN STRIP

    private List<List<T>> segments = new LinkedList<>();
    private int nOp = 0;

    // END STRIP

    // Add a new segment (list) to the SegmentedList.
    public void addSegment(List<T> segment) {
        // BEGIN STRIP
        nOp++;
        segments.add(segment);
        // END STRIP
    }

    // Remove a segment by its index.
    public void removeSegment(int index) {
        // BEGIN STRIP
        nOp++;
        if (index >= 0 && index < segments.size()) {
            segments.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Segment index out of bounds.");
        }
        // END STRIP
    }

    // Get the total size of the segmented list (across all segments).
    public int size() {
        // STUDENT return -1;
        // BEGIN STRIP
        int totalSize = 0;
        for (List<T> segment : segments) {
            totalSize += segment.size();
        }
        return totalSize;
        // END STRIP
    }

    // Retrieve an element at a global index (spanning all segments).
    public T get(int globalIndex) {
        // STUDENT return null;
        // BEGIN STRIP
        int count = 0;
        for (List<T> segment : segments) {
            if (globalIndex < count + segment.size()) {
                return segment.get(globalIndex - count);
            }
            count += segment.size();
        }
        throw new IndexOutOfBoundsException("Index out of bounds.");
        // END STRIP
    }



    // Return an iterator for the segmented list.
    @Override
    public Iterator<T> iterator() {
        // STUDENT return null;
        // BEGIN STRIP
        return new SegmentedListIterator();
        // END STRIP
    }

    // BEGIN STRIP

    // Inner class to implement the iterator.
    private class SegmentedListIterator implements Iterator<T> {

        private int segmentIndex = 0; // Current segment we are traversing
        private int elementIndex = 0; // Current element within the segment

        private int t = nOp;

        @Override
        public boolean hasNext() {
            if (t != nOp) {
                throw new ConcurrentModificationException("Concurrent modification detected.");
            }
            // If we have reached the end of a segment, move to the next segment.
            while (segmentIndex < segments.size()) {
                if (elementIndex < segments.get(segmentIndex).size()) {
                    return true; // There's more elements in this segment.
                }
                segmentIndex++;
                elementIndex = 0; // Reset element index for the next segment.
            }
            return false; // No more elements left.
        }

        @Override
        public T next() {
            if (t != nOp) {
                throw new ConcurrentModificationException("Concurrent modification detected.");
            }
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements left.");
            }
            return segments.get(segmentIndex).get(elementIndex++);
        }
    }

    // END STRIP

}
