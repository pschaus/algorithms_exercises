package strings;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(Enclosed.class)
public class IncrementalHashTest {

    public static class TestNotParameterized {
        @Test
        @Grade(value = 1, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your algorithm, debug it first with this small example with M=2", onFail=true)
        public void readableTestToDebugWithLength2() {

            char [] input = new char[] {5,6,7,8,9,10};
            int Q = 10;
            int M = 2;
            int R = IncrementalHash.R;

            IncrementalHash hash = new IncrementalHash(Q, M);

            int [] expectedHash = new int[input.length-M];
            for (int from = 0; from < input.length-M ; from++) {
                expectedHash[from] = (input[from] * R + input[from+1]) % Q; // here M = 2, don't need a loop for that
            }

            int h = expectedHash[0]; // first hash (at from = 0)

            for (int i = 1; i < input.length-M;i++) {
                h = hash.nextHash(input,h,i);
                assertEquals(expectedHash[i], h);
            }
        }

        @Test
        @Grade(value = 1, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your algorithm, debug it first with this small example with M=3", onFail=true)
        public void readableTestToDebugWithLength3() {

            char [] input = new char[] {10,9,8,7,6,5};
            int Q = 20;
            int M = 3;
            int R = IncrementalHash.R;

            IncrementalHash hash = new IncrementalHash(Q, M);

            int [] expectedHash = new int[input.length-M];
            for (int from = 0; from < input.length-M ; from++) {
                expectedHash[from] = (input[from] * R * R + input[from+1] * R + input[from+2]) % Q; // here M = 3, don't need a loop for that
            }

            int h = expectedHash[0]; // first hash (at from = 0)

            for (int i = 1; i < input.length-M;i++) {
                h = hash.nextHash(input,h,i);
                assertEquals(expectedHash[i], h);
            }
        }
    }


    @RunWith(Parameterized.class)
    @Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
    public static class TestParameterizedSmall {

        @Parameterized.Parameters(name = "{0}")
        public static Collection data() {
            LinkedList<Object []> coll = new LinkedList<>();
            for (int i = 0; i < 6; i++) {
                String file = "data/strings.IncrementalHash/instance_"+i;
                coll.add(new Object[] {file, new Instance(file)});
            }
            return coll;
        }

        final Instance instance;

        public TestParameterizedSmall(String name, Instance instance) {
            this.instance = instance;
        }

        @Test
        @Grade(value = 1, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your algorithm, incorrect hash, debug on small provided example", onFail=true)
        public void test() throws Exception {
            IncrementalHash hash = new IncrementalHash(instance.Q, instance.M);
            int h = instance.hash[0];
            for (int i = 1; i < instance.size- instance.M; i++) {
                h = hash.nextHash(instance.input, h,i);
                assertEquals(instance.hash[i],h);
            }
        }
    }


    @RunWith(Parameterized.class)
    @Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
    public static class TestParameterizedLarge {

        @Parameterized.Parameters(name = "{0}")
        public static Collection data() {
            LinkedList<Object []> coll = new LinkedList<>();
            for (int i = 0; i < 6; i++) {
                String file = "data/strings.IncrementalHash/large_instance_"+i;
                coll.add(new Object[] {file, new Instance(file)});
            }
            return coll;
        }

        final Instance instance;

        public TestParameterizedLarge(String name, Instance instance) {
            this.instance = instance;
        }

        @Test
        @Grade(value = 1, cpuTimeout = 1000)
        @GradeFeedback(message = "Are you sure your code is in O(1) ?", onTimeout=true)
        @GradeFeedback(message = "Sorry, something is wrong with your algorithm, incorrect hash, debug on small provided example", onFail=true)
        public void test() throws Exception {
            IncrementalHash hash = new IncrementalHash(instance.Q, instance.M);
            int h = instance.hash[0];
            for (int i = 1; i < instance.size- instance.M; i++) {
                h = hash.nextHash(instance.input, h,i);
                assertEquals(instance.hash[i],h);
            }
        }
    }

    static class Instance {

        int[][] matrix;
        int from;
        Set<Integer> destination;
        int solution;

        int size;
        int Q;
        int M;
        int maxChar;

        char [] input;
        int [] hash;


        public Instance(String file) {
            try {
                Scanner dis = new Scanner(new FileInputStream(file));

                size = dis.nextInt();
                Q = dis.nextInt();
                M = dis.nextInt();
                maxChar = dis.nextInt();

                input = new char[size];
                hash = new int[size-M];

                for (int i = 0; i < size; i++) {
                    input[i] = (char) dis.nextInt();
                }
                for (int i = 0; i < size-M; i++) {
                    hash[i] = dis.nextInt();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
