package effectivejava.chapter5.item31;

import java.util.*;

/**
 * 
 * For maximum flexibility, use wildcard types on input parameters that
 * represent producers or consumers.If an input parameter is both a producer and
 * a consumer, then wildcard types will do you no good: you need an exact type
 * match, which is what you get without any wildcards. Here is a mnemonic to
 * help you remember which wildcard type to use:
 * 
 * PECS stands for producer-extends, consumer-super.
 * 
 * In other words, if a parameterized type represents a T producer, use <?
 * extends T>; if it represents a T consumer, use <? super T>. In our Stack
 * example, pushAll’s src parameter produces E instances for use by the Stack,
 * so the appropriate type for src is Iterable<? extends E>; popAll’s dst
 * parameter consumes E instances from the Stack, so the appropriate type for
 * dst is Collection<? super E>. The PECS mnemonic captures the fundamental
 * principle that guides the use of wildcard types. Naftalin and Wadler call it
 * the Get and Put Principle [Naftalin07, 2.4].
 *
 */
// Generic stack with bulk methods using wildcard types (Pages 139-41)
public class Stack<E> {
	private E[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	// The elements array will contain only E instances from push(E).
	// This is sufficient to ensure type safety, but the runtime
	// type of the array won't be E[]; it will always be Object[]!
	@SuppressWarnings("unchecked")
	public Stack() {
		elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(E e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public E pop() {
		if (size == 0)
			throw new EmptyStackException();
		E result = elements[--size];
		elements[size] = null; // Eliminate obsolete reference
		return result;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private void ensureCapacity() {
		if (elements.length == size)
			elements = Arrays.copyOf(elements, 2 * size + 1);
	}

//    // pushAll staticfactory without wildcard type - deficient!
	public void pushAllDeficient(Iterable<E> src) {
		for (E e : src)
			push(e);
	}

	// Wildcard type for parameter that serves as an E producer
	public void pushAll(Iterable<? extends E> src) {
		for (E e : src)
			push(e);
	}

//    // popAll staticfactory without wildcard type - deficient!
	public void popAllDeficient(Collection<E> dst) {
		while (!isEmpty())
			dst.add(pop());
	}

	// Wildcard type for parameter that serves as an E consumer
	public void popAll(Collection<? super E> dst) {
		while (!isEmpty())
			dst.add(pop());
	}

	// Little program to exercise our generic Stack
	public static void main(String[] args) {
		Stack<Number> numberStack = new Stack<>();
		Iterable<Integer> integers = Arrays.asList(3, 1, 4, 1, 5, 9);

		// The method pushAll(Iterable<Number>) in the type Stack<Number> is not
		// applicable for the arguments (Iterable<Integer>)
		// Below won't compile
		// numberStack.pushAllDeficient(integers);
		numberStack.pushAll(integers);

		Collection<Object> objects = new ArrayList<>();
		// The method popAllDeficient(Collection<Number>) in the type Stack<Number> is
		// not applicable for the arguments (Collection<Object>)
		// Below won't compile
		// numberStack.popAllDeficient(objects);
		numberStack.popAll(objects);

		System.out.println(objects);
	}
}
