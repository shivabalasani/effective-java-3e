package effectivejava.chapter7.item42;

import java.util.Arrays;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static java.util.Comparator.*;

import static java.util.Comparator.comparingInt;

/**
 * 
 * Item 42: Prefer lambdas to anonymous classes.
 * 
 * Historically, interfaces (or, rarely, abstract classes) with a single
 * abstract method were used as function types. Their instances, known as
 * function objects, represent functions or actions. Since JDK 1.1 was released
 * in 1997, the primary means of creating a function object was the anonymous
 * class (Item 24).
 * 
 * Anonymous classes were adequate for the classic objected-oriented design
 * patterns requiring function objects, notably the Strategy pattern [Gamma95].
 * The Comparator interface represents an abstract strategy for sorting; the
 * anonymous class in Version 1 is a concrete strategy for sorting strings. The
 * verbosity of anonymous classes, however, made functional programming in Java
 * an unappealing prospect.
 * 
 * In Java 8, the language formalized the notion that interfaces with a single
 * abstract method are special and deserve special treatment. These interfaces
 * are now known as functional interfaces, and the language allows you to create
 * instances of these interfaces using lambda expressions, or lambdas for short.
 * 
 * Note that the types of the lambda (Comparator<String>), of its parameters (s1
 * and s2, both String), and of its return value (int) are not present in
 * Version 2 . The compiler deduces these types from context, using a process
 * known as type inference.In some cases, the compiler won’t be able to
 * determine the types, and you’ll have to specify them.Omit the types of all
 * lambda parameters unless their presence makes your program clearer. Version 2
 * won’t compile if the variable words is declared to be of the raw type List
 * instead of the parameterized type List<String>.
 * 
 * In summary, as of Java 8, lambdas are by far the best way to represent small
 * function objects. Don’t use anonymous classes for function objects unless you
 * have to create instances of types that aren’t functional interfaces. Also,
 * remember that lambdas make it so easy to represent small function objects
 * that it opens the door to functional programming techniques that were not
 * previously practical in Java.
 *
 */

// Sorting with function objects (Pages 193-4)
public class SortFourWays {
	public static void main(String[] args) {
		String[] strings = { "hemp", "jut", "nylon" };
		List<String> words = Arrays.asList(strings);

		// Version 1 :Anonymous class instance as a function object -obsolete!(Page193)
		Collections.sort(words, new Comparator<String>() {
			public int compare(String s1, String s2) {
				return Integer.compare(s1.length(), s2.length());
			}
		});
		System.out.println(words);
		Collections.shuffle(words);

		// Version 2 : Lambda expression as function object(replaces anonymous class)
		// (Page194)
		Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
		System.out.println(words);
		Collections.shuffle(words);

		// Version 3 : Comparator construction method (with method reference) in place
		// of lambda (Page 194)
		Collections.sort(words, comparingInt(String::length));
		System.out.println(words);
		Collections.shuffle(words);

		// Version 4 : Default method List.sort in conjunction with comparator
		// construction method (Page 194)
		words.sort(comparingInt(String::length));
		System.out.println(words);
	}
}
