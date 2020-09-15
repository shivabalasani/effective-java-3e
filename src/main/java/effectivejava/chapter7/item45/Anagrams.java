package effectivejava.chapter7.item45;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * Item 45: Use streams judiciously.
 * 
 * The streams API was added in Java 8 to ease the task of performing bulk
 * operations, sequentially or in parallel. This API provides two key
 * abstractions: the stream, which represents a finite or infinite sequence of
 * data elements, and the stream pipeline, which represents a multistage
 * computation on these elements. The elements in a stream can come from
 * anywhere. Common sources include collections, arrays, files, regular
 * expression pattern matchers, pseudorandom number generators, and other
 * streams. The data elements in a stream can be object references or primitive
 * values. Three primitive types are supported: int, long, and double.
 * 
 * A stream pipeline consists of a source stream followed by zero or more
 * intermediate operations and one terminal operation.Stream pipelines are
 * evaluated lazily: evaluation doesn’t start until the terminal operation is
 * invoked, and data elements that aren’t required in order to complete the
 * terminal operation are never computed. This lazy evaluation is what makes it
 * possible to work with infinite streams.
 *
 * In summary, some tasks are best accomplished with streams, and others with
 * iteration. Many tasks are best accomplished by combining the two approaches.
 * There are no hard and fast rules for choosing which approach to use for a
 * task, but there are some useful heuristics. In many cases, it will be clear
 * which approach to use; in some cases, it won’t. If you’re not sure whether a
 * task is better served by streams or iteration, try both and see which works
 * better.
 */
//Prints all large anagram groups in a dictionary iteratively
//Two words are anagrams if they consist of the same letters in a different order.
public class Anagrams {
	public static void main(String[] args) throws IOException {
		String str = "eat tea tan ate nat bat ban bet nab";
		int minGroupSize = 1;
		Map<String, Set<String>> groups = new HashMap<>();

		// File dictionary = new File(args[0]);
		// try (Scanner s = new Scanner(dictionary)) {
		try (Scanner s = new Scanner(str)) {
			while (s.hasNext()) {
				String word = s.next();
				groups.computeIfAbsent(alphabetize(word), (unused) -> new TreeSet<>()).add(word);
			}
		}
		for (Set<String> group : groups.values())
			if (group.size() >= minGroupSize)
				System.out.println(group.size() + ": " + group);
	}

	private static String alphabetize(String s) {
		char[] a = s.toCharArray();
		Arrays.sort(a);
		return new String(a);
	}
}
