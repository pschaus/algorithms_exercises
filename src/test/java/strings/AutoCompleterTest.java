package strings;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

// BEGIN STRIP
import org.javagrader.GradeFeedback;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Named.named;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
// END STRIP

@Grade
public class AutoCompleterTest {

    @Test
    @Grade(value=1)
    public void testEmptyTrie() {
        AutoCompleter ac = new AutoCompleter(new String[]{});
        assertNull(ac.complete(""));
        assertNull(ac.complete("a"));
    }

    @Test
    @Grade(value=1)
    public void testPrefixNotInTrie() {
        AutoCompleter ac = new AutoCompleter(new String[]{ "abruptly",  "babylonite", "cinurous", "drabbing", "exocardiac"});
        assertNull(ac.complete("factive"));
        assertNull(ac.complete("babylonites"));
    }

    @Test
    @Grade(value=1)
    public void testPrefixIsKey() {
        String[] dictionary = new String[]{ "abruptly",  "babylonite", "cinurous", "drabbing", "exocardiac"};
        AutoCompleter ac = new AutoCompleter(dictionary);
        for (String word : dictionary) {
            assertEquals(ac.complete(word), word);
        }
    }

    @Test
    @Grade(value=1)
    public void testPrefixInTrie() {
        String[] dictionary = new String[]{ "abruptly", "abrus", "babylonite", "cinurous", "drabbing", "exocardiac"};
        AutoCompleter ac = new AutoCompleter(dictionary);
        for (String word : dictionary) {
            assertEquals(ac.complete(word.substring(0, word.length()-1)), word);
        }
        assertEquals(ac.complete("a"), "abrus");
    }

    @Test
    @Grade(value=1)
    public void testLexicographic() {
         String[] dictionary = {"bifold", "bind","cat", "car", "dot", "dog", "bike", "bill",  };
         AutoCompleter ac = new AutoCompleter(dictionary);
         assertEquals(ac.complete("ca"), "car");
         assertEquals(ac.complete("do"), "dog");
         assertEquals(ac.complete("bi"), "bike");
    }

    @Test
    @Grade(value=1)
    public void testMultipleKeysForPrefix() {
        String[] dictionary = new String[]{ "abruptly", "abrus", "abrui", "babylonite", "cinurous", "drabbing", "exocardiac"};
        AutoCompleter ac = new AutoCompleter(dictionary);
        assertEquals(ac.complete("a"), "abrui");
    }

    // BEGIN STRIP
    static Stream<Arguments> dataProvider() {
        return Stream.of(new File("data/strings.AutoCompleter").listFiles())
                .filter(file -> !file.isDirectory())
                .map(file -> Arguments.of(named(file.getName(), new Instance(file.getPath()))));
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 3)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Hint: debug on a small example")
    @MethodSource("dataProvider")
    public void test(Instance instance) {
        AutoCompleter ac = new AutoCompleter(instance.dictionary.toArray(new String[0]));
        for (int i = 0; i < instance.requests.size(); i++) {
            assertEquals(ac.complete(instance.requests.get(i)), instance.outputs.get(i));
        }
    }

    static class Instance {
        List<String> dictionary = new ArrayList<>();
        List<String> requests = new ArrayList<>();
        List<String> outputs = new ArrayList<>();

        Instance(String file) {
            try {
                Scanner scan = new Scanner(new FileInputStream(file));
                int n = scan.nextInt();
                scan.nextLine();
                for (int i = 0; i < n; i++) {
                    dictionary.add(scan.nextLine());
                }
                n = scan.nextInt();
                scan.nextLine();
                for (int i = 0; i < n; i++) {
                    String line = scan.nextLine();
                    String[] parts = line.split(" ");
                    requests.add(parts[0]);
                    if (parts.length > 1) {
                        outputs.add(parts[1]);
                    } else {
                        outputs.add(null);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    // END STRIP

}
