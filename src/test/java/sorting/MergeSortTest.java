package sorting;

import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;



import static org.junit.Assert.assertArrayEquals;


public class MergeSortTest {

    @Test
    @Grade(value = 50)
    public void testSortOdd()
    {
        String message = "Test [1 4 3 8 6]";
        Integer[] arr = new Integer[]{1, 4, 3, 8, 6};

        MergeSort.sort(arr);
        assertArrayEquals(message, new Integer[]{1, 3, 4, 6, 8}, arr);
    }

    @Test
    @Grade(value = 50)
    public void testSortEven()
    {
        String message = "Test [1 9 4 3 8 6]";
        Integer[] arr = new Integer[]{1, 9, 4, 3, 8, 6};

        MergeSort.sort(arr);
        assertArrayEquals(message, new Integer[]{1, 3, 4, 6, 8, 9}, arr);
    }

}
