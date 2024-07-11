package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
// END STRIP

/**
 * The erdos number is a "collaborative distance" metric to Paul Erdos (a prolific mathematician)
 * based on co-authorship of mathematical articles.
 * It is computed as follows:
 * - Erdos has, by definition an erdos-number of 0.
 * - For each other author, we look at all his/her co-authors in each article.
 *   If n is the minimum erdos-number from all his co-authors, then this author has an erdos-number of n+1.
 *
 * For example:
 *
 * Given this set of co-authors relations:
 *
 * 		{ "Paul Erdös", "Edsger W. Dijkstra" }
 * 		{ "Edsger W. Dijkstra", "Alan M. Turing" }
 * 		{ "Edsger W. Dijkstra", "Donald Knuth" }
 * 		{ "Donald Knuth", "Stephen Cook", "Judea Pearl" }
 *
 * 	The erdos number of Paul Erdos is 0, of Edsger W. Dijkstra is 1, of Alan M. Turing is 2, of Donald Knuth is 2, of Stephen Cook is 3.
 *
 * 	Debug your code on the small examples in the test suite.
 */
public class Erdos {

	public static final String erdos = "Paul Erdös";

	// BEGIN STRIP
	private HashMap<String, Integer> distances;
	// END STRIP


	/**
	 * Constructs an Erdos object and computes the Erdős numbers for each author.
	 *
	 * The constructor should run in O(n*m^2) where n is the number of co-author relations,
	 * and m the maximum number of co-authors in one article.
	 *
	 * @param articlesAuthors An ArrayList of String arrays, where each array represents the list of authors of a single article.
	 */
	public Erdos(ArrayList<String []> articlesAuthors) {
		// TODO
		// BEGIN STRIP
		HashMap<String, HashSet<String>> adj = new HashMap<>();
		this.distances = new HashMap<>();

		// Compute the adjacency matrix from the list of articles
		for (String [] authors : articlesAuthors) {
			for (int i = 0; i < authors.length; i++) {
				for (int j = i+1; j < authors.length; j++) {
					if (!adj.containsKey(authors[i])) {
						adj.put(authors[i], new HashSet<String>());
					}
					if (!adj.containsKey(authors[j])) {
						adj.put(authors[j], new HashSet<String>());
					}
					adj.get(authors[i]).add(authors[j]);
					adj.get(authors[j]).add(authors[i]);
				}
			}
		}

		// Compute the distance starting from erdos, doing a BFS on the graph of co-authors
		LinkedList<QEntry> q = new LinkedList<>();
		q.add(new QEntry(Erdos.erdos, 0));

		while (!q.isEmpty()) {
			QEntry entry = q.pop();
			String author = entry.author;
			int distance = entry.distance;
			if (!distances.containsKey(author)) {
				this.distances.put(author, distance);
				for (String coAuthor : adj.get(author)) {
					if (!distances.containsKey(coAuthor)) {
						q.add(new QEntry(coAuthor, distance + 1));
					}
				}
			}
		}
		// END STRIP
	}

	/**
	 * Returns the Erdős number of a given author.
	 * This method is expected to run in O(1).
	 * @param author The name of the author whose Erdős number is to be found.
	 * @return The Erdős number of the specified author. If the author is not in the network, returns -1.
	 */
	public int findErdosNumber(String author) {
		// TODO
		// STUDENT return -1;
		// BEGIN STRIP
		return this.distances.get(author);
		// END STRIP
	}

	// BEGIN STRIP
	private class QEntry {
		public String author;
		public int distance;

		private QEntry(String author, int distance) {
			this.author = author;
			this.distance = distance;
		}
	}
	// END STRIP
}
