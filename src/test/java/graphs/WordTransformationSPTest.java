package graphs;

import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;


class Helpers {
    public static String generateWord(int size,int seed) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random(seed);
        StringBuilder buffer = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }

    public static String shuffle(String input, int seed){
        Random r = new Random(seed);

        List<Character> characters = new ArrayList<>();
        for(char c:input.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while(characters.size()!=0){
            int randPicker = (int)(r.nextFloat()*characters.size());
            output.append(characters.remove(randPicker));
        }

        return output.toString();
    }

}

@RunWith(Enclosed.class)
public class WordTransformationSPTest {

    @RunWith(Parameterized.class)
    public static class WTSPCorrectnessTests {
        private String from;
        private static final int SIZE_OF_WORD = 7; //<=10
        private static final int TEST_SIZE = 10;

        public WTSPCorrectnessTests(String from) {
            this.from = from;
        }

        @Test
        @Grade(value = 2)
        public void simpleTest() {
            Random r = new Random(45);
            int x = ((int) r.nextFloat()*(SIZE_OF_WORD-1)) + 2, y = ((int) r.nextFloat()*(SIZE_OF_WORD-1)) + 2;
            int start = Math.min(x,y), end = Math.max(x,y);

            String s = from;
            String d = WordTransformationSP.rotation(s,start,end);
            assertEquals(end-start, WordTransformationSP.minimalCost(s, d));
        }

        @Parameterized.Parameters
        public static String[] data() {
            Random r = new Random(12345L);
            String[] tests = new String[TEST_SIZE];

            for (int i = 0; i < TEST_SIZE; i++) {
                tests[i] = Helpers.generateWord(SIZE_OF_WORD, r.nextInt() );
            }
            return tests;
        }


    }

    @RunWith(Parameterized.class)
    public static class WTSPStaticTests {
        private Instance instance;

        public WTSPStaticTests(Instance instance) {
            this.instance = instance;
        }

        @Test
        @Grade(value = 1)
        public void staticTest() {
            assertEquals(instance.cost, WordTransformationSP.minimalCost(instance.from, instance.to));
        }

        @Parameterized.Parameters
        public static Instance[] data() {
            return new Instance[]{
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
            };
        }

        private static class Instance {
            public String from;
            public String to;
            public int cost;

            public Instance(String from, String to, int cost) {
                this.from = from;
                this.to = to;
                this.cost = cost;
            }

        }

    }


    @RunWith(Parameterized.class)
    public static class WTSPComplexityTests {
        private String from;
        private static final int SIZE_OF_WORD = 6; //<=10
        private static final int TOTAL_OF_WORD = 8; //<=10
        private static final int TEST_SIZE = 10;
        private static int[] seeds = {24, 123, 1234, 12345 };

        public WTSPComplexityTests(String from) {
            this.from = from;
        }

        @Test(timeout=20)
        @Grade(value=4) //4*10 = 40
        public void runAsExpected() {
            Random r = new Random(seeds[2]);
            String s = from;
            String d = Helpers.shuffle(s, r.nextInt());
            WordTransformationSP.minimalCost(s, d);

        }

        @Test(timeout=1000)
        @Grade(value=1)
        public void runAsExtreme() {
            String s = from+Helpers.generateWord(TOTAL_OF_WORD- SIZE_OF_WORD,seeds[3]);
            String d = Helpers.shuffle(s,seeds[0]);
            WordTransformationSP.minimalCost(s, d);
        }

        @Parameterized.Parameters
        public static String[] data() {
            Random r = new Random(seeds[0]);
            String[] tests = new String[TEST_SIZE];
            for (int i = 0; i < TEST_SIZE; i++) {
                tests[i] = Helpers.generateWord(SIZE_OF_WORD, r.nextInt() );
            }
            return tests;
        }
    }
}
