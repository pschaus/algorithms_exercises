package sorting;

import java.util.Random;
import java.lang.Math;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class ClosestPairGenerator {

    public static void main(String [] args) {
        Random r = new Random(862336);
        int [] input_sizes = new int[]{100,10_000, 100_000};
        for (int input_size : input_sizes) {
            for (int instance_id = 0; instance_id < 100; instance_id++) {
                int [] input = randomInput(input_size, r);
                int query = r.nextInt(5*input_size);
                int [] result = ClosestPair.closestPair(input, query);
                String instanceFile = "data/sorting.ClosestPair/in_" + input_size + "_" + instance_id;
                writeInstance(instanceFile, input, query, result);
            }
        }
    }

    private static int [] randomInput(int size, Random r) {
        int [] input = new int[size];
        for (int i = 0; i < size; i++) {
            input[i] = r.nextInt(3*size);
        }
        return input;
    }

    private static void writeInstance(String file, int [] input, int query, int [] result) {
        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(file));
            p.println(input.length);
            for (int i : input) {
                p.println(i);
            }
            p.println(query);
            p.println(result[0]);
            p.println(result[1]);
            p.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
