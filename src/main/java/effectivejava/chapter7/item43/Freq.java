package effectivejava.chapter7.item43;

import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * Item 43: Prefer method references to lambdas.
 * 
 * The primary advantage of lambdas over anonymous classes is that they are more
 * succinct. Java provides a way to generate function objects even more succinct
 * than lambdas: method references.
 * 
 * The more parameters a method has, the more boilerplate you can eliminate with
 * a method reference. In some lambdas, however, the parameter names you choose
 * provide useful documentation, making the lambda more readable and
 * maintainable than a method reference, even if the lambda is longer.
 * 
 * There’s nothing you can do with a method reference that you can’t also do
 * with a lambda (with one obscure exception—see JLS, 9.9-2 if you’re curious).
 * That said, method references usually result in shorter, clearer code. They
 * also give you an out if a lambda gets too long or complex: You can extract
 * the code from the lambda into a new method and replace the lambda with a
 * reference to that method. You can give the method a good name and document it
 * to your heart’s content.
 * 
 * The Function interface provides a generic static factory method to return the
 * identity function, Function.identity(). It’s typically shorter and cleaner
 * not to use this method but to code the equivalent lambda inline: x -> x.
 * 
 * Many method references refer to static methods, but there are four kinds that
 * do not. Two of them are bound and unbound instance method references. In
 * bound references, the receiving object is specified in the method reference.
 * Bound references are similar in nature to static references: the function
 * object takes the same arguments as the referenced method. In unbound
 * references, the receiving object is specified when the function object is
 * applied, via an additional parameter before the method’s declared parameters.
 * Unbound references are often used as mapping and filter functions in stream
 * pipelines (Item 45). Finally, there are two kinds of constructor references,
 * for classes and arrays. Constructor references serve as factory objects. All
 * five kinds of method references are summarized in the table below:
 *
 * ----------------------------------------------------------------------------------
 * Method Ref 				Type Example 			Lambda Equivalent
 * ----------------------------------------------------------------------------------
 * Static 					Integer::parseInt 		str -> Integer.parseInt(str)
 * 
 * Bound					Instant.now()::isAfter  Instant then = Instant.now(); 
 * 																t -> then.isAfter(t)
 * 
 * Unbound 					String::toLowerCase 	str -> str.toLowerCase() 
 * 
 * Class Constructor		TreeMap<K,V>::new 		() -> new TreeMap<K,V> 
 * 
 * Array Constructor 		int[]::new 				len ->new int[len] 
 * 
 * ----------------------------------------------------------------------------------
 * 
 * In summary, method references often provide a more succinct alternative to
 * lambdas. Where method references are shorter and clearer, use them; where
 * they aren’t, stick with lambdas.
 */
// Frequency table implemented with map.merge, using lambda and method reference (Page 197)
public class Freq {
	public static void main(String[] args) {
		String[] strings = { "z", "a", "c", "a", "d", "z" };
		Map<String, Integer> frequencyTable = new TreeMap<>();

		for (String s : strings)
			frequencyTable.merge(s, 1, (count, incr) -> count + incr); // Lambda
		System.out.println(frequencyTable);

		frequencyTable.clear();
		for (String s : args)
			frequencyTable.merge(s, 1, Integer::sum); // Method reference
		System.out.println(frequencyTable);

	}
}
