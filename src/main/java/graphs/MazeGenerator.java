package graphs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class MazeGenerator {

    public static void main(String [] args) {
        Random r = new Random(563996);
        int[] grid_sizes = new int[]{100, 200, 500};
        for (int grid_size : grid_sizes) {

            for (int instance_id = 0; instance_id < 10; instance_id++) {
                int [][] matrix = randomMaze(grid_size, 0.3f);
                ArrayList<ArrayList> queries = randomQueries(grid_size, 10, r);
                String file = "data/graphs.Maze/in_" + grid_size + "_" + instance_id;
                writeInstance(file, matrix, queries);
            }
        }
    }

    private static int[][] randomMaze(int size, float p){
        int[][] matrix = new int[size][size];
        Random rand = new Random(34333);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (rand.nextFloat() >= p ){
                    matrix[i][j] = 0;
                }
                else {
                    matrix[i][j] = 1;
                }
            }

        }
        return matrix;
    }

    private static ArrayList<ArrayList> randomQueries(int grid_size, int number, Random r) {
        ArrayList<ArrayList> queries = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            queries.add(new ArrayList<Integer>());
            ArrayList q = queries.get(i);
            for (int j = 0; j <4; j++) {
                q.add(r.nextInt(grid_size));
            }
        }
        return queries;
    }


    private static void writeInstance(String file, int [][] matrix, ArrayList<ArrayList> queries) {
        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(file));
            p.println(matrix.length);
            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix.length; col++) {
                    p.println(matrix[row][col]);
                }
            }
            p.println(queries.size());
            for (ArrayList<Integer> query : queries) {
                p.println(query.get(0) + " " + query.get(1) + " " + query.get(2) + " " + query.get(3));
                LinkedList<Integer> path = (LinkedList<Integer>) Maze.shortestPath(matrix, query.get(0), query.get(1), query.get(2), query.get(3));
                p.println(path.size());
                for(int val: path){
                    p.println(val);
                }
            }

            p.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
