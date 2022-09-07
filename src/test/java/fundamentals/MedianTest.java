package fundamentals;

import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;

import static org.junit.Assert.*;


public class MedianTest {

    public static int partition(Median.Vector a, int lo, int hi) {
        int i = lo, j = hi+1;
        int v = a.get(lo);
        while (true) {
            while (a.get(++i) < v) if (i == hi) break;
            while (v < a.get(--j)) if (j == lo) break;
            if (i >= j) break;
            a.swap(i,j);
        }
        a.swap(lo,j);
        return j;
    }

    public static int median(Median.Vector a, int lo, int hi) {
        int i = partition(a,lo,hi);
        if (i == a.size()/2) return a.get(i);
        else if (i < a.size()/2) {
            return median(a,i+1,hi);
        } else {
            return median(a,lo,i-1);
        }
    }

    public static void sort(Median.Vector a, int lo, int hi) {
        if (lo < hi) {
            int i = partition(a,lo,hi);
            sort(a,lo,i-1);
            sort(a,i+1,hi);
        }
    }

    public static Median.Vector randomVector(int n) {
        java.util.Random rand = new java.util.Random();
        int [] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = rand.nextInt(n);
        }
        Median.Vector v = new Median.Vector(array.length);
        for (int i = 0; i < v.size(); i++) {
            v.set(i,array[i]);
        }
        return v;
    }

    @Test
    @Grade(value = 30)
    public void testMedianOk() {
        for (int i = 100; i < 1000; i += 10) {
            Median.Vector v = randomVector(i+1);
            assertEquals("correct median value computed", Median.median(v, 0, v.size() - 1), median(v, 0, v.size() - 1));
        }
    }

    @Test
    @Grade(value = 50)
    public void testComplexityNLogNOk() {
        for (int i = 100; i < 2000000; i += 100000) {
            Median.Vector v1 = randomVector(i+1);
            Median.median(v1,0,v1.size()-1);

            Median.Vector v2 = randomVector(i+1);
            sort(v2,0,v2.size()-1);

            assertTrue("complexity larger than O(n.log(n))",v1.nOp() <= v2.nOp()*3);
        }
    }
    @Test
    @Grade(value = 20)
    public void testComplexityNOk() {
        for (int i = 100; i < 2000000; i += 100000) {
            Median.Vector v1 = randomVector(i+1);
            Median.median(v1,0,v1.size()-1);

            Median.Vector v2 = randomVector(i+1);
            median(v2,0,v2.size()-1);

            assertTrue("complexity larger than O(n) expected",v1.nOp() <= v2.nOp()*3);
        }
    }


}
