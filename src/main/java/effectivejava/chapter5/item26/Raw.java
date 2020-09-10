package effectivejava.chapter5.item26;

import java.util.*;

/**
 * 
 * Item 26: Don’t use raw types
 * 
 * A class or interface whose declaration has one or more type parameters is a
 * generic class or interface. For example, the List interface has a single type
 * parameter, E, representing its element type.The full name of the interface is
 * List<E> (read “list of E”),Generic classes and interfaces are collectively
 * known as generic types.Each generic type defines a set of parameterized
 * types, which consist of the class or interface name followed by an
 * angle-bracketed list of actual type parameters corresponding to the generic
 * type’s formal type parameters.For example, List<String> (read “list of
 * string”) is a parameterized type representing a list whose elements are of
 * type String. (String is the actual type parameter corresponding to the formal
 * type parameter E.)
 * 
 * Finally, each generic type defines a raw type, which is the name of the
 * generic type used without any accompanying type parameters [JLS, 4.8]. For
 * example, the raw type corresponding to List<E> is List. Raw types behave as
 * if all of the generic type information were erased from the type declaration.
 * 
 * As noted earlier, it is legal to use raw types (generic types without their
 * type parameters), but you should never do it. If you use raw types, you lose
 * all the safety and expressiveness benefits of generics.Migration
 * compatibility, drove the decisions to support raw types and to implement
 * generics using erasure (Item 28).
 * 
 * What is the difference between the unbounded wildcard type Set<?> and the raw
 * type Set? Does the question mark really buy you anything? Not to belabor the
 * point, but the wildcard type is safe and the raw type isn’t. You can put any
 * element into a collection with a raw type, easily corrupting the collection’s
 * type invariant (as demonstrated by the unsafeAdd method on page 119); you
 * can’t put any element (other than null) into a Collection<?>. Not only can’t
 * you put any element (other than null) into a Collection<?>, but you can’t
 * assume anything about the type of the objects that you get out. If these
 * restrictions are unacceptable, you can use generic methods (Item 30) or
 * bounded wildcard types (Item 31).
 * 
 * There are a few minor exceptions to the rule that you should not use raw
 * types. You must use raw types in class literals. List.class, String[].class,
 * and int.class are all legal, but List<String>.class and List<?>.class are
 * not.A second exception to the rule concerns the instanceof operator. Because
 * generic type information is erased at runtime, it is illegal to use the
 * instanceof operator on parameterized types other than unbounded wildcard
 * types.
 * 
 * In summary, using raw types can lead to exceptions at runtime, so don’t use
 * them. They are provided only for compatibility and interoperability with
 * legacy code that predates the introduction of generics. As a quick review,
 * Set<Object> is a parameterized type representing a set that can contain
 * objects of any type, Set<?> is a wildcard type representing a set that can
 * contain only objects of some unknown type, and Set is a raw type, which opts
 * out of the generic type system. The first two are safe, and the last is not.
 * 
 * Term 						Example 						Item
 * -----------------------------------------------------------------------------
 * Parameterized type 			List<String> 					Item 26
 * 
 * Actual type  parameter 		String 							Item 26
 * 
 * Generic type 				List<E> 						Items 26, 29
 * 
 * Formal type parameter 		E 								Item 26
 * 
 * Unbounded wildcard type 		List<?> 						Item 26
 * 
 * Raw type 					List 							Item 26
 * 
 * Bounded type parameter 		<E extends Number> 				Item 29
 * 
 * Recursive type bound 		<T extends Comparable<T>> 		Item 30
 * 
 * Bounded wildcard type 		List<? extends Number> 			Item 31
 * 
 * Generic method static <E> 	List<E> asList(E[] a) 			Item 30
 * 
 * Type token 					String.class 					Item 33
 *-----------------------------------------------------------------------------
 */

public class Raw {
	public static void main(String[] args) {

		// Raw collection type - don't do this!
		// My stamp collection. Contains only Stamp instances.
		// private final Collection stamps = ... ;

		// Erroneous insertion of coin into stamp collection
		// stamps.add(new Coin( ... )); // Emits "unchecked call" warning

		// You don’t get an error until you try to retrieve the coin from the stamp
		// collection:
		// Raw iterator type - don't do this!
		// for (Iterator i = stamps.iterator(); i.hasNext(); )
		// Stamp stamp = (Stamp) i.next(); // Throws ClassCastException
		// stamp.cancel();

		// Parameterized collection type - typesafe
		// private final Collection<Stamp> stamps = ... ;

		// This is the preferred way to use the instanceof operator with generic types:
		// Legitimate use of raw type - instanceof operator
		// if (o instanceof Set) { // Raw type
		// Set<?> s = (Set<?>) o; // Wildcard type
		// }

		// Fails at runtime - unsafeAdd method uses a raw type (List)! (Page 119)
		List<String> strings = new ArrayList<>();
		unsafeAdd(strings, Integer.valueOf(42));
		String s = strings.get(0); // Has compiler-generated cast

		//--------------------------------------------------------------------------------------
		Set<?> s1 = new HashSet();
		// The method add(capture#1-of ?) in the type Set<capture#1-of
		// ?> is not applicable for the arguments (String)
		// s1.add("str1");
		s1.add(null); //Cannot add anything other than null

		Set<Object> s2 = new HashSet();
		s2.add("str1");
		s2.add(4);
		s2.add(null);
		
		//--------------------------------------------------------------------------------------
		List<String> testList = new ArrayList<String>();
		String str = "Add me!";
		// addObjectToList1 doesn't compile, because you cannot add anything except null
		// to a List<?>.
		addObjectToList1(testList, str);

		// addObjectToList2 compiles, but the call to it in main() doesn't compile,
		// because List<Object> is not a super type of List<String>.
		// addObjectToList2(testList, str);

		// addObjectToList3 both compiles and the call works. This is the way to add
		// elements to a generic list.
		addObjectToList3(testList, str);
	}

	private static void unsafeAdd(List list, Object o) {
		list.add(o);
	}

	// Use of raw type for unknown element type - don't do this!
	static int numElementsInCommon(Set s1, Set s2) {
		int result = 0;
		for (Object o1 : s1)
			if (s2.contains(o1))
				result++;
		return result;
	}

	// If you want to use a generic type but you don’t know or care what
	// the actual type parameter is, you can use a question mark instead.
	// Uses unbounded wildcard type - typesafe and flexible
	static int numElementsInCommonSafe(Set<?> s1, Set<?> s2) {
		int result = 0;
		for (Object o1 : s1)
			if (s2.contains(o1))
				result++;
		return result;
	}
	
	private static void addObjectToList1(final List<?> aList, final Object o) {
		// The method add(capture#4-of ?) in the type List<capture#4-of ?> is not
		// applicable for the arguments (Object)
		// aList.add(o); Won't compile
		aList.add(null);
	}

	private static void addObjectToList2(final List<Object> aList, final Object o) {
		aList.add(o);
	}

	private static <T> void addObjectToList3(final List<T> aList, final T o) {
		aList.add(o);
	}

}
