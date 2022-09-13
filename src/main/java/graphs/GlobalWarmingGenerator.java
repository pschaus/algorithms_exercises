package graphs;

import utils.Point;

import java.util.Random;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GlobalWarmingGenerator {

    public static void main(String [] args) {
        Random r = new Random(563996);
        int[] grid_sizes = new int[]{100, 200, 500};
        for (int grid_size : grid_sizes) {
            for (int instance_id = 0; instance_id < 100; instance_id++) {
                int waterLevel = 100 + r.nextInt(500);
                int [][] altitude = randomAltitude(grid_size, r, waterLevel);
                Point [] queries = randomQueries(grid_size, 100, r);
                String file = "data/graphs.GlobalWarming/in_" + grid_size + "_" + instance_id;
                writeInstance(file, altitude, waterLevel, queries);
            }
        }
    }

    private static int[][] randomAltitude(int size, Random r, int waterLevel) {
        int [][] altitude = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                boolean aboveWater = r.nextFloat() <= 0.3;
                if (aboveWater) {
                    altitude[row][col] = waterLevel + r.nextInt(1000);
                } else {
                    altitude[row][col] = r.nextInt(waterLevel);
                }
            }
        }
        return altitude;
    }

    private static Point[] randomQueries(int grid_size, int number, Random r) {
        Point[] queries = new Point[number];
        for (int i = 0; i < number; i++) {
            queries[i] = new Point(r.nextInt(grid_size), r.nextInt(grid_size));
        }
        return queries;
    }

    private static void writeInstance(String file, int [][] altitude, int waterLevel, Point [] queries) {
        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(file));
            p.println(altitude.length);
            for (int row = 0; row < altitude.length; row++) {
                for (int col = 0; col < altitude.length; col++) {
                    p.println(altitude[row][col]);
                }
            }
            p.println(waterLevel);
            p.println(queries.length);
            for (Point query : queries) {
                p.println(query.getX());
                p.println(query.getY());
            }
            GlobalWarming globalWarming = new GlobalWarming(altitude, waterLevel);
            p.println(globalWarming.nbIslands());
            p.println(globalWarming.onSameIsland(queries[0], queries[0]));
            for (int i = 0; i < queries.length - 1; i++) {
                p.println(globalWarming.onSameIsland(queries[i], queries[i+1]));
            }
            p.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
