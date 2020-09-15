package effectivejava.chapter7.item45;

import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 
 * The following program solves the same problem, using streams without
 * overusing them. The result is a program that’s both shorter and clearer than
 * the original:
 * 
 * In the absence of explicit types, careful naming of lambda parameters is
 * essential to the readability of stream pipelines.
 * 
 * Note also that word alphabetization is done in a separate alphabetize method.
 * This enhances readability by providing a name for the operation and keeping
 * implementation details out of the main program. Using helper methods is even
 * more important for readability in stream pipelines than in iterative code
 * because pipelines lack explicit type information and named temporary
 * variables.
 * 
 * Refactor existing code to use streams and use them in new code only where it
 * makes sense to do so.
 * 
 * ----------------------------------------------------------------------------------
 * There are some things you can do from code blocks that you can’t do from
 * function objects:
 * 
 * • From a code block, you can read or modify any local variable in scope; from
 * a lambda, you can only read final or effectively final variables [JLS
 * 4.12.4], and you can’t modify any local variables.
 * 
 * • From a code block, you can return from the enclosing method, break or
 * continue an enclosing loop, or throw any checked exception that this method
 * is declared to throw; from a lambda you can do none of these things.
 * 
 * If a computation is best expressed using these techniques, then it’s probably
 * not a good match for streams. Conversely, streams make it very easy to do
 * some things: • Uniformly transform sequences of elements
 * 
 * • Filter sequences of elements
 * 
 * • Combine sequences of elements using a single operation (for example to add
 * them, concatenate them, or compute their minimum)
 * 
 * • Accumulate sequences of elements into a collection, perhaps grouping them
 * by some common attribute
 * 
 * • Search a sequence of elements for an element satisfying some criterion
 *
 * -------------------------------------------------------------------------------
 */
//Tasteful use of streams enhances clarity and conciseness
public class AnagramsEfficient {
	public static void main(String[] args) throws IOException {
		Stream<String> strings = Stream.of("eat", "tea", "tan", "ate", "nat", "bat", "ban", "bet", "nab");
		int minGroupSize = 1;

		// Path dictionary = Paths.get(args[0])
		// try (Stream<String> words = Files.lines(dictionary)) {
		try (Stream<String> words = strings) {
			words.collect(groupingBy(word -> alphabetize(word))).values().stream()
					.filter(group -> group.size() >= minGroupSize)
					.forEach(g -> System.out.println(g.size() + ": " + g));
		}

	}

	private static String alphabetize(String s) {
		char[] a = s.toCharArray();
		Arrays.sort(a);
		return new String(a);
	}
}