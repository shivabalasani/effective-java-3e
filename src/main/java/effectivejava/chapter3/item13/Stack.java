package effectivejava.chapter3.item13;

import java.util.Arrays;

/**
 * 
 * If the clone method merely returns super.clone(), the resulting Stack
 * instance will have the correct value in its size field, but its elements
 * field will refer to the same array as the original Stack instance. Modifying
 * the original will destroy the invariants in the clone and vice versa.
 * 
 * This situation could never occur as a result of calling the sole constructor
 * in the Stack class. In effect, the clone method functions as a constructor;
 * you must ensure that it does no harm to the original object and that it
 * properly establishes invariants on the clone.
 * 
 * The Cloneable architecture is incompatible with normal use of final fields
 * referring to mutable objects, In order to make a class cloneable, it may be
 * necessary to remove final modifiers from some fields.
 * 
 * Public clone methods should omit the throws clause, as methods that don’t
 * throw checked exceptions are easier to use (Item 71).
 * 
 * A better approach to object copying is to provide a copy constructor or copy
 * factory.
 * 
 * // Copy constructor - public Yum(Yum yum) { ... };
 * 
 * // Copy factory - public static Yum newInstance(Yum yum) { ... };
 * 
 * The copy constructor approach and its static factory variant have many
 * advantages over Cloneable/clone: they don’t rely on a risk-prone
 * extralinguistic object creation mechanism; they don’t demand unenforceable
 * adherence to thinly documented conventions; they don’t conflict with the
 * proper use of final fields; they don’t throw unnecessary checked exceptions;
 * and they don’t require casts.
 * 
 * As a rule, copy functionality is best provided by constructors or factories.
 * A notable exception to this rule is arrays, which are best copied with the
 * clone method
 */

// A cloneable version of Stack (Pages 60-61)
public class Stack implements Cloneable {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
	this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
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

    // Clone method for class with references to mutable state
    @Override
    public Stack clone() {
	try {
	    Stack result = (Stack) super.clone();
	    result.elements = elements.clone();
	    return result;
	} catch (CloneNotSupportedException e) {
	    throw new AssertionError();
	}
    }

    // Ensure space for at least one more element.
    private void ensureCapacity() {
	if (elements.length == size)
	    elements = Arrays.copyOf(elements, 2 * size + 1);
    }

    // To see that clone works, call with several command line arguments
    public static void main(String[] args) {
	String[] strs = { "str1", "str2" };
	Stack stack = new Stack();
	for (String arg : strs)
	    stack.push(arg);
	Stack copy = stack.clone();
	while (!stack.isEmpty())
	    System.out.print(stack.pop() + " ");
	System.out.println();
	while (!copy.isEmpty())
	    System.out.print(copy.pop() + " ");
    }
}
