package graphs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.github.guillaumederval.javagrading.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import java.util.*;

public class BubblesTest {

    @Test
    @Grade(value = 1)
    public void exampleTest() {
        List<Contact> contacts = Collections.unmodifiableList(Arrays.asList(
                new Contact("Alice", "Bob"),
                new Contact("Alice", "Carole"),
                new Contact("Carole", "Alice"),
                new Contact("Eve", "Alice"),
                new Contact("Alice", "Frank"),
                new Contact("Bob", "Carole"),
                new Contact("Bob", "Eve"),
                new Contact("Bob", "Frank"),
                new Contact("Bob", "Carole"),
                new Contact("Bob", "Eve"),
                new Contact("Bob", "Frank"),
                new Contact("Bob", "Carole"),
                new Contact("Eve", "Frank")
        ));
        List<ForbiddenRelation> fr = Bubbles.cleanBubbles(contacts, 3);
        assertEquals(fr.size(), 1);
        assertEquals(fr.get(0), new ForbiddenRelation("Alice", "Bob"));
    }

    @Test
    @Grade(value = 1)
    public void example2Test() {
        List<Contact> contacts = Collections.unmodifiableList(Arrays.asList(
                new Contact("Alice", "Bob"),
                new Contact("Alice", "Carole"),
                new Contact("Carole", "Alice"),
                new Contact("Eve", "Alice"),
                new Contact("Alice", "Frank"),
                new Contact("Bob", "Carole"),
                new Contact("Bob", "Eve"),
                new Contact("Bob", "Frank"),
                new Contact("Bob", "Carole"),
                new Contact("Bob", "Eve"),
                new Contact("Bob", "Frank"),
                new Contact("Bob", "Carole"),
                new Contact("Eve", "Frank")
        ));
        List<ForbiddenRelation> fr = Bubbles.cleanBubbles(contacts, 2);
        Collections.sort(fr);
        assertEquals(fr.size(), 3);
    }


    @Test
    @Grade(value = 1)
    public void randomTest() {

        Random random = new Random(90208);
        final int SIZE = 10;
        final int BSIZE = 4;
        final int GBSIZE = 7;

        LinkedList<Integer>[] contacts = new LinkedList[SIZE];
        List<Contact> ccontacts = new LinkedList<>();

        for (int i = 0; i < SIZE; i++)
            contacts[i] = new LinkedList<>();

        for (int i = 0; i < SIZE; i++) {
            int size = random.nextInt(GBSIZE) + 1;

            LinkedList<Integer> ids = new LinkedList<>();
            for (int j = 0; j < SIZE - i - 1; j++)
                ids.add(j);
            Collections.shuffle(ids, random);

            while (contacts[i].size() < size && !ids.isEmpty()) {
                int j = (ids.pop() + i + 1) % SIZE;
                contacts[i].push(j);
                contacts[j].push(i);
                ccontacts.add(new Contact(Integer.toString(i), Integer.toString(j)));
            }

            Collections.sort(contacts[i]);
        }

        List<ForbiddenRelation> frs = Bubbles.cleanBubbles(ccontacts, BSIZE);
        Collections.sort(frs);

        for (ForbiddenRelation fr : frs) {
            int a = Integer.parseInt(fr.a);
            int b = Integer.parseInt(fr.b);

            assertTrue(contacts[a].remove((Integer) b));
            assertTrue(contacts[b].remove((Integer) a));
        }

        for (LinkedList<Integer> c : contacts)
            assertTrue(c.size() <= BSIZE);
    }


    @Test(timeout = 3000)
    @Grade(value = 1, cpuTimeout = 2000)
    public void perfTest() {
        exampleTest();
        example2Test();
        randomTest();

        Random random = new Random(90208);
        final int SIZE = 1000;
        final int BSIZE = 200;
        final int GBSIZE = 400;

        HashSet<Integer>[] contacts = new HashSet[SIZE];
        List<Contact> ccontacts = new LinkedList<>();

        for (int i = 0; i < SIZE; i++)
            contacts[i] = new HashSet<>();

        for (int i = 0; i < SIZE; i++) {
            int size = random.nextInt(GBSIZE) + 1;

            LinkedList<Integer> ids = new LinkedList<>();
            for (int j = 0; j < SIZE - i - 1; j++)
                ids.add(j);
            Collections.shuffle(ids, random);

            while (contacts[i].size() < size && !ids.isEmpty()) {
                int j = (ids.pop() + i + 1) % SIZE;
                contacts[i].add(j);
                contacts[j].add(i);
                ccontacts.add(new Contact(Integer.toString(i), Integer.toString(j)));
            }
        }

        List<ForbiddenRelation> frs = Bubbles.cleanBubbles(ccontacts, BSIZE);
        Collections.sort(frs);

        for (ForbiddenRelation fr : frs) {
            int a = Integer.parseInt(fr.a);
            int b = Integer.parseInt(fr.b);

            assertTrue(contacts[a].remove(b));
            assertTrue(contacts[b].remove(a));
        }
        for (HashSet<Integer> c : contacts)
            assertTrue(c.size() <= BSIZE);
    }
}
