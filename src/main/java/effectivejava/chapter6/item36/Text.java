package effectivejava.chapter6.item36;

import java.util.*;

/**
 * 
 * Item 36: Use EnumSet instead of bit fields
 * 
 * Some programmers who use enums in preference to int constants still cling to
 * the use of bit fields when they need to pass around sets of constants. There
 * is no reason to do this, because a better alternative exists. The java.util
 * package provides the EnumSet class to efficiently represent sets of values
 * drawn from a single enum type. This class implements the Set interface,
 * providing all of the richness, type safety, and interoperability you get with
 * any other Set implementation. But internally, each EnumSet is represented as
 * a bit vector. If the underlying enum type has sixty-four or fewer
 * elements—and most do—the entire EnumSet is represented with a single long, so
 * its performance is comparable to that of a bit field. Bulk operations, such
 * as removeAll and retainAll, are implemented using bit wise arithmetic, just
 * as you’d do manually for bit fields. But you are insulated from the ugliness
 * and error-proneness of manual bit twiddling: the EnumSet does the hard work
 * for you.
 * 
 * In summary, just because an enumerated type will be used in sets, there is no
 * reason to represent it with bit fields. The EnumSet class combines the
 * conciseness and performance of bit fields with all the many advantages of
 * enum types described in Item 34. The one real disadvantage of EnumSet is that
 * it is not, as of Java 9, possible to create an immutable EnumSet, but this
 * will likely be remedied in an upcoming release. In the meantime, you can wrap
 * an EnumSet with Collections.unmodifiableSet, but conciseness and performance
 * will suffer.
 *
 */
// EnumSet - a modern replacement for bit fields (Page 170)
public class Text {
	public enum Style {
		BOLD, ITALIC, UNDERLINE, STRIKETHROUGH
	}

	// Any Set could be passed in, but EnumSet is clearly best
	public void applyStyles(Set<Style> styles) {
		System.out.printf("Applying styles %s to text%n", Objects.requireNonNull(styles));
	}

	// Sample use
	public static void main(String[] args) {
		Text text = new Text();
		text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
	}
}