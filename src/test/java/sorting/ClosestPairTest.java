package sorting;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
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

    @Test
    @Grade(value=1, cpuTimeout=1000)
    @Order(1)
    public void testExample() {
        int [] a = new int[]{5, 10, 1, 75, 150, 151, 155, 18, 75, 50, 30};
        assertTrue(isExpectedArray(ClosestPair.closestPair(a, 20), 10, 10));
        assertTrue(isExpectedArray(ClosestPair.closestPair(a, 153), 1, 151));
        assertTrue(isExpectedArray(ClosestPair.closestPair(a, 13), 1, 10));
        assertTrue(isExpectedArray(ClosestPair.closestPair(a, 140), 75, 75));
        assertTrue(isExpectedArray(ClosestPair.closestPair(a, 170), 18, 151));
    }

    @Test
    @Grade(value=1) 
    @Order(1)
    public void testClosestPair0() {
        int [] input = new int [] {-5};

        int x = 155;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(165,Math.abs(x-res[0]-res[1]));
    }

    @Test
    @Grade(value=1) 
    @Order(1)
    public void testClosestPair1() {
        int [] input = new int [] {5,10,1,150,151,155,18,50,30};

        int x = 155;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(x,res[0]+res[1]);
    }
 
    @Test
    @Grade(value=1) 
    @Order(1)
    public void testClosestPair2() {
        int [] input = new int [] {5,10,1,150,151,155,18,50,30};

        int x = 36;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(x,res[0]+res[1]);
    }

 
    @Test
    @Grade(value=1) 
    @Order(1)
    public void testClosestPair3() {
        int [] input = new int [] {5,10,1,150,151,155,18,50,30};

        int x = 13;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(2,Math.abs(x-res[0]-res[1]));

    }


    @Test
    @Grade(value=1) 
    @Order(1)
    public void testClosestPair4() {
        int [] input = new int [] {5,10,1,150,151,155,18,50,30};

        int x = 170;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(1,Math.abs(x-res[0]-res[1]));
    }

    @Test
    @Grade(value=1) 
    @Order(1)
    public void testClosestPair5() {
        int [] input = new int [] {5,10,1,150,151,155,18,50,30};

        int x = -1;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(3,Math.abs(x-res[0]-res[1]));
    }

    @Test
    @Grade(value=1) 
    @Order(1)
    public void testClosestPair6() {
        int [] input = new int [] {5,10,1,150,151,155,18,50,30};

        int x = 1000;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(690,Math.abs(x-res[0]-res[1]));
    }


    @Test
    @Grade(value=1) 
    @Order(1)
    public void testClosestPair7() {
        int [] input = new int [] {5,10,1,75,150,151,155,18,75,50,30};

        int x = 140;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(10,Math.abs(x-res[0]-res[1]));
    }
    
    static Stream<Instance> dataProvider() {
            return Stream.of(new File("data/sorting.ClosestPair").listFiles())
                .filter(file -> !file.isDirectory())
                .map(file -> new Instance(file.getPath()));
    }

    @ParameterizedTest
    @Grade(value=1, cpuTimeout=1000)
    @MethodSource("dataProvider")
    @Order(2)
    public void testComplexity(Instance instance) {
        ClosestPair.closestPair(instance.input.clone(), instance.query);
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
