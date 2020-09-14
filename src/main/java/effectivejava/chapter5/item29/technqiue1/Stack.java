package effectivejava.chapter5.item29.technqiue1;

import java.util.Arrays;

import effectivejava.chapter5.item29.EmptyStackException;

/**
 * Stack.java:8: Cannot create a generic array of E if you do
 * 
 * elements = new E[DEFAULT_INITIAL_CAPACITY];
 * 
 * As explained in Item 28, you can’t create an array of a non-reifiable type,
 * such as E. This problem arises every time you write a generic type that is
 * backed by an array. There are two reasonable ways to solve it. The first
 * solution directly circumvents the prohibition on generic array creation:
 * create an array of Object and cast it to the generic array type. Now in place
 * of an error, the compiler will emit a warning. This usage is legal, but it’s
 * not (in general) typesafe:
 * 
 * Stack.java:8: warning: [unchecked] unchecked cast found: Object[], required:
 * E[] elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
 * 
 * The second way to eliminate the generic array creation error in Stack is to
 * change the type of the field elements from E[] to Object[]. see technique 2
 * 
 * The first technique is preferable and more commonly used in practice.It does,
 * however, cause heap pollution (Item 32): the runtime type of the array does
 * not match its compile-time type (unless E happens to be Object). This makes
 * some programmers sufficiently queasy that they opt for the second technique,
 * though the heap pollution is harmless in this situation.
 * 
 * You can create a Stack<Object>, Stack<int[]>, Stack<List<String>>, or Stack
 * of any other object reference type. Note that you can’t create a Stack of a
 * primitive type: trying to create a Stack<int> or Stack<double> will result in
 * a compile-time error.
 *
 */
// Generic stack using E[] (Pages 130-3)
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

	// Little program to exercise our generic Stack
	public static void main(String[] args) {
		String[] strs = { "stack", "with", "generics" };
		Stack<String> stack = new Stack<>();
		for (String arg : strs)
			stack.push(arg);
		while (!stack.isEmpty())
			System.out.println(stack.pop().toUpperCase());
	}
}
