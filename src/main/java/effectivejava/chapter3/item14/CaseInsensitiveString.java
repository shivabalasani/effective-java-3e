package effectivejava.chapter3.item14;

import java.util.*;

/**
 * 
 * - Item 14: Consider implementing Comparable
 * 
 * - Sorting an array of objects that implement Comparable is as simple as this:
 * Arrays.sort(a); Classes that depend on comparison include the sorted
 * collections TreeSet and TreeMap and the utility classes Collections and
 * Arrays, which contain searching and sorting algorithms.
 * 
 * In Java 7, static compare methods were added to all of Java’s boxed primitive
 * classes.Use of the relational operators < and > in compareTo methods is
 * verbose and error-prone and no longer recommended.
 * 
 * In summary, whenever you implement a value class that has a sensible
 * ordering, you should have the class implement the Comparable interface so
 * that its instances can be easily sorted, searched, and used in
 * comparison-based collections.When comparing field values in the
 * implementations of the compareTo methods, avoid the use of the < and >
 * operators. Instead, use the static compare methods in the boxed primitive
 * classes or the comparator construction methods in the Comparator interface
 *
 */
// Single-field Comparable with object reference field (Page 69)
public final class CaseInsensitiveString implements Comparable<CaseInsensitiveString> {
	private final String s;

	public CaseInsensitiveString(String s) {
		this.s = Objects.requireNonNull(s);
	}

	// Fixed equals method (Page 40)
	@Override
	public boolean equals(Object o) {
		return o instanceof CaseInsensitiveString && ((CaseInsensitiveString) o).s.equalsIgnoreCase(s);
	}

	@Override
	public int hashCode() {
		return s.hashCode();
	}

	@Override
	public String toString() {
		return s;
	}

	// Using an existing comparator to make a class comparable
	public int compareTo(CaseInsensitiveString cis) {
		return String.CASE_INSENSITIVE_ORDER.compare(s, cis.s);
	}

	public static void main(String[] args) {
		Set<CaseInsensitiveString> s = new TreeSet<>();
		String[] strs = { "z", "s", "ac", "ab", "ab" };
		for (String arg : strs)
			s.add(new CaseInsensitiveString(arg));
		System.out.println(s);
	}
}