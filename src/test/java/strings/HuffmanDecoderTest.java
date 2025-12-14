package strings;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Stream;


public class HuffmanDecoderTest {

    @Test
    @Grade(value=1)
    public void testExample() {
        String sentence = "This is the small example.";
        String encodedsentence = "100111101111001110111100111010001110100110101111111100010010101001100011001111000001000110010";
        HuffmanDecoder.Node root = HuffmanDecoderGenerator.HuffmanTree(sentence);
        String studentdecodedsentence = HuffmanDecoder.decode(encodedsentence, root);
        assertEquals(sentence, studentdecodedsentence);
    }

    @Test
    @Grade(value=1)
    public void testExampleShuffle() {
        String sentence = "This is the small example.";
        String encodedsentence = "100111101111001110111100111010001110100110101111111100010010101001100011001111000001000110010";
        HuffmanDecoder.Node root = HuffmanDecoderGenerator.HuffmanTree(sentence);
        HuffmanDecoderGenerator.MessTree(root);
        String studentdecodedsentence = HuffmanDecoder.decode(encodedsentence, root);
        assertEquals(sentence, studentdecodedsentence);
    }

    @Test
    @Grade(value=1)
    public void testOneLetter() {
        String sentence = "x";
        String encodedsentence = "0";
        HuffmanDecoder.Node root = HuffmanDecoderGenerator.HuffmanTree(sentence);
        String studentdecodedsentence = HuffmanDecoder.decode(encodedsentence, root);
        assertEquals(sentence, studentdecodedsentence);
    }

    @Test
    @Grade(value=1)
    public void testEmpty() {
        HuffmanDecoder.Node root = HuffmanDecoderGenerator.HuffmanTree("");
        String studentdecodedsentence = HuffmanDecoder.decode("", root);
        assertEquals("", studentdecodedsentence);
    }

    static Stream<Instance> dataProvider() {
        return Stream.of(new File("data/strings.HuffmanDecoder").listFiles())
                .filter(file -> !file.isDirectory())
                .filter(file -> !(file.getName().equals("LoremIpsum.txt")))
                .map(file -> new Instance(file.getPath()));
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 1000)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Hint: debug on the small example")
    @MethodSource("dataProvider")
    public void test(Instance instance)  {
        assertEquals(instance.sentence, instance.studentdecodedsentence);
    }

    static class Instance {
        String sentence;
        String encodedsentence;
        String studentdecodedsentence;

        public Instance(String file) {
            try {
                Scanner scan = new Scanner(new FileInputStream(file));
                sentence = scan.nextLine();
                encodedsentence  = scan.nextLine();
                HuffmanDecoder.Node root = HuffmanDecoderGenerator.HuffmanTree(sentence);
                HuffmanDecoderGenerator.MessTree(root);
                studentdecodedsentence = HuffmanDecoder.decode(encodedsentence, root);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}