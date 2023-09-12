package sorting;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@Grade
public class MergeSortTest {

    @Test
    @Grade(value = 1)
    public void testSortOdd()
    {
        Integer[] arr = new Integer[]{1, 4, 3, 8, 6};

        MergeSort.sort(arr);
        assertArrayEquals(new Integer[]{1, 3, 4, 6, 8}, arr);
    }

    @Test
    @Grade(value = 1)
    public void testSortEven()
    {
        Integer[] arr = new Integer[]{1, 9, 4, 3, 8, 6};

        MergeSort.sort(arr);
        assertArrayEquals(new Integer[]{1, 3, 4, 6, 8, 9}, arr);
    }

}
