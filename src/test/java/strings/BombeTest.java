package strings;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.security.SecureRandom;

import strings.Bombe.*;

public class BombeTest {


    public static String randRow(int n, String letters) {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(letters.charAt(rnd.nextInt(letters.length())));
        }
        return sb.toString();
    }

    public static String rotateString(String s, int k) {
        if (s == null || s.isEmpty()) return s;
        List<Character> list = new ArrayList<>(s.length());
        for (char c : s.toCharArray()) list.add(c);
        Collections.rotate(list, -k);
        StringBuilder sb = new StringBuilder(list.size());
        for (char c : list) sb.append(c);
        return sb.toString();
    }

    @Test
    @Grade(value = 1)
    public void sampleTest1() {
        char[][] bomb = new char[][]{
                "RGRBB".toCharArray(),
                "RBBGB".toCharArray(),
                "BBBRB".toCharArray(),
                "GRBBR".toCharArray()
        };

        assertArrayEquals(new int[]{1, 4}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }

    @Test
    @Grade(value = 1)
    public void sampleTest2() {
        char[][] bomb = new char[][]{
                "RRRRRR".toCharArray(),
                "GGGGGG".toCharArray(),
                "BBBBBB".toCharArray(),
                "RGRGRG".toCharArray()
        };

        assertArrayEquals(new int[]{-1, -1}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }

    @Test
    @Grade(value = 1)
    public void subPatternExists() {
        char[][] bomb = new char[][]{
                "RGRGRGRGRGRGRGRG".toCharArray(),
                "GRGRGRGRGRGRGRGR".toCharArray(),
        };
        assertArrayEquals(new int[]{1, 2}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }

    @Test
    @Grade(value = 1)
    public void almostRotation() {
        char[][] bomb = new char[][]{
                "RGBRGBRGB".toCharArray(),
                "RGBRGBRGG".toCharArray(),
        };
        assertArrayEquals(new int[]{-1, -1}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }

    @Test
    @Grade(value = 1)
    public void minOrder() {
        char[][] bomb = new char[][]{
                "aaa".toCharArray(),
                "bbb".toCharArray(),
                "aaa".toCharArray(),
                "bbb".toCharArray(),
                "ccc".toCharArray(),
                "ccc".toCharArray()
        };
        assertArrayEquals(new int[]{1, 3}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }

    @Test
    @Grade(value = 1)
    public void minOrder2() {
        char[][] bomb = new char[][]{
                "aaa".toCharArray(),
                "aab".toCharArray(),
                "aac".toCharArray(),
                "aab".toCharArray(),
                "aaa".toCharArray(),
                "aab".toCharArray()
        };
        assertArrayEquals(new int[]{1, 5}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }

    @Test
    @Grade(value = 1)
    public void uniqueCharPerRow() {
        char[][] bomb = new char[][]{
                "rrrrrrrrrrrr".toCharArray(),
                "gggggggggggg".toCharArray(),
                "cccccccccccc".toCharArray(),
                "aaaaaaaaaaaa".toCharArray(),
                "bbbbbbbbbbbb".toCharArray()
        };

        assertArrayEquals(new int[]{-1, -1}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000, unit = TimeUnit.MILLISECONDS)
    public void complexityTestBoom1() { // n*m <= 5*10^5 (on donne une bonne marge)
        char[][] bomb = new char[100][5000];
        final String letters = "abcdefghijklmnopqrstuvwxyz";

        for(int i = 0; i < 100; i++) {
            bomb[i] = randRow(5000, letters).toCharArray();
        }
        assertArrayEquals(new int[]{-1, -1}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }
    @Test
    @Grade(value = 1, cpuTimeout = 1000, unit = TimeUnit.MILLISECONDS)
    public void complexityTestBoom2() { // n*m <= 5*10^5 (on donne une bonne marge)
        char[][] bomb = new char[5000][100];
        final String letters = "abcdefghijklmnopqrstuvwxyz";

        for(int i = 0; i < 5000; i++) {
            bomb[i] = randRow(100, letters).toCharArray();
        }
        assertArrayEquals(new int[]{-1, -1}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000, unit = TimeUnit.MILLISECONDS)
    public void complexityTestBoom3() {
        char[][] bomb = new char[50][10000];
        final String letters = "abcdefghijklmnopqrstuvwxyz";

        for(int i = 0; i < 50; i++) {
            bomb[i] = randRow(10000, letters).toCharArray();
        }
        assertArrayEquals(new int[]{-1, -1}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000, unit = TimeUnit.MILLISECONDS)
    public void avoidSortOptimization() {
        char[][] bomb = new char[50][10000];
        final String letters = "ab";

        for(int i = 0; i < 50; i++) {
            bomb[i] = randRow(10000, letters).toCharArray();
        }
        assertArrayEquals(new int[]{-1, -1}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000, unit = TimeUnit.MILLISECONDS)
    public void avoidSortOptimization2() {
        char[][] bomb = new char[50][10000];
        final String letters = "abcd";

        for(int i = 0; i < 50; i++) {
            bomb[i] = randRow(10000, letters).toCharArray();
        }
        assertArrayEquals(new int[]{-1, -1}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000, unit = TimeUnit.MILLISECONDS)
    public void complexityTestSolution1() {
        char[][] bomb = new char[50][10000];
        final String letters = "abcdefghijklmnopqrstuvwxyz";

        for(int i = 0; i < bomb.length; i++) {
            bomb[i] = (randRow(bomb[0].length, letters).toCharArray());
        }
        SecureRandom rnd = new SecureRandom();
        int first = rnd.nextInt(bomb.length);
        int second = first;
        while(second == first) {
            second = rnd.nextInt(bomb.length);
        }

        bomb[first] = bomb[second];
        String rotated = rotateString(new String(bomb[first]), rnd.nextInt(2*bomb[0].length));
        bomb[first] = rotated.toCharArray();
        assertArrayEquals(new int[]{Math.min(first+1, second+1), Math.max(first+1, second+1)}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }
    @Test
    @Grade(value = 1, cpuTimeout = 1000, unit = TimeUnit.MILLISECONDS)
    public void complexityTestSolution2() {
        char[][] bomb = new char[10000][50];
        final String letters = "abcdefghijklmnopqrstuvwxyz";

        for(int i = 0; i < bomb.length; i++) {
            bomb[i] = (randRow(bomb[0].length, letters).toCharArray());
        }
        SecureRandom rnd = new SecureRandom();
        int first = rnd.nextInt(bomb.length);
        int second = first;
        while(second == first) {
            second = rnd.nextInt(bomb.length);
        }

        bomb[first] = bomb[second];
        String rotated = rotateString(new String(bomb[first]), rnd.nextInt(2*bomb[0].length));
        bomb[first] = rotated.toCharArray();
        assertArrayEquals(new int[]{Math.min(first+1, second+1), Math.max(first+1, second+1)}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000, unit = TimeUnit.MILLISECONDS)
    public void complexityTestSolution3() {
        char[][] bomb = new char[707][707];
        final String letters = "abcdefghijklmnopqrstuvwxyz";

        for(int i = 0; i < bomb.length; i++) {
            bomb[i] = (randRow(bomb[0].length, letters).toCharArray());
        }
        SecureRandom rnd = new SecureRandom();
        int first = rnd.nextInt(bomb.length);
        int second = first;
        while(second == first) {
            second = rnd.nextInt(bomb.length);
        }

        bomb[first] = bomb[second];
        String rotated = rotateString(new String(bomb[first]), rnd.nextInt(2*bomb[0].length));
        bomb[first] = rotated.toCharArray();
        assertArrayEquals(new int[]{Math.min(first+1, second+1), Math.max(first+1, second+1)}, Bombe.solve(bomb.length, bomb[0].length, bomb));
    }

}
