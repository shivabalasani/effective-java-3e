package effectivejava.chapter8.item55;

import java.util.*;

/**
 * 
 * Item 55: Return optionals judiciously.
 * 
 * Prior to Java 8, there were two approaches you could take when writing a
 * method that was unable to return a value under certain circumstances. Either
 * you could throw an exception, or you could return null (assuming the return
 * type was an object reference type). Neither of these approaches is perfect.
 * Exceptions should be reserved for exceptional conditions (Item 69), and
 * throwing an exception is expensive because the entire stack trace is captured
 * when an exception is created. Returning null doesn’t have these shortcomings,
 * but it has its own. If a method returns null, clients must contain
 * special-case code to deal with the possibility of a null return, unless the
 * programmer can prove that a null return is impossible.
 * 
 * In Java 8, there is a third approach to writing methods that may not be able
 * to return a value. The Optional<T> class represents an immutable container
 * that can hold either a single non-null T reference or nothing at all. An
 * optional that contains nothing is said to be empty. A value is said to be
 * present in an optional that is not empty. An optional is essentially an
 * immutable collection that can hold at most one element.A method that
 * conceptually returns a T but may be unable to do so under certain
 * circumstances can instead be declared to return an Optional<T>.An
 * Optional-returning method is more flexible and easier to use than one that
 * throws an exception, and it is less error-prone than one that returns null.
 * 
 * Never return a null value from an Optional-returning method: it defeats the
 * entire purpose of the facility.
 * 
 * In summary, if you find yourself writing a method that can’t always return a
 * value and you believe it is important that users of the method consider this
 * possibility every time they call it, then you should probably return an
 * optional. You should, however, be aware that there are real performance
 * consequences associated with returning optionals; for performance-critical
 * methods, it may be better to return a null or throw an exception. Finally,
 * you should rarely use an optional in any other capacity than as a return
 * value.
 *
 */
// Using Optional<T> as a return type (Pages 249-251)
public class Max {

	// 1. Returns maximum value in collection - throws exception if empty (Page 249)
	public static <E extends Comparable<E>> E maxThrowsException(Collection<E> c) {
		if (c.isEmpty())
			throw new IllegalArgumentException("Empty collection");

		E result = null;
		for (E e : c)
			if (result == null || e.compareTo(result) > 0)
				result = Objects.requireNonNull(e);

		return result;
	}

	// 2. Returns maximum value in collection as an Optional<E> (Page 250)
	// It is a programming error to pass null to Optional.of(value). If you do this,
	// the method responds by throwing a NullPointerException. The
	// Optional.ofNullable(value) method accepts a possibly null value and returns
	// an empty optional if null is passed in.
	public static <E extends Comparable<E>> Optional<E> maxOptional(Collection<E> c) {
		if (c.isEmpty())
			return Optional.empty();

		E result = null;
		for (E e : c)
			if (result == null || e.compareTo(result) > 0)
				result = Objects.requireNonNull(e);

		return Optional.of(result);
	}

	// 3. Returns max val in collection as Optional<E> - uses stream (Page 250)
	public static <E extends Comparable<E>> Optional<E> max(Collection<E> c) {
		return c.stream().max(Comparator.naturalOrder());
	}

	public static void main(String[] args) {
		List<String> words = Arrays.asList("optional", "string");

		System.out.println(max(words));

		// Using an optional to provide a chosen default value (Page 251)
		String lastWordInLexicon = max(words).orElse("No words...");
		System.out.println(lastWordInLexicon);

		// Using an optional to throw a chosen exception
		String myWord = max(words).orElseThrow(RuntimeException::new);

		// If you can prove that an optional is nonempty, you can get the value from the
		// optional without specifying an action to take if the optional is empty, but
		// if you’re wrong, your code will throw a NoSuchElementException:
		// Using optional when you know there’s a return value
		// Element lastNobleGas = max(Elements.NOBLE_GASES).get();
	}
}
