package effectivejava.chapter5.item31;

import java.util.*;

/**
 * 
 * Properly used, wildcard types are nearly invisible to the users of a class.
 * They cause methods to accept the parameters they should accept and reject
 * those they should reject. If the user of a class has to think about wildcard
 * types, there is probably something wrong with its API.
 *
 */
// Generic union method with wildcard types for enhanced flexibility (Pages 142-3)
public class Union {

	// Note that the return type is still Set<E>. Do not use bounded wildcard types
	// as return types.
	public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2) {
		Set<E> result = new HashSet<E>(s1);
		result.addAll(s2);
		return result;
	}

	// Simple program to exercise flexible generic staticfactory
	public static void main(String[] args) {
		Set<Integer> integers = new HashSet<>();
		integers.add(1);
		integers.add(3);
		integers.add(5);

		Set<Double> doubles = new HashSet<>();
		doubles.add(2.0);
		doubles.add(4.0);
		doubles.add(6.0);

		Set<Number> numbers = union(integers, doubles);

		// Explicit type parameter - required prior to Java 8
		Set<Number> numbersPriorJava8 = Union.<Number>union(integers, doubles);

		System.out.println(numbers);
		System.out.println(numbersPriorJava8);
	}
}
