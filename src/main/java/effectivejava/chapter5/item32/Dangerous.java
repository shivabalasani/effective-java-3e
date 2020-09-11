package effectivejava.chapter5.item32;

import java.util.List;

/**
 * 
 * Item 32: Combine generics and varargs judiciously
 * 
 * In summary, varargs and generics do not interact well because the varargs
 * facility is a leaky abstraction built atop arrays, and arrays have different
 * type rules from generics. Though generic varargs parameters are not typesafe,
 * they are legal. If you choose to write a method with a generic (or
 * parameterized) varargs parameter, first ensure that the method is typesafe,
 * and then annotate it with @SafeVarargs so it is not unpleasant to use.
 *
 */
// It is unsafe to store a value in a generic varargs array parameter (Page 146)
public class Dangerous {

	// Mixing generics and varargs can violate type safety!
	static void dangerous(List<String>... stringLists) {
		List<Integer> intList = List.of(42);
		Object[] objects = stringLists;
		objects[0] = intList; // Heap pollution

		// This cast fails, demonstrating that type safety has been compromised,
		// and it is unsafe to store a value in a generic varargs array parameter.
		String s = stringLists[0].get(0); // ClassCastException
	}

	public static void main(String[] args) {
		dangerous(List.of("There be dragons!"));
	}
}
