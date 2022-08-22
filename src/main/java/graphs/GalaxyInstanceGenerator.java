package graphs;

import graphs.GalaxyPath;

import java.io.*;
import java.util.*;

public class GalaxyInstanceGenerator {
    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            int [][] m = randomGraph(20,70);
            Set<Integer> dest = Collections.unmodifiableSet(new HashSet<Integer>(Arrays.asList(1,2,3)));
            int res = GalaxyPath.findPath(m,0, dest);
            writeInstance("data/graphs.GalaxyPath/in_20_"+i,m,0,dest.size(), dest,res);
        }

        for (int i = 0; i < 100; i++) {
            int [][] m = randomGraph(500,1000);
            Set<Integer> dest = Collections.unmodifiableSet(new HashSet<Integer>(Arrays.asList(1,2,3)));
            int res = GalaxyPath.findPath(m,0, dest);
            writeInstance("data/graphs.GalaxyPath/in_100_"+i,m,0,dest.size(), dest,res);
        }

    }


    public static int[][] randomGraph(int n, int nEdges) {
        int [][] matrix = new int[n][n];
        Random r = new Random();

        for (int i = 0; i < nEdges; i++) {
            int a = r.nextInt(n);
            int b = r.nextInt(n);
            matrix[a][b] = r.nextInt(n);
        }
        return  matrix;
    }


    public static void writeInstance(String file, int [][] matrix, int from, int nDest, Set<Integer> destination, int solution) {
        int n = matrix.length;
        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(file));
            p.println(n);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    p.println(matrix[i][j]);
                }
            }
            p.println(from);
            p.println(destination.size());
            for (int d: destination) {
                p.println(d);
            }
            p.println(solution);
            p.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
