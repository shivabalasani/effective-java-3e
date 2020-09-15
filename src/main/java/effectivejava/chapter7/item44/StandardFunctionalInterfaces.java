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
 * Most of the standard functional interfaces exist only to provide support for
 * primitive types. Don’t be tempted to use basic functional interfaces with
 * boxed primitives instead of primitive functional interfaces. While it works,
 * it violates the advice of Item 61, “prefer primitive types to boxed
 * primitives.” The performance consequences of using boxed primitives for bulk
 * operations can be deadly.
 * 
 * You should seriously consider writing a purpose-built functional interface in
 * preference to using a standard one if you need a functional interface that
 * shares one or more of the following characteristics with Comparator:
 * 
 * • It will be commonly used and could benefit from a descriptive name.
 * 
 * • It has a strong contract associated with it.
 * 
 * • It would benefit from custom default methods.
 * 
 * FunctionalInterface serves three purposes: it tells readers of the class and
 * its documentation that the interface was designed to enable lambdas; it keeps
 * you honest because the interface won’t compile unless it has exactly one
 * abstract method; and it prevents maintainers from accidentally adding
 * abstract methods to the interface as it evolves. Always annotate your
 * functional interfaces with the @FunctionalInterface annotation. Do not
 * provide a method with multiple overloadings that take different functional
 * interfaces in the same argument position if it could create a possible
 * ambiguity in the client.
 * 
 * In summary, now that Java has lambdas, it is imperative that you design your
 * APIs with lambdas in mind. Accept functional interface types on input and
 * return them on output. It is generally best to use the standard interfaces
 * provided in java.util.function.Function, but keep your eyes open for the
 * relatively rare cases where you would be better off writing your own
 * functional interface.
 * 
 */

//Unnecessary functional interface; use a standard one instead.
@FunctionalInterface
interface EldestEntryRemovalFunction<K, V> {
	boolean remove(Map<K, V> map, Map.Entry<K, V> eldest);
}