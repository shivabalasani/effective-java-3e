package effectivejava.chapter8.item55;

import java.util.Optional;

/**
 * 
 * When programming with streams, it is not uncommon to find yourself with a
 * Stream<Optional<T>> and to require a Stream<T> containing all the elements in
 * the nonempty optionals in order to proceed. If you’re using Java 8, here’s
 * how to bridge the gap:
 * 
 * streamOfOptionals .filter(Optional::isPresent).map(Optional::get)
 * 
 * In Java 9, Optional was outfitted with a stream() method. This method is an
 * adapter that turns an Optional into a Stream containing an element if one is
 * present in the optional, or none if it is empty. In conjunction with Stream’s
 * flatMap method (Item 45), this method provides a concise replacement for the
 * code snippet above:
 * 
 * streamOfOptionals.flatMap(Optional::stream)
 * 
 * Not all return types benefit from the optional treatment. Container types,
 * including collections, maps, streams, arrays, and optionals should not be
 * wrapped in optionals. Rather than returning an empty Optional<List<T>>, you
 * should simply return an empty List<T> (Item 54).
 * 
 * So when should you declare a method to return Optional<T> rather than T? As a
 * rule, you should declare a method to return Optional<T> if it might not be
 * able to return a result and clients will have to perform special processing
 * if no result is returned.
 * 
 * Returning an optional that contains a boxed primitive type is prohibitively
 * expensive compared to returning a primitive type because the optional has two
 * levels of boxing instead of zero. Therefore, the library designers saw fit to
 * provide analogues of Optional<T> for the primitive types int, long, and
 * double. These optional types are OptionalInt, OptionalLong, and
 * OptionalDouble.Therefore, you should never return an optional of a boxed
 * primitive type, with the possible exception of the “minor primitive types,”
 * Boolean, Byte, Character, Short, and Float.
 * 
 * It is almost never appropriate to use an optional as a key, value, or element
 * in a collection or array.
 * 
 * Is it ever appropriate to store an optional in an instance field? Often it’s
 * a “bad smell”: it suggests that perhaps you should have a subclass containing
 * the optional fields. But sometimes it may be justified. Consider the case of
 * our NutritionFacts class in Item 2. A NutritionFacts instance contains many
 * fields that are not required. You can’t have a subclass for every possible
 * combination of these fields. Also, the fields have primitive types, which
 * make it awkward to express absence directly. The best API for NutritionFacts
 * would return an optional from the getter for each optional field, so it makes
 * good sense to simply store those optionals as fields in the object.
 *
 */
// Avoiding unnecessary use of Optional's isPresent method (Page 252)
public class ParentPid {
	public static void main(String[] args) {
		ProcessHandle ph = ProcessHandle.current();

		// Inappropriate use of isPresent
		Optional<ProcessHandle> parentProcess = ph.parent();
		System.out.println(
				"Parent PID: " + (parentProcess.isPresent() ? String.valueOf(parentProcess.get().pid()) : "N/A"));

		// Equivalent (and superior) code using orElse
		System.out.println("Parent PID: " + ph.parent().map(h -> String.valueOf(h.pid())).orElse("N/A"));
	}
}
