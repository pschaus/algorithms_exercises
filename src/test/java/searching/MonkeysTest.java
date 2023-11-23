package searching;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.javagrader.Grade;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

@Grade
public class MonkeysTest {

    @ParameterizedTest
    @Grade(value=1)
    @CsvSource({
            "data/searching.Monkeys/input_advent21.txt,  324122188240430",
            "data/searching.Monkeys/input_advent21_debug.txt, 152"
    })
    void test(String fname, long solution) throws Exception {
        Instance instance = new Instance(fname,solution);
        assertEquals(instance.solution, Monkeys.evaluateRoot(instance.lines));
    }


    static class Instance {


        List<Monkeys.Monkey> lines;
        long solution;

        public Instance(String file, long solution) {
            try {
                lines = readInput(file);
                this.solution = solution;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }


    public static List<Monkeys.Monkey> readInput(String file) {
        List<Monkeys.Monkey> input = new LinkedList<>();
        try {
            String[] lines = readLines(file);

            for (String l : lines) {
                if (l.length() < 10) {

                    String monkey = l.substring(0, 4);
                    int n = Integer.parseInt(l.split(" ")[1]);
                    input.add(new Monkeys.YellingMonkey(monkey, n));

                } else {
                    // tjhg: svdh * svcm
                    String[] monkeys = l.split(":");
                    String monkey = monkeys[0];
                    monkeys[1] = monkeys[1].trim();
                    String left = monkeys[1].substring(0, 4);
                    char op = monkeys[1].charAt(5);
                    String right = monkeys[1].substring(7, 11);
                    input.add(new Monkeys.OperationMonkey(monkey, left, op, right));

                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return input;
    }

    public static String[] readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }
}
