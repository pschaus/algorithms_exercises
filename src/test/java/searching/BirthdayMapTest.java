package searching;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import java.util.List;


@Grade
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BirthdayMapTest {

    
    @Test
    @Grade(value = 1)
    @Order(0)
    public void testSimple() {
        BirthdayMap birthdayMap = new BirthdayMap();

        Person alice = new Person("Alice", "2000-07-17");
        Person bob = new Person("Bob", "2000-08-15");
        Person charlie = new Person("Charlie", "2001-06-06");

        birthdayMap.addPerson(alice);
        birthdayMap.addPerson(bob);
        birthdayMap.addPerson(charlie);

        List<Person> peopleBornIn2000 = birthdayMap.getPeopleBornInYear("2000");

        assertTrue(peopleBornIn2000.contains(alice));
        assertTrue(peopleBornIn2000.contains(bob));
        assertFalse(peopleBornIn2000.contains(charlie));
    }


    // BEGIN STRIP

    public static void testCorrectnessRandom() {
        Random rand = new Random(0);
        BirthdayMap birthdayMap = new BirthdayMap();
        int size = 100000; // adjust as needed
        String targetDate =  "2005-03-15";
        int countTargetDate = 0;
        for (int i = 0; i < size; i++) {
            String month = String.format("%02d", rand.nextInt(12) + 1);
            String day = String.format("%02d", rand.nextInt(28) + 1); // choose a day between 1 and 28 to avoid leap year issues
            String year = String.valueOf(2000 + (i % 21)); // cycle through years 2000 to 2020
            String date = year + "-" + month + "-" + day;
            Person person = new Person("Person" + i, date);
            if (targetDate.equals(date)) {
                countTargetDate++;
            }
            birthdayMap.addPerson(person);
        }

        // Now check if all the persons are correctly retrieved by their year of birth
        int tot = 0;
        for (int year = 2000; year <= 2020; year++) {
            String yearString = String.valueOf(year);
            List<Person> people = birthdayMap.getPeopleBornInYear(yearString);
            tot += people.size();
            // All the people in this list should have been born in the year
            for (Person person : people) {
                assertTrue(person.birthday.startsWith(yearString));
            }
        }
        assertEquals(size, tot);
        assertEquals(countTargetDate, birthdayMap.getPeopleBornOnDate(targetDate).size());
    }

    @Test
    @Grade(value = 4, cpuTimeout = 1000)
    @Order(1)
    public void testCorrect() {
        testCorrectnessRandom();
    }
    

    @Test
    @Grade(value = 5, cpuTimeout = 1000)
    @Order(1)
    public void testEfficientInsertion() {

        testCorrectnessRandom();

        Random rand = new Random(0);



        // Time the operation
        long start = System.nanoTime();

        BirthdayMap birthdayMap = new BirthdayMap();
        String year = "2000";

        for (int i = 0; i < 100000; i++) {
            Person person = new Person("Person" + i, year + "-" + ((i % 12) + 1) + "-" + ((i % 28) + 1));
            birthdayMap.addPerson(person);
            year = (1000 + rand.nextInt(8000))+"";
        }

        birthdayMap.addPerson(new Person("toto", "2000-03-30"));

        long duration = System.nanoTime() - start;

        assertTrue(duration < 1000000000); // 1 second

    }

    @Test
    @Grade(value = 5, cpuTimeout = 1000)
    @Order(1)
    public void testEfficientPeopleBornOnDate() {

        testCorrectnessRandom();

        // Time the operation

        Random rand = new Random(0);
        BirthdayMap birthdayMap = new BirthdayMap();
        String year = "2000";

        for (int i = 0; i < 100000; i++) {
            Person person = new Person("Person" + i, year + "-" + ((i % 12) + 1) + "-" + ((i % 28) + 1));
            birthdayMap.addPerson(person);
            year = (1000 + rand.nextInt(8000))+"";
        }

        // Time the operation
        long start = System.nanoTime();

        birthdayMap.addPerson(new Person("toto", "2000-03-30"));

        for (int i = 0; i < 1000; i++) {
            List<Person> res = birthdayMap.getPeopleBornOnDate("2000-03-30");
            assertEquals("toto",res.get(0).name);
            assertEquals("2000-03-30", res.get(0).birthday);
        }

        long duration = System.nanoTime() - start;

        assertTrue(duration < 10000000); // 0.01 second

    }


    @Test
    @Grade(value = 5, cpuTimeout = 1000)
    @Order(1)
    public void testEfficientPeopleBornOnYear() {

        testCorrectnessRandom();

        // Time the operation

        Random rand = new Random(0);
        BirthdayMap birthdayMap = new BirthdayMap();
        String year = "2000";

        for (int i = 0; i < 10000; i++) {
            Person person = new Person("Person" + i, year + "-" + ((i % 12) + 1) + "-" + ((i % 28) + 1));
            birthdayMap.addPerson(person);
            year = (1000 + rand.nextInt(8000))+"";
        }

        // Time the operation
        long start = System.nanoTime();

        birthdayMap.addPerson(new Person("toto1", "9101-03-30"));
        birthdayMap.addPerson(new Person("toto2", "9101-04-31"));

        for (int i = 0; i < 1000; i++) {
            List<Person> res = birthdayMap.getPeopleBornInYear("9101");
            assertEquals(2,res.size());

            assertEquals("toto1",res.get(0).name);
            assertEquals("toto2",res.get(1).name);

            assertEquals("9101-03-30", res.get(0).birthday);
            assertEquals("9101-04-31", res.get(1).birthday);
        }

        long duration = System.nanoTime() - start;

        assertTrue(duration < 10000000); // 0.01 second

    }


    // END STRIP
    

}
