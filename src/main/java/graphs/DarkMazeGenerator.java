package graphs;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class DarkMazeGenerator {


    public static void main(String [] args) {

        Random r = new Random(563996);
        int[] grid_sizes = new int[]{5, 10, 20};
        for (int grid_size : grid_sizes) {

            for (int instance_id = 0; instance_id < 10; instance_id++) {
                ArrayList<ArrayList> queries = randomQueries(grid_size, 10, r);
                int [][] matrix = randomMaze(grid_size, 0.3f);
                String file = "data/graphs.DarkMaze/in_" + grid_size + "_" + instance_id;
                writeInstance(file, matrix, queries);
            }
        }
    }

    private static int[][] randomMaze(int size, float p){
        int[][] matrix = new int[size][size];
        Random rand = new Random(34333);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                float r = rand.nextFloat();
                if (r >= p ){
                    matrix[i][j] = 0;
                } else if (r < p && r>= p/3) {
                    matrix[i][j] = -1;
                } else {
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
        Random r = new Random(563996);
        try {
            int  nBattery = 9 + r.nextInt(19-9);

            PrintWriter p = new PrintWriter(new FileOutputStream(file));
            p.println(matrix.length);
            p.println(nBattery);

            int endX = (int) queries.get(0).get(2);
            int endY = (int) queries.get(0).get(3);

            matrix[endX][endY] = 2;
            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix.length; col++) {
                    p.println(matrix[row][col]);
                }
            }
            p.println(queries.size());

            for (ArrayList<Integer> query : queries) {
                p.println(query.get(0) + " " + query.get(1) + " " + endX + " " + endY);

                List<Integer> path = DarkMaze.findPath(query.get(0), query.get(1), endX, endY, nBattery, matrix);
                if (path == null){
                    p.println(0);
                }else{
                    p.println(path.size());
                    for(int val: path){
                        p.println(val);
                    }
                }
            }

            p.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
