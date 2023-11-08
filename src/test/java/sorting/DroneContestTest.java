package sorting;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@Grade
public class DroneContestTest {
    private static Drone[] copyAndShuffle(Drone[] in, Random r) {
        Drone[] out = Arrays.copyOf(in, in.length);
        Collections.shuffle(Arrays.asList(out), r);
        return out;
    }

    // BEGIN STRIP
    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void simple1() { //everything the same, without holes, without duplicates
        Random r = new Random(526829);
        for(int test = 0; test < 100; test++) {
            Drone[] content = new Drone[20];
            int start = r.nextInt(30)+1;
            int initStart = start;
            int height = r.nextInt(1000000)+1;
            for(int i = 0; i < 20; i++) {
                int end = start + r.nextInt(30)+1;
                content[i] = new Drone(start, end, height);
                start = end;
            }
            int initEnd = start;

            LinkedList<HeightChange> changes = DroneContest.findHighest(copyAndShuffle(content, r));
            assertEquals(3, changes.size());
            assertEquals( 0, changes.get(0).time);
            assertEquals( 0, changes.get(0).height);
            assertEquals(initStart, changes.get(1).time);
            assertEquals(height, changes.get(1).height);
            assertEquals(initEnd, changes.get(2).time);
            assertEquals(0, changes.get(2).height);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void simple2() { //everything the same, without holes, with duplicates below
        Random r = new Random(5268293);
        for(int test = 0; test < 100; test++) {
            Drone[] content = new Drone[40];
            int start = r.nextInt(30)+1;
            int initStart = start;
            int height = r.nextInt(1000000)+2;
            for(int i = 0; i < 20; i++) {
                int end = start + r.nextInt(30)+4;
                content[i] = new Drone(start, end, height);
                start = end;
            }
            int initEnd = start;

            for(int i = 20; i < 40; i++) {
                int a = r.nextInt(initEnd - initStart - 2) + initStart + 1;
                int b = r.nextInt(initEnd - initStart - 3) + initStart + 1;
                if(a == b) {
                    b += 1;
                }
                if(a > b) {
                    int c = b;
                    b = a;
                    a = c;
                }

                content[i] = new Drone(a, b, height/2);
            }

            LinkedList<HeightChange> changes = DroneContest.findHighest(copyAndShuffle(content, r));
            assertEquals(3, changes.size());
            assertEquals( 0, changes.get(0).time);
            assertEquals( 0, changes.get(0).height);
            assertEquals(initStart, changes.get(1).time);
            assertEquals(height, changes.get(1).height);
            assertEquals(start, changes.get(2).time);
            assertEquals(0, changes.get(2).height);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void simple3() { //everything the same with holes, without duplicates
        Random r = new Random(5268292);
        for(int test = 0; test < 100; test++) {
            Drone[] content = new Drone[20];
            int start = r.nextInt(30)+1;
            int height = r.nextInt(1000000)+2;
            boolean first = true;
            for(int i = 0; i < 20; i++) {
                int delay = 0;
                if(first)
                    first = false;
                else
                    delay = r.nextInt(30)+1;

                int end = delay + start + r.nextInt(30) + 4;
                content[i] = new Drone(delay + start, end, height);
                start = end;
            }

            LinkedList<HeightChange> changes = DroneContest.findHighest(copyAndShuffle(content, r));
            assertEquals(20*2 + 1, changes.size());
            int idx = 0;
            for(HeightChange c: changes) {
                if(idx == 0) {
                    assertEquals(0, c.height);
                    assertEquals(0, c.time);
                }
                else if(idx % 2 == 1) {
                    assertEquals(height, c.height);
                    assertEquals(content[(idx-1)/2].start, c.time);
                }
                else {
                    assertEquals(0, c.height);
                    assertEquals(content[(idx-2)/2].end, c.time);
                }
                idx++;
            }
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void simple4() { //one big thing, with a lot of useless things inside
        Random r = new Random(5268291);
        for(int test = 0; test < 100; test++) {
            Drone[] content = new Drone[100];
            int start = r.nextInt(100) + 10;
            int end = start + r.nextInt(100) + 10;
            int height = r.nextInt(100000) + 100;
            content[0] = new Drone(start, end, height);
            for(int i = 1; i < 100; i++) {
                int s = r.nextInt(end-start-3) + start + 1;
                int e = r.nextInt(end-start-3) + start + 1;
                if(e == s)
                    e++;
                if(s > e) {
                    int c = s;
                    s = e;
                    e = c;
                }
                content[i] = new Drone(s, e, r.nextInt(height-20)+10);
            }

            LinkedList<HeightChange> changes = DroneContest.findHighest(copyAndShuffle(content, r));
            assertEquals(3, changes.size());
            assertEquals( 0, changes.get(0).time);
            assertEquals( 0, changes.get(0).height);
            assertEquals(start, changes.get(1).time);
            assertEquals(height, changes.get(1).height);
            assertEquals(end, changes.get(2).time);
            assertEquals(0, changes.get(2).height);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void simple5Wide() { //one big thing, with a lot of useless things inside, but very wide
        Random r = new Random(5268291);
        for(int test = 0; test < 100; test++) {
            Drone[] content = new Drone[100];
            int start = r.nextInt(100) + 10;
            int end = start + r.nextInt(1000000) + 1000000;
            int height = r.nextInt(100000) + 100;
            content[0] = new Drone(start, end, height);
            for(int i = 1; i < 100; i++) {
                int s = r.nextInt(end-start-3) + start + 1;
                int e = r.nextInt(end-start-3) + start + 1;
                if(e == s)
                    e++;
                if(s > e) {
                    int c = s;
                    s = e;
                    e = c;
                }
                content[i] = new Drone(s, e, r.nextInt(height-20)+10);
            }

            List<Drone> l = Arrays.asList(content);
            Collections.shuffle(l, r);
            l.toArray(content);

            LinkedList<HeightChange> changes = DroneContest.findHighest(copyAndShuffle(content, r));
            assertEquals(3, changes.size());
            assertEquals( 0, changes.get(0).time);
            assertEquals( 0, changes.get(0).height);
            assertEquals(start, changes.get(1).time);
            assertEquals(height, changes.get(1).height);
            assertEquals(end, changes.get(2).time);
            assertEquals(0, changes.get(2).height);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 3000)
    public void simple6Numerous() { //one big thing, with a LOT of useless things inside
        Random r = new Random(5268291);
        for(int test = 0; test < 100; test++) {
            Drone[] content = new Drone[5000];
            int start = r.nextInt(100) + 10;
            int end = start + r.nextInt(100) + 10;
            int height = r.nextInt(100000) + 100;
            content[0] = new Drone(start, end, height);
            for(int i = 1; i < content.length; i++) {
                int s = r.nextInt(end-start-3) + start + 1;
                int e = r.nextInt(end-start-3) + start + 1;
                if(e == s)
                    e++;
                if(s > e) {
                    int c = s;
                    s = e;
                    e = c;
                }
                content[i] = new Drone(s, e, r.nextInt(height-20)+10);
            }

            LinkedList<HeightChange> changes = DroneContest.findHighest(copyAndShuffle(content, r));
            assertEquals(3, changes.size());
            assertEquals( 0, changes.get(0).time);
            assertEquals( 0, changes.get(0).height);
            assertEquals(start, changes.get(1).time);
            assertEquals(height, changes.get(1).height);
            assertEquals(end, changes.get(2).time);
            assertEquals(0, changes.get(2).height);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void tricky1() { //20 things starting at time 1, ending at 3, 20 things starting at time 3 ending at 6, 20 things starting at 7 ending at 8.
        Random r = new Random(5268291);
        for(int test = 0; test < 100; test++) { //test multiple shuffles
            Drone[] content = new Drone[60];
            for(int i = 0; i < 20; i++)
                content[i] = new Drone(1, 3, 1);
            for(int i = 20; i < 40; i++)
                content[i] = new Drone(3, 6, 1 + (test % 2));
            for(int i = 40; i < 60; i++)
                content[i] = new Drone(7, 8, 1 + (test % 3));

            LinkedList<HeightChange> changes = DroneContest.findHighest(copyAndShuffle(content, r));
            assertEquals( 0, changes.get(0).time);
            assertEquals( 0, changes.get(0).height);

            if((test % 2) == 0)
                assertEquals(5, changes.size());
            else
                assertEquals(6, changes.size());
            assertEquals(1, changes.get(1).time);
            assertEquals(1, changes.get(1).height);
            if((test % 2) == 0) {
                assertEquals(6, changes.get(2).time);
                assertEquals(0, changes.get(2).height);
                assertEquals(7, changes.get(3).time);
                assertEquals(1 + (test % 3), changes.get(3).height);
                assertEquals(8, changes.get(4).time);
                assertEquals(0, changes.get(4).height);
            }
            else {
                assertEquals(3, changes.get(2).time);
                assertEquals(1 + (test % 2), changes.get(2).height);
                assertEquals(6, changes.get(3).time);
                assertEquals(0, changes.get(3).height);
                assertEquals(7, changes.get(4).time);
                assertEquals(1 + (test % 3), changes.get(4).height);
                assertEquals(8, changes.get(5).time);
                assertEquals(0, changes.get(5).height);
            }
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void tricky2() { // everything starting/ending at the same place, different heights
        Random r = new Random(5268291);
        for(int test = 0; test < 100; test++) { //test multiple shuffles
            Drone[] content = new Drone[100];
            int start = r.nextInt(100)+1;
            int end = start + r.nextInt(100)+1;
            int maxHeight = 0;
            for(int i = 0; i < 100; i++) {
                int height = r.nextInt(100000)+1;
                maxHeight = Math.max(height, maxHeight);
                content[i] = new Drone(start, end, height);
            }

            LinkedList<HeightChange> changes = DroneContest.findHighest(copyAndShuffle(content, r));
            assertEquals( 0, changes.get(0).time);
            assertEquals( 0, changes.get(0).height);
            assertEquals( start, changes.get(1).time);
            assertEquals( maxHeight, changes.get(1).height);
            assertEquals( end, changes.get(2).time);
            assertEquals( 0, changes.get(2).height);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void tricky3() { // only one!
        Random r = new Random(5268291);
        for(int test = 0; test < 100; test++) { //test multiple shuffles
            Drone[] content = new Drone[1];
            int start = r.nextInt(100)+1;
            int end = start + r.nextInt(100)+1;
            int height = r.nextInt(100000)+1;
            content[0] = new Drone(start, end, height);

            LinkedList<HeightChange> changes = DroneContest.findHighest(copyAndShuffle(content, r));
            assertEquals( 0, changes.get(0).time);
            assertEquals( 0, changes.get(0).height);
            assertEquals( start, changes.get(1).time);
            assertEquals( height, changes.get(1).height);
            assertEquals( end, changes.get(2).time);
            assertEquals( 0, changes.get(2).height);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void tricky3Wide() {
        Random r = new Random(5268291);
        for(int test = 0; test < 100; test++) { //test multiple shuffles
            Drone[] content = new Drone[1];
            int start = r.nextInt(100)+1;
            int end = Integer.MAX_VALUE - 100;
            int height = r.nextInt(100000)+1;
            content[0] = new Drone(start, end, height);

            LinkedList<HeightChange> changes = DroneContest.findHighest(copyAndShuffle(content, r));
            assertEquals( 0, changes.get(0).time);
            assertEquals( 0, changes.get(0).height);
            assertEquals( start, changes.get(1).time);
            assertEquals( height, changes.get(1).height);
            assertEquals( end, changes.get(2).time);
            assertEquals( 0, changes.get(2).height);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void trickyComplexityWideSize() {
        Random r = new Random(5268291);
        for(int test = 0; test < 100; test++) { //test multiple shuffles
            Drone[] content = new Drone[10000];
            int start = r.nextInt(100)+1;
            int end = r.nextInt(100)+10000;
            for(int i = 0; i < 10000; i++)
                content[i] = new Drone(start, end, 100);

            LinkedList<HeightChange> changes = DroneContest.findHighest(copyAndShuffle(content, r));
            assertEquals( 0, changes.get(0).time);
            assertEquals( 0, changes.get(0).height);
            assertEquals( start, changes.get(1).time);
            assertEquals( 100, changes.get(1).height);
            assertEquals( end, changes.get(2).time);
            assertEquals( 0, changes.get(2).height);
        }
    }
    // END STRIP

    @Test
    @Grade(value = 2, cpuTimeout = 1000)
    public void example() { // only one!
        Drone[] participants = new Drone[7];
        participants[0] = new Drone(1, 5, 3);
        participants[1] = new Drone(3, 12, 5);
        participants[2] = new Drone(6, 14, 1);
        participants[3] = new Drone(7, 15, 4);
        participants[4] = new Drone(15, 18, 5);
        participants[5] = new Drone(16, 20, 1);
        participants[6] = new Drone(17, 19, 2);

        LinkedList<HeightChange> changes = DroneContest.findHighest(participants);


        int[] correctTime = new int[]{0, 1, 3, 12, 15, 18, 19, 20};
        int[] correctHeight = new int[]{0, 3, 5, 4, 5, 2, 1, 0};

        assertEquals(correctTime.length, changes.size());
        for(int i = 0; i < correctTime.length; i++) {
            assertEquals(correctTime[i], changes.get(i).time);
            assertEquals(correctHeight[i], changes.get(i).height);
        }
    }
    // BEGIN STRIP

    @Test
    @Grade(value = 3, cpuTimeout = 3000)
    public void randomSmall() {
        randomSize(200, 1, 100, 1000);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 3000)
    public void randomBigSize() {
        randomSize(10000, 1, 100, 50);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 5000)
    public void randomBigWide() {
        randomSize(200, 1, Integer.MAX_VALUE - 100, 20);
    }

    @Test
    @Grade(value = 2, cpuTimeout = 3000)
    public void randomBigWideAndSize() {
        randomSize(10000, 1, Integer.MAX_VALUE - 100, 20);
    }

    private static void randomSize(int size, int minBase, int maxBase, int nTests) {
        Random r = new Random(5262+size);
        for(int test = 0; test < nTests; test++) {
            Drone[] content = new Drone[size];
            int min = r.nextInt(5)+minBase;
            int max = r.nextInt(5)+maxBase;
            for(int i = 0; i < size; i++) {
                int s = r.nextInt(max - min) + min;
                int e = r.nextInt(max - min) + min;
                if(s == e)
                    e++;
                else if(s > e) {
                    int c = e;
                    e = s;
                    s = c;
                }

                int h = r.nextInt(10000000);
                content[i] = new Drone(s, e, h);
            }

            LinkedList<HeightChange> changes = DroneContest.findHighest(content);
            LinkedList<HeightChange> correct = findHighestSol(content);

            assertEquals(correct.size(), changes.size());
            Iterator<HeightChange> a = changes.iterator();
            Iterator<HeightChange> b = correct.iterator();
            while (a.hasNext()) {
                HeightChange x = a.next();
                HeightChange y = b.next();
                assertEquals(y.time, x.time);
                assertEquals(y.height, x.height);
            }
        }
    }

    private static LinkedList<HeightChange> findHighestSol(Drone[] participants) {
        LinkedList<HeightChange> output = new LinkedList<>();

        output.add(new HeightChange(0, 0));

        class Event {
            int time;
            int height;
            boolean add;
            public Event(int t, int h, boolean a) {
                time = t;
                height = h;
                add = a;
            }
        }

        Event[] events = new Event[participants.length * 2];

        for(int i = 0; i < participants.length; i++) {
            events[2*i] = new Event(participants[i].start, participants[i].height, true);
            events[2*i+1] = new Event(participants[i].end, participants[i].height, false);
        }

        Arrays.sort(events, (a, b) -> {
            if(a.time != b.time)
                return a.time - b.time;
            if(a.add != b.add)
                return a.add ? -1 : 1;
            return b.height - a.height;
        });

        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(0, 1);
        for(Event e: events) {
            if(e.add) {
                map.put(e.height, map.getOrDefault(e.height, 0) + 1);
            }
            else {
                int cur = map.get(e.height);
                if(cur == 1)
                    map.remove(e.height);
                else
                    map.put(e.height, cur - 1);
            }

            if(map.lastKey() != output.getLast().height) {
                if(output.getLast().time == e.time)
                    output.getLast().height = map.lastKey();
                else
                    output.add(new HeightChange(e.time, map.lastKey()));
            }
        }

        return output;
    }
    // END STRIP
}
