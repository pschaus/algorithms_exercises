package graphs;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;
import graphs.GalaxyPath;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;


@RunWith(Enclosed.class)
public class GalaxyPathTest {

    public static class TestNotParameterized {
        @Test
        @Grade(value = 1, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your algorithm, debug first this small with five galaxies", onFail = true)
        public void readableTestToDebug() {
            // Hint: feel free to duplicate and modify this test for your needs

            int[][] matrix = new int[5][5];

            matrix[0][1] = 1;
            matrix[0][2] = 1;
            matrix[1][3] = 3;
            matrix[2][4] = 1;
            matrix[3][4] = 3;

            HashSet<Integer> dest = new HashSet<>();
            dest.add(4);

            // The path 0->2->4 is not possible because when it arrives at 2,
            // the current time is 1 (duration for travelling through a by-pass) and the by-pass 2->4 is closed.
            // Therefore the only feasible path to reach the destination is 0 -> 1 -> 3 -> 4 (length of 3 expected)
            int len = GalaxyPath.findPath(matrix, 0, dest);
            assertEquals(3, len);

            // If we now set the delay for closing the by-pass 2->4 to 2, then the path 0 -> 2 -> 4 becomes feasible (length of 2 expected)
            matrix[2][4] = 2;
            len = GalaxyPath.findPath(matrix, 0, dest);
            assertEquals(2, len);
        }

    }

    @RunWith(Parameterized.class)
    @Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
    public static class TestParameterized {

        @Parameterized.Parameters(name = "{0}")
        public static Collection data() {
            LinkedList<Object []> coll = new LinkedList<>();
            for (int i = 0; i < 100; i++) {
                String name = "data/graphs.GalaxyPath/in_20_"+i;
                coll.add(new Object[] {name, new Instance(name)});
            }
            return coll;
        }

        final Instance instance;

        public TestParameterized(String name, Instance instance) {
            this.instance = instance;
        }

        @Test
        @Grade(value = 1, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your algorithm, hint: debug on the small with five galaxies, modify the example if necessary to find the bug", onFail=true)
        @GradeFeedback(message = "Are you sure your code is in O(#galaxies^2) ?", onTimeout=true)
        public void test() throws Exception {

            // Hint: feel free to duplicate and modify this test for your needs


            int[][] matrix = new int[5][5];

            matrix[0][1] = 1;
            matrix[0][2] = 1;
            matrix[1][3] = 3;
            matrix[2][4] = 1;
            matrix[3][4] = 3;

            HashSet<Integer> dest = new HashSet<>();
            dest.add(4);

            int len = GalaxyPath.findPath(matrix, 0, dest);
            assertEquals(3, len);

            matrix[2][4] = 2;
            len = GalaxyPath.findPath(matrix, 0, dest);
            assertEquals(2, len);


            assertEquals(instance.solution, GalaxyPath.findPath(instance.matrix, instance.from, instance.destination));
        }

        static class Instance {

            int[][] matrix;
            int from;
            Set<Integer> destination;
            int solution;

            public Instance(String file) {

                try {
                    Scanner dis = new Scanner(new FileInputStream(file));
                    int n = dis.nextInt();
                    matrix = new int[n][n];
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            matrix[i][j] = dis.nextInt();
                        }
                    }
                    from = dis.nextInt();
                    int nDest = dis.nextInt();
                    Set<Integer> dest = new HashSet<>();
                    for (int i = 0; i < nDest; i++) {
                        dest.add(dis.nextInt());
                    }
                    destination = Collections.unmodifiableSet(dest);
                    solution = dis.nextInt();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
