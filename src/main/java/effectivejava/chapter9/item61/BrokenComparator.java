package effectivejava.chapter9.item61;

import java.util.*;

/**
 * 
 * Item 61: Prefer primitive types to boxed primitives.
 * 
 * Java has a two-part type system, consisting of primitives, such as int,
 * double, and boolean, and reference types, such as String and List. Every
 * primitive type has a corresponding reference type, called a boxed primitive.
 * The boxed primitives corresponding to int, double, and boolean are Integer,
 * Double, and Boolean.
 * 
 * There are three major differences between primitives and boxed primitives.
 * First, primitives have only their values, whereas boxed primitives have
 * identities distinct from their values. In other words, two boxed primitive
 * instances can have the same value and different identities. Second, primitive
 * types have only fully functional values, whereas each boxed primitive type
 * has one nonfunctional value, which is null, in addition to all the functional
 * values of the corresponding primitive type. Last, primitives are more time-
 * and space-efficient than boxed primitives. All three of these differences can
 * get you into real trouble if you aren’t careful.
 * 
 * So what’s the problem with Version 1? The first test in naturalOrder works
 * fine. Evaluating the expression i < j causes the Integer instances referred
 * to by i and j to be auto-unboxed; that is, it extracts their primitive
 * values. The evaluation proceeds to check if the first of the resulting int
 * values is less than the second. But suppose it is not. Then the next test
 * evaluates the expression i == j, which performs an identity comparison on the
 * two object references. If i and j refer to distinct Integer instances that
 * represent the same int value, this comparison will return false, and the
 * comparator will incorrectly return 1, indicating that the first Integer value
 * is greater than the second. Applying the == operator to boxed primitives is
 * almost always wrong.
 * 
 * In practice, if you need a comparator to describe a type’s natural order, you
 * should simply call Comparator.naturalOrder(), and if you write a comparator
 * yourself, you should use the comparator construction methods, or the static
 * compare methods on primitive types (Item 14).
 * 
 * In summary, use primitives in preference to boxed primitives whenever you
 * have the choice. Primitive types are simpler and faster. If you must use
 * boxed primitives, be careful! Autoboxing reduces the verbosity, but not the
 * danger, of using boxed primitives. When your program compares two boxed
 * primitives with the == operator, it does an identity comparison, which is
 * almost certainly not what you want. When your program does mixed-type
 * computations involving boxed and unboxed primitives, it does unboxing, and
 * when your program does unboxing, it can throw a NullPointerException.
 * Finally, when your program boxes primitive values, it can result in costly
 * and unnecessary object creations.
 *
 */
// Broken comparator - can you spot the flaw? - Page 273
public class BrokenComparator {
	public static void main(String[] args) {

		// Problem 1 : Version 1
		Comparator<Integer> naturalOrder = (i, j) -> (i < j) ? -1 : (i == j ? 0 : 1);

		// Version 2 : Fixed Comparator - Page 274
		Comparator<Integer> naturalOrderFixed = (iBoxed, jBoxed) -> {
			int i = iBoxed, j = jBoxed; // Auto-unboxing
			return i < j ? -1 : (i == j ? 0 : 1);
		};

		int result = naturalOrder.compare(new Integer(42), new Integer(42));
		System.out.println(result);

		int resultFixed = naturalOrderFixed.compare(new Integer(42), new Integer(42));
		System.out.println(resultFixed);
	}
}
