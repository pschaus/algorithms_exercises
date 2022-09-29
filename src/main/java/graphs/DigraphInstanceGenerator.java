package graphs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class DigraphInstanceGenerator {

    public static void main(String[] args) {

        int [] nNodes = new int[]{10, 20, 30, 50, 100, 200};
        for (int i = 0; i < nNodes.length; i++) {
            Digraph randomGraph = generateRandomDigraph(nNodes[i]);
            writeInstance("data/graphs.Digraph/in_rand_dig_"+i, randomGraph);
            writeInstance("data/graphs.Digraph/in_rand_rev_"+i, randomGraph.reverse());
        }
        nNodes = new int[]{10000, 5000, 7500, 12500};
        for (int i = 0; i < nNodes.length; i++) {
            Digraph randomGraph = generateWeirdComplexGraphs(nNodes[i]);
            writeInstance("data/graphs.Digraph/in_comp_dig_"+i, randomGraph);
            writeInstance("data/graphs.Digraph/in_comp_rev_"+i, randomGraph.reverse());
        }
    }

    public static void writeInstance(String file, Digraph digraph) {

        int V = digraph.V();
        int E = digraph.E();

        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(file));
            p.println(V);
            p.println(E);
            for (int i = 0; i < V; i++) {
                ArrayList<Integer> adj = (ArrayList<Integer>) digraph.adj(i);
                p.println(i);
                p.println(adj.size());
                for(int node: adj){
                    p.println(node);
                }
            }
            p.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static Digraph generateWeirdComplexGraphs(int n) {

        Random rand = new Random(5412);

        boolean modulo = rand.nextBoolean();

        Digraph digraph = new Digraph(n);

        if(modulo) {
            for (int i = 0; i < n; i++) {
                digraph.adj((i + 1) % n);
            }
        }
        else{
            for (int k = 1; k < n; k++) {
                digraph.addEdge(0, k);
            }
        }

        return digraph;
    }


    public static Digraph generateRandomDigraph(int n) {
        Random r1 = new Random();
        Random r2 = new Random(r1.nextInt(4));
        Digraph digraph = new Digraph(n);
        boolean[][] matrix = new boolean[n][n];
        for (int k = 0; k < n*2; k++) {
            int v = r2.nextInt(n);
            int w = r2.nextInt(n);
            if (v != w && !matrix[v][w]) {
                digraph.addEdge(v, w);
                matrix[v][w] = true;
            }
        }
        return digraph;
    }


}
