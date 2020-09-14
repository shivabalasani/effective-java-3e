package effectivejava.chapter7.item44;

/**
 * 
 * --------------------------------------------------------------------------
 * Predicate
 * --------------------------------------------------------------------------
 * Predicate<T> : Represents a predicate (boolean-valued function) of one
 * argument (reference type) -- boolean test(T t);
 * 
 * DoublePredicate : Accepts one double-valued argument -- boolean test(double
 * value);
 * 
 * IntPredicate : Accepts one int-valued argument -- boolean test(int value);
 * 
 * LongPredicate : Accepts one long-valued argument -- boolean test(long value);
 * 
 * BiPredicate<T,U> : Accepts two arguments(reference types) - -boolean test(T
 * t, U u);
 *
 */
public interface Predicates<T, U> {

	/** Predicate<T> */
	boolean test(T t);

	/** DoublePredicate */
	boolean test(double value);

	/** IntPredicate */
	boolean test(int value);

	/** LongPredicate */
	boolean test(long value);

	/** BiPredicate<T,U> */
	boolean test(T t, U u);

}
