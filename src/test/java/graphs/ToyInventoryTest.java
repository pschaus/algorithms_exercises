package graphs;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

// BEGIN STRIP
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Named.named;
// END STRIP


@Grade
public class ToyInventoryTest {

    @Test
    @Grade(value = 1)
    public void testSimple() {
        String[][] relations = new String[3][2];
        relations[0][0] = "piano";
        relations[0][1] = "flute";
        relations[1][0] = "flute";
        relations[1][1] = "xylophone";
        relations[2][0] = "car";
        relations[2][1] = "helicopter";
        String[] occurrences_name = new String[]{"piano", "flute", "piano", "xylophone", "car", "helicopter"};
        int[] occurrences_count = new int[]{3, 2, 1, 5, 2, 3};
        String[] requests = new String[]{"piano", "flute", "helicopter", "teddybear"};
        int[] results = ToyInventory.answerRequests(relations, occurrences_name, occurrences_count, requests);
        assertArrayEquals(new int[]{11, 11, 5, 0}, results);
    }

    // BEGIN STRIP
    @Test
    @Grade(value = 1)
    public void testEmptyRequests() {
        String[][] relations = new String[3][2];
        relations[0][0] = "piano";
        relations[0][1] = "flute";
        relations[1][0] = "flute";
        relations[1][1] = "xylophone";
        relations[2][0] = "car";
        relations[2][1] = "helicopter";
        String[] occurrences_name = new String[]{"piano", "flute", "piano", "xylophone", "car", "helicopter"};
        int[] occurrences_count = new int[]{3, 2, 1, 5, 2, 3};
        String[] requests = new String[]{};
        int[] results = ToyInventory.answerRequests(relations, occurrences_name, occurrences_count, requests);
        assertArrayEquals(new int[]{}, results);
    }

    @Test
    @Grade(value = 1)
    public void testEmptyRelations() {
        String[][] relations = new String[0][2];
        String[] occurrences_name = new String[]{"piano", "flute", "piano", "xylophone", "car", "helicopter"};
        int[] occurrences_count = new int[]{3, 2, 1, 5, 2, 3};
        String[] requests = new String[]{"piano", "flute", "helicopter", "teddybear"};
        int[] results = ToyInventory.answerRequests(relations, occurrences_name, occurrences_count, requests);
        assertArrayEquals(new int[]{4, 2, 3, 0}, results);
    }

    @Test
    @Grade(value = 1)
    public void testEmptyCounts() {
        String[][] relations = new String[3][2];
        relations[0][0] = "piano";
        relations[0][1] = "flute";
        relations[1][0] = "flute";
        relations[1][1] = "xylophone";
        relations[2][0] = "car";
        relations[2][1] = "helicopter";
        String[] occurrences_name = new String[]{};
        int[] occurrences_count = new int[]{};
        String[] requests = new String[]{"piano", "flute", "helicopter", "teddybear"};
        int[] results = ToyInventory.answerRequests(relations, occurrences_name, occurrences_count, requests);
        assertArrayEquals(new int[]{0, 0, 0, 0}, results);
    }

    public static Instance[] instances;

    @BeforeAll
    public static void setUpClass() {
        Random rand = new Random(84363);
        instances = new Instance[10];
        for (int i = 0; i < 10; i++) {
            instances[i] = generateRandomInstance(100_000, rand);
        }
    }

    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    private static String randomString(Random rand) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            int index = rand.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }
        return sb.toString();
    }

    private static Instance generateRandomInstance(int n_objects, Random rand) {
        HashMap<String, Integer> map = new HashMap<>();
        while (map.size() < n_objects) {
            String randomString = randomString(rand);
            map.putIfAbsent(randomString, map.size());
        }
        ArrayList<String> names = new ArrayList<String>(map.keySet());

        SolutionUF uf = new SolutionUF(n_objects);

        int nOccurences = rand.nextInt(n_objects / 2) + n_objects / 2;
        String[] occurrences_name = new String[nOccurences];
        int[] occurrences_count = new int[nOccurences];
        for (int i = 0; i < nOccurences; i++) {
            String name = names.get(rand.nextInt(names.size()));
            int n = rand.nextInt(1000);
            occurrences_name[i] = name;
            occurrences_count[i] = n;
            uf.nb[map.get(name)] += n;
        }

        HashSet<String> link_set = new HashSet<>();
        ArrayList<String[]> links = new ArrayList<>();
        int n_families = (int) (n_objects * (rand.nextInt(61)+20)/100.0);
        while (uf.count != n_families) {
            String a = names.get(rand.nextInt(names.size()));
            String b = names.get(rand.nextInt(names.size()));
            if (a.equals(b) || link_set.contains(a + b) || link_set.contains(b + a)) {
                continue;
            }
            links.add(new String[]{a, b});
            link_set.add(a + b);
            link_set.add(b + a);
            uf.union(map.get(a), map.get(b));
        }

        String[][] relations = new String[links.size()][2];
        for (int i = 0; i < links.size(); i++) {
            relations[i] = links.get(i);
        }

        int n_requests = rand.nextInt(n_objects/2) + n_objects/2;
        String[] requests = new String[n_requests];
        int[] solutions = new int[n_requests];
        for (int i = 0; i < n_requests; i++) {
            requests[i] = names.get(rand.nextInt(names.size()));
            solutions[i] = uf.nb[uf.find(map.get(requests[i]))];
        }
        return new Instance(relations, occurrences_name, occurrences_count, requests, solutions);
    }

    public static Stream<Arguments> instanceProvider() {
        return IntStream.range(0, instances.length).mapToObj(i -> Arguments.of(named("Instance " + i, instances[i])));
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Hint: debug on a small example or try to reduce the complexity of your algorithm")
    @MethodSource("instanceProvider")
    public void test(Instance instance) {
        long nano_start = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();

        int[] results = ToyInventory.answerRequests(instance.relations, instance.occurrences_name, instance.occurrences_count, instance.requests);

        long nano_end = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
        assertTrue(nano_end - nano_start < 1_000_000_000, "Execution time is more than 1 CPU second");

        assertArrayEquals(instance.solution, results);
    }

    private static class Instance {
        public String[][] relations;
        public String[] occurrences_name;
        public int[] occurrences_count;
        public String[] requests;

        public int[] solution;

        public Instance(String[][] relations, String[] occurrences_name, int[] occurrences_count, String[] requests, int[] solution) {
            this.relations = relations;
            this.occurrences_name = occurrences_name;
            this.occurrences_count = occurrences_count;
            this.requests = requests;
            this.solution = solution;
        }
    }

    private static class SolutionUF {
        private int[] id;
        private int[] sz;
        private int[] nb;
        private int count;

        public SolutionUF(int n) {
            id = new int[n];
            sz = new int[n];
            nb = new int[n];
            for (int i = 0; i < n; i++) {
                id[i] = i;
                sz[i] = 1;
                nb[i] = 0;
            }
            count = n;
        }

        public int find(int p) {
            return id[p] == p ? p : (id[p] = find(id[p]));
        }

        public void union(int p, int q) {
            int i = find(p);
            int j = find(q);
            if (i == j) {
                return;
            }
            if (sz[i] < sz[j]) {
                id[i] = j;
                sz[j] += sz[i];
                nb[j] += nb[i];
            } else {
                id[j] = i;
                sz[i] += sz[j];
                nb[i] += nb[j];
            }
            count--;
        }
    }
    // END STRIP
}
