package effectivejava.chapter5.item32;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * When compiling pickTwo method, the compiler generates code to create a
 * varargs parameter array in which to pass two T instances to toArray. This
 * code allocates an array of type Object[], which is the most specific type
 * that is guaranteed to hold these instances, no matter what types of objects
 * are passed to pickTwo at the call site. The toArray method simply returns
 * this array to pickTwo, which in turn returns it to its caller, so pickTwo
 * will always return an array of type Object[].
 * 
 * This example is meant to drive home the point that it is unsafe to give
 * another method access to a generic varargs parameter array, with two
 * exceptions: it is safe to pass the array to another varargs method that is
 * correctly annotated with @SafeVarargs, and it is safe to pass the array to a
 * non-varargs method that merely computes some function of the contents of the
 * array.
 * 
 * This implies that you should never write unsafe varargs methods like
 * dangerous or toArray. Every time the compiler warns you of possible heap
 * pollution from a generic varargs parameter in a method you control, check
 * that the method is safe. As a reminder, a generic varargs methods is safe if:
 * 1. it doesn’t store anything in the varargs parameter array, and 2. it
 * doesn’t make the array (or a clone) visible to untrusted code.
 *
 */
// Subtle heap pollution (Pages 147-8)
public class PickTwo {
	// UNSAFE - Exposes a reference to its generic parameter array!
	static <T> T[] toArray(T... args) {
		return args;
	}

	static <T> T[] pickTwo(T a, T b, T c) {
		switch (ThreadLocalRandom.current().nextInt(3)) {
		case 0:
			return toArray(a, b);
		case 1:
			return toArray(a, c);
		case 2:
			return toArray(b, c);
		}
		throw new AssertionError(); // Can't get here
	}

	public static void main(String[] args) {
		// There is nothing at all wrong with this method, so it compiles without
		// generating any warnings. But when you run it, it throws a ClassCastException,
		// though it contains no visible casts. What you don’t see is that the compiler
		// has generated a hidden cast to String[] on the value returned by pickTwo so
		// that it can be stored in attributes. The cast fails, because Object[] is not
		// a subtype of String[].
		String[] attributes = pickTwo("Good", "Fast", "Cheap");
		System.out.println(Arrays.toString(attributes));
	}
}
