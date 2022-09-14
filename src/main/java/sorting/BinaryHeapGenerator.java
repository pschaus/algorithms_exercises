package sorting;

import java.util.Random;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BinaryHeapGenerator {

    public static void main(String [] args) {
        Random r = new Random(478789);
        int [] input_sizes = new int[]{100, 1000, 10000};
        for (int input_size : input_sizes) {
            for (int instance_id=0; instance_id < 100; instance_id++) {
                int [] input = randomInput(input_size, r, 2*input_size);
                String file = "data/sorting.BinaryHeap/in_" + input_size + "_" + instance_id;
                writeInstance(file, input);
            }
        }
    }

    private static int[] randomInput(int size, Random r, int bound) {
        int [] input = new int[size];
        for (int i = 0; i < size; i++) {
            input[i] = r.nextInt(bound);
        }
        return input;
    }

    private static void writeInstance(String file, int [] input) {
        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(file));
            p.println(input.length);
            for (int i : input) {
                p.println(i);
            }
            p.close();
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
