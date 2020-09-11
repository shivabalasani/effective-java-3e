package effectivejava.chapter5.item29;

import java.util.Arrays;

/**
 * 
 * Item 29: Favor generic types
 * 
 * In summary, generic types are safer and easier to use than types that require
 * casts in client code. When you design new types, make sure that they can be
 * used without such casts. This will often mean making the types generic. If
 * you have any existing types that should be generic but aren’t, generify them.
 * This will make life easier for new users of these types without breaking
 * existing clients (Item 26).
 *
 */

// Object-based collection - a prime candidate for generics
public class StackWithoutGenerics {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public StackWithoutGenerics() {
	elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
	ensureCapacity();
	elements[size++] = e;
    }

    public Object pop() {
	if (size == 0)
	    throw new EmptyStackException();
	Object result = elements[--size];
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
}