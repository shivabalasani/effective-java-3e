package effectivejava.chapter8.item49;

import java.math.BigInteger;
import java.util.Objects;

/**
 * 
 * Item 49: Check parameters for validity.
 * 
 * Most methods and constructors have some restrictions on what values may be
 * passed into their parameters. For example, it is not uncommon that index
 * values must be non-negative and object references must be non-null. You
 * should clearly document all such restrictions and enforce them with checks at
 * the beginning of the method body. This is a special case of the general
 * principle that you should attempt to detect errors as soon as possible after
 * they occur. Failing to do so makes it less likely that an error will be
 * detected and makes it harder to determine the source of an error once it has
 * been detected. If an invalid parameter value is passed to a method and the
 * method checks its parameters before execution, it will fail quickly and
 * cleanly with an appropriate exception.In other words, failure to validate
 * parameters, can result in a violation of failure atomicity (Item 76).
 * 
 * For public and protected methods, use the Javadoc @throws tag to document the
 * exception that will be thrown if a restriction on parameter values is
 * violated (Item 74). Typically, the resulting exception will be
 * IllegalArgumentException, IndexOutOfBoundsException, or NullPointerException
 * (Item 72). Once you’ve documented the restrictions on a method’s parameters
 * and you’ve documented the exceptions that will be thrown if these
 * restrictions are violated, it is a simple matter to enforce the restrictions.
 * 
 * Note that the doc comment does not say “mod throws NullPointerException if m
 * is null,” even though the method does exactly that, as a byproduct of
 * invoking m.signum(). This exception is documented in the class-level doc
 * comment for the enclosing BigInteger class. The class-level comment applies
 * to all parameters in all of the class’s public methods. This is a good way to
 * avoid the clutter of documenting every NullPointerException on every method
 * individually.
 * 
 * The Objects.requireNonNull method, added in Java 7, is flexible and
 * convenient, so there’s no reason to perform null checks manually anymore.You
 * can specify your own exception detail message if you wish. The method returns
 * its input, so you can perform a null check at the same time as you use a
 * value:
 * 
 * For an unexported method, you, as the package author, control the
 * circumstances under which the method is called, so you can and should ensure
 * that only valid parameter values are ever passed in. Therefore, nonpublic
 * methods can check their parameters using assertions, as shown below: In
 * essence, these assertions are claims that the asserted condition will be
 * true, regardless of how the enclosing package is used by its clients. Unlike
 * normal validity checks, assertions throw AssertionError if they fail. And
 * unlike normal validity checks, they have no effect and essentially no cost
 * unless you enable them, which you do by passing the -ea (or
 * -enableassertions) flag to the java command.
 * 
 * To summarize, each time you write a method or constructor, you should think
 * about what restrictions exist on its parameters. You should document these
 * restrictions and enforce them with explicit checks at the beginning of the
 * method body. It is important to get into the habit of doing this. The modest
 * work that it entails will be paid back with interest the first time a
 * validity check fails.
 *
 */
public class MethodParameters {

	private static final BigInteger FIVE = new BigInteger("5");
	private static final BigInteger SIX = new BigInteger("6");

	/**
	 * Returns a BigInteger whose value is (this mod m). This method differs from
	 * the remainder method in that it always returns a non-negative BigInteger.
	 *
	 * @param m the modulus, which must be positive
	 * @return this mod m
	 * @throws ArithmeticException if m is less than or equal to 0
	 */
	public static BigInteger mod(BigInteger m) {
		if (m.signum() <= 0)
			throw new ArithmeticException("Modulus <= 0: " + m);

		// Do the computation
		return mod(m);
	}

	private static void multiplyToLenCheck(int[] array, int length) {
		array = Objects.requireNonNull(array, "Custom Message");

		if (length > array.length) {
			throw new ArrayIndexOutOfBoundsException(length - 1);
		}
	}

	// Private helper function for a recursive sort
	private static void sort(long a[], int offset, int length) {
		assert a != null;
		assert offset >= 0 && offset <= a.length;
		assert length >= 0 && length <= a.length - offset;
		System.out.println("Do the computation");
	}

	public static void main(String[] args) {
		System.out.println(SIX.mod(FIVE));
		System.out.println("-----------------------------");

		sort(null, 0, 0);
		System.out.println("-----------------------------");

		multiplyToLenCheck(null, 4);
	}

}
