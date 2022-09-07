package fundamentals;

import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class UnionTest {

    public static Union.Interval[] unionSolution(Union.Interval[] intervals) {
        if (intervals.length == 0) return intervals;
        Arrays.sort(intervals);
        int min = intervals[0].min;
        int max = intervals[0].max;
        ArrayList<Union.Interval> res = new ArrayList<>();
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].min > max) {
                // close
                res.add(new Union.Interval(min, max));
                min = intervals[i].min;
                max = intervals[i].max;
            } else {
                max = Math.max(max, intervals[i].max);
            }
        }
        res.add(new Union.Interval(min, max));
        return res.toArray(new Union.Interval[0]);
    }

    @Test
    @Grade(value = 25)
    public void testUnits() {

        Union.Interval i1 = new Union.Interval(1, 3);
        Union.Interval i2 = new Union.Interval(1, 3);
        Union.Interval[] result = Union.union(new Union.Interval[]{i1, i2});
        assertArrayEquals(result, new Union.Interval[]{new Union.Interval(1, 3)});

        i1 = new Union.Interval(1, 3);
        i2 = new Union.Interval(2, 4);
        result = Union.union(new Union.Interval[]{i1, i2});
        assertArrayEquals(result, new Union.Interval[]{new Union.Interval(1, 4)});

        i1 = new Union.Interval(1, 2);
        i2 = new Union.Interval(2, 4);
        result = Union.union(new Union.Interval[]{i1, i2});
        assertArrayEquals(result, new Union.Interval[]{new Union.Interval(1, 4)});

        i1 = new Union.Interval(1, 2);
        i2 = new Union.Interval(3, 4);
        result = Union.union(new Union.Interval[]{i1, i2});
        assertArrayEquals(result, new Union.Interval[]{new Union.Interval(1, 2), new Union.Interval(3, 4)});


        i1 = new Union.Interval(1, 2);
        i2 = new Union.Interval(2, 2);
        result = Union.union(new Union.Interval[]{i1, i2});
        assertArrayEquals(result, new Union.Interval[]{new Union.Interval(1, 2)});

        i1 = new Union.Interval(1, 1);
        i2 = new Union.Interval(2, 2);
        result = Union.union(new Union.Interval[]{i1, i2});
        assertArrayEquals(result, new Union.Interval[]{new Union.Interval(1, 1), new Union.Interval(2, 2)});

        Union.Interval i0 = new Union.Interval(7, 9);
        i1 = new Union.Interval(5, 8);
        i2 = new Union.Interval(2, 4);
        result = Union.union(new Union.Interval[]{i0, i1, i2});
        assertArrayEquals(result, new Union.Interval[]{new Union.Interval(2, 4), new Union.Interval(5, 9)});


        i0 = new Union.Interval(10, 10);
        i1 = new Union.Interval(2, 4);
        i2 = new Union.Interval(3, 4);
        Union.Interval i3 = new Union.Interval(5, 6);
        Union.Interval i4 = new Union.Interval(6, 9);
        Union.Interval i5 = new Union.Interval(6, 8);
        result = Union.union(new Union.Interval[]{i0, i1, i2, i3, i4, i5});
        assertArrayEquals(result, new Union.Interval[]{new Union.Interval(2, 4), new Union.Interval(5, 9), new Union.Interval(10, 10)});
    }

    public static Union.Interval randomInterval(Random rand) {
        int min = rand.nextInt(20);
        return new Union.Interval(min, min + rand.nextInt(4));
    }

    @Test(timeout = 1000)
    @Grade(value = 25)
    public void testRandom2() {
        int[] seeds = new int[]{1, 5, 7, 11, 13};
        Random rand = new java.util.Random(seeds[2]);

        for (int i = 0; i < 500; i++) {
            Union.Interval[] intervals = new Union.Interval[10];
            for (int k = 0; k < intervals.length; k++) {
                intervals[k] = randomInterval(rand);
            }
            assertArrayEquals(Union.union(intervals), unionSolution(intervals));
        }
    }

    @Test(timeout = 11000)
    @Grade(value = 50)
    public void testComplexity() {
        final Union.Interval[] intervals = new Union.Interval[1000000];
        Random rand = new java.util.Random();
        for (int k = 0; k < intervals.length; k++) {
            int min = rand.nextInt(Integer.MAX_VALUE - 1000);
            intervals[k] = new Union.Interval(min, min + rand.nextInt(1000));
        }

        Union.Interval[] res = Union.union(intervals);
        assertArrayEquals(res, unionSolution(intervals));
    }
}
