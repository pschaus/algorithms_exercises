package searching;

import org.junit.Test;

import com.github.guillaumederval.javagrading.Grade;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is just a limited number of tests provided for convenience
 * Don't hesitate to extend it with other tests
 */
public class LinearProbingHashSTTest {


    @Test
    public void testExample() {
        LinearProbingHashST<Integer,String> lp = new LinearProbingHashST<>();

        assertEquals(4,lp.capacity());
        assertEquals(0,lp.size());

        lp.put(5, "five");
        assertEquals("five",lp.get(5));
        assertEquals(1,lp.size());

        lp.put(9,"nine");
        assertEquals("nine",lp.get(9));
        assertEquals(2,lp.size());

        lp.put(9,"neuf");
        assertEquals("neuf",lp.get(9));
        assertEquals(2,lp.size());

        lp.put(8,"huit");
        assertEquals("huit",lp.get(8));
        assertEquals(3,lp.size());
        assertEquals(8,lp.capacity());


        lp.put(0,"zero");
        assertEquals("zero",lp.get(0));
        assertEquals(4,lp.size());
        assertEquals(8,lp.capacity());


        lp.put(16,"sixteen");
        assertEquals("sixteen",lp.get(16));
        assertEquals(5,lp.size());
        assertEquals(16,lp.capacity());


        assertTrue(lp.contains(5));
        assertTrue(lp.contains(9));


        assertTrue(!lp.contains(4));
        assertTrue(!lp.contains(32));
        assertTrue(!lp.contains(64));
    }



    @Test
    public void testCorrectness() {
        for (int size = 20; size < 1000; size += 10) {
            for (int k = 0; k < 10; k++) {

                Set<Integer> in = new HashSet<>();
                Set<Integer> out = new HashSet<>();

                LinearProbingHashST<Integer,String> lp = new LinearProbingHashST<>();

                Random rand = new Random();
                for (int i = 0; i < size; i++) {
                    int v = rand.nextInt();
                    in.add(v);
                    lp.put(v, v + "");
                }
                assertTrue(lp.size() >= lp.capacity()/4);
                assertTrue(lp.size() <= lp.capacity()/2);
                for (int i: in) {
                    assertTrue(lp.contains(i));
                    assertTrue(lp.get(i).equals(i+""));
                }
                for (int j = 0; j < size; j++) {
                    int v = rand.nextInt();
                    if (!in.contains(v)) {
                        assertTrue(!lp.contains(v));
                    }
                }
            }
        }
        correctnessTestBis();
    }

    @Test
    @Grade(value=50.0, cpuTimeout = 1000)
    public void testComplexity() {

        LinearProbingHashST<Integer,String> lp = new LinearProbingHashST<>();

        Random rand = new Random();

        for (int i = 0; i < 1000; i++) {
            int v = rand.nextInt();
            lp.put(v, v + "");
        }
        for (int i = 0; i < 10000; i++) {
            lp.put(i%1000, i%1000 + "");
        }
        for (int i = 0; i < 1000; i++) {
            int v = rand.nextInt();
            lp.put(v, v + "");
        }

    }

    //More tests
    private void correctnessTestBis() {

        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<>(1);

        String instance[] = generateInstance();

        int i = 0;
        for (String key : instance){
            st.put(key, i);
            i++;
        }

        //System.out.println("cap="+instance.capaity);
        i = 0;
        for (Object s : st.keys()) {
            assertEquals(new Integer(i),st.get(s+""));
            assertEquals(instance[i],s+"");
            i++;
        }

        assertTrue(i != 0);
    }

    private String[] generateInstance(){
        String[] sp = new String[]{"Aa", "BB"};
        String[] list = new String[8];

        int count = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    list[count] = sp[i] + sp[j] + sp[k];
                    count++;
                }
            }
        }

        return list;
    }
    
}
