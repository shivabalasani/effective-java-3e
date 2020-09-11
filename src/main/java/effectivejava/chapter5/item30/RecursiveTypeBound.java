package effectivejava.chapter5.item30;

import java.util.*;

/**
 * 
 * It is permissible, though relatively rare, for a type parameter to be bounded
 * by some expression involving that type parameter itself. This is what’s known
 * as a recursive type bound. A common use of recursive type bounds is in
 * connection with the Comparable interface, which defines a type’s natural
 * ordering (Item 14).
 * 
 * Comparables are always consumers, so you should generally use Comparable<?
 * super T> in preference to Comparable<T>. The same is true of comparators;
 * therefore, you should generally use Comparator<? super T> in preference to
 * Comparator<T>.
 *
 */
// Using a recursive type bound to express mutual comparability (Pages 137-8)
public class RecursiveTypeBound {
	// Returns max value in a collection - uses recursive type bound
	public static <E extends Comparable<E>> E max(Collection<E> c) {
		if (c.isEmpty())
			throw new IllegalArgumentException("Empty collection");

		E result = null;
		for (E e : c)
			if (result == null || e.compareTo(result) > 0)
				result = Objects.requireNonNull(e);

		return result;
	}

	public static void main(String[] args) {
		String[] strings = { "jute", "hemp", "nylon" };
		List<String> argList = Arrays.asList(strings);
		System.out.println(max(argList));
	}
}