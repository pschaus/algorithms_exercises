package graphs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class MineClimbingInstanceGenerator {
    static int nInstances = 100;
    static int nTests = 5;


    public static void main(String[] args) {
        Random random = new Random(58465);
        int hi = 100;
        int lo = 5;
        for (int i = 0; i < nInstances; i++) {
            int size  = random.nextInt(hi - lo) + lo;
            int[][] mine = generateRandomMine(size);
            writeInstance("data/graphs.MineClimbing/in_rand_" + i, mine);

        }

        for (int i = 0; i < 2; i++) {
            int[][] mine = generateRandomMine(1000);
            writeInstance("data/graphs.MineClimbing/in_comp_" + i, mine);

        }
    }


    public static int[][] generateRandomMine(int size) {

        int[][] mine = new int[size][size];
        Random rand = new Random(5623);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mine[i][j] = rand.nextInt(1000);
            }
        }
        return mine;
    }



    public static void writeInstance(String file, int[][] mine){

        Random rand = new Random(542);
        int size  = mine.length;

        try{
            PrintWriter p = new PrintWriter(new FileOutputStream(file));
            p.println(size);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    p.println(mine[i][j]);
                }
            }
            p.println(nTests);
            for (int i = 0; i < nTests; i++) {
                int startX = rand.nextInt(size);
                int startY = rand.nextInt(size);
                int endX = rand.nextInt(size);
                int endY = rand.nextInt(size);
                int solution = MineClimbing.best_distance(mine, startX, startY, endX, endY);

                p.println(startX);
                p.println(startY);
                p.println(endX);
                p.println(endY);
                p.println(solution);
            }
            p.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
