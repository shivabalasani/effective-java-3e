package effectivejava.chapter8.item52;

import java.util.*;

/**
 * 
 * Prior to Java 5, all primitive types were radically different from all
 * reference types, but this is not true in the presence of autoboxing, and it
 * has caused real trouble.
 * 
 * The call to set.remove(i) selects the overloading remove(E), where E is the
 * element type of the set (Integer), and autoboxes i from int to Integer. This
 * is the behavior you’d expect, so the program ends up removing the positive
 * values from the set. The call to list.remove(i), on the other hand, selects
 * the overloading remove(int i), which removes the element at the specified
 * position in the list. If you start with the list [-3, -2, -1, 0, 1, 2] and
 * remove the zeroth element, then the first, and then the second, you’re left
 * with [-2, 0, 2], and the mystery is solved.
 * 
 * The confusing behavior demonstrated by the example below came about because
 * the List<E> interface has two overloadings of the remove method: remove(E)
 * and remove(int). Prior to Java 5 when the List interface was “generified,” it
 * had a remove(Object) method in place of remove(E), and the corresponding
 * parameter types, Object and int, were radically different. But in the
 * presence of generics and autoboxing, the two parameter types are no longer
 * radically different. In other words, adding generics and autoboxing to the
 * language damaged the List interface.
 * 
 *
 */
// What does this program print? (Page 241)
public class SetList {
	public static void main(String[] args) {
		Set<Integer> set = new TreeSet<>();
		List<Integer> list = new ArrayList<>();

		for (int i = -3; i < 3; i++) {
			set.add(i);
			list.add(i);
		}
		for (int i = 0; i < 3; i++) {
			set.remove(i);
			list.remove(i);
		}
		// [-3, -2, -1] [-2, 0, 2]
		System.out.println(set + " " + list);

		// Fixed version : cast list.remove’s argument to Integer,
		// forcing the correct overloading to be selected.
		Set<Integer> setFixed = new TreeSet<>();
		List<Integer> listFixed = new ArrayList<>();

		for (int i = -3; i < 3; i++) {
			setFixed.add(i);
			listFixed.add(i);
		}
		for (int i = 0; i < 3; i++) {
			setFixed.remove(i);
			listFixed.remove((Integer) i); // or remove(Integer.valueOf(i))
		}
		// [-3, -2, -1] [-2, 0, 2]
		System.out.println(setFixed + " " + listFixed);
	}
}
