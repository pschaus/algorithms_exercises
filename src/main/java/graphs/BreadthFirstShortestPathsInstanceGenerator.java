package graphs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class BreadthFirstShortestPathsInstanceGenerator {

    public static void main(String[] args) {

        Random rand = new Random(50);
        int lo = 30;
        int hi = 50;

        for (int i = 0; i < 15; i++) {
            int nEdges = rand.nextInt( 200) + 100;
            int nNodes = rand.nextInt(hi - lo) + lo;
            List<Integer> nodelist = new ArrayList<>();
            for (int x = 0; x < nNodes; x++){
                nodelist.add(x);
            }

            int nSources = rand.nextInt(6) + 4;
            int nDest = rand.nextInt(20);


            List<Integer> sources = getRandomElement(nodelist, nSources);
            List<Integer> destinations = getRandomElement(nodelist, nDest);



            BreadthFirstShortestPaths.Graph g = generateRandomGraph(nNodes, nEdges);

            BreadthFirstShortestPaths bfs = new BreadthFirstShortestPaths(g, sources);
            int[] solutions = new int[nDest];
            for (int j = 0; j < nDest; j++) {
                solutions[j] = bfs.distTo(destinations.get(j));
            }
            writeInstance("data/graphs.BreadthFirstShortestPaths/in_1_" + i , g,   sources, destinations, solutions);

        }

        for (int i = 0; i < 4; i++) {
            int nEdges = rand.nextInt( 200) + 600;
            int nNodes = 300;
            List<Integer> nodelist = new ArrayList<>();
            for (int x = 0; x < nNodes; x++){
                nodelist.add(x);
            }

            int nSources = rand.nextInt(6) + 4;
            int nDest = rand.nextInt(20);

            List<Integer> sources = getRandomElement(nodelist, nSources);
            List<Integer> destinations = getRandomElement(nodelist, nDest);



            BreadthFirstShortestPaths.Graph g = generateRandomGraph(nNodes, nEdges);

            BreadthFirstShortestPaths bfs = new BreadthFirstShortestPaths(g, sources);
            int[] solutions = new int[nDest];
            for (int j = 0; j < nDest; j++) {
                solutions[j] = bfs.distTo(destinations.get(j));
            }
            writeInstance("data/graphs.BreadthFirstShortestPaths/in_2_" + i , g,   sources, destinations, solutions);

        }

    }


    public static BreadthFirstShortestPaths.Graph generateRandomGraph(int n, int nEdges) {

        BreadthFirstShortestPaths.Graph randomGraph = new BreadthFirstShortestPaths.Graph(n);

        for (int i = 0; i < nEdges; i++) {
            int v = (int) (randomGraph.V() * Math.random());
            int w = (int) (randomGraph.V() * Math.random());
            randomGraph.addEdge(v, w);
        }

        return randomGraph;
    }

    public static void writeInstance(String file, BreadthFirstShortestPaths.Graph graph, List<Integer> sources, List<Integer> dest, int[] solution) {

        int V = graph.V();
        int E = graph.E();
        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(file));
            p.println(V + " " + E);
            for (int i= 0; i < V; i++){
                Iterable<Integer> adj = graph.adj(i);
                for(int e: adj){
                    p.println(i+" "+e);
                }
            }
            p.println(sources.size());
            for (Integer source : sources) {
                p.println(source);
            }
            p.println(dest.size());
            for (Integer d : dest) {
                p.println(d);
            }
            p.println(solution.length);
            for (int d : solution) {
                p.println(d);
            }
            p.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static List<Integer>
    getRandomElement(List<Integer> list, int totalItems)
    {
        Random rand = new Random();

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

}


