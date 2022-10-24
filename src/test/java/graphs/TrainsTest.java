package graphs;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import graphs.Trains.*;
import com.github.guillaumederval.javagrading.Grade;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;

public class TrainsTest {
    HashMap<StationTime, LinkedList<StationTime>> simpleExample1() {
        HashMap<StationTime, LinkedList<StationTime>> relations = new HashMap<>();
        
        relations.put(new StationTime("Bxl-midi", 10), new LinkedList<>(Arrays.asList(
                new StationTime("Bruges", 20),
                new StationTime("Ostende", 25),
                new StationTime("Blankenberg", 26),
                new StationTime("Namur", 12)
        )));

        relations.put(new StationTime("Bruges", 12), new LinkedList<>(Arrays.asList(
                new StationTime("Ostende", 14),
                new StationTime("Blankenberg", 16),
                new StationTime("Namur", 30)
        )));

        relations.put(new StationTime("Namur", 13), new LinkedList<>(Arrays.asList(
                new StationTime("Paris", 28),
                new StationTime("Charleroi", 16)
        )));

        relations.put(new StationTime("Namur", 15), new LinkedList<>(Arrays.asList(
                new StationTime("Paris", 26),
                new StationTime("Charleroi", 16)
        )));

        return relations;
    }

    HashMap<StationTime, LinkedList<StationTime>> lessSimpleExample() {
        HashMap<StationTime, LinkedList<StationTime>> relations = simpleExample1();
        relations.put(new StationTime("A", 1), new LinkedList<>(Arrays.asList(new StationTime("D", 199),new StationTime("F", 381),new StationTime("D", 199))));
        relations.put(new StationTime("A", 235), new LinkedList<>(Arrays.asList(new StationTime("F", 615),new StationTime("E", 690),new StationTime("D", 433))));
        relations.put(new StationTime("A", 514), new LinkedList<>(Arrays.asList(new StationTime("A", 514))));
        relations.put(new StationTime("A", 897), new LinkedList<>(Arrays.asList(new StationTime("D", 1095))));
        relations.put(new StationTime("B", 26), new LinkedList<>(Arrays.asList(new StationTime("B", 26))));
        relations.put(new StationTime("B", 279), new LinkedList<>(Arrays.asList(new StationTime("C", 585),new StationTime("E", 579))));
        relations.put(new StationTime("B", 310), new LinkedList<>(Arrays.asList(new StationTime("A", 520),new StationTime("E", 610),new StationTime("A", 520))));
        relations.put(new StationTime("B", 575), new LinkedList<>(Arrays.asList(new StationTime("C", 881),new StationTime("E", 875),new StationTime("C", 881))));
        relations.put(new StationTime("B", 907), new LinkedList<>(Arrays.asList(new StationTime("D", 1221))));
        relations.put(new StationTime("C", 34), new LinkedList<>(Arrays.asList(new StationTime("B", 340))));
        relations.put(new StationTime("C", 227), new LinkedList<>(Arrays.asList(new StationTime("A", 658),new StationTime("F", 545),new StationTime("C", 227))));
        relations.put(new StationTime("C", 245), new LinkedList<>(Arrays.asList(new StationTime("C", 245))));
        relations.put(new StationTime("C", 276), new LinkedList<>(Arrays.asList(new StationTime("F", 594),new StationTime("F", 594))));
        relations.put(new StationTime("C", 585), new LinkedList<>(Arrays.asList(new StationTime("C", 585),new StationTime("C", 585))));
        relations.put(new StationTime("C", 809), new LinkedList<>(Arrays.asList(new StationTime("F", 1127),new StationTime("D", 1179))));
        relations.put(new StationTime("D", 63), new LinkedList<>(Arrays.asList(new StationTime("B", 377),new StationTime("E", 485),new StationTime("B", 377))));
        relations.put(new StationTime("D", 436), new LinkedList<>(Arrays.asList(new StationTime("E", 858),new StationTime("C", 806),new StationTime("E", 858))));
        relations.put(new StationTime("D", 613), new LinkedList<>(Arrays.asList(new StationTime("E", 1035))));
        relations.put(new StationTime("D", 968), new LinkedList<>(Arrays.asList(new StationTime("A", 1166),new StationTime("F", 1439))));
        relations.put(new StationTime("E", 59), new LinkedList<>(Arrays.asList(new StationTime("F", 333))));
        relations.put(new StationTime("E", 423), new LinkedList<>(Arrays.asList(new StationTime("D", 845))));
        relations.put(new StationTime("E", 794), new LinkedList<>(Arrays.asList(new StationTime("A", 1249),new StationTime("C", 866),new StationTime("D", 1216))));
        relations.put(new StationTime("E", 817), new LinkedList<>(Arrays.asList(new StationTime("E", 817),new StationTime("B", 1117),new StationTime("A", 1272))));
        relations.put(new StationTime("E", 956), new LinkedList<>(Arrays.asList(new StationTime("E", 956),new StationTime("F", 1230))));
        relations.put(new StationTime("E", 968), new LinkedList<>(Arrays.asList(new StationTime("C", 1040))));
        relations.put(new StationTime("F", 30), new LinkedList<>(Arrays.asList(new StationTime("D", 501),new StationTime("E", 304))));
        relations.put(new StationTime("F", 62), new LinkedList<>(Arrays.asList(new StationTime("C", 380),new StationTime("A", 442),new StationTime("F", 62))));
        relations.put(new StationTime("F", 370), new LinkedList<>(Arrays.asList(new StationTime("C", 688),new StationTime("F", 370),new StationTime("D", 841))));
        relations.put(new StationTime("F", 530), new LinkedList<>(Arrays.asList(new StationTime("D", 1001))));
        relations.put(new StationTime("F", 701), new LinkedList<>(Arrays.asList(new StationTime("D", 1172),new StationTime("A", 1081))));
        relations.put(new StationTime("F", 730), new LinkedList<>(Arrays.asList(new StationTime("E", 1004))));
        return relations;
    }

    @Test
    @Grade(value=4)
    public void exampleTest() {
        Map<String, Integer> out = Trains.reachableEarliest(simpleExample1(), new StationTime("Bxl-midi", 5));
        assertEquals(7, out.size());
        assertThat(out.keySet(), allOf(
                hasItem("Ostende"),
                hasItem("Charleroi"),
                hasItem("Blankenberg"),
                hasItem("Bxl-midi"),
                hasItem("Bruges"),
                hasItem("Paris"),
                hasItem("Namur")
        ));
        assertEquals(out.get("Ostende"), (Integer)25);
        assertEquals(out.get("Charleroi"), (Integer)16);
        assertEquals(out.get("Blankenberg"), (Integer)26);
        assertEquals(out.get("Bxl-midi"), (Integer)5);
        assertEquals(out.get("Bruges"), (Integer)20);
        assertEquals(out.get("Paris"), (Integer)26);
        assertEquals(out.get("Namur"), (Integer)12);
    }

    @Test
    @Grade(value=3)
    public void example2Test() {
        Map<String, Integer> out = Trains.reachableEarliest(simpleExample1(), new StationTime("Bruges", 5));
        assertEquals(4, out.size());
        assertThat(out.keySet(), allOf(
                hasItem("Ostende"),
                hasItem("Blankenberg"),
                hasItem("Bruges"),
                hasItem("Namur")
        ));
        assertEquals(out.get("Ostende"), (Integer)14);
        assertEquals(out.get("Blankenberg"), (Integer)16);
        assertEquals(out.get("Bruges"), (Integer)5);
        assertEquals(out.get("Namur"), (Integer)30);
    }

    @Test
    @Grade(value=2)
    public void lessSimple1Test() {
        Map<String, Integer> out = Trains.reachableEarliest(lessSimpleExample(), new StationTime("A", 5));
        assertEquals(6, out.size());
        assertThat(out.keySet(), allOf(
                hasItem("A"),
                hasItem("B"),
                hasItem("C"),
                hasItem("D"),
                hasItem("E"),
                hasItem("F")
        ));
        assertEquals(out.get("A"), (Integer)5);
        assertEquals(out.get("B"), (Integer)1117);
        assertEquals(out.get("C"), (Integer)806);
        assertEquals(out.get("D"), (Integer)433);
        assertEquals(out.get("E"), (Integer)690);
        assertEquals(out.get("F"), (Integer)615);
    }

    @Test
    @Grade(value=2)
    public void lessSimple2Test() {
        Map<String, Integer> out = Trains.reachableEarliest(lessSimpleExample(), new StationTime("B", 500));
        assertEquals(5, out.size());
        assertThat(out.keySet(), allOf(
                hasItem("B"),
                hasItem("C"),
                hasItem("D"),
                hasItem("E"),
                hasItem("F")
        ));
        assertEquals(out.get("B"), (Integer)500);
        assertEquals(out.get("C"), (Integer)881);
        assertEquals(out.get("D"), (Integer)1221);
        assertEquals(out.get("E"), (Integer)875);
        assertEquals(out.get("F"), (Integer)1230);
    }

    @Test
    @Grade(value=2)
    public void lessSimple3Test() {
        Map<String, Integer> out = Trains.reachableEarliest(lessSimpleExample(), new StationTime("B", 0));
        assertEquals(6, out.size());
        assertThat(out.keySet(), allOf(
                hasItem("A"),
                hasItem("B"),
                hasItem("C"),
                hasItem("D"),
                hasItem("E"),
                hasItem("F")
        ));
                assertEquals(out.get("A"), (Integer)520);
                assertEquals(out.get("B"), (Integer)0);
                assertEquals(out.get("C"), (Integer)585);
                assertEquals(out.get("D"), (Integer)1095);
                assertEquals(out.get("E"), (Integer)579);
                assertEquals(out.get("F"), (Integer)1127);
    }
}