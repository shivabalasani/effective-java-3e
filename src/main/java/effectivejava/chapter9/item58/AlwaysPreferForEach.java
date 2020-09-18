package effectivejava.chapter9.item58;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * Item 58: Prefer for-each loops to traditional for loops.
 * 
 * Version 1 and Version 2 idioms are better than while loops (Item 57), but
 * they aren’t perfect. The iterator and the index variables are both just
 * clutter—all you need are the elements. Furthermore, they represent
 * opportunities for error. The iterator occurs three times in each loop and the
 * index variable four, which gives you many chances to use the wrong variable.
 * If you do, there is no guarantee that the compiler will catch the problem.
 * Finally, the two loops are quite different, drawing unnecessary attention to
 * the type of the container and adding a (minor) hassle to changing that type.
 * 
 * The for-each loop (officially known as the “enhanced for statement”) in
 * Version 3 solves all of these problems. It gets rid of the clutter and the
 * opportunity for error by hiding the iterator or index variable. The resulting
 * idiom applies equally to collections and arrays, easing the process of
 * switching the implementation type of a container from one to the other:
 * 
 * When you see the colon (:), read it as “in.” Thus, the loop above reads as
 * “for each element e in elements.” There is no performance penalty for using
 * for-each loops, even for arrays: the code they generate is essentially
 * identical to the code you would write by hand.
 * 
 * Not only does the for-each loop let you iterate over collections and arrays,
 * it lets you iterate over any object that implements the Iterable interface,
 * which consists of a single method. Here is how the interface looks:
 * 
 * public interface Iterable<E> { // Returns an iterator over the elements in
 * this iterable Iterator<E> iterator(); }
 * 
 * It is a bit tricky to implement Iterable if you have to write your own
 * Iterator implementation from scratch, but if you are writing a type that
 * represents a group of elements, you should strongly consider having it
 * implement Iterable, even if you choose not to have it implement Collection.
 * This will allow your users to iterate over your type using the for-each loop,
 * and they will be forever grateful.
 * 
 * In summary, the for-each loop provides compelling advantages over the
 * traditional for loop in clarity, flexibility, and bug prevention, with no
 * performance penalty. Use for-each loops in preference to for loops wherever
 * you can.
 *
 */
public class AlwaysPreferForEach {

	enum Element {
		HYDROGEN, HELIUM
	}

	static Collection<Element> elements = Arrays.asList(Element.values());

	public static void main(String[] args) {
		String[] strings = { "a", "b" };

		// Version 1 : Not the best way to iterate over a collection!
		for (Iterator<Element> i = elements.iterator(); i.hasNext();) {
			Element e = i.next();
			System.out.println(e);
			// ... Do something with e and i
		}

		System.out.println("-----------------------------");
		// Version 3 : Not the best way to iterate over an array!
		for (int i = 0; i < strings.length; i++) {
			System.out.println(strings[i]);
			// ... Do something with a[i]
		}

		System.out.println("-----------------------------");
		// Version 3 : The preferred idiom for iterating over collections and arrays
		for (Element e : elements) {
			System.out.println(e);
			// ...Do something with e
		}
	}
}
