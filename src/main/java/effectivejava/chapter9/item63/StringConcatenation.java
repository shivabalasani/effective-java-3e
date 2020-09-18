package effectivejava.chapter9.item63;

/**
 * 
 * Item 63: Beware the performance of string concatenation.
 * 
 * The string concatenation operator (+) is a convenient way to combine a few
 * strings into one. It is fine for generating a single line of output or
 * constructing the string representation of a small, fixed-size object, but it
 * does not scale. Using the string concatenation operator repeatedly to
 * concatenate n strings requires time quadratic in n. This is an unfortunate
 * consequence of the fact that strings are immutable (Item 17). When two
 * strings are concatenated, the contents of both are copied.
 * 
 * Version 1 performs abysmally if the number of items is large. To achieve
 * acceptable performance, use a StringBuilder in place of a String to store the
 * statement under construction:see Version 2
 * 
 * A lot of work has gone into making string concatenation faster since Java 6,
 * but the difference in the performance of the two methods is still dramatic:
 * If numItems returns 100 and lineForItem returns an 80-character string, the
 * second method runs 6.5 times faster than the first on my machine. Because the
 * first method is quadratic in the number of items and the second is linear,
 * the performance difference gets much larger as the number of items grows.
 * Note that the second method preallocates a StringBuilder large enough to hold
 * the entire result, eliminating the need for automatic growth. Even if it is
 * detuned to use a default-sized StringBuilder, it is still 5.5 times faster
 * than the first method.
 * 
 * The moral is simple: Don’t use the string concatenation operator to combine
 * more than a few strings unless performance is irrelevant. Use StringBuilder’s
 * append method instead. Alternatively, use a character array, or process the
 * strings one at a time instead of combining them.
 *
 */
public class StringConcatenation {

	private static final int LINE_WIDTH = 1000;

	public int numItems() {
		return 100000;
	}

	public String lineForItem(int i) {
		return "Random String";
	}

	// Version 1 : Inappropriate use of string concatenation - Performs poorly!
	public String statement() {
		String result = "";
		for (int i = 0; i < numItems(); i++)
			result += lineForItem(i); // String concatenation
		return result;
	}

	// Version 2
	public String statementPreferred() {
		StringBuilder b = new StringBuilder(numItems() * LINE_WIDTH);
		for (int i = 0; i < numItems(); i++)
			b.append(lineForItem(i));
		return b.toString();
	}

}
