package strings;

import org.junit.Test;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.lang.management.*;

/**
 * This is just a limited number of tests provided for convenience
 * Don't hesitate to extend it with other tests
 */
public class LinearProbingHashSTTest {

    public HashMap<Integer,Integer> uniqInteger = null;
    Integer gui(int x) {
        if(uniqInteger == null)
            uniqInteger = new HashMap<>();
        if(uniqInteger.containsKey(x))
            return uniqInteger.get(x);
        Integer y = x;
        uniqInteger.put(y,y);
        return y;
    }

    static class LinearProbingHashSTGetTest<Key, Value> extends LinearProbingHashST<Key, Value> {

        public LinearProbingHashSTGetTest() {
            super();
        }

        public LinearProbingHashSTGetTest(int capacity) {
            super(capacity);
        }

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("first argument to put() is null");

            // double table size if 50% full
            if (n >= m/2) resize(2*m);

            int i;

            for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
                if (keys[i].equals(key)) {
                    vals[i] = val;
                    return;
                }
            }
            keys[i] = key;
            vals[i] = val;
            n++;
        }



        protected void resize(int capacity) {
            Key[] keysTmp = (Key[])   new Object[capacity];
            Value[] valsTmp = (Value[]) new Object[capacity];

            for (int i = 0; i < m; i++) {
                if (keys[i] != null) {
                    int j = (keys[i].hashCode() & 0x7fffffff) % capacity;
                    for (; keysTmp[j] != null; j = (j + 1) % capacity) {
                        if (keysTmp[j].equals(keys[i])) {
                            valsTmp[j] = vals[i];
                            return;
                        }
                    }
                    keysTmp[j] = keys[i];
                    valsTmp[j] = vals[i];
                }
            }
            keys = keysTmp;
            vals = valsTmp;
            m    = capacity;
        }

    }

    static class LinearProbingHashSTPutTest<Key, Value> extends LinearProbingHashST<Key, Value> {

        public LinearProbingHashSTPutTest() {
            super();
        }

        public LinearProbingHashSTPutTest(int capacity) {
            super(capacity);
        }


        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("argument to get() is null");
            for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
                if (keys[i].equals(key))
                    return vals[i];
            return null;
        }


        protected void resize(int capacity) {
            Key[] keysTmp = (Key[])   new Object[capacity];
            Value[] valsTmp = (Value[]) new Object[capacity];


            for (int i = 0; i < m; i++) {
                if (keys[i] != null) {
                    int j = (keys[i].hashCode() & 0x7fffffff) % capacity;
                    for (; keysTmp[j] != null; j = (j + 1) % capacity) {
                        if (keysTmp[j].equals(keys[i])) {
                            valsTmp[j] = vals[i];
                            return;
                        }
                    }
                    keysTmp[j] = keys[i];
                    valsTmp[j] = vals[i];
                }
            }
            keys = keysTmp;
            vals = valsTmp;
            m    = capacity;
        }


    }



    @Test(timeout = 5000)
    public void testExample() {
        LinearProbingHashST<Integer,String> lp = new LinearProbingHashST<>();

        assertEquals(4,lp.capacity());
        assertEquals(0,lp.size());

        lp.put(gui(5),"five");
        assertEquals("five",lp.get(gui(5)));
        assertEquals(1,lp.size());

        lp.put(gui(9),"nine");
        assertEquals("nine",lp.get(gui(9)));
        assertEquals(2,lp.size());

        lp.put(gui(9),"neuf");
        assertEquals("neuf",lp.get(gui(9)));
        assertEquals(2,lp.size());

        lp.put(gui(8),"huit");
        assertEquals("huit",lp.get(gui(8)));
        assertEquals(3,lp.size());
        assertEquals(8,lp.capacity());

        lp.put(gui(0),"zero");
        assertEquals("zero",lp.get(gui(0)));
        assertEquals(4,lp.size());
        assertEquals(8,lp.capacity());

        lp.put(gui(16),"sixteen");
        assertEquals("sixteen",lp.get(gui(16)));
        assertEquals(5,lp.size());
        assertEquals(16,lp.capacity());

        assertTrue(lp.contains(gui(5)));
        assertTrue(lp.contains(gui(9)));


        assertTrue(!lp.contains(gui(4)));
        assertTrue(!lp.contains(gui(32)));
        assertTrue(!lp.contains(gui(64)));

        //staticTestJohn();
    }


    @Test(timeout = 5000)
    public void getPutWorkingNoResize() {
        Random rand = new Random(728972);

        for (int size = 20; size < 1000; size += 31) {
            for (int k = 0; k < 5; k++) {

                Set<Integer> in = new HashSet<>();
                Set<Integer> out = new HashSet<>();

                LinearProbingHashST<Integer,String> lp = new LinearProbingHashST<Integer,String>(size*3);

                for (int i = 0; i < size; i++) {
                    Integer v = gui(rand.nextInt());
                    in.add(v);
                    lp.put(v, v + "");
                }

                for (Integer i: in) {
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
    }

    @Test(timeout = 5000)
    public void putWorking() {
        Random rand = new Random(228901);
        for (int size = 20; size < 1000; size += 25) {
            for (int k = 0; k < 5; k++) {

                Set<Integer> in = new HashSet<>();
                Set<Integer> out = new HashSet<>();

                LinearProbingHashSTPutTest<Integer,String> lp = new LinearProbingHashSTPutTest<Integer,String>();


                for (int i = 0; i < size; i++) {
                    Integer v = gui(rand.nextInt());
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
    }

    @Test(timeout = 5000)
    public void putNoResizeWorking() {
        Random rand = new Random(292082);
        for (int size = 20; size < 1000; size += 27) {
            for (int k = 0; k < 5; k++) {

                Set<Integer> in = new HashSet<>();
                Set<Integer> out = new HashSet<>();

                LinearProbingHashSTPutTest<Integer,String> lp = new LinearProbingHashSTPutTest<Integer,String>(size*2+1);

                for (int i = 0; i < size; i++) {
                    Integer v = gui(rand.nextInt());
                    if(!in.contains(v)) {
                        in.add(v);
                        lp.put(v, v + "");
                    }
                }

                for (Integer i: in) {
                    assertTrue(lp.contains(i));
                    assertTrue(lp.get(i).equals(i+""));
                }
                for (int j = 0; j < size; j++) {
                    Integer v = gui(rand.nextInt());
                    if (!in.contains(v)) {
                        assertTrue(!lp.contains(v));
                    }
                }
            }
        }
    }

    @Test(timeout = 5000)
    public void putNoResizeWorking2() {
        Random rand = new Random(292082);
        for (int size = 20; size < 1000; size += 27) {
            for (int k = 0; k < 5; k++) {

                Set<Integer> in = new HashSet<>();
                Set<Integer> out = new HashSet<>();

                LinearProbingHashSTPutTest<Integer,String> lp = new LinearProbingHashSTPutTest<Integer,String>(size*2+1);

                for (int i = 0; i < size; i++) {
                    Integer v = gui(rand.nextInt());
                    in.add(v);
                    lp.put(v, v + "");
                }

                for (Integer i: in) {
                    lp.put(i, i + "2");
                }

                for (Integer i: in) {
                    assertTrue(lp.contains(i));
                    assertTrue(lp.get(i).equals(i+"2"));
                }
                for (int j = 0; j < size; j++) {
                    Integer v = gui(rand.nextInt());
                    if (!in.contains(v)) {
                        assertTrue(!lp.contains(v));
                    }
                }
            }
        }
    }

    @Test(timeout = 5000)
    public void getWithoutPutWorking() {
        Random rand = new Random(3980280);
        for (int size = 20; size < 1000; size += 41) {
            for (int k = 0; k < 5; k++) {

                Set<Integer> in = new HashSet<>();
                Set<Integer> out = new HashSet<>();

                LinearProbingHashSTGetTest<Integer,String> lp = new LinearProbingHashSTGetTest<Integer,String>(size * 2+1);



                for (int i = 0; i < size; i++) {
                    Integer v = gui(rand.nextInt());
                    in.add(v);
                    lp.put(v, v + "");
                }

                for (Integer i: in) {
                    assertTrue(lp.contains(i));
                    assertTrue(lp.get(i).equals(i+""));
                }
                for (int j = 0; j < size; j++) {
                    Integer v = gui(rand.nextInt());
                    if (!in.contains(v)) {
                        assertTrue(!lp.contains(v));
                    }
                }
            }
        }
    }


    @Test(timeout = 5000)
    public void testCorrectness() {
        Random rand = new Random(1792827);

        for (int size = 20; size < 1000; size += 50) {
            for (int k = 0; k < 5; k++) {

                Set<Integer> in = new HashSet<>();
                Set<Integer> out = new HashSet<>();

                LinearProbingHashST<Integer,String> lp = new LinearProbingHashST<>();



                for (int i = 0; i < size; i++) {
                    Integer v = gui(rand.nextInt());
                    in.add(v);
                    lp.put(v, v + "");

                }
                assertTrue(lp.size() >= lp.capacity()/4);
                assertTrue(lp.size() <= lp.capacity()/2);

                for (Integer i: in) {
                    assertTrue(lp.contains(i));
                    assertTrue(lp.get(i).equals(i+""));
                }
                for (int j = 0; j < size; j++) {
                    Integer v = gui(rand.nextInt());
                    if (!in.contains(v)) {
                        assertTrue(!lp.contains(v));
                    }
                }
            }
        }

        //correctnessTestJohn();
    }

    static class MyInteger {
        public int content;
        public MyInteger(int x) {
            content = x;
        }
        public boolean equals(Object x) {
            return x instanceof MyInteger && ((MyInteger)x).content == content;
        }
        @Override
        public int hashCode() {
            return content;
        }
    }

    @Test(timeout = 5000)
    public void testCorrectnessMI() {
        Random rand = new Random(1792827);

        for (int size = 20; size < 1000; size += 50) {
            for (int k = 0; k < 5; k++) {

                Set<Integer> in = new HashSet<>();
                Set<Integer> out = new HashSet<>();

                LinearProbingHashST<MyInteger,String> lp = new LinearProbingHashST<>();



                for (int i = 0; i < size; i++) {
                    Integer v = rand.nextInt();
                    in.add(v);
                    lp.put(new MyInteger(v), v + "");

                }
                assertTrue(lp.size() >= lp.capacity()/4);
                assertTrue(lp.size() <= lp.capacity()/2);

                for (Integer i: in) {
                    assertTrue(lp.contains(new MyInteger(i)));
                    assertTrue(lp.get(new MyInteger(i)).equals(i+""));
                }
                for (int j = 0; j < size; j++) {
                    int v = rand.nextInt();
                    if (!in.contains(v)) {
                        assertTrue(!lp.contains(new MyInteger(v)));
                    }
                }
            }
        }

        //correctnessTestJohn();
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

    @Test(timeout = 10000)
    public void testComplexity() {
        ThreadMXBean thread = ManagementFactory.getThreadMXBean();
        long cpu = thread.getCurrentThreadCpuTime();

        testExample();
        testCorrectness();

        LinearProbingHashST<Integer,String> lp = new LinearProbingHashST<>();

        Random rand = new Random(567892);

        for (int i = 0; i < 1000; i++) {
            Integer v = gui(rand.nextInt());
            lp.put(v, v + "");
        }
        for (int i = 0; i < 100000; i++) {
            lp.put(gui(i%10000), i%10000 + "");
        }
        for (int i = 0; i < 1000; i++) {
            Integer v = gui(rand.nextInt());
            lp.put(v, v + "");
        }

        assertTrue(thread.getCurrentThreadCpuTime() - cpu <= 2L * 1000000000L);
    }

    ///John test
    private void correctnessTestJohn() {

        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<>(7);

        String instance[] = generateInstance();
        Integer expectedVals[] = {12, 13, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 14, 15};

        int i = 0;
        for (String key : instance){
            st.put(key, i);
            i++;
        }

        //System.out.println("cap="+instance.capaity);
        i = 0;
        for (Object s : st.keys()) {
            assertEquals(expectedVals[i],st.get(s+""));
            assertEquals(instance[expectedVals[i]],s+"");
            i++;
        }

        assertTrue(i != 0);
    }

    private String[] generateInstance(){
        String[] sp = new String[]{"Aa", "BB"};
        String[] list = new String[16];

        int count = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l < 2; l++) {
                        list[count] = sp[i] + sp[j] + sp[k] + sp[l];
                        count++;
                    }
                }
            }
        }

        return list;
    }

    private void staticTestJohn() {
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<>(11);
        String str = "S E A R C H E X A M P L E";
        String keys[] =  str.split(" ");

        //String expectedKeys = "X C E H L M P R S A ";
        //String expectedVals = "7 4 12 5 11 9 10 3 0 8 ";

        int i = 0;
        for (String key : keys){
            st.put(key, i);
            i++;
        }

        // print keys
        //String obtainedKeys = "";
        //String obtainedVals = "";
        for (Object s : st.keys()) {
            //obtainedKeys += s + " ";
            //obtainedVals += st.get(""+s) + " ";
            assertEquals(keys[Integer.parseInt(st.get(""+s)+"")],s+"");
        }

        //assertEquals(obtainedKeys,expectedKeys);
        ///assertEquals(obtainedVals,expectedVals);

    }

}
