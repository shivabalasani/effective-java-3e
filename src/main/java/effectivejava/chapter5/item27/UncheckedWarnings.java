package effectivejava.chapter5.item27;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Item 27: Eliminate unchecked warnings
 * 
 * When you get warnings that require some thought, persevere! Eliminate every
 * unchecked warning that you can.If you eliminate all warnings, you are assured
 * that your code is typesafe, which is a very good thing. It means that you
 * won’t get a ClassCastException at runtime, and it increases your confidence
 * that your program will behave as you intended.
 * 
 * If you can’t eliminate a warning, but you can prove that the code that
 * provoked the warning is typesafe, then (and only then) suppress the warning
 * with an @SuppressWarnings("unchecked") annotation.Always use the
 * SuppressWarnings annotation on the smallest scope possible.Typically this
 * will be a variable declaration or a very short method or constructor. Never
 * use SuppressWarnings on an entire class. Doing so could mask critical
 * warnings.
 * 
 * Every time you use a @SuppressWarnings("unchecked") annotation, add a comment
 * saying why it is safe to do so.
 * 
 * In summary, unchecked warnings are important. Don’t ignore them. Every
 * unchecked warning represents the potential for a ClassCastException at
 * runtime. Do your best to eliminate these warnings. If you can’t eliminate an
 * unchecked warning and you can prove that the code that provoked it is
 * typesafe, suppress the warning with a @SuppressWarnings("unchecked")
 * annotation in the narrowest possible scope. Record the rationale for your
 * decision to suppress the warning in a comment.
 *
 */
public class UncheckedWarnings {

	public static void main(String[] args) {

		// HashSet is a raw type. References to generic type HashSet<E> should be
		// parameterized
		Set<String> exaltation = new HashSet();

		Set<String> exaltationWithoutWarning = new HashSet<>();

		System.out.println(exaltation + " " + exaltationWithoutWarning);
	}

	public <T> T[] toArray(T[] a) {
		int size = 10;
		if (a.length < size)
			// Type safety: Unchecked cast from Object[] to T[]
			return (T[]) Arrays.copyOf(a, size, a.getClass());
		System.arraycopy(a, 0, a, 0, size);
		if (a.length > size)
			a[size] = null;
		return a;
	}

	// Adding local variable to reduce scope of @SuppressWarnings
	public <T> T[] toArrayWithSupressWarnings(T[] a) {
		int size = 10;
		if (a.length < size) {
			// This cast is correct because the array we're creating
			// is of the same type as the one passed in, which is T[].
			@SuppressWarnings("unchecked")
			T[] result = (T[]) Arrays.copyOf(a, size, a.getClass());
			return result;
		}
		System.arraycopy(a, 0, a, 0, size);
		if (a.length > size)
			a[size] = null;
		return a;
	}
}
