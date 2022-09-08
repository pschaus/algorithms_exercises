package searching;

import java.util.Random;
import java.lang.Math;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BinarySearchTreeGenerator {

    public static void main(String [] args) {
        Random r = new Random(40195385);
        int [] sequence_sizes = new int[]{100, 200, 400, 600, 800, 1000};
        for (int sequence_size : sequence_sizes) {
            for (int instance_id = 0; instance_id < 100; instance_id++) {
                int [] input = randomInput(sequence_size, r);
                int max_value = -1;
                int min_value = 1000000;
                for (int i = 0; i < input.length; i++) {
                    max_value = Math.max(max_value, input[i]);
                    min_value = Math.min(min_value, input[i]);
                }
                boolean above_limit = r.nextBoolean();
                String instanceFile = "data/searching.BinarySearchTree/in_" + sequence_size + "_" + instance_id;
                if (above_limit) {
                    int query = max_value + 1 + r.nextInt(1000);
                    writeInstance(instanceFile, input, query);
                } else {
                    boolean below_limit = r.nextBoolean();
                    if (below_limit) {
                        int query = min_value - 1 - r.nextInt(1000);
                        writeInstance(instanceFile, input, query);
                    } else {
                        int query = min_value + r.nextInt(max_value - min_value);
                        writeInstance(instanceFile, input, query);
                    }
                }
            }
        }
    }

    private static int[] randomInput(int size, Random r) {
        int [] input = new int[size];
        for (int i = 0; i < input.length; i++) {
            input[i] = r.nextInt(1000000);
        }
        return input;
    }

    private static void writeInstance(String file, int [] input, int query) {
        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(file));
            p.println(input.length);
            for (int i : input) {
                p.println(i);
            }
            p.println(query);
            p.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
