package graphs;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
// END STRIP

@Grade
public class ErdosTest {

	@Test
	@Grade(value = 0.75)
	@GradeFeedback(message="Your algorithm does not return the correct result on the given example. Debug it locally", on=TestResultStatus.FAIL)
	public void testSimple() {
		ArrayList<String []> authors = new ArrayList<>();
		authors.add(new String [] { "Paul Erdös", "Edsger W. Dijkstra" });
		authors.add(new String [] { "Edsger W. Dijkstra", "Alan M. Turing" });
		authors.add(new String [] { "Edsger W. Dijkstra", "Donald Knuth" });
		authors.add(new String [] { "Donald Knuth", "Stephen Cook", "Judea Pearl" });

		Erdos erdos = new Erdos(authors);
		assertEquals(0, erdos.findErdosNumber(Erdos.erdos));
		assertEquals(1, erdos.findErdosNumber("Edsger W. Dijkstra"));
		assertEquals(2, erdos.findErdosNumber("Alan M. Turing"));
		assertEquals(2, erdos.findErdosNumber("Donald Knuth"));
		assertEquals(3, erdos.findErdosNumber("Stephen Cook"));
		assertEquals(3, erdos.findErdosNumber("Judea Pearl"));
	}

	// BEGIN STRIP

	private final int LARGE_DATASET_SIZE = 1000; // 100_000;
	private final int MIDDLE_DATASET_SIZE = 500; //1_000;
	private String [] authors_small = new String[] { Erdos.erdos, "Pierre", "Siegfried", "Luc", "Yves", "Donald", "Alan", "Vanessa", "Eric", "Cristelle" };
	private String [] authors_middle = Stream.concat(Stream.of(Erdos.erdos), IntStream.range(0, MIDDLE_DATASET_SIZE).mapToObj(Integer::toString)).toArray(String[]::new);
	private String [] authors_large = Stream.concat(Stream.of(Erdos.erdos), IntStream.range(0, LARGE_DATASET_SIZE).mapToObj(Integer::toString)).toArray(String[]::new);

	@Test
	@Grade(value = 0.75)
	@GradeFeedback(message="Your algorithm does not return the correct result when each author has a collaboration with every other author", on=TestResultStatus.FAIL)
	@GradeFeedback(message="Your code is too slow. Check that you have the requested complexity", on=TestResultStatus.TIMEOUT)
	public void smallTestMesh() {
		ArrayList<String []> authors = new ArrayList<>();
		for (int i = 0; i < authors_small.length; i++) {
			for (int j = i+1; j < authors_small.length; j++) {
				authors.add(new String [] { authors_small[i], authors_small[j] });
			}
		}
		Erdos erdos = new Erdos(authors);
		assertEquals(0, erdos.findErdosNumber(Erdos.erdos));
		for (int i = 1; i < authors_small.length; i++) {
			assertEquals(1, erdos.findErdosNumber(authors_small[i]));
		}
	}

	@Test
	@Grade(value = 0.75)
	@GradeFeedback(message="Your algorithm does not return the correct result when there is a paper with all authors.", on=TestResultStatus.FAIL)
	@GradeFeedback(message="Your code is too slow. Check that you have the requested complexity", on=TestResultStatus.TIMEOUT)
	public void smallTestOneForAll() {
		ArrayList<String []> authors = new ArrayList<>();
		authors.add(new String [] {authors_small[1], authors_small[3]});
		authors.add(new String [] {authors_small[3], authors_small[6]});
		authors.add(new String [] {authors_small[5], authors_small[4]});
		authors.add(authors_small);

		Erdos erdos = new Erdos(authors);
		assertEquals(0, erdos.findErdosNumber(Erdos.erdos));
		for (int i = 1; i < authors_small.length; i++) {
			assertEquals(1, erdos.findErdosNumber(authors_small[i]));
		}
	}

	@Test
	@Grade(value = 0.75)
	@GradeFeedback(message="Your algorithm does not return the correct result when there is a single way to link them to Erdos", on=TestResultStatus.FAIL)
	@GradeFeedback(message="Your code is too slow. Check that you have the requested complexity", on=TestResultStatus.TIMEOUT)
	public void smallTestChaining() {
		ArrayList<String []> authors = new ArrayList<>();
		for (int i = 0; i < authors_small.length - 1; i++) {
			authors.add(new String [] { authors_small[i], authors_small[i+1] });
		}

		Erdos erdos = new Erdos(authors);
		assertEquals(0, erdos.findErdosNumber(Erdos.erdos));
		for (int i = 1; i < authors_small.length; i++) {
			assertEquals(i, erdos.findErdosNumber(authors_small[i]));
		}
	}

	@Test
	@Grade(value = 1, cpuTimeout = 500, unit=TimeUnit.MILLISECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	@GradeFeedback(message="Your code is too slow. Check that you have the requested complexity", on=TestResultStatus.FAIL)
	public void middleTestMesh() {
		ArrayList<String []> authors = new ArrayList<>();
		for (int i = 0; i < authors_middle.length; i++) {
			for (int j = i+1; j < authors_middle.length; j++) {
				authors.add(new String [] { authors_middle[i], authors_middle[j] });
			}
		}
		Erdos erdos = new Erdos(authors);
		assertEquals(0, erdos.findErdosNumber(Erdos.erdos));
		for (int i = 1; i < authors_middle.length; i++) {
			assertEquals(1, erdos.findErdosNumber(authors_middle[i]));
		}
	}

	@Test
	@Grade(value = 1, cpuTimeout = 500, unit=TimeUnit.MILLISECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	@GradeFeedback(message="Your code is too slow. Check that you have the requested complexity", on=TestResultStatus.FAIL)
	public void middleTestOneForAll() {
		ArrayList<String []> authors = new ArrayList<>();
		authors.add(new String [] {authors_middle[1], authors_middle[3]});
		authors.add(new String [] {authors_middle[3], authors_middle[6]});
		authors.add(new String [] {authors_middle[5], authors_middle[4]});
		authors.add(authors_middle);

		Erdos erdos = new Erdos(authors);
		assertEquals(0, erdos.findErdosNumber(Erdos.erdos));
		for (int i = 1; i < authors_middle.length; i++) {
			assertEquals(1, erdos.findErdosNumber(authors_middle[i]));
		}
	}

	@Test
	@Grade(value = 1, cpuTimeout = 1000, unit= TimeUnit.MILLISECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	@GradeFeedback(message="Your code is too slow. Check that you have the requested complexity", on=TestResultStatus.FAIL)
	public void largeTestChaining() {
		ArrayList<String []> authors = new ArrayList<>();
		for (int i = 0; i < authors_large.length - 1; i++) {
			authors.add(new String [] { authors_large[i], authors_large[i+1] });
		}

		Erdos erdos = new Erdos(authors);
		assertEquals(0, erdos.findErdosNumber(Erdos.erdos));
		for (int i = 1; i < authors_large.length; i++) {
			assertEquals(i, erdos.findErdosNumber(authors_large[i]));
		}
	}

	// END STRIP
}
