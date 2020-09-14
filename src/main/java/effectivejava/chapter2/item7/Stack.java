package effectivejava.chapter2.item7;

import java.util.*;

/**
 * 
 * Item 7: Eliminate obsolete object references
 * 
 * Nulling out object references should be the exception rather than the norm.
 * The best way to eliminate an obsolete reference is to let the variable that
 * contained the reference fall out of scope. This occurs naturally if you
 * define each variable in the narrowest possible scope (Item 57)
 * 
 * Generally speaking, whenever a class manages its own memory, the programmer
 * should be alert for memory leaks. Whenever an element is freed, any object
 * references contained in the element should be nulled out.
 * 
 * Another common source of memory leaks is caches.
 * 
 * A third common source of memory leaks is listeners and other callbacks.
 * 
 * Debug memory leaks using tools like heap profiler.
 *
 */
// Can you spot the "memory leak"?  (Pages 26-27)
public class Stack {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	public Stack() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(Object e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public Object pop() {
		if (size == 0)
			throw new EmptyStackException();
		return elements[--size];
	}

	/**
	 * Ensure space for at least one more element, roughly doubling the capacity
	 * each time the array needs to grow.
	 */
	private void ensureCapacity() {
		if (elements.length == size)
			elements = Arrays.copyOf(elements, 2 * size + 1);
	}

	// If a stack grows and then shrinks, the objects that were popped off the stack
	// will not be garbage collected, even if the program using the stack has no
	// more references to them. This is because the stack maintains obsolete
	// references to these objects.
	// An obsolete reference is simply a reference that will never be dereferenced
	// again.

	// Corrected version of pop method (Page 27)
	public Object popCorrectedVersion() {
		if (size == 0)
			throw new EmptyStackException();
		Object result = elements[--size];
		// null out object references once they become obsolete.
		elements[size] = null; // Eliminate obsolete reference
		return result;
	}

	public static void main(String[] args) {
		Stack stack = new Stack();
		for (String arg : args)
			stack.push(arg);

		while (true)
			System.err.println(stack.pop());
	}
}
