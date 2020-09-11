package effectivejava.chapter5.item30;

import java.util.*;

/**
 * 
 * The type parameter list, which declares the type parameters, goes between a
 * method’s modifiers and its return type. In this example, the type parameter
 * list is <E>, and the return type is Set<E>. The naming conventions for type
 * parameters are the same for generic methods and generic types (Items 29, 68):
 *
 */
// Generic union method and program to exercise it  (Pages 135-6)
public class Union {

	// Generic method
	public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
		Set<E> result = new HashSet<>(s1);
		result.addAll(s2);
		return result;
	}

	// Uses raw types - unacceptable! (Item 26)
	public static Set unionRaw(Set s1, Set s2) {
		// Warning : HashSet is a raw type. References to generic type HashSet<E> should
		// be parameterized
		Set result = new HashSet(s1);
		// Warning : Type safety: The method addAll(Collection) belongs to the raw type
		// Set. References to generic type Set<E> should be parameterized
		result.addAll(s2);
		return result;
	}

	// Simple program to exercise generic method
	public static void main(String[] args) {
		Set<String> guys = Set.of("Tom", "Dick", "Harry");
		Set<String> stooges = Set.of("Larry", "Moe", "Curly");
		Set<String> aflCio = union(guys, stooges);
		System.out.println(aflCio);
	}
}
