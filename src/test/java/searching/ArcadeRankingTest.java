package searching;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Grade
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArcadeRankingTest {

    @Test
    @Grade(value = 1)
    @Order(0)
    @GradeFeedback(message="Your comparator is not correct, the players are not sorted decreasingly. Debug it locally", on=TestResultStatus.FAIL)
    public void testComparator() {
        Set<Player> set = new HashSet<>();
        set.add(new Player(15, "G4M3R"));
        set.add(new Player(2, "FoxhoundFinn33"));
        set.add(new Player(646, "Xx_DarkLink_xX"));
        set.add(new Player(584, "Faker"));
        set.add(new Player(34, "DiabloX9"));
        set.add(new Player(842, "Rapha"));
        MyPlayers players = new MyPlayers(set);
        // verify that players are sorted decreasingly
        for (int i = 0; i < players.size()-1; i++) {
            assertTrue(players.get(i).score >= players.get(i+1).score);
        }
    }

    @Test
    @Grade(value = 1)
    @Order(1)
    @GradeFeedback(message="Your algorithm does not return the correct result on the given example for getRank. Debug it locally", on=TestResultStatus.FAIL)
    public void testGetRankSmall() {
        Set<Player> set = new HashSet<>();
        set.add(new Player(15, "G4M3R"));
        set.add(new Player(2, "FoxhoundFinn33"));
        set.add(new Player(646, "Xx_DarkLink_xX"));
        set.add(new Player(584, "Faker"));
        set.add(new Player(34, "DiabloX9"));
        set.add(new Player(842, "Rapha"));

        // sorted scores: 842, 646, 584, 34, 15, 2
        // ranks:           0,   1,   2,  3,  4, 5

        MyPlayers players = new MyPlayers(set);
        assertEquals(0, ArcadeRanking.getRank(players,842)); // this score is present
        assertEquals(1, ArcadeRanking.getRank( players,650));
        assertEquals(1, ArcadeRanking.getRank( players,646)); // this score is present
        assertEquals(2, ArcadeRanking.getRank(players,600));
        assertEquals(2, ArcadeRanking.getRank(players,584)); // this score is present
        assertEquals(3, ArcadeRanking.getRank(players,34)); // this score is present
        assertEquals(4, ArcadeRanking.getRank(players, 15)); // this score is present
        assertEquals(5, ArcadeRanking.getRank(players,3));
        assertEquals(5, ArcadeRanking.getRank(players,2)); // this score is present
        assertEquals(6, ArcadeRanking.getRank(players,0));
    }

    @Test
    @Grade(value = 2, cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @Order(2)
    @GradeFeedback(message="Your algorithm exceed the log number of calls for getRank. Debug it locally", on=TestResultStatus.FAIL)
    public void testGetRankComplexity() {
        Set<Player> set = new HashSet<>();
        int logn = 10;
        int n = (1 << logn);
        // add players with a score from 0 to n-1 (where n = 2^logn)
        for (int i = 0; i < n; i++) {
            set.add(new Player(i*2, "Player" + i));
        }
        MyPlayers players = new MyPlayers(set);
        for (int i = 0; i < n; i++) {
            // verify the rank is correct
            assertEquals(n-i-1, ArcadeRanking.getRank(players, i*2)); // present
            // verify the number of calls to get is at most 2*log(n)
            assertTrue(players.getAndResetCalls() <= 4*logn+5);
        }
        for (int i = 0; i < n; i++) {
            // verify the rank is correct
            assertEquals(n-i-1, ArcadeRanking.getRank(players, i*2+1)); // not present
            // verify the number of calls to get is at most 2*log(n)
            assertTrue(players.getAndResetCalls() <= 4*logn+5);
        }

    }

    // Implementation of the Player interface
    // Do not modify this class otherwise you may not pass the tests
    class MyPlayers implements ArcadeRanking.Players {

        Player [] players;
        int nbGet = 0;

        public MyPlayers(Set<Player> players) {
            this.players = players.stream().toArray(Player[]::new);
            Arrays.sort(this.players);
        }

        @Override
        public Player get(int i) {
            nbGet++;
            return players[i];
        }

        @Override
        public int size() {
            return players.length;
        }

        private int getAndResetCalls() {
            int r = nbGet;
            nbGet = 0;
            return r;
        }
    }

}
