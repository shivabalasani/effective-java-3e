package effectivejava.chapter4.item21;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 
 * Item 21: Design interfaces for posterity
 * 
 * Prior to Java 8, it was impossible to add methods to interfaces without
 * breaking existing implementations. If you added a new method to an interface,
 * existing implementations would, in general, lack the method, resulting in a
 * compile-time error. In Java 8, the default method construct was added [JLS
 * 9.4], with the intent of allowing the addition of methods to existing
 * interfaces. But adding new methods to existing interfaces is fraught with
 * risk.
 * 
 * Before Java 8, these implementations were written with the tacit
 * understanding that their interfaces would never acquire any new methods. Many
 * new default methods were added to the core collection interfaces in Java 8,
 * primarily to facilitate the use of lambdas (Chapter 6). The Java libraries’
 * default methods are high-quality general-purpose implementations, and in most
 * cases, they work fine. But it is not always possible to write a default
 * method that maintains all invariants of every conceivable implementation.
 * 
 * In the presence of default methods, existing implementations of an interface
 * may compile without error or warning but fail at runtime. Using default
 * methods to add new methods to existing interfaces should be avoided unless
 * the need is critical, in which case you should think long and hard about
 * whether an existing interface implementation might be broken by your default
 * method implementation.
 * 
 * It is also worth noting that default methods were not designed to support
 * removing methods from interfaces or changing the signatures of existing
 * methods. Neither of these interface changes is possible without breaking
 * existing clients.The moral is clear. Even though default methods are now a
 * part of the Java platform, it is still of the utmost importance to design
 * interfaces with great care.
 * 
 * While it may be possible to correct some interface flaws after an interface
 * is released, you cannot count on it.
 *
 */
public interface InterfacePosterity<E> extends Iterable<E> {

	Iterator<E> iterator();

	// Default method added to the Collection interface in Java 8
	default boolean removeIf(Predicate<? super E> filter) {
		Objects.requireNonNull(filter);
		boolean removed = false;
		final Iterator<E> each = iterator();
		while (each.hasNext()) {
			if (filter.test(each.next())) {
				each.remove();
				removed = true;
			}
		}
		return removed;
	}

}
