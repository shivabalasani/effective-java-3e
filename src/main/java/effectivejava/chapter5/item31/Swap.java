package effectivejava.chapter5.item31;

import java.util.*;

/**
 * 
 * Here are two possible declarations for a static method to swap two indexed
 * items in a list. The first uses an unbounded type parameter (Item 30) and the
 * second an unbounded wildcard: // Two possible declarations for the swap
 * method
 * 
 * public static <E> void swap(List<E> list, int i, int j);
 * 
 * public static void swap(List<?> list, int i, int j);
 * 
 * Which of these two declarations is preferable, and why? In a public API, the
 * second is better because it’s simpler. You pass in a list—any list—and the
 * method swaps the indexed elements. There is no type parameter to worry about.
 * As a rule, if a type parameter appears only once in a method declaration,
 * replace it with a wildcard. If it’s an unbounded type parameter, replace it
 * with an unbounded wildcard; if it’s a bounded type parameter, replace it with
 * a bounded wildcard.
 *
 */
// Private helper method for wildcard capture (Page 145)
public class Swap {
	public static void swap(List<?> list, int i, int j) {
		// Swap.java:5: error: incompatible types: Object cannot be
		// converted to CAP#1
		
		// It doesn’t seem right that we can’t put an element back into the list that we
		// just took it out of. The problem is that the type of list is List<?>, and you
		// can’t put any value except null into a List<?>.
		
		// list.set(i, list.set(j, list.get(i)));
		swapHelper(list, i, j);
	}

	// Private helper method for wildcard capture
	private static <E> void swapHelper(List<E> list, int i, int j) {
		list.set(i, list.set(j, list.get(i)));
	}

	public static void main(String[] args) {
		// Swap the first and last argument and print the resulting list
		String[] strings = { "jute", "hemp", "cotton", "nylon" };
		List<String> argList = Arrays.asList(strings);
		swap(argList, 0, argList.size() - 1);
		System.out.println(argList);
	}
}
