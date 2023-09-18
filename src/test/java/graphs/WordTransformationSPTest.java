package graphs;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import java.util.stream.IntStream;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class WordTransformationSPTest {

    @Grade
    @Order(0)
    @Test
    public void simpleTest() {
        String s = "abc";
        String d = "cba";
        assertEquals(3, WordTransformationSP.minimalCost(s, d));

        String s2 = "HAMBURGER";
        String d2 = "HAMBEGRUR";
        assertEquals(4, WordTransformationSP.minimalCost(s2, d2));

    }

    // BEGIN STRIP
    public static final Random random = new Random(12345L);
    
    // For the simple tests, with use words of size 7
    static Stream<String> dataProvider() {
        return IntStream.range(0, 10).mapToObj(i -> {
            return Helpers.generateWord(7);
        });
    }
    
    @ParameterizedTest
    @Grade(value = 2)
    @MethodSource("dataProvider")
    @Order(1)
    public void simpleRandomTest(String from) {
        Random r = new Random(45);
        // 7 is the size of the word from (i.e. from.length() == 7)
        int x = ((int) r.nextFloat()*(7-1)) + 2, y = ((int) r.nextFloat()*(7-1)) + 2;
        int start = Math.min(x,y), end = Math.max(x,y);

        String s = from;
        String d = WordTransformationSP.rotation(s,start,end);
        assertEquals(end-start, WordTransformationSP.minimalCost(s, d));
    }
    
    static Stream<Instance> staticDataProvider() {
        return Stream.of(
            new Instance("wdaowkl", "aowlwkd", 12),
            new Instance("fxuldkv", "kfdxvul", 13),
            new Instance("bzwxnxg", "wxbxgnz", 12),
            new Instance("hkhddmc", "ckhddhm", 9),
            new Instance("fcavjtb", "bftjcva", 12),
            new Instance("qqbtghc", "tbqhqcg", 10),
            new Instance("zexqiig", "xezgiqi", 8),
            new Instance("ikstclp", "ktsilpc", 9),
            new Instance("cuwpysz", "cpysuzw", 10),
            new Instance("cvnooos", "oconosv", 10),
            new Instance("zumnlit", "zutnlmi", 8),
            new Instance("wefscav", "aecvsfw", 14),
            new Instance("pvgzoxg", "zvgxpgo", 12),
            new Instance("lgeiyzm", "gezymli", 11),
            new Instance("ofimxuj", "xumifoj", 8),
            new Instance("bvzjphs", "jhbzspv", 12),
            new Instance("nnyiqgx", "xiyqngn", 13),
            new Instance("lqllyat", "qlltayl", 6),
            new Instance("uacfnsi", "aucinfs", 7),
            new Instance("vfwtotc", "ofwttcv", 12),
            new Instance("ftfxxha", "txfxhaf", 11),
            new Instance("defrhmz", "zefdrhm", 12),
            new Instance("mrxcrbk", "cbrkrxm", 13),
            new Instance("dkwohei", "odkhewi", 10),
            new Instance("ahraabk", "akbraah", 9),
            new Instance("lgwhpak", "wkphgal", 13),
            new Instance("wjeekzr", "rwejkez", 10),
            new Instance("xotudui", "ouuxitd", 13),
            new Instance("ucuadky", "cuydauk", 8),
            new Instance("kcraftl", "tlafcrk", 13)
        );
    }
    
    @ParameterizedTest
    @Grade(value = 1)
    @MethodSource("staticDataProvider")
    @Order(1)
    public void staticTest(Instance instance) {
        assertEquals(instance.cost, WordTransformationSP.minimalCost(instance.from, instance.to));
    }
    
    static Stream<String> dataProviderComplexity() {
        return IntStream.range(0, 10).mapToObj(i -> {
            return Helpers.generateWord(6);
        });
    }

    @ParameterizedTest
    @Grade(value=1, cpuTimeout = 1000)
    @MethodSource("dataProviderComplexity")
    @Order(2)
    public void runAsExtreme(String from) {
        String s = from+Helpers.generateWord(8 - 6);
        String d = Helpers.shuffle(s);
        WordTransformationSP.minimalCost(s, d);
    }
    // END STRIP
}

// BEGIN STRIP
class Instance {
    public String from;
    public String to;
    public int cost;

    public Instance(String from, String to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

}


class Helpers {

    public static String generateWord(int size) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        StringBuilder buffer = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (WordTransformationSPTest.random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }

    public static String shuffle(String input){

        List<Character> characters = new ArrayList<>();
        for(char c:input.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while(characters.size()!=0){
            int randPicker = (int)(WordTransformationSPTest.random.nextFloat()*characters.size());
            output.append(characters.remove(randPicker));
        }

        return output.toString();
    }
}
// END STRIP
