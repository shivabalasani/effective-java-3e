package effectivejava.chapter7.item44;

import java.util.Map;

/**
 * 
 * Item 44: Favor the use of standard functional interfaces.
 * 
 * Now that Java has lambdas, best practices for writing APIs have changed
 * considerably. For example, the Template Method pattern [Gamma95], wherein a
 * subclass overrides a primitive method to specialize the behavior of its
 * superclass, is far less attractive. The modern alternative is to provide a
 * static factory or constructor that accepts a function object to achieve the
 * same effect. More generally, you’ll be writing more constructors and methods
 * that take function objects as parameters. Choosing the right functional
 * parameter type demands care.
 * 
 * If one of the standard functional interfaces does the job, you should
 * generally use it in preference to a purpose-built functional interface. This
 * will make your API easier to learn, by reducing its conceptual surface area,
 * and will provide significant interoperability benefits, as many of the
 * standard functional interfaces provide useful default methods. The Predicate
 * interface, for instance, provides methods to combine predicates. The standard
 * BiPredicate<Map<K,V>, Map.Entry<K,V>> interface should be used in preference
 * to a custom EldestEntryRemovalFunction interface.
 * 
 * There are forty-three interfaces in java.util.Function. You can’t be expected
 * to remember them all, but if you remember six basic interfaces, you can
 * derive the rest when you need them. The basic interfaces operate on object
 * reference types. The Operator interfaces represent functions whose result and
 * argument types are the same. The Predicate interface represents a function
 * that takes an argument and returns a boolean. The Function interface
 * represents a function whose argument and return types differ. The Supplier
 * interface represents a function that takes no arguments and returns (or
 * “supplies”) a value. Finally, Consumer represents a function that takes an
 * argument and returns nothing, essentially consuming its argument. The six
 * basic functional interfaces are summarized below:
 * 
 * Interface ----------------- Function ------------------Signature Example
 * --------------------------------------------------------------------------
 * UnaryOperator<T> ----------T apply(T t) ------------- String::toLowerCase
 * 
 * BinaryOperator<T> ---------T apply(T t1, T t2) ------ BigInteger::add
 * 
 * Predicate<T> --------------boolean test(T t) ---------Collection::isEmpty
 * 
 * Function<T,R> -------------R apply(T t) --------------Arrays::asList
 * 
 * Supplier<T> ---------------T get() -------------------Instant::now
 * 
 * Consumer<T> ---------------void accept(T t) ----------System.out::println
 * -------------------------------------------------------------------------
 * 
 * There are also three variants of each of the six basic interfaces to operate
 * on the primitive types int, long, and double. Their names are derived from
 * the basic interfaces by prefixing them with a primitive type. So, for
 * example, a predicate that takes an int is an IntPredicate, and a binary
 * operator that takes two long values and returns a long is a
 * LongBinaryOperator. None of these variant types is parameterized except for
 * the Function variants, which are parameterized by return type. For example,
 * LongFunction<int[]> takes a long and returns an int[].
 * 
 */

//Unnecessary functional interface; use a standard one instead.
@FunctionalInterface
interface EldestEntryRemovalFunction<K, V> {
	boolean remove(Map<K, V> map, Map.Entry<K, V> eldest);
}