package sorting;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class BitwiseRadixSortTest {

    @Test
    @Order(1)
    @Grade(value = 1)
    public void testExample() {
        int [] arr = new int[]{5, 10, 1, 75, -10, -23};
        BitwiseRadixSort sorter = new BitwiseRadixSort();
        sorter.sort(arr);
        assertArrayEquals(new int[]{-23, -10, 1, 5, 10, 75}, arr);
    }

    @Test
    @Order(2)
    @Grade(value = 1)
    public void testEmpty() {
        int [] arr = new int[]{};
        BitwiseRadixSort sorter = new BitwiseRadixSort();
        sorter.sort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    @Order(2)
    @Grade(value = 1)
    public void testOneNumber(){
        int [] arr = new int[]{1};
        BitwiseRadixSort sorter = new BitwiseRadixSort();
        sorter.sort(arr);
        assertArrayEquals(new int[]{1}, arr);
    }

    @Test
    @Order(2)
    @Grade(value = 1)
    public void testPositiveNumbers(){
        int size = 100;
        int [] arr = generateRandomNumber(size, 0, 1000);
        int [] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);

        BitwiseRadixSort sorter = new BitwiseRadixSort();
        sorter.sort(arr);
        assertArrayEquals(copy, arr);
    }

    @Test
    @Order(2)
    @Grade(value = 1)
    public void testNegativeNumbers(){
        int size = 100;
        int [] arr = generateRandomNumber(size, -1000, -1);
        int [] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);

        BitwiseRadixSort sorter = new BitwiseRadixSort();
        sorter.sort(arr);
        assertArrayEquals(copy, arr);
    }

    @Test
    @Order(2)
    @Grade(value = 1)
    public void testMixSignedNumbers(){
        int size = 1000;
        int [] arr = generateRandomNumber(size, -10000, 10000);
        int [] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);

        BitwiseRadixSort sorter = new BitwiseRadixSort();
        sorter.sort(arr);
        assertArrayEquals(copy, arr);
    }

    @Test
    @Order(2)
    @Grade(value = 1)
    public void testSameNumbers(){
        int size = 100;
        Random random = new Random();
        int value = random.nextInt(size);
        int [] arr = new int[size];
        Arrays.fill(arr, value);
        int [] copy = Arrays.copyOf(arr, arr.length);

        BitwiseRadixSort sorter = new BitwiseRadixSort();
        sorter.sort(arr);
        assertArrayEquals(copy, arr);
    }


    @Test
    @Order(3)
    @Grade(value = 1, cpuTimeout = 1000)
    public void testLargeNumbers(){
        int size = 100000;
        int [] arr = generateRandomNumber(size, -size, size);
        int [] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);

        BitwiseRadixSort sorter = new BitwiseRadixSort();
        sorter.sort(arr);
        assertArrayEquals(copy, arr);
    }

    private int [] generateRandomNumber(int size, int lb, int ub){
        Random rand = new Random(1234);
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(ub - lb) + lb;
        }
        return arr;
    }

}
