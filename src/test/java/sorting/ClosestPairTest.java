package sorting;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;
import org.junit.runners.Parameterized;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.stream.Collectors;

@RunWith(Enclosed.class)
public class ClosestPairTest {

    private static boolean isExpectedArray(int [] result, int firstExpected, int secondExpected) {
        if (result.length != 2) {
            return false;
        }
        return (result[0] == firstExpected && result[1] == secondExpected) || (result[0] == secondExpected && result[1] == firstExpected);
    }

    private static boolean inArray(int [] input, int [] res) {
        boolean a = false;
        boolean b = false;
        for (int v : input) {
            a |= (v == res[0]);
            b |= (v == res[1]);
            if (a && b) return true;
        }
        return false;
    }

    public static class TestNotParameterized {

        @Test
        @Grade(value=1, cpuTimeout=1000)
        public void testExample() {
            int [] a = new int[]{5, 10, 1, 75, 150, 151, 155, 18, 75, 50, 30};
            assertTrue(isExpectedArray(ClosestPair.closestPair(a, 20), 10, 10));
            assertTrue(isExpectedArray(ClosestPair.closestPair(a, 153), 1, 151));
            assertTrue(isExpectedArray(ClosestPair.closestPair(a, 13), 1, 10));
            assertTrue(isExpectedArray(ClosestPair.closestPair(a, 140), 75, 75));
            assertTrue(isExpectedArray(ClosestPair.closestPair(a, 170), 18, 151));
        }


        @Test(timeout=300)
        @Grade(value=1) 
        public void testClosestPair0() {
            int [] input = new int [] {-5};

            int x = 155;

            int [] res = ClosestPair.closestPair(input.clone(),x);
            assertTrue(inArray(input,res));
            assertEquals(2,res.length);
            assertEquals(165,Math.abs(x-res[0]-res[1]));
        }


     
        @Test(timeout=300)
        @Grade(value=1) 
        public void testClosestPair1() {
            int [] input = new int [] {5,10,1,150,151,155,18,50,30};

            int x = 155;

            int [] res = ClosestPair.closestPair(input.clone(),x);
            assertTrue(inArray(input,res));
            assertEquals(2,res.length);
            assertEquals(x,res[0]+res[1]);
        }


     
        @Test(timeout=300)
        @Grade(value=1) 
        public void testClosestPair2() {
            int [] input = new int [] {5,10,1,150,151,155,18,50,30};

            int x = 36;

            int [] res = ClosestPair.closestPair(input.clone(),x);
            assertTrue(inArray(input,res));
            assertEquals(2,res.length);
            assertEquals(x,res[0]+res[1]);
        }

     
        @Test(timeout=300)
        @Grade(value=1) 
        public void testClosestPair3() {
            int [] input = new int [] {5,10,1,150,151,155,18,50,30};

            int x = 13;

            int [] res = ClosestPair.closestPair(input.clone(),x);
            assertTrue(inArray(input,res));
            assertEquals(2,res.length);
            assertEquals(2,Math.abs(x-res[0]-res[1]));

        }


        @Test(timeout=300)
        @Grade(value=1) 
        public void testClosestPair4() {
            int [] input = new int [] {5,10,1,150,151,155,18,50,30};

            int x = 170;

            int [] res = ClosestPair.closestPair(input.clone(),x);
            assertTrue(inArray(input,res));
            assertEquals(2,res.length);
            assertEquals(1,Math.abs(x-res[0]-res[1]));
        }

        @Test(timeout=300)
        @Grade(value=1) 
        public void testClosestPair5() {
            int [] input = new int [] {5,10,1,150,151,155,18,50,30};

            int x = -1;

            int [] res = ClosestPair.closestPair(input.clone(),x);
            assertTrue(inArray(input,res));
            assertEquals(2,res.length);
            assertEquals(3,Math.abs(x-res[0]-res[1]));
        }

        @Test(timeout=300)
        @Grade(value=1) 
        public void testClosestPair6() {
            int [] input = new int [] {5,10,1,150,151,155,18,50,30};

            int x = 1000;

            int [] res = ClosestPair.closestPair(input.clone(),x);
            assertTrue(inArray(input,res));
            assertEquals(2,res.length);
            assertEquals(690,Math.abs(x-res[0]-res[1]));
        }


        @Test(timeout=300)
        @Grade(value=1) 
        public void testClosestPair7() {
            int [] input = new int [] {5,10,1,75,150,151,155,18,75,50,30};

            int x = 140;

            int [] res = ClosestPair.closestPair(input.clone(),x);
            assertTrue(inArray(input,res));
            assertEquals(2,res.length);
            assertEquals(10,Math.abs(x-res[0]-res[1]));
        }
    }

    @RunWith(Parameterized.class)
    @Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
    public static class TestComplexity {

        @Parameterized.Parameters(name="{0}")
        public static Collection data() {
            return Stream.of(new File("data/sorting.ClosestPair").listFiles())
                .filter(file -> !file.isDirectory())
                .map(file -> new Object [] { file.getName(), new Instance(file.getPath()) })
                .collect(Collectors.toList());
        }

        final Instance instance;

        public TestComplexity(String name, Instance instance) {
            this.instance = instance;
        }

        @Test
        @Grade(value=1, cpuTimeout=1000)
        public void test() {
            int [] r = ClosestPair.closestPair(this.instance.input.clone(), this.instance.query);
            assertTrue(r[0] + r[1] == this.instance.result[0] + this.instance.result[1] && inArray(this.instance.input, r));
        }
    }

    private static class Instance {
        int [] input;
        int query;
        int [] result;

        public Instance(String file) {
            try {
                Scanner scan = new Scanner(new FileInputStream(file));
                int n = scan.nextInt();
                this.input = new int[n];
                for (int i = 0; i < n; i++) {
                    this.input[i] = scan.nextInt();
                }
                this.query = scan.nextInt();
                this.result = new int[2];
                result[0] = scan.nextInt();
                result[1] = scan.nextInt();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
