package effectivejava.chapter2.item4;

/**
 * 
 * Item 4: Enforce noninstantiability with a private constructor
 * 
 * Attempting to enforce noninstantiability by making a class abstract does not
 * work.
 * 
 * A default constructor is generated only if a class contains no explicit
 * constructors, so a class can be made noninstantiable by including a private
 * constructor.
 *
 * As a side effect, this idiom also prevents the class from being subclassed.
 * All constructors must invoke a superclass constructor, explicitly or
 * implicitly, and a subclass would have no accessible superclass constructor to
 * invoke.
 * 
 *
 */
// Noninstantiable utility class (Page 19)
public class UtilityClass {
	// Suppress default constructor for noninstantiability
	private UtilityClass() {
		throw new AssertionError();
	}

	// Remainder omitted
}
