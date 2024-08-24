package graphs;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import graphs.Electricity.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@Grade
public class ElectricityTest {
    @Test
    @Grade(value = 1)
    public void simpleTest() {
        int[][] edges = new int[][]{
                {0, 1, 5},
                {1, 2, 10},
                {0, 2, 20}
        };
        assertEquals(Electricity.minimumSpanningCost(3, edges), 15);
    }

    @Test
    @Grade(value = 1)
    public void complexGraphTest() {
        int[][] edges = new int[][]{
                {0, 1, 10},
                {1, 4, 8},
                {4, 2, 1},
                {2, 0, 7},
                {2, 5, 4},
                {2, 3, 8},
                {3, 5, 9},
                {5, 0, 1}};

        assertEquals(22, Electricity.minimumSpanningCost(6,edges));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 2000, unit = TimeUnit.MILLISECONDS)
    public void complexityTest1() {
        int n = 100000;
        int answer = 0;
        ArrayList<int []> edgeList = new ArrayList<>();

        HashSet<String> seen = new HashSet<>();

        Random r = new Random(0);

        for (int i = 0; i < n - 1; i++) {
            answer += 1;
            edgeList.add(new int[]{i, i + 1, 1});
            seen.add(i + " " + (i + 1));
            seen.add((i + 1) + " " + i);
        }
        for (int i = 0; i < n; i++) {
            int a = r.nextInt(n);
            int b = r.nextInt(n);
            if (a != b && !seen.contains(a + " " + b) && !seen.contains(b + " " + a)) {
                edgeList.add(new int[]{a, b, n + r.nextInt(1000)});
                seen.add(a + " " + b);
                seen.add(b + " " + a);
            }
        }
        int [][] edges = edgeList.toArray(new int[0][]);
        assertEquals(answer, Electricity.minimumSpanningCost(n,edges));
    }


}
