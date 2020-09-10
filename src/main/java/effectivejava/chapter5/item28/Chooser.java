package effectivejava.chapter5.item28;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * Item 28: Prefer lists to arrays.
 * 
 * Arrays differ from generic types in two important ways. First, arrays are
 * covariant. This scary-sounding word means simply that if Sub is a subtype of
 * Super, then the array type Sub[] is a subtype of the array type Super[].
 * Generics, by contrast, are invariant: for any two distinct types Type1 and
 * Type2, List<Type1> is neither a subtype nor a supertype of List<Type2> [JLS,
 * 4.10; Naftalin07, 2.5]. You might think this means that generics are
 * deficient, but arguably it is arrays that are deficient.
 * 
 * The second major difference between arrays and generics is that arrays are
 * reified [JLS, 4.7]. This means that arrays know and enforce their element
 * type at runtime.Generics, by contrast, are implemented by erasure [JLS, 4.6].
 * This means that they enforce their type constraints only at compile time and
 * discard (or erase) their element type information at runtime. It is illegal
 * to create an array of a generic type, a parameterized type, or a type
 * parameter. Therefore, none of these array creation expressions are legal: new
 * List<E>[], new List<String>[], new E[]. All will result in generic array
 * creation errors at compile time.
 *
 * In summary, arrays and generics have very different type rules. Arrays are
 * covariant and reified; generics are invariant and erased. As a consequence,
 * arrays provide runtime type safety but not compile-time type safety, and vice
 * versa for generics. As a rule, arrays and generics don’t mix well. If you
 * find yourself mixing them and getting compile-time errors or warnings, your
 * first impulse should be to replace the arrays with lists.
 */

// List-based Chooser - typesafe (Page 129)
public class Chooser<T> {
	private final List<T> choiceList;

	public Chooser(Collection<T> choices) {
		choiceList = new ArrayList<>(choices);
	}

	public T choose() {
		Random rnd = ThreadLocalRandom.current();
		return choiceList.get(rnd.nextInt(choiceList.size()));
	}

	public static void main(String[] args) {
		// ------------------------------------------------------------
		// This code fragment is legal: Fails at runtime!
		// Object[] objectArray = new Long[1];
		// objectArray[0] = "I don't fit in"; // Throws ArrayStoreException

		// but this one is not: // Won't compile!
		// List<Object> ol = new ArrayList<Long>(); // Incompatible types
		// ol.add("I don't fit in");

		// Why generic array creation is illegal - won't compile!
		// List<String>[] stringLists = new List<String>[1]; // (1)
		// List<Integer> intList = List.of(42); // (2)
		// Object[] objects = stringLists; // (3)
		// objects[0] = intList; // (4)
		// String s = stringLists[0].get(0); // (5)
		// ------------------------------------------------------------

		List<Integer> intList = List.of(1, 2, 3, 4, 5, 6);

		Chooser<Integer> chooser = new Chooser<>(intList);

		for (int i = 0; i < 10; i++) {
			Number choice = chooser.choose();
			System.out.println(choice);
		}
	}
}

//Chooser - a class badly in need of generics!
class ChooserArray {
	private final Object[] choiceArray;

	public ChooserArray(Collection choices) {
		choiceArray = choices.toArray();
	}

	public Object choose() {
		Random rnd = ThreadLocalRandom.current();
		return choiceArray[rnd.nextInt(choiceArray.length)];
	}
}

// A first cut at making Chooser generic - 
// won't compile unless you cast the Object array to a T array:
class ChooserWithGenericArray<T> {
	private final T[] choiceArray;

	public ChooserWithGenericArray(Collection<T> choices) {
		choiceArray = (T[]) choices.toArray();
	}
//choose method unchanged
}
