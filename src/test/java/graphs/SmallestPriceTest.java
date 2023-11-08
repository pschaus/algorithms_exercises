package graphs;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;



@Grade
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SmallestPriceTest {


    @Test
    @Order(0)
    @Grade(value = 1, cpuTimeout = 1000)
    public void smallGraph() {

        SmallestPrice.WeightedGraph graph = new SmallestPrice.WeightedGraph(6);
        graph.addEdge(new SmallestPrice.DirectedEdge(0, 1, 1));
        graph.addEdge(new SmallestPrice.DirectedEdge(0, 3, 2));
        graph.addEdge(new SmallestPrice.DirectedEdge(1, 2, 1));
        graph.addEdge(new SmallestPrice.DirectedEdge(3, 2, 1));
        graph.addEdge(new SmallestPrice.DirectedEdge(3, 4, 2));
        graph.addEdge(new SmallestPrice.DirectedEdge(2, 5, 4));
        graph.addEdge(new SmallestPrice.DirectedEdge(4, 5, 1));

        int source = 0;

        ArrayList<SmallestPrice.Pair> dests = new ArrayList<>();
        dests.add(new SmallestPrice.Pair(5, 40));
        dests.add(new SmallestPrice.Pair(4, 60));

        int[] costs = new int[]{3, 5, 4};
        int[] expectedPrices = new int[]{-1, 40, 60};

        for (int i = 0; i < costs.length; i++) {
            assertEquals(expectedPrices[i], SmallestPrice.getSmallestPrice(graph, source, costs[i], dests));
        }

    }

    // BEGIN STRIP


    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 1000)
    @Order(1)
    @MethodSource("dataProvider")
    @GradeFeedback(message = "Your method does not return the good value")
    public void testRandomGraph(Instance instance) {
        for (int i = 0; i < instance.solutions.size(); i++) {
            int source = instance.sources.get(i);
            int cost = instance.costs.get(i);
            int solution = instance.solutions.get(i);
            assertEquals(solution, SmallestPrice.getSmallestPrice(instance.graph, source, cost, instance.destinations));
        }
    }
    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    @Order(1)
    @GradeFeedback(message = "Your method does not return the good value")
    public void testSpecialGraph() {
        String name = "data/graphs.SmallestPrice/in_special";
        Instance instance = new Instance(name);
        for (int i = 0; i < instance.solutions.size(); i++) {
            int source = instance.sources.get(i);
            int cost = instance.costs.get(i);
            int solution = instance.solutions.get(i);
            assertEquals(solution, SmallestPrice.getSmallestPrice(instance.graph, source, cost, instance.destinations));
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1200)
    @Order(2)
    public void complexityTest() {
        ArrayList<Instance> instances = generateInstances();
        for (Instance instance : instances) {
            for (int j = 0; j < instance.solutions.size(); j++) {
                int source = instance.sources.get(j);
                int cost = instance.costs.get(j);
                int solution = instance.solutions.get(j);
                assertEquals(solution, SmallestPrice.getSmallestPrice(instance.graph, source, cost, instance.destinations));
            }
        }
    }





    // Helpers for parameterized and complexity tests
    static class Instance {

        SmallestPrice.WeightedGraph graph;
        List<Integer> sources;
        List<SmallestPrice.Pair> destinations;
        List<Integer> costs;
        List<Integer> solutions;

        public Instance(String file) {
            try {

                Scanner dis = new Scanner(new FileInputStream(file));
                String line = dis.nextLine();
                String[] base_info = line.split(" ");
                graph = new SmallestPrice.WeightedGraph(Integer.parseInt(base_info[0]));
                int E = Integer.parseInt(base_info[1]);
                for (int i = 0; i < E; i++) {
                    line = dis.nextLine();
                    String[] edge = line.split(" ");
                    int u = Integer.parseInt(edge[0]);
                    int v = Integer.parseInt(edge[1]);
                    int w = Integer.parseInt(edge[2]);
                    graph.addEdge(new SmallestPrice.DirectedEdge(u, v, w));
                }

                int nSources = dis.nextInt();
                sources = new ArrayList<>();
                for (int i = 0; i < nSources; i++) {
                    sources.add(dis.nextInt());
                }

                int nCosts = dis.nextInt();
                costs = new ArrayList<>();
                for (int i = 0; i < nCosts; i++) {
                    costs.add(dis.nextInt());
                }
                int nDests = dis.nextInt();
                dis.nextLine();
                destinations = new ArrayList<>();
                for (int i = 0; i < nDests; i++) {
                    line = dis.nextLine();
                    String[] pair = line.split(" ");
                    destinations.add(new SmallestPrice.Pair(Integer.parseInt(pair[0]), Integer.parseInt(pair[1])));
                }

                int nSols = dis.nextInt();
                solutions = new ArrayList<>();
                for (int i = 0; i < nSols; i++) {
                    solutions.add(dis.nextInt());
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public Instance(SmallestPrice.WeightedGraph graph, List<Integer> sources, List<SmallestPrice.Pair> destinations, List<Integer> costs, List<Integer> solutions) {
            this.graph = graph;
            this.sources = sources;
            this.destinations = destinations;
            this.costs = costs;
            this.solutions = solutions;
        }

    }

    static Stream<Instance> dataProvider() {
        return IntStream.range(0, 15).mapToObj(i -> new Instance("data/graphs.SmallestPrice/in_1_" + i));
    }

    class SolNode implements Comparable<SolNode> {
        private final int node;
        private final int weight;

        public SolNode(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        public int getNode() {
            return node;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(SolNode o) {
            return weight - o.weight;
        }
    }


    public int randInt(Random rand, int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public  List<Integer> getRandomElements(List<Integer> list, int totalItems) {
        Random rand = new Random(456464);

        // create a temporary list for storing
        // selected element
        List<Integer> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {

            // take a random index between 0 to size
            // of given List
            int randomIndex = rand.nextInt(list.size());

            // add element in temporary list
            newList.add(list.get(randomIndex));

            // Remove selected element from original list
            list.remove(randomIndex);
        }
        return newList;
    }

    public int expected(SmallestPrice.WeightedGraph graph, int source, int maxTime, List<SmallestPrice.Pair> destinations) {

        int minCost = Integer.MAX_VALUE;

        int[] distTo = new int[graph.V()];
        Arrays.fill(distTo, Integer.MAX_VALUE);

        HashMap<Integer, Integer> destinationsMap = new HashMap<>();
        for (SmallestPrice.Pair pair : destinations) {
            destinationsMap.put(pair.getNode(), pair.getPrice());
        }

        PriorityQueue<SolNode> queue = new PriorityQueue<>();
        queue.add(new SolNode(source, 0));
        distTo[source] = 0;

        while (!queue.isEmpty()) {
            SolNode node = queue.poll();
            int u = node.getNode();
            Integer value = destinationsMap.get(u);
            if (value != null) {
                minCost = Math.min(value, minCost);
                destinationsMap.remove(u);
            }
            if (destinationsMap.isEmpty()) {
                break;
            }

            for (SmallestPrice.DirectedEdge edge : graph.outEdges(node.getNode())) {
                int w = edge.to();
                int newPathCost = distTo[u] + edge.weight();
                if (distTo[w] > newPathCost && newPathCost <= maxTime) {
                    distTo[edge.to()] = newPathCost;
                    queue.add(new SolNode(w, newPathCost));
                }
            }
        }
        if (minCost == Integer.MAX_VALUE) {
            return -1;
        }
        return minCost;

    }

    public SmallestPrice.WeightedGraph generateRandomGraph(int V, int E, int maxWeight) {
        SmallestPrice.WeightedGraph graph = new SmallestPrice.WeightedGraph(V);
        Random random = new Random(56256);
        for (int i = 0; i < E; i++) {
            int v = random.nextInt(V);
            int w = random.nextInt(V);
            int weight = random.nextInt(maxWeight);
            SmallestPrice.DirectedEdge e = new SmallestPrice.DirectedEdge(v, w, weight);
            graph.addEdge(e);
        }
        return graph;
    }

    public ArrayList<Instance> generateInstances() {
        int nbInstances = 5;
        int subInstances = 5;
        Random random = new Random();
        ArrayList<Instance> instances = new ArrayList<>();
        for (int i = 0; i < nbInstances; i++) {
            int nNodes = randInt(random, 100000, 150000);
            int nEdges = nNodes;

            List<Integer> nodesList = new ArrayList<>();
            for (int x = 0; x < nNodes; x++) {
                nodesList.add(x);
            }

            List<Integer> sources = new ArrayList<>();
            List<Integer> costs = new ArrayList<>();
            List<Integer> solutions = new ArrayList<>();

            int nDests = randInt(random, 5000, 10000);
            List<Integer> destinations = getRandomElements(nodesList, nDests);

            List<SmallestPrice.Pair> dests = new ArrayList<>();
            int price = randInt(random, 10, 500);
            for (Integer destination : destinations) {
                dests.add(new SmallestPrice.Pair(destination, price));
            }

            int maxCost = randInt(random, 1, 10);
            SmallestPrice.WeightedGraph graph = generateRandomGraph(nNodes, nEdges, maxCost);

            for (int j = 0; j < subInstances; j++) {
                int cost = Integer.MAX_VALUE;
                costs.add(cost);
                int source = randInt(random, 0, nNodes - 1);
                sources.add(source);

                int solution = expected(graph, source, cost, dests);
                solutions.add(solution);
            }

            Instance inst = new Instance(graph, sources, dests, costs, solutions);
            instances.add(inst);


        }
        return instances;

    }

    // END STRIP
}
