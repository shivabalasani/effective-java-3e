package effectivejava.chapter9.item61;

/**
 * 
 * No, it doesn’t print Unbelievable—but what it does is almost as strange. It
 * throws a NullPointerException when evaluating the expression i == 42. The
 * problem is that i is an Integer, not an int, and like all nonconstant object
 * reference fields, its initial value is null. When the program evaluates the
 * expression i == 42, it is comparing an Integer to an int. In nearly every
 * case when you mix primitives and boxed primitives in an operation, the boxed
 * primitive is auto-unboxed. If a null object reference is auto-unboxed, you
 * get a NullPointerException. As this program demonstrates, it can happen
 * almost anywhere. Fixing the problem is as simple as declaring i to be an int
 * instead of an Integer.
 * 
 * In all three of the programs discussed in this item, the problem was the
 * same: the programmer ignored the distinction between primitives and boxed
 * primitives and suffered the consequences. In the first two problems, the
 * consequences were outright failure; in the third, severe performance
 * problems.
 * 
 * So when should you use boxed primitives? They have several legitimate uses.
 * The first is as elements, keys, and values in collections. You can’t put
 * primitives in collections, so you’re forced to use boxed primitives. This is
 * a special case of a more general one. You must use boxed primitives as type
 * parameters in parameterized types and methods (Chapter 5), because the
 * language does not permit you to use primitives. For example, you cannot
 * declare a variable to be of type ThreadLocal<int>, so you must use
 * ThreadLocal<Integer> instead. Finally, you must use boxed primitives when
 * making reflective method invocations (Item 65).
 *
 */
// What does this program do? - Page 274
public class Unbelievable {
	static Integer i;

	public static void main(String[] args) {
		// Problem 2
		if (i == 42)
			System.out.println("Unbelievable");

		// Problem 3 : Hideously slow program! Can you spot the object creation?
		Long sum = 0L;
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			sum += i;
		}
		System.out.println(sum);
	}
}