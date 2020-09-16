package effectivejava.chapter7.item47;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * It is possible to translate for-loop Version 3 directly into a stream. The
 * result is Version 2 which is more concise than Version 1, but perhaps a bit
 * less readable. Like the for-loop in Version 3, Version 2 does not emit the
 * empty list. In order to fix this deficiency, you could either use concat, as
 * we did in the previous version 1, or replace 1 by (int) Math.signum(start) in
 * the rangeClosed call.
 *
 */
// Three ways to generate a stream of all the sublists of a list (Pages 219-20)
public class SubLists {
	// Version 1 : Returns a stream of all the sublists of its input list (Page 219)
	public static <E> Stream<List<E>> of(List<E> list) {
		return Stream.concat(Stream.of(Collections.emptyList()), prefixes(list).flatMap(SubLists::suffixes));
	}

	private static <E> Stream<List<E>> prefixes(List<E> list) {
		return IntStream.rangeClosed(1, list.size()).mapToObj(end -> list.subList(0, end));
	}

	private static <E> Stream<List<E>> suffixes(List<E> list) {
		return IntStream.range(0, list.size()).mapToObj(start -> list.subList(start, list.size()));
	}

	// ---------------------------------------------------------------------------------
	// Version 2: Returns a stream of all the sublists of its input list, excluding
	// the empty list This version is derived from the obvious iterative code (Page
	// 220)
	public static <E> Stream<List<E>> ofStream(List<E> list) {
		return IntStream.range(0, list.size()).mapToObj(
				start -> IntStream.rangeClosed(start + 1, list.size()).mapToObj(end -> list.subList(start, end)))
				.flatMap(x -> x);
	}

	public static void main(String[] args) {
		String[] strings = { "a", "b", "c" };
		List<String> list = Arrays.asList(strings);

		System.out.println("------------- Version 1 : -------------------------");
		SubLists.of(list).forEach(System.out::println);

		System.out.println("--------------- Version 2 : -------------------------");
		SubLists.ofStream(list).forEach(System.out::println);

		System.out.println("----------------- Version 3 : -----------------------");
		// Our sublist implementation is similar in spirit to the obvious nested
		// for-loop:
		for (int start = 0; start < list.size(); start++)
			for (int end = start + 1; end <= list.size(); end++)
				System.out.println(list.subList(start, end));
	}
}
