package effectivejava.chapter5.item30;

import java.util.function.UnaryOperator;

/**
 * 
 * Item 30: Favor generic methods
 * 
 * Just as classes can be generic, so can methods. Static utility methods that
 * operate on parameterized types are usually generic. All of the “algorithm”
 * methods in Collections (such as binarySearch and sort) are generic.
 * 
 * On occasion, you will need to create an object that is immutable but
 * applicable to many different types. Because generics are implemented by
 * erasure (Item 28), you can use a single object for all required type
 * parameterizations, but you need to write a static factory method to
 * repeatedly dole out the object for each requested type parameterization. This
 * pattern, called the generic singleton factory, is used for function objects
 * (Item 42) such as Collections.reverseOrder, and occasionally for collections
 * such as Collections.emptySet.
 * 
 * Suppose that you want to write an identity function dispenser. The libraries
 * provide Function.identity, so there’s no reason to write your own (Item 59),
 * but it is instructive. It would be wasteful to create a new identity function
 * object time one is requested, because it’s stateless. If Java’s generics were
 * reified, you would need one identity function per type, but since they’re
 * erased a generic singleton will suffice.
 * 
 * In summary, generic methods, like generic types, are safer and easier to use
 * than methods requiring their clients to put explicit casts on input
 * parameters and return values. Like types, you should make sure that your
 * methods can be used without casts, which often means making them generic. And
 * like types, you should generify existing methods whose use requires casts.
 * This makes life easier for new users without breaking existing clients (Item
 * 26).
 *
 */
// Generic singleton factory pattern (Page 136-7)
public class GenericSingletonFactory {
	// Generic singleton factory pattern
	private static UnaryOperator<Object> IDENTITY_FN = (t) -> t;

	@SuppressWarnings("unchecked")
	public static <T> UnaryOperator<T> identityFunction() {
		return (UnaryOperator<T>) IDENTITY_FN;
	}

	// Sample program to exercise generic singleton
	public static void main(String[] args) {
		String[] strings = { "jute", "hemp", "nylon" };
		UnaryOperator<String> sameString = identityFunction();
		for (String s : strings)
			System.out.println(sameString.apply(s));

		Number[] numbers = { 1, 2.0, 3L };
		UnaryOperator<Number> sameNumber = identityFunction();
		for (Number n : numbers)
			System.out.println(sameNumber.apply(n));
	}
}