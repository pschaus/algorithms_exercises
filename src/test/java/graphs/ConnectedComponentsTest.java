package graphs;

import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import utils.Graph;

import java.util.Random;

import static org.junit.Assert.assertEquals;


public class ConnectedComponentsTest {

    private static class WeightedQuickUnionUF {
        private int[] parent;   // parent[i] = parent of i
        private int[] size;     // size[i] = number of sites in subtree rooted at i
        private int count;      // number of components

        public WeightedQuickUnionUF(int n) {
            count = n;
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }


        public int count() {
            return count;
        }

        public int find(int p) {
            validate(p);
            while (p != parent[p])
                p = parent[p];
            return p;
        }

        // validate that p is a valid index
        private void validate(int p) {
            int n = parent.length;
            if (p < 0 || p >= n) {
                throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (n - 1));
            }
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;

            // make smaller root point to larger one
            if (size[rootP] < size[rootQ]) {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            } else {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            }
            count--;
        }
    }

    public void testRandomGraphOk(int n, int e) {
        Graph g = new Graph(n);
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);
        Random r = new Random(6);
        for (int i = 0; i < e; i++) {
            int orig = r.nextInt(n);
            int dest = r.nextInt(n);
            g.addEdge(orig, dest);
            uf.union(orig, dest);
        }
        int nbCC = ConnectedComponents.numberOfConnectedComponents(g);
        assertEquals(uf.count(), nbCC);
    }

    @Test
    @Grade(value = 25)
    public void cycleGraphOk() {
        int n = 1002;
        Graph g = new Graph(n);
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);
        g.addEdge(0, n - 1);
        uf.union(0, n - 1);

        for (int i = 1; i < n; i++) {
            g.addEdge(i, (i - 1) % n);
            uf.union(i, (i - 1) % n);
        }

        assertEquals(uf.count(), ConnectedComponents.numberOfConnectedComponents(g));
    }


    @Test(timeout = 3000)
    @Grade(value = 25)
    public void complexityOk() {
        int n = 7002;
        Graph g = new Graph(n);
        for (int i = 0; i < n; i++) {
            g.addEdge(i, (i + 2) % n);
        }
        ConnectedComponents.numberOfConnectedComponents(g);
    }

    @Test
    @Grade(value = 50)
    public void correctness() {
        testRandomGraphOk(600, 120);
        testRandomGraphOk(220, 7);
        testRandomGraphOk(105, 3);
        testRandomGraphOk(0, 0);
        testRandomGraphOk(10, 2 * 10);
        testRandomGraphOk(42, 23);
        testRandomGraphOk(420, 123);
        testRandomGraphOk(90, 40);
    }

}
